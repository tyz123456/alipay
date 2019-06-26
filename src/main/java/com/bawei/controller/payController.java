package com.bawei.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.bawei.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bawei.config.AlipayConfig;
import com.bawei.service.PayService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Created by yazhou on 2019/6/23.
 */
@Controller
@RequestMapping("/pay")
public class payController {

    @Autowired
    private PayService payService;

    @RequestMapping("/createToken")
    @ResponseBody
    public ResultVo<Boolean> createToken(@RequestParam("userId") Long userId, @RequestParam("amout")BigDecimal amount){
        ResultVo<Boolean> token = payService.createToken(userId, amount);
        return token;
    }

    @RequestMapping("/submit")
    @ResponseBody
    public void toPay(String payToken, String money, HttpServletResponse response){
        ResultVo<String> stringResultVo = payService.toAliPayByToken(payToken);


        if (stringResultVo.getCode()!=0){

        }
        String form=stringResultVo.getData();

        try{
            response.setContentType("text/html;charset="+AlipayConfig.CHARSET);
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();

            // 这里和普通的接口调用不同，使用的是sdkExecute
            /*AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            orderString=alipayTradeAppPayResponse.getBody();*///就是orderString 可以直接给APP请求，无需再做处理。
            //     this.createAlipayMentOrder(alipaymentOrder);//创建新的商户支付宝订单*/
          /*  System.out.println(orderString);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @RequestMapping("/topay")
    public void toPay(String money, HttpServletResponse response) throws Exception{
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = "9C蛋炒饭";
            // 订单名称，必填
            String subject = new String("p2p充值".getBytes("ISO-8859-1"),"UTF-8");
            System.out.println(subject);
            // 付款金额，必填
            String total_amount = money;
            // 商品描述，可空
            String body = new String(("p2p充值" + money +"元").getBytes("ISO-8859-1"),"UTF-8");
            // 超时时间 可空
            String timeout_express="2m";
            // 销售产品码 必填
            String product_code="QUICK_WAP_WAY";
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
            AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
            model.setOutTradeNo(out_trade_no);
            model.setSubject(subject);
            model.setTotalAmount(total_amount);
            model.setBody(body);
            model.setTimeoutExpress(timeout_express);
            model.setProductCode(product_code);
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(AlipayConfig.notify_url);
            // 设置同步地址
            alipay_request.setReturnUrl(AlipayConfig.return_url);

            // form表单生产
            String form = "";
            try {
                // 调用SDK生成表单
                form = client.pageExecute(alipay_request).getBody();
                response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
                response.getWriter().write(form);//直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();
            } catch (AlipayApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
