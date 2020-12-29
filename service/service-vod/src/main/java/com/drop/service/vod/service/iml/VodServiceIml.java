package com.drop.service.vod.service.iml;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;
import com.drop.service.vod.service.VodService;
import com.drop.service.vod.utils.PropertiesUtil;
import com.drop.service.vod.utils.SDKUtil;
import com.drop.common.service_base.exceptionhandler.DropException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.*;
import com.aliyun.vod.upload.resp.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceIml implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        String videoId = null;
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            //去掉扩展名
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }


    @Override
    public boolean removeVideo(String videoId) {
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        try {
            DefaultAcsClient client = SDKUtil.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            return response != null;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DropException(20001, "删除视频失败");
        }

    }

    @Override
    public boolean removeBatch(List<String> videoList) {
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        try {
            DefaultAcsClient client = SDKUtil.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoIds = StringUtils.join(videoList.toArray(), ",");
            request.setVideoIds(videoIds);
            DeleteVideoResponse response = client.getAcsResponse(request);
            return response != null;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DropException(20001, "删除视频失败");
        }
    }

    @Override
    public String getPlayAuth(String id) {
        String accessKeyId = PropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = PropertiesUtil.ACCESS_KEY_SECRET;

        try {
            DefaultAcsClient client = SDKUtil.initVodClient(accessKeyId, accessKeySecret);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return response.getPlayAuth();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DropException(20001, "获取视频凭证失败");
        }

    }
}
