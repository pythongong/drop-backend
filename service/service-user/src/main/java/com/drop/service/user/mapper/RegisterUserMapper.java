package com.drop.service.user.mapper;

import com.drop.service.user.entity.RegisterUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
public interface RegisterUserMapper extends BaseMapper<RegisterUser> {

    Integer countRegister(String day);
}
