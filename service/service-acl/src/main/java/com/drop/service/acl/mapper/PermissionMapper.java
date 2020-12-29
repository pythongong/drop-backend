package com.drop.service.acl.mapper;

import com.drop.service.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2020-12-25
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectAllPermissionValue();

    List<String> selectPermissionValueByUserId(String id);

    List<Permission> selectPermissionByUserId(String userId);
}
