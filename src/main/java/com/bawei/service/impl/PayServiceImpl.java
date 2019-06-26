package com.bawei.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.bawei.config.AlipayConfig;
import com.bawei.entity.PaymentInfo;
import com.bawei.util.RedisUtil;
import com.bawei.vo.CodeMsg;
import com.bawei.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bawei.mapper.PayMapper;
import com.bawei.service.PayService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yazhou on 2019/6/24.
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultVo<Boolean> createToken(Long userId, BigDecimal amount) {
        String uuid = UUID.randomUUID().toString();
        PaymentInfo paymentInfo = new PaymentInfo(userId, new Date(), amount);
        int insert = payMapper.insert(paymentInfo);
        if (insert > 0){
            return ResultVo.success(true);
        }
        return ResultVo.error(CodeMsg.TOTAL_ERROR);
    }

    @Override
    public ResultVo<String> toAliPayByToken(String payToken) {
        try {
            Long paymenetInfoId = redisUtil.get(payToken, Long.class);
            if (paymenetInfoId == null) {
                return ResultVo.error(CodeMsg.TOTAL_ERROR);
            }
            PaymentInfo paymentInfo = payMapper.selectByPrimaryKey(paymenetInfoId);

            if (paymenetInfoId == null) {
                return ResultVo.error(CodeMsg.TOTAL_ERROR);
            }
            //String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // double random = Math.random();
            //System.out.println(str);
            String out_trade_no = paymenetInfoId+"";
            // 订单名称，必填
            String subject = new String("p2p充值".getBytes("ISO-8859-1"), "UTF-8");
            // 付款金额，必填

            String total_amount = new String(paymentInfo.getAmount().setScale(2, RoundingMode.HALF_UP).toString());
            /*// 商品描述，可空
            String desc = "用户"+paymentInfo.getCreateuserId()+"充值"+total_amount+"元";
            String body = new String(desc.getBytes("ISO-8859-1"),"UTF-8");
            // 超时时间 可空
            String timeout_express="2m";*/

            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            //AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
            AlipayTradePagePayRequest alipay_request = new AlipayTradePagePayRequest();
            // 封装请求支付信息
            //BizContent需要json字符串
            /**
             * out_trade_no
             * total_amount
             * subject
             * product_code:FAST_INSTANT_TRADE_PAY 即时到账
             */
            alipay_request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
                    + "\"," + "\"subject\":\"" + subject + "\","
                    // + "\"body\":\""+ body +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            // 设置异步通知地址
            alipay_request.setNotifyUrl(AlipayConfig.notify_url);
            // 设置同步地址
            alipay_request.setReturnUrl(AlipayConfig.return_url);

            // 调用SDK生成表单
            String form = client.pageExecute(alipay_request).getBody();
            return ResultVo.success(form);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return ResultVo.error(CodeMsg.TOTAL_ERROR);
        }
    }
}
