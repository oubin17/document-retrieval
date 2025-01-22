package com.odk.baseservice.impl.document;

import cn.dev33.satoken.stp.StpUtil;
import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DirectoryApi;
import com.odk.baseapi.request.document.DirSearchRequest;
import com.odk.baseapi.request.document.DirectoryCreateRequest;
import com.odk.baseapi.request.document.DirectoryUpdateRequest;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.model.file.DirectoryDO;
import com.odk.basedomain.repository.file.DirectoryRepository;
import com.odk.basemanager.deal.document.DirectoryManager;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.dto.document.DirSearchDTO;
import com.odk.baseutil.dto.document.DirectoryCreateDTO;
import com.odk.baseutil.dto.document.DirectoryUpdateDTO;
import com.odk.baseutil.entity.DirectoryEntity;
import com.odk.baseutil.enums.BizScene;
import com.odk.baseutil.enums.DirSearchTypeEnum;
import com.odk.baseutil.enums.DirectoryTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DirectoryService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/8
 */
@Service
public class DirectoryService extends AbstractApiImpl implements DirectoryApi {

    private DirectoryRepository directoryRepository;

    private DirectoryManager directoryManager;

    private OrganizationDomain organizationDomain;

    @Override
    public ServiceResponse<String> createDirectory(DirectoryCreateRequest directoryCreateRequest) {
        return super.executeProcess(BizScene.DIRECTORY_CREATE, directoryCreateRequest, new CallBack<String, String>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
                AssertUtil.isNotEmpty(directoryCreateRequest.getDirectoryName(), BizErrorCode.PARAM_ILLEGAL, "目录名称不为空");
            }

            @Override
            protected Object convert(Object request) {
                if (StringUtils.isEmpty(directoryCreateRequest.getParentId())) {
                    //如果传空，默认在根目录上传
                    directoryCreateRequest.setParentId("0");
                } else {
                    //校验父节点是否合法
                    DirectoryDO directoryDO = directoryRepository.findByIdAndDirectoryTypeAndStatus(directoryCreateRequest.getParentId(), DirectoryTypeEnum.FOLDER.getCode(), CommonStatusEnum.NORMAL.getCode());
                    AssertUtil.notNull(directoryDO, BizErrorCode.PARAM_ILLEGAL, "父节点非法");
                }
                DirectoryCreateDTO directoryCreateDTO = new DirectoryCreateDTO();
                BeanUtils.copyProperties(directoryCreateRequest, directoryCreateDTO);
                return directoryCreateDTO;
            }

            @Override
            protected String doProcess(Object args) {
                DirectoryCreateDTO request = (DirectoryCreateDTO) args;
                return directoryManager.createDirectory(request);
            }

            @Override
            protected String convertResult(String docId) {
                return docId;
            }
        });
    }


    @Override
    public ServiceResponse<Boolean> updateDirectory(DirectoryUpdateRequest updateRequest) {
        return super.executeProcess(BizScene.DIRECTORY_UPDATE, updateRequest, new CallBack<Boolean, Boolean>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
                AssertUtil.isNotEmpty(updateRequest.getId(), BizErrorCode.PARAM_ILLEGAL, "目录ID不为空");
                AssertUtil.isNotEmpty(updateRequest.getDirectoryName(), BizErrorCode.PARAM_ILLEGAL, "目录名称不为空");
            }

            @Override
            protected Object convert(Object request) {

                DirectoryUpdateDTO updateDTO = new DirectoryUpdateDTO();
                BeanUtils.copyProperties(updateRequest, updateDTO);
                return updateDTO;
            }

            @Override
            protected Boolean doProcess(Object args) {
                DirectoryUpdateDTO updateDTO = (DirectoryUpdateDTO) args;
                return directoryManager.updateDirectory(updateDTO);
            }

            @Override
            protected Boolean convertResult(Boolean docId) {
                return docId;
            }
        });
    }

    @Override
    public ServiceResponse<Boolean> deleteDirectory(String dirId) {
        return super.executeProcess(BizScene.DIRECTORY_DELETE, dirId, new CallBack<Boolean, Boolean>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
            }

            @Override
            protected Boolean doProcess(Object args) {
                return directoryManager.deleteDirectory(dirId);
            }

            @Override
            protected Boolean convertResult(Boolean result) {
                return result;
            }
        });
    }

    @Override
    public ServiceResponse<List<DirectoryEntity>> directoryTree(String orgId) {
        return super.executeProcess(BizScene.DIRECTORY_TREE, orgId, new CallBack<List<DirectoryEntity>, List<DirectoryEntity>>() {

            @Override
            protected Object convert(Object request) {
                return organizationDomain.checkAndReturnCurrentOrgId(orgId, StpUtil.getLoginIdAsString());
            }

            @Override
            protected List<DirectoryEntity> doProcess(Object args) {

                return directoryManager.directoryTree((String) args);
            }

            @Override
            protected List<DirectoryEntity> convertResult(List<DirectoryEntity> result) {
                return result;
            }
        });
    }

    @Override
    public ServiceResponse<List<DirectoryEntity>> directorySearch(DirSearchRequest dirSearchRequest) {
        return super.executeProcess(BizScene.DIRECTORY_SEARCH, dirSearchRequest, new CallBack<List<DirectoryEntity>, List<DirectoryEntity>>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(dirSearchRequest, BizErrorCode.PARAM_ILLEGAL);
                AssertUtil.notNull(DirSearchTypeEnum.getByCode(dirSearchRequest.getSearchType()), BizErrorCode.PARAM_ILLEGAL, "搜索类型异常");
            }

            @Override
            protected Object convert(Object request) {
                String currentOrgId = organizationDomain.checkAndReturnCurrentOrgId(dirSearchRequest.getOrgId(), StpUtil.getLoginIdAsString());
                dirSearchRequest.setOrgId(currentOrgId);
                DirSearchDTO dirSearchDTO = new DirSearchDTO();
                BeanUtils.copyProperties(dirSearchRequest, dirSearchDTO);
                return dirSearchDTO;
            }


            @Override
            protected List<DirectoryEntity> doProcess(Object args) {
                DirSearchDTO dirSearchDTO = (DirSearchDTO) args;

                if (StringUtils.isEmpty(dirSearchRequest.getKeyword())) {
                    return directoryManager.directoryTree(dirSearchRequest.getOrgId());
                } else {
                    return directoryManager.directorySearch(dirSearchDTO);
                }
            }

            @Override
            protected List<DirectoryEntity> convertResult(List<DirectoryEntity> result) {
                return result;
            }
        });
    }

    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Autowired
    public void setDirectoryManager(DirectoryManager directoryManager) {
        this.directoryManager = directoryManager;
    }

    @Autowired
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }
}
