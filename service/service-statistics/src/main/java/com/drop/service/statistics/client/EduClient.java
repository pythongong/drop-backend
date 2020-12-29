package com.drop.service.statistics.client;

import com.drop.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    @GetMapping("service-edu/teacher/countTeacherDaily/{day}")
    public R countTeacherDaily(@PathVariable String day);

    @GetMapping("service-edu/course/countCourseDaily/{day}")
    public R countCourseDaily(@PathVariable String day);
}
