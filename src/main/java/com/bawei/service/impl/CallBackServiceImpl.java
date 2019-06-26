package com.bawei.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.bawei.client.AccountClient;
import com.bawei.config.AlipayConfig;
import com.bawei.entity.PaymentInfo;
import com.bawei.mapper.PayMapper;
import com.bawei.service.CallBackService;
import com.bawei.util.BidConst;
import com.bawei.util.JsonUtil;
import com.bawei.vo.CodeMsg;
import com.bawei.vo.PayMsgVo;
import com.bawei.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yazhou on 2019/6/25.
 */
@Service
public class CallBackServiceImpl implements CallBackService {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private AccountClient accountClient;

    @Override
    public ResultVo<PayMsgVo> sysncCallBack(Map<String, String> params) {
        try {
            boolean rsaCheckV1 = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if (rsaCheckV1==false){
                return ResultVo.error(CodeMsg.TOTAL_ERROR);
            }

            String out_trade_no = params.get("out_trade_no");//商户订单号
            String trade_no = params.get("trade_no");//交易单号
            String total_amount = params.get("total_amount");

            PayMsgVo payMsgVo = new PayMsgVo();
            payMsgVo.setBizOrderId(out_trade_no);
            payMsgVo.setPayAmount(total_amount);
            payMsgVo.setPlatOrderId(trade_no);

            return ResultVo.success(payMsgVo);

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return ResultVo.error(CodeMsg.TOTAL_ERROR);
    }

    @Override
    public ResultVo<PayMsgVo> asysncCallBack(Map<String, String> alipayNotifyMap){
        try {
            boolean rsaCheckV1 = AlipaySignature.rsaCheckV1(alipayNotifyMap, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if (rsaCheckV1==false){
                return ResultVo.error(CodeMsg.TOTAL_ERROR);
            }

            String out_trade_no = alipayNotifyMap.get("out_trade_no");//商户订单号
            String trade_no = alipayNotifyMap.get("trade_no");//交易单号
            String total_amount = alipayNotifyMap.get("total_amount");

            Long paymentInfoId = Long.valueOf(out_trade_no);
            PaymentInfo paymentInfo = payMapper.selectByPrimaryKey(paymentInfoId);
            if (paymentInfo.getState()== BidConst.PAYMENT_STATE_DONE){
                return ResultVo.success(true);
            }
            Lock lock=new ReentrantLock();
            lock.lock();
            try {
                ResultVo<Boolean> booleanResultVo = accountClient.rechargeAccount(paymentInfo.getCreateuserId(), new BigDecimal(total_amount));
                if (booleanResultVo.getCode()!=0){
                    return ResultVo.error(CodeMsg.TOTAL_ERROR);
                }

                paymentInfo.setState(BidConst.PAYMENT_STATE_DONE);
                paymentInfo.setPayMessage(JsonUtil.toJson(alipayNotifyMap));
                paymentInfo.setUpdated(new Date());
                int i = payMapper.updateByPrimaryKey(paymentInfo);

                if (i<=0){

                }
            }finally {
                lock.unlock();
            }
            //return ResultVo.success(payMsgVo);
            return   ResultVo.success(true);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return ResultVo.error(CodeMsg.TOTAL_ERROR);
    }
}
