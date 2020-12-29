package com.drop.service.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drop.common.utils.R;
import com.drop.service.cms.entity.CrmBanner;
import com.drop.service.cms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-cms/banner-admin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    CrmBannerService bannerService;

    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<CrmBanner> pageParam = new Page<>(page, limit);
        bannerService.page(pageParam,null);
        return R.ok().data("items", pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }
    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R update(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("delete/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}
