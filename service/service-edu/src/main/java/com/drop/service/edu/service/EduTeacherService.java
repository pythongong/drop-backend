package com.drop.service.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.service.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getWebPages(Page<EduTeacher> pageTeacher, QueryWrapper<EduTeacher> wrapper);

    Integer countTeacherByDay(String day);
}
