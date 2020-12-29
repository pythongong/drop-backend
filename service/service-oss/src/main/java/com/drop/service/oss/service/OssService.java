package com.drop.service.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);

    String uploadFileCover(MultipartFile file);
}
