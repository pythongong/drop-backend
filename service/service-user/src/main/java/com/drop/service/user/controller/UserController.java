package com.drop.service.user.controller;


import com.drop.common.utils.JWTUtils;
import com.drop.common.utils.R;
import com.drop.service.user.entity.RegisterUser;
import com.drop.service.user.entity.vo.RegisterVo;
import com.drop.service.user.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/service-user")
public class UserController {

    @Autowired
    RegisterUserService userService;

    @PostMapping("login")
    public R login(@RequestBody RegisterUser user){
        String token  = userService.login(user);
        return R.ok().data("token", token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        String id = JWTUtils.getIdByJwtToken(request);
        RegisterUser user = userService.getById(id);
        return R.ok().data("userInfo", user);
    }

    @GetMapping(value = "countRegisterDaily/{day}")
    public R countRegisterDaily(
            @PathVariable String day){
        Integer count = userService.countRegisterByDay(day);
        return R.ok().data("registerNumDaily", count);
    }





}

