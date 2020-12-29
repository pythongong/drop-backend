package com.drop.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.common.utils.JWTUtils;
import com.drop.common.utils.MD5;
import com.drop.service.user.entity.RegisterUser;
import com.drop.service.user.entity.vo.RegisterVo;
import com.drop.service.user.mapper.RegisterUserMapper;
import com.drop.service.user.service.RegisterUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drop.common.service_base.exceptionhandler.DropException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
@Service
public class RegisterUserServiceImpl extends ServiceImpl<RegisterUserMapper, RegisterUser> implements RegisterUserService {
    @Override
    public String login(RegisterUser user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new DropException(20001, "用户名或密码为空");
        }

        QueryWrapper<RegisterUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        RegisterUser member = baseMapper.selectOne(wrapper);
        if (member == null) throw new DropException(20001, "用户不存在");

        if (! MD5.encrypt(password).equals(member.getPassword())){
            throw new DropException(20001, "密码错误");
        }

        String token = JWTUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {

        String username = registerVo.getUsername();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new DropException(20001, "用户名或密码为空");
        }

        QueryWrapper<RegisterUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        int count = baseMapper.selectCount(wrapper);

        if (count > 0) throw new DropException(20001, "用户已存在");

        password = MD5.encrypt(password);
        RegisterUser user = new RegisterUser();
        BeanUtils.copyProperties(registerVo, user);
        user.setPassword(password);
        baseMapper.insert(user);
    }

    @Override
    public Integer countRegisterByDay(String day) {

        return baseMapper.countRegister(day);
    }
}
