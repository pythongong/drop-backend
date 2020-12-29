package com.drop.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.common.service_base.exceptionhandler.DropException;
import com.drop.service.edu.entity.chapter.ChapterVo;
import com.drop.service.edu.entity.chapter.VideoVo;
import com.drop.service.edu.mapper.EduChapterMapper;
import com.drop.service.edu.entity.EduChapter;
import com.drop.service.edu.entity.EduVideo;
import com.drop.service.edu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drop.service.edu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;
    @Override
    public List<ChapterVo> getFrontChapterVideo(String courseId) {

        List<ChapterVo> chapterVoList = new ArrayList<>();

        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter>  chapterList = baseMapper.selectList(chapterQueryWrapper);

        for (EduChapter chapter : chapterList){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id",chapter.getId());
            List<EduVideo> videoList = videoService.list(videoQueryWrapper);
            for (EduVideo video : videoList) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);
                videoVoList.add(videoVo);
            }
            chapterVo.setVideos(videoVoList);
            chapterVoList.add(chapterVo);
        }

        return chapterVoList;
    }

    @Override
    public boolean delete(String id) {
        QueryWrapper<EduVideo> videoQuery = new QueryWrapper<>();
        videoQuery.eq("chapter_id", id);
        if( videoService.count(videoQuery) > 0) throw new DropException(20001,"请先删除小节");
        else {
            int res = baseMapper.deleteById(id);
            return res > 0;
        }
    }

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
