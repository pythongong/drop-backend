package com.drop.service.edu.entity.chapter;

import lombok.Data;

@Data
public class VideoVo {

    private String id;
    private String title;
    private String videoSourceId;

    private boolean free;
}
