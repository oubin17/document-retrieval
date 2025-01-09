package com.odk.baseservice.impl.document;

import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DirectoryApi;
import com.odk.baseapi.request.document.DirectoryCreateRequest;
import com.odk.basedomain.model.file.DirectoryDO;
import com.odk.basedomain.repository.file.DirectoryRepository;
import com.odk.basemanager.deal.document.DirectoryManager;
import com.odk.basemanager.dto.document.DirectoryCreateDTO;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.entity.DirectoryEntity;
import com.odk.baseutil.enums.BizScene;
import com.odk.baseutil.enums.DirectoryTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public ServiceResponse<String> createDirectory(DirectoryCreateRequest directoryCreateRequest) {
        return super.queryProcess(BizScene.DIRECTORY_CREATE, directoryCreateRequest, new QueryApiCallBack<String, String>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
                AssertUtil.isNotEmpty(directoryCreateRequest.getDirectoryName(), BizErrorCode.PARAM_ILLEGAL, "文件夹名称不为空");
            }

            @Override
            protected Object convert(Object request) {
                if (directoryCreateRequest.getParentId() == null) {
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
    public ServiceResponse<Boolean> deleteDirectory(String dirId) {
        return super.queryProcess(BizScene.DIRECTORY_DELETE, dirId, new QueryApiCallBack<Boolean, Boolean>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
                Optional<DirectoryDO> byId = directoryRepository.findById(dirId);
                AssertUtil.isTrue(byId.isPresent(), BizErrorCode.PARAM_ILLEGAL, "节点不存在");
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
    public ServiceResponse<List<DirectoryEntity>> directoryTree() {
        return super.queryProcess(BizScene.DIRECTORY_TREE, null, new QueryApiCallBack<List<DirectoryEntity>, List<DirectoryEntity>>() {
            @Override
            protected List<DirectoryEntity> doProcess(Object args) {
                return directoryManager.directoryTree();
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
}
