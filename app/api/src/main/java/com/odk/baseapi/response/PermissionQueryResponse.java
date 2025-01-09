package com.odk.baseapi.response;

import com.odk.baseutil.entity.RolePermissionEntity;
import com.odk.baseutil.entity.UserRoleEntity;
import lombok.Data;

import java.util.List;

/**
 * PermissionQueryResponse
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
@Data
public class PermissionQueryResponse {

    private String userId;

    private List<UserRoleEntity> roles;

    private List<RolePermissionEntity> permissions;
}
