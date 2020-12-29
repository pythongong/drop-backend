package com.drop.service.user.service;

import com.drop.service.user.entity.RegisterUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drop.service.user.entity.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
public interface RegisterUserService extends IService<RegisterUser> {

    String login(RegisterUser user);

    void register(RegisterVo registerVo);

    Integer countRegisterByDay(String day);
}
