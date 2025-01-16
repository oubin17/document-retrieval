package com.odk.basedomain.domain.inter;

import com.odk.baseutil.dto.document.DirSearchDTO;
import com.odk.baseutil.entity.DirectoryEntity;

import java.util.List;

/**
 * DirectoryDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
public interface DirectoryDomain {

    /**
     * 文件树
     *
     * @return
     */
    List<DirectoryEntity> directoryTree(String orgId);

    /**
     * 根据文件内容匹配
     *
     * @param dirSearchDTO
     * @return
     */
    List<DirectoryEntity> searchByFileContent(DirSearchDTO dirSearchDTO);

}
