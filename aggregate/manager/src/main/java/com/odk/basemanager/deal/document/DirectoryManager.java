package com.odk.basemanager.deal.document;

import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.basedomain.domain.inter.DirectoryDomain;
import com.odk.baseutil.entity.DirectoryEntity;
import com.odk.basedomain.model.file.DirectoryDO;
import com.odk.basedomain.repository.file.DirectoryRepository;
import com.odk.basemanager.dto.document.DirectoryCreateDTO;
import com.odk.baseutil.enums.DirectoryTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DirectoryManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@Slf4j
@Service
public class DirectoryManager {

    private DirectoryDomain directoryDomain;

    private DirectoryRepository directoryRepository;

    /**
     * 创建文件夹
     *
     * @param createDTO
     * @return
     */
    public String createDirectory(DirectoryCreateDTO createDTO) {
        if (null != createDTO.getParentId() && !StringUtils.equals(createDTO.getParentId(), "0")) {
            DirectoryDO directoryDO = directoryRepository.findByIdAndDirectoryTypeAndStatus(createDTO.getParentId(), DirectoryTypeEnum.FOLDER.getCode(), CommonStatusEnum.NORMAL.getCode());
            AssertUtil.notNull(directoryDO, BizErrorCode.PARAM_ILLEGAL, "父节点不存在");
        }
        DirectoryDO directoryDO = new DirectoryDO();
        directoryDO.setDirectoryName(createDTO.getDirectoryName());
        directoryDO.setDirectoryType(DirectoryTypeEnum.FOLDER.getCode());
        directoryDO.setParentId(createDTO.getParentId());
        directoryDO.setStatus(CommonStatusEnum.NORMAL.getCode());
        directoryDO = directoryRepository.save(directoryDO);
        return directoryDO.getId();

    }

    /**
     * 删除文件夹（同时删除子文件夹和文件）
     *
     * 这里只删除父文件夹，因为如果父节点删除了，子节点永远不会被查出来
     *
     * @param directoryId
     * @return
     */
    public boolean deleteDirectory(String directoryId) {
        int count = directoryRepository.updateStatus(directoryId, CommonStatusEnum.DELETE.getCode());
        return count > 0;
    }

    /**
     * 目录树
     *
     * @return
     */
    public List<DirectoryEntity> directoryTree() {
        return this.directoryDomain.directoryTree();
    }

    @Autowired
    public void setDirectoryDomain(DirectoryDomain directoryDomain) {
        this.directoryDomain = directoryDomain;
    }

    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }
}
