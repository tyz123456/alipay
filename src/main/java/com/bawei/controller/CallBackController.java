package com.bawei.controller;

import com.bawei.service.CallBackService;
import com.bawei.vo.PayMsgVo;
import com.bawei.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yazhou on 2019/6/25.
 */
@Controller
@RequestMapping("callback")
public class CallBackController {

    @Autowired
    private CallBackService callBackService;

    @RequestMapping("async_callback")
    @ResponseBody
    public String dealNotfyUrl(HttpServletRequest request)throws Exception{
        Map<String,String> alipayNotifyMap= getNotifyMap(request);
        System.out.println("-------------------------收到了异步通知-------------------");
        ResultVo<PayMsgVo> payMsgVoResultVo= callBackService.asysncCallBack(alipayNotifyMap);
        return "";
    }

    private Map<String,String> getNotifyMap(HttpServletRequest request) throws Exception{
        Map<String,String> params=new HashMap<String,String>();
        Map parameterMap = request.getParameterMap();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();){
            String name= (String) iterator.next();
            String[] values = (String[]) parameterMap.get(name);
            String valueStr = String.join(",", values);
            /*String valueStr="";
            for (int i = 0; i <values.length-1 ; i++) {
                valueStr=(i==values.length-1)?valueStr+values[i]:valueStr+values[i]+",";

            }*/
            //乱码解决

            valueStr= new String(valueStr.getBytes("ISO-8859-1"),"UTF-8");
            params.put(name,valueStr);
        }
        return params;
    }

    @RequestMapping("sync_callback")
    public void dealReturnUrl(HttpServletRequest request, HttpServletResponse response)throws Exception{
        Map<String,String> alipayNotifyMap= getNotifyMap(request);

        ResultVo<PayMsgVo> payMsgVoResultVo= callBackService.sysncCallBack(alipayNotifyMap);
        System.out.println("-------------------------收到了同步通知-------------------");

        if (payMsgVoResultVo.getCode()!=0){
            PrintWriter writer = response.getWriter();
            writer.print("支付失败了");
            writer.flush();
            writer.close();

        }else {
            response.setContentType("text/html");
            PayMsgVo data1 = payMsgVoResultVo.getData();
            String str="<form action='http://api.p2p.cn/payservice/callback/result' method='post' name='punchout_form'>\n" +
                    "<input type='hidden' name='bizOrderId' value='"+data1.getBizOrderId()+"'>" +
                    " <input type='hidden' name='platOrderId' value='"+data1.getPlatOrderId()+"'>" +
                    " <input type='hidden' name='payAmount' value='"+data1.getPayAmount()+"'>" +
                    " </form>" +
                    " <script>document.forms[0].submit();</script>";


            PrintWriter writer = response.getWriter();
            // String data="支付成功了  订单编号为："+payMsgVoResultVo.getData().getPlatOrderId();
            //data= new String(data.getBytes("ISO-8859-1"),"UTF-8");
            writer.print(str);
            writer.flush();
            writer.close();
        }

    }


}
