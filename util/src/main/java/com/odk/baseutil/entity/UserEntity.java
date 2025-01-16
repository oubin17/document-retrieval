package com.odk.baseutil.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * UserEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/4
 */
@Data
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7881070936696286996L;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户类型
     * {@link com.odk.base.enums.user.UserTypeEnum}
     */
    private String userType;

    /**
     * 用户状态
     * {@link com.odk.base.enums.user.UserStatusEnum}
     */
    private String userStatus;

    /**
     * 用户所关联的组织
     */
    private Set<String> orgIds;

    /**
     * 查询用户组织树
     */
    private OrganizationEntity organizationTree;


}
