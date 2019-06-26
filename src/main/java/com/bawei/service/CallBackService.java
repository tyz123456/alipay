package com.bawei.service;

import com.bawei.vo.PayMsgVo;
import com.bawei.vo.ResultVo;

import java.util.Map;

/**
 * Created by yazhou on 2019/6/25.
 */
public interface CallBackService {
    ResultVo<PayMsgVo> asysncCallBack(Map<String, String> alipayNotifyMap);

    ResultVo<PayMsgVo> sysncCallBack(Map<String, String> alipayNotifyMap);
}
