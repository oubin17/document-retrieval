package com.odk.basedomain.model.file;

import com.odk.base.dos.BaseDO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * FileSearchDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_file_search", indexes = {

})
@EntityListeners(AuditingEntityListener.class)
public class FileSearchDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 7924807458967180888L;

    /**
     * 文件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文档内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 组织id
     */
    @Column(name = "org_id")
    private String orgId;
}
