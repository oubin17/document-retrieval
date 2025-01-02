package com.odk.basedomain.domain.inter;

import com.odk.basedomain.model.es.DocumentDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * SearchDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public interface SearchDomain {

    Page<DocumentDO> searchByFileContentsContains(String keyword, Pageable pageable);
}
