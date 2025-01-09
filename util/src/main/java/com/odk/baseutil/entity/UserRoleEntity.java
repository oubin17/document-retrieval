package com.odk.baseutil.entity;

import lombok.Data;

/**
 * UserRoleEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/8
 */
@Data
public class UserRoleEntity {

    /**
     * 角色id
     */
    private String id;

    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态
     */
    private String status;
}
