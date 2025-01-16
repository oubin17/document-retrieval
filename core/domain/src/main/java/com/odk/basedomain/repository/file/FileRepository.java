package com.odk.basedomain.repository.file;

import com.odk.basedomain.model.file.FileDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FileRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public interface FileRepository extends JpaRepository<FileDO, String> {

    /**
     * 查询文本匹配
     *
     * @param fileName
     * @return
     */
    Page<FileDO> findByFileNameLike(String fileName, Pageable pageable);

    /**
     * 查询文件名称
     *
     * @param fileName
     * @param orgId
     * @return
     */
    List<FileDO> findByFileNameLikeAndOrgId(String fileName,String orgId);

    /**
     * 更新文件状态
     * @param id
     * @param status
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE FileDO d SET d.status = :status where d.id = :id")
    int updateFileStatus(@Param("id") String id, @Param("status") String status);

}
