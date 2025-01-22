package com.odk.baseweb.document;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DirectoryApi;
import com.odk.baseapi.request.document.DirSearchRequest;
import com.odk.baseapi.request.document.DirectoryCreateRequest;
import com.odk.baseapi.request.document.DirectoryUpdateRequest;
import com.odk.baseutil.entity.DirectoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DirController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@RestController
@RequestMapping("/dir")
public class DirController {

    private DirectoryApi directoryApi;

    /**
     * 创建目录
     *
     * @param directoryCreateRequest
     * @return
     */
    @SaCheckRole(value = {"ADMIN"})
    @PostMapping("/create")
    public ServiceResponse<String> createDirectory(@RequestBody DirectoryCreateRequest directoryCreateRequest) {
        return directoryApi.createDirectory(directoryCreateRequest);
    }

    /**
     * 更新目录
     *
     * @param updateRequest
     * @return
     */
    @SaCheckRole(value = {"ADMIN"})
    @PostMapping("/update")
    public ServiceResponse<Boolean> updateDirectory(@RequestBody DirectoryUpdateRequest updateRequest) {
        return directoryApi.updateDirectory(updateRequest);
    }

    /**
     * 删除目录
     *
     * @param dirId
     * @return
     */
    @SaCheckRole(value = {"ADMIN"})
    @DeleteMapping("/delete")
    public ServiceResponse<Boolean> deleteByFileName(@RequestParam("dirId") String dirId) {
        return directoryApi.deleteDirectory(dirId);
    }

    /**
     * 目录树
     *
     * @return
     */
    @GetMapping("/tree")
    public ServiceResponse<List<DirectoryEntity>> directoryTree(@RequestParam(value = "orgId", required = false) String orgId) {
        return directoryApi.directoryTree(orgId);
    }

    /**
     * 目录树
     *
     * @return
     */
    @PostMapping("/search")
    public ServiceResponse<List<DirectoryEntity>> directorySearch(@RequestBody DirSearchRequest dirSearchRequest) {
        return directoryApi.directorySearch(dirSearchRequest);
    }


    @Autowired
    public void setDirectoryApi(DirectoryApi directoryApi) {
        this.directoryApi = directoryApi;
    }
}
