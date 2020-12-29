package com.drop.service.cms.controller;

import com.drop.common.utils.R;
import com.drop.service.cms.entity.CrmBanner;
import com.drop.service.cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-cms/banner-front")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    CrmBannerService bannerService;


    @GetMapping("getAll")
    public R getAll(){
        List<CrmBanner> bannerList = bannerService.getBannerList();
        return R.ok().data("bannerList",bannerList);
    }


}
