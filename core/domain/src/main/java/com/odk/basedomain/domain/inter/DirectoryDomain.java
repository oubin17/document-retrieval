package com.odk.basedomain.domain.inter;

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
    List<DirectoryEntity> directoryTree();
}
