package com.drop.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.service.edu.entity.EduTeacher;
import com.drop.service.edu.mapper.EduTeacherMapper;
import com.drop.service.edu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getWebPages(Page<EduTeacher> pageParam, QueryWrapper<EduTeacher> wrapper) {
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageParam, wrapper);


        Map<String, Object> map = new HashMap<>();
        List<EduTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        //是否有下页
        boolean hasNext = pageParam.hasNext();
        //是否有上页
        boolean hasPrevious = pageParam.hasPrevious();


        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public Integer countTeacherByDay(String day) {

        return baseMapper.countTeacherByDay(day);
    }
}
