package com.drop.service.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.service.edu.entity.front.CourseWebVo;
import com.drop.service.edu.entity.front.FrontCourseVo;
import com.drop.service.edu.entity.vo.CourseInfo;
import com.drop.service.edu.entity.vo.PublishVo;
import com.drop.service.edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String id);

    void updateCourse(CourseInfo courseInfo);

    PublishVo getPublishInfo(String id);

    boolean removeCourse(String id);

    List<EduCourse> getCourseByTeacher(String id);

    Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, FrontCourseVo courseVo, QueryWrapper<EduCourse> wrapper);

    CourseWebVo getFrontCourseInfo(String courseId);

    Integer countCourseDaily(String day);
}
