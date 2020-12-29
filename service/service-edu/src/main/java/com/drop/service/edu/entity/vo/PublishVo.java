package com.drop.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private String name;
    private String description;
    private Integer lessonNum;
    private String firstSubject;
    private String secondSubject;
    private String price;//只用于显示
}
