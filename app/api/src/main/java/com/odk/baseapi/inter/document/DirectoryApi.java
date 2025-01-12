package com.odk.baseapi.inter.document;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.request.document.DirSearchRequest;
import com.odk.baseapi.request.document.DirectoryCreateRequest;
import com.odk.baseutil.entity.DirectoryEntity;

import java.util.List;

/**
 * DirectoryApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/8
 */
public interface DirectoryApi {

    /**
     * 创建文件夹
     *
     * @param directoryCreateRequest
     * @return
     */
    ServiceResponse<String> createDirectory(DirectoryCreateRequest directoryCreateRequest);

    /**
     * 根据id删除目录
     *
     * @param dirId
     * @return
     */
    ServiceResponse<Boolean> deleteDirectory(String dirId);

    /**
     * 目录树
     *
     * @return
     */
    ServiceResponse<List<DirectoryEntity>> directoryTree();

    /**
     * 文本内容
     *
     * @param dirSearchRequest
     * @return
     */
    ServiceResponse<List<DirectoryEntity>> directorySearch(DirSearchRequest dirSearchRequest);
}
