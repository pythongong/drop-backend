package com.drop.service.statistics.service;

import com.drop.service.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
public interface StatisticsService extends IService<StatisticsDaily> {

    void getRegister(String day);

    Map<String, Object> getChart(String type, String begin, String end);
}
