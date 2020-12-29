package com.drop.service.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.common.utils.R;
import com.drop.service.edu.entity.EduTeacher;
import com.drop.service.edu.entity.EduCourse;
import com.drop.service.edu.service.EduCourseService;
import com.drop.service.edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service-edu/teacher-front")

public class FrontTeacherController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        Map<String, Object> map = teacherService.getWebPages(pageTeacher, wrapper);
        return R.ok().data(map);
    }

    @GetMapping("getTeacher/{id}")
    public R getById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        List<EduCourse> courseList = courseService.getCourseByTeacher(id);
        return R.ok().data("teacher", teacher).data("courseList",courseList);
    }

}
