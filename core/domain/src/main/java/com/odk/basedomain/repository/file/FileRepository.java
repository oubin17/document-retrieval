package com.odk.basedomain.repository.file;

import com.odk.basedomain.domain.file.FileDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public interface FileRepository extends JpaRepository<FileDO, Long> {
}
