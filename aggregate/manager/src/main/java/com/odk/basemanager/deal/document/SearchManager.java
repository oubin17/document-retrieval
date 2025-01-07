package com.odk.basemanager.deal.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.domain.inter.SearchDomain;
import com.odk.basedomain.model.file.FileDO;
import com.odk.basedomain.model.file.FileSearchDO;
import com.odk.basedomain.repository.file.FileRepository;
import com.odk.basemanager.entity.SearchEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SearchManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@Service
public class SearchManager {

    private SearchDomain searchDomain;

    private FileRepository fileRepository;


    public PageResponse<SearchEntity> search(String keyword, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        PageResponse<FileSearchDO> documentDOS = searchDomain.searchByFileContentsContains(keyword, pageRequest);
        List<SearchEntity> collect = documentDOS.getPageList().stream().map(fileSearchDO -> {
            SearchEntity searchEntity = new SearchEntity();
            BeanUtils.copyProperties(fileSearchDO, searchEntity);
            Optional<FileDO> fileDOOptional = fileRepository.findById(fileSearchDO.getFileId());
            fileDOOptional.ifPresent(fileDO -> BeanUtils.copyProperties(fileDO, searchEntity));
            return searchEntity;
        }).collect(Collectors.toList());
        return PageResponse.of(collect, documentDOS.getCount());
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setSearchDomain(SearchDomain searchDomain) {
        this.searchDomain = searchDomain;
    }
}
