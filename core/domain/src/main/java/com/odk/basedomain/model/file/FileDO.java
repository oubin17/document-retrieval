package com.odk.basedomain.model.file;

import com.odk.base.dos.BaseDO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * FileDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_file", indexes = {
        @Index(name = "idx_file_name", columnList = "file_name")
})
@EntityListeners(AuditingEntityListener.class)
public class FileDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 7924807458967180888L;

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "com.odk.basedomain.idgenerate.CustomIDGenerator")
    private String id;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private String fileSize;

    /**
     * 文件全路径
     */
    @Column(name = "full_file_path")
    private String fullFilePath;

    /**
     * 文件状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 组织id
     */
    @Column(name = "org_id")
    private String orgId;
}
