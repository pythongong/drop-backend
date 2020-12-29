package com.drop.service.edu.mapper;

import com.drop.service.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2020-12-07
 */
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {

    Integer countTeacherByDay(String day);
}
