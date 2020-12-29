package com.drop.service.edu.controller;


import com.drop.common.utils.R;
import com.drop.service.edu.client.VodClient;
import com.drop.service.edu.entity.EduVideo;
import com.drop.service.edu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/service-edu/video")

public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("save")
    public R save(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }

    @PutMapping("update")
    public R update(@RequestBody EduVideo video){
        videoService.updateById(video);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        String videoId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoId)){
            vodClient.removeVideo(videoId);
        }

        boolean flag = videoService.removeById(id);
        if (flag) return R.ok();
        else return R.error();
    }
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("video", video);
    }
}

