package com.odk.basedomain.repository.file;

import com.odk.basedomain.model.file.FileDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public interface FileRepository extends JpaRepository<FileDO, Long> {

    /**
     * 查询文本匹配
     *
     * @param fileName
     * @return
     */
    Page<FileDO> findByFileNameLike(String fileName, Pageable pageable);

}
