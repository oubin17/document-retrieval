package com.odk.baseutil.entity;

import lombok.Data;

import java.util.List;

/**
 * OrganizationEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@Data
public class UserOrganizationEntity {

    private String userId;

    private String orgId;

    private String orgName;

    private String orgType;

    private String parentId;

    /**
     * 组织树结构
     */
    private List<OrganizationEntity> organizationEntities;
}
