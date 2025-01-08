package com.odk.baseutil.entity;

import lombok.Data;

/**
 * RolePermissionEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/8
 */
@Data
public class RolePermissionEntity {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限状态
     */
    private String status;
}
