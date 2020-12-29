package com.drop.service.edu.controller;


import com.drop.common.utils.R;
import com.drop.service.edu.entity.EduChapter;
import com.drop.service.edu.entity.chapter.ChapterVo;
import com.drop.service.edu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/service-edu/chapter")

public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("getAll/{courseId}")
    public R getAll(@PathVariable String courseId){
        List<ChapterVo> chapterVos = chapterService.getFrontChapterVideo(courseId);
        return R.ok().data("chapterList", chapterVos);
    }

    @PostMapping("save")
    public R save(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }

    @PutMapping("update")
    public R update(@RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id){
        boolean flag = chapterService.delete(id);
        if (flag) return R.ok();
        else return R.error();
    }
}

