package com.drop.service.edu.service;

import com.drop.service.edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String id);
}
