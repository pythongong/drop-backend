package com.drop.service.statistics.controller;


import com.drop.common.utils.R;
import com.drop.service.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/service-statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @PostMapping("getRegister/{day}")
    public R getRegister(@PathVariable String day) {
        statisticsService.getRegister(day);
        return R.ok();
    }

    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String
            end){
        Map<String, Object> map = statisticsService.getChart(type, begin, end);
        return R.ok().data(map);
    }

}

