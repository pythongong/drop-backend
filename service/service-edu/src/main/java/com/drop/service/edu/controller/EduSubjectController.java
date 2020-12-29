package com.drop.service.edu.controller;


import com.drop.common.utils.R;
import com.drop.service.edu.entity.subject.OneSubject;
import com.drop.service.edu.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程分类 前端控制器
 * </p>
 *
 * @author alex
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/service-edu/subject")

public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;
    //添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //1 获取上传的excel文件 MultipartFile
        //返回错误提示信息
        subjectService.importSubjectData(file,subjectService);
        //判断返回集合是否为空
        return R.ok();
    }

    @GetMapping("getAllSubjects")
    public R getAll(){
        List<OneSubject> list = subjectService.getSubjects();
        return R.ok().data("list",list);
    }
}

