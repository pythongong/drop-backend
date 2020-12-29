package com.drop.service.cms.service;

import com.drop.service.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-15
 */
public interface CrmBannerService extends IService<CrmBanner> {


    List<CrmBanner> getBannerList();
}
