package com.drop.service.edu.service;

import com.drop.service.edu.entity.chapter.ChapterVo;
import com.drop.service.edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
public interface EduChapterService extends IService<EduChapter> {


    boolean delete(String id);

    void removeByCourseId(String id);

    List<ChapterVo> getFrontChapterVideo(String courseId);
}
