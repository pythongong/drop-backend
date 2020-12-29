package com.drop.service.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.common.utils.R;
import com.drop.service.edu.entity.chapter.ChapterVo;
import com.drop.service.edu.entity.front.CourseWebVo;
import com.drop.service.edu.entity.front.FrontCourseVo;
import com.drop.service.edu.entity.EduCourse;
import com.drop.service.edu.service.EduChapterService;
import com.drop.service.edu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service-edu/course-front")

public class FrontCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) FrontCourseVo courseVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        Map<String,Object> map = courseService.getFrontCourseList(pageCourse,courseVo, wrapper);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getFrontCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getFrontChapterVideo(courseId);

        return R.ok().data("courseWeb",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}
