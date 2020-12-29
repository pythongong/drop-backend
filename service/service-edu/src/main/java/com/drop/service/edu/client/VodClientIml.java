package com.drop.service.edu.client;

import com.drop.common.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientIml implements VodClient{
    @Override
    public R removeVideo(String id) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除视频失败");
    }
}
