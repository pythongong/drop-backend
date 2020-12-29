package com.drop.service.edu.mapper;

import com.drop.service.edu.entity.front.CourseWebVo;
import com.drop.service.edu.entity.vo.PublishVo;
import com.drop.service.edu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishVo getPublishInfo(String courseId);

    CourseWebVo getFrontCourseInfo(String courseId);

    Integer countCourseDaily(String day);
}
