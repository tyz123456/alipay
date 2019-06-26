package com.bawei.service;

import com.bawei.vo.ResultVo;

import java.math.BigDecimal;

/**
 * Created by yazhou on 2019/6/24.
 */
public interface PayService {
    ResultVo<Boolean> createToken(Long userId, BigDecimal amount);

    ResultVo<String> toAliPayByToken(String payToken);
}
