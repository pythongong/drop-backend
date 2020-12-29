package com.drop.service.vod.controller;

import com.drop.common.utils.R;

import com.drop.service.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/service-vod/video")

public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public R upload(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id){
        boolean flag = vodService.removeVideo(id);
        if (flag) return R.ok();
        else return R.error();
    }

    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList){
        boolean flag = vodService.removeBatch(videoList);
        if (flag) return R.ok();
        else return R.error();
    }

    @GetMapping("getAuth/{id}")
    public R getAuth(@PathVariable String id){
        String playAuth = vodService.getPlayAuth(id);
        return R.ok().data("playAuth", playAuth);
    }
}
