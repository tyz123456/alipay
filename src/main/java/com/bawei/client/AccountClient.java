package com.bawei.client;

import com.bawei.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * Created by yazhou on 2019/6/25.
 */
@RequestMapping("account")
@FeignClient(name = "p2p-bid-service")
public interface AccountClient {

    @RequestMapping("recharge")
    public ResultVo<Boolean> rechargeAccount(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount);
}
