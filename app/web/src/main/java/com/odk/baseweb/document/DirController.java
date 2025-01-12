package com.odk.baseweb.document;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DirectoryApi;
import com.odk.baseapi.request.document.DirSearchRequest;
import com.odk.baseapi.request.document.DirectoryCreateRequest;
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
     * 创建文件夹
     *
     * @param directoryCreateRequest
     * @return
     */
    @PostMapping("/create")
    public ServiceResponse<String> queryByFileName(@RequestBody DirectoryCreateRequest directoryCreateRequest) {
        return directoryApi.createDirectory(directoryCreateRequest);
    }

    /**
     * 删除文件夹
     *
     * @param dirId
     * @return
     */
    @DeleteMapping("/delete")
    public ServiceResponse<Boolean> queryByFileName(@RequestParam("dirId") String dirId) {
        return directoryApi.deleteDirectory(dirId);
    }

    /**
     * 目录树
     *
     * @return
     */
    @GetMapping("/tree")
    public ServiceResponse<List<DirectoryEntity>> directoryTree() {
        return directoryApi.directoryTree();
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
