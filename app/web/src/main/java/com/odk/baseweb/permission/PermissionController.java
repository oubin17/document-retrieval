package com.odk.baseweb.permission;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.permission.PermissionApi;
import com.odk.baseapi.request.role.RoleAddRequest;
import com.odk.baseapi.request.role.UserRoleRelaRequest;
import com.odk.baseapi.response.PermissionQueryResponse;
import com.odk.baseutil.enums.InnerRoleEnum;
import org.springframework.web.bind.annotation.*;

/**
 * PermissionController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionApi permissionApi;

    public PermissionController(PermissionApi permissionApi) {
        this.permissionApi = permissionApi;
    }


    /**
     * 查询当前用户角色权限
     *
     * @return
     */
    @GetMapping("/info")
    public ServiceResponse<PermissionQueryResponse> currentUserPermission() {
        return permissionApi.userPermission(StpUtil.getLoginIdAsString());
    }

    /**
     * 查询用户角色权限
     *
     * @param userId
     * @return
     */
    @SaCheckRole(value = {"ADMIN", "SUPER-ADMIN"}, mode = SaMode.OR)
    @GetMapping("/userId")
    public ServiceResponse<PermissionQueryResponse> queryUserPermission(@RequestParam("userId") String userId) {
        return permissionApi.userPermission(userId);
    }

    /**
     * 添加角色
     *
     * @param roleAddRequest
     * {@link InnerRoleEnum}
     * @return
     */
    @SaCheckRole(value = {"SUPER-ADMIN"})
    @PostMapping("/role/add")
    public ServiceResponse<String> addRole(@RequestBody RoleAddRequest roleAddRequest) {
        return permissionApi.addRole(roleAddRequest);
    }

    /**
     * 用户-角色绑定
     *
     * @param relaRequest
     * @return
     */
    @SaCheckRole(value = {"SUPER-ADMIN"})
    @PostMapping("/role/rela/add")
    public ServiceResponse<String> addRoleRel(@RequestBody UserRoleRelaRequest relaRequest) {
        return permissionApi.addRoleRela(relaRequest);
    }
}
