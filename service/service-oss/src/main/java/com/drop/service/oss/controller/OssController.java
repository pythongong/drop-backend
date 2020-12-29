package com.drop.service.oss.controller;

import com.drop.common.utils.R;
import com.drop.service.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/service-oss/file")

public class OssController {

    @Autowired
    private OssService ossService;
    //上传头像的方法
    @PostMapping("avatar")
    public R uploadOssAvatar(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

    @PostMapping("cover")
    public R uploadOssCover(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileCover(file);
        return R.ok().data("url",url);
    }


}
