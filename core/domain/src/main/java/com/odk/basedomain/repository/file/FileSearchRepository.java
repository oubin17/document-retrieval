package com.odk.basedomain.repository.file;

import com.odk.basedomain.model.file.FileSearchDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * FileSearchRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
public interface FileSearchRepository extends JpaRepository<FileSearchDO, String> {

    /**
     * 根据关键字查找
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Query(value = "select * from t_file_search  WHERE MATCH(file_name, content) AGAINST(:keyword IN NATURAL LANGUAGE MODE) limit :pageNo, :pageSize", nativeQuery = true)
    List<FileSearchDO> findByCondition(@Param("keyword") String keyword, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 匹配数量
     *
     * @param keyword
     * @return
     */
    @Query(value = "select count(1) from t_file_search WHERE MATCH(file_name, content) AGAINST(:keyword IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    int conditionCount(@Param("keyword") String keyword);

    /**
     * 根据文件id删除
     *
     * @param fileId
     * @return
     */
    int deleteByFileId(String fileId);
}
