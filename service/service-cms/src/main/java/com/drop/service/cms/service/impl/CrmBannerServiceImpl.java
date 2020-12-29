package com.drop.service.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.service.cms.entity.CrmBanner;
import com.drop.service.cms.mapper.CrmBannerMapper;
import com.drop.service.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-15
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'getBannerList'")
    @Override
    public List<CrmBanner> getBannerList() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        //降序排列
        wrapper.orderByDesc("id");
        //拼接SQL语句
        wrapper.last("limit 2");
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}
