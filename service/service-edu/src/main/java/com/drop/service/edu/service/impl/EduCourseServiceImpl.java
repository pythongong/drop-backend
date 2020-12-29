package com.drop.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.common.service_base.exceptionhandler.DropException;
import com.drop.service.edu.entity.front.CourseWebVo;
import com.drop.service.edu.entity.front.FrontCourseVo;
import com.drop.service.edu.entity.vo.CourseInfo;
import com.drop.service.edu.entity.vo.PublishVo;
import com.drop.service.edu.entity.EduCourse;
import com.drop.service.edu.entity.EduCourseDescription;
import com.drop.service.edu.mapper.EduCourseMapper;
import com.drop.service.edu.service.EduChapterService;
import com.drop.service.edu.service.EduCourseDescriptionService;
import com.drop.service.edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drop.service.edu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Override
    public CourseWebVo getFrontCourseInfo(String courseId) {

        return baseMapper.getFrontCourseInfo(courseId);
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageParam, FrontCourseVo courseVo, QueryWrapper<EduCourse> wrapper) {
        if(!StringUtils.isEmpty(courseVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public String saveCourse(CourseInfo courseInfo) {

        ////将课程表单的信息传入课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0){
            return "";
        }

        //将课程表单的信息传入课程描述，注意要用课程表的id
        String id = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(courseInfo.getDescription());
        boolean result = descriptionService.save(courseDescription);
        if(! result) {
            throw new DropException(20001,"查询课程详情失败...");
        }
        return id;
    }

    @Override
    public void updateCourse(CourseInfo courseInfo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfo, course);
        int res = baseMapper.updateById(course);
        if(res <= 0) throw new DropException(20001,"课程修改失败");

        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfo.getDescription());
        description.setId(courseInfo.getId());
        descriptionService.updateById(description);

    }



    @Override
    public CourseInfo getCourseInfo(String id) {

        CourseInfo courseInfo = new CourseInfo();
        EduCourse course = baseMapper.selectById(id);
        if (course == null) throw new DropException(20001,"课程不存在");

        BeanUtils.copyProperties(course,courseInfo);

        EduCourseDescription description = new EduCourseDescription();
        QueryWrapper<EduCourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        int count = descriptionService.count(wrapper);

        if(count > 0) {
            EduCourseDescription courseDescription = descriptionService.getById(id);
        }
        else{

            description.setId(id);
            descriptionService.save(description);
        }
        courseInfo.setDescription(description.getDescription());


        return courseInfo;
    }

    @Override
    public PublishVo getPublishInfo(String id) {

        return baseMapper.getPublishInfo(id);
    }

    @Override
    public boolean removeCourse(String id) {
        videoService.removeByCourseId(id);
        chapterService.removeByCourseId(id);
        descriptionService.removeById(id);

        int res = baseMapper.deleteById(id);
        return res > 0;
    }

    @Override
    public List<EduCourse> getCourseByTeacher(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public Integer countCourseDaily(String day) {
        return baseMapper.countCourseDaily(day);
    }
}
