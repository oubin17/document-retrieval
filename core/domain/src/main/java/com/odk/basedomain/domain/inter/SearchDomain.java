package com.odk.basedomain.domain.inter;

import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.model.file.FileSearchDO;
import org.springframework.data.domain.Pageable;

/**
 * SearchDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public interface SearchDomain {

    PageResponse<FileSearchDO> searchByFileContentsContains(String keyword, Pageable pageable);
}
