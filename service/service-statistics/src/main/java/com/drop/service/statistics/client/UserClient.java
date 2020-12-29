package com.drop.service.statistics.client;

import com.drop.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-user")
public interface UserClient {

    @GetMapping(value = "/service-user/countRegisterDaily/{day}")
    public R countRegister(@PathVariable("day") String day);
}
