package com.odk.baseservice.impl.permission;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.permission.PermissionApi;
import com.odk.baseapi.request.role.RoleAddRequest;
import com.odk.baseapi.request.role.UserRoleRelaRequest;
import com.odk.baseapi.response.PermissionQueryResponse;
import com.odk.baseapi.vo.PermissionVO;
import com.odk.baseapi.vo.UserRoleVo;
import com.odk.basemanager.deal.permission.PermissionManager;
import com.odk.basemanager.entity.PermissionEntity;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.enums.BizScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PermissionService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/9
 */
@Service
public class PermissionService extends AbstractApiImpl implements PermissionApi {

    private PermissionManager permissionManager;


    @Override
    public ServiceResponse<PermissionQueryResponse> userPermission(Long userId) {
        return super.queryProcess(BizScene.USER_PERMISSION_QUERY, userId, new QueryApiCallBack<PermissionEntity, PermissionQueryResponse>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL, "userId is null.");
            }

            @Override
            protected PermissionEntity doProcess(Object args) {
                return permissionManager.getAllPermissions(userId);
            }

            @Override
            protected PermissionQueryResponse convertResult(PermissionEntity permissionEntity) {
                if (null == permissionEntity) {
                    return null;
                }
                PermissionQueryResponse permissionQueryResponse = new PermissionQueryResponse();
                permissionQueryResponse.setUserId(permissionEntity.getUserId());
                List<UserRoleVo> roles = permissionEntity.getRoles().stream().map(userRoleDO -> {
                    UserRoleVo userRoleVo = new UserRoleVo();
                    userRoleVo.setId(userRoleDO.getId());
                    userRoleVo.setRoleCode(userRoleDO.getRoleCode());
                    userRoleVo.setRoleName(userRoleDO.getRoleName());
                    userRoleVo.setStatus(userRoleDO.getStatus());
                    return userRoleVo;
                }).collect(Collectors.toList());
                permissionQueryResponse.setRoles(roles);
                List<PermissionVO> permissions = permissionEntity.getPermissions().stream().map(permissionDO -> {
                    PermissionVO permissionVO = new PermissionVO();
                    permissionVO.setId(permissionDO.getId());
                    permissionVO.setPermissionCode(permissionDO.getPermissionCode());
                    permissionVO.setPermissionName(permissionDO.getPermissionName());
                    permissionVO.setStatus(permissionDO.getStatus());
                    return permissionVO;
                }).collect(Collectors.toList());
                permissionQueryResponse.setPermissions(permissions);
                return permissionQueryResponse;
            }

        });
    }

    @Override
    public ServiceResponse<Long> addRole(RoleAddRequest roleAddRequest) {
        return super.queryProcess(BizScene.USER_ROLE_ADD, roleAddRequest, new QueryApiCallBack<Long, Long>() {

            @Override
            protected void checkParams(Object request) {
                RoleAddRequest addRequest = (RoleAddRequest) request;
                AssertUtil.notNull(addRequest.getRoleCode(), BizErrorCode.PARAM_ILLEGAL, "roleCode不为空");
                AssertUtil.notNull(addRequest.getRoleName(), BizErrorCode.PARAM_ILLEGAL, "roleName不为空");
            }

            @Override
            protected Object convert(Object request) {
                RoleAddRequest addRequest = (RoleAddRequest) request;
                return new String[]{addRequest.getRoleCode(), addRequest.getRoleName()};
            }

            @Override
            protected Long doProcess(Object args) {
                String[] args1 = (String[]) args;
                return permissionManager.addRole(args1[0], args1[1]);
            }

            @Override
            protected Long convertResult(Long roleId) {
                return roleId;
            }

        });
    }


    @Override
    public ServiceResponse<Long> addRoleRela(UserRoleRelaRequest relaRequest) {
        return super.queryProcess(BizScene.ROLE_RELA_ADD, relaRequest, new QueryApiCallBack<Long, Long>() {

            @Override
            protected void checkParams(Object request) {
                UserRoleRelaRequest roleRelaRequest = (UserRoleRelaRequest) request;
                AssertUtil.notNull(roleRelaRequest.getRoleId(), BizErrorCode.PARAM_ILLEGAL, "roleId 不为空");
                AssertUtil.notNull(roleRelaRequest.getUserId(), BizErrorCode.PARAM_ILLEGAL, "userId 不为空");
            }

            @Override
            protected Long doProcess(Object args) {
                UserRoleRelaRequest roleRelaRequest = (UserRoleRelaRequest) args;
                return permissionManager.addUserRoleRela(roleRelaRequest.getRoleId(), roleRelaRequest.getUserId());
            }

            @Override
            protected Long convertResult(Long id) {
                return id;
            }

        });
    }

    @Autowired
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }
}
