package com.drop.service.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {


    String uploadVideo(MultipartFile file);

    boolean removeVideo(String id);

    boolean removeBatch(List<String> videoList);

    String getPlayAuth(String id);
}
