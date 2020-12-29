package com.drop.service.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.common.utils.R;
import com.drop.service.edu.entity.EduTeacher;
import com.drop.service.edu.entity.EduCourse;
import com.drop.service.edu.service.EduCourseService;
import com.drop.service.edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service-edu/front")

public class FrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @Cacheable(value = "index", key = "'getIndex'")
    @GetMapping("index")
    public R getIndex(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(null);

        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(null);

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
