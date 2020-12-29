package com.drop.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.service.edu.client.VodClient;
import com.drop.service.edu.entity.EduVideo;
import com.drop.service.edu.mapper.EduVideoMapper;
import com.drop.service.edu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);

        List<String> videoIdList = new ArrayList<>();
        for(EduVideo video : videoList){
            String videoId = video.getVideoSourceId();
            if(! StringUtils.isEmpty(videoId)){
                videoIdList.add(videoId);
            }
        }

        if (videoIdList.size() > 0) vodClient.deleteBatch(videoIdList);

        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", id);
        baseMapper.delete(wrapper2);
    }
}
