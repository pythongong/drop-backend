package com.drop.service.statistics.service.impl;

import com.alibaba.nacos.client.naming.utils.RandomUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.service.statistics.client.EduClient;
import com.drop.service.statistics.client.UserClient;
import com.drop.service.statistics.entity.StatisticsDaily;
import com.drop.service.statistics.mapper.StatisticsDailyMapper;
import com.drop.service.statistics.service.StatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private EduClient eduClient;

    @Override
    public void getRegister(String day) {
        //删除已存在的统计对象
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);

        //获取统计信息
        Integer registerNum = (Integer) userClient.countRegister(day).getData().get("registerNumDaily");
        Integer teacherNum =  (Integer)  eduClient.countTeacherDaily(day).getData().get("teacherNumDaily");
        Integer courseNum = (Integer) eduClient.countCourseDaily(day).getData().get("courseNumDaily");//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setTeacherNum(teacherNum);
        daily.setCourseNum(courseNum);
        //统计日期
        daily.setDateCalculated(day);

        baseMapper.insert(daily);

    }

    @Override
    public Map<String, Object> getChart(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select(type, "date_calculated");
        //小于等于
        wrapper.between("date_calculated",begin, end);
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        //list对应前端的json数组
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        Map<String, Object> map = new HashMap<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        for(StatisticsDaily daily : dailyList){
            dateList.add(daily.getDateCalculated());
            switch (type){
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "teacher_num":
                    dataList.add(daily.getTeacherNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
