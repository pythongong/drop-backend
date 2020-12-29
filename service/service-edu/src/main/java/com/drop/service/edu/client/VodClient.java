package com.drop.service.edu.client;

import com.drop.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodClientIml.class)
@Component
public interface VodClient {
    @DeleteMapping("/service-vod/video/delete/{id}")
    R removeVideo(@PathVariable String id);

    @DeleteMapping("/service-vod/video/deleteBatch")
    R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
