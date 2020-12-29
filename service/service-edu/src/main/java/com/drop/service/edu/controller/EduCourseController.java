package com.drop.service.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.common.utils.R;
import com.drop.service.edu.entity.EduCourse;
import com.drop.service.edu.entity.vo.CourseInfo;
import com.drop.service.edu.entity.vo.CourseQuery;
import com.drop.service.edu.entity.vo.PublishVo;
import com.drop.service.edu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/service-edu/course")

public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("add")
    public R add(@RequestBody CourseInfo courseInfo){
        String courseId = courseService.saveCourse(courseInfo);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId", courseId);
        }
        else return R.error().message("增加课程失败");
    }

    @GetMapping("getAll")
    public R getAll(){
        List<EduCourse> courseList = courseService.list(null);
        return R.ok().data("list",courseList);
    }

    @GetMapping("page/{current}/{limit}")
    public R getPage(@PathVariable long current,
                     @PathVariable long limit, CourseQuery query){
        Page<EduCourse> page = new Page<>(current,limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String name = query.getName();
        String begin = query.getBegin();
        String end = query.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }


        //排序
        wrapper.orderByDesc("gmt_create");
        courseService.page(page,wrapper);

        long total = page.getTotal();
        List<EduCourse> courseList = page.getRecords();
        return R.ok().data("list", courseList).data("total",total);
    }

    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        CourseInfo courseInfo = courseService.getCourseInfo(id);
        return R.ok().data("courseInfo", courseInfo);
    }

    @PutMapping("update")
    public R update(@RequestBody CourseInfo courseInfo){
       courseService.updateCourse(courseInfo);
       return R.ok();
    }

    @GetMapping("getPublishInfo/{id}")
    public R getPublish(@PathVariable String id){
        PublishVo publishVo = courseService.getPublishInfo(id);
        return R.ok().data("publishInfo", publishVo);
    }

    @PutMapping("publish/{id}")
    public R publish(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id){

        boolean flag = courseService.removeCourse(id);
        if (flag) return R.ok();
        else return R.error();
    }

    @GetMapping("countCourseDaily/{day}")
    public R countCourseDaily(@PathVariable String day){
        Integer count = courseService.countCourseDaily(day);
        return R.ok().data("courseNumDaily", count);
    }
}

