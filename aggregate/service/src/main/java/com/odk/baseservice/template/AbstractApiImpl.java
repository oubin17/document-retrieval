package com.odk.baseservice.template;

import com.alibaba.fastjson.JSON;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.BaseResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseutil.constext.ServiceContextHolder;
import com.odk.baseutil.enums.BizScene;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * AbstractApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/4
 */
@Slf4j
public class AbstractApiImpl extends AbstractApi {

    /**
     * summary log
     */
    private static final String SEP = ",";

    private static final String NULL_REPLACE = "-";

    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";

    /**
     * 通用服务处理模板
     * 1.规定入参出参需要满足的条件；
     * 2.规定对象在service层和controller层的不同父类；
     *
     * @param bizScene
     * @param object
     * @param callBack
     * @param <T>
     * @return
     */
    protected <T, R> ServiceResponse<R> queryProcess(BizScene bizScene, Object object, QueryApiCallBack<T, R> callBack) {
        long startTime = System.currentTimeMillis();
        log.info(buildRequestDigestLog(bizScene, object, ServiceContextHolder.getUserId()));
        ServiceResponse<R> response = null;
        try {
            //1. 初始化上下文
            initContext(bizScene);
            //2.简单参数校验
            //3.参数校验
            callBack.checkParams(object);
            //5.对象转换：request -> dto
            Object args = callBack.convert(object);
            //5.对象转换：request -> dto
            T apiResponse = callBack.doProcess(args);
            //6.出参转换：dto -> response
            response = callBack.assembleResult(apiResponse);
            log.info(buildResponseDigestLog(bizScene, response, ServiceContextHolder.getUserId()));
        } catch (BizException exception) {
            response = handleBizException(exception);
        } catch (Throwable t) {
            response = handleSystemException(t);
        } finally {
            long executeTime = System.currentTimeMillis() - startTime;
            callBack.afterProcess(response);
            if (null != response) {
                log.info(buildDigestLog(bizScene, response.isSuccess(), response.getErrorCode(), executeTime, ServiceContextHolder.getUserId()));
            }
            clearContext();
        }
        return response;
    }

    /**
     * 通用服务处理模板
     * 1.规定入参出参需要满足的条件；
     * 2.规定对象在service层和controller层的不同父类；
     *
     * @param bizScene
     * @param request
     * @param callBack
     * @param <T>
     * @param <R>
     * @return
     */
    protected <T, R> ServiceResponse<R> bizProcess(BizScene bizScene, BaseRequest request, ApiCallBack<T, R> callBack) {
        long startTime = System.currentTimeMillis();
        log.info(buildRequestDigestLog(bizScene, request, ServiceContextHolder.getUserId()));
        ServiceResponse<R> response = null;
        try {
            //1. 初始化上下文
            initContext(bizScene);
            //2.简单参数校验
            AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL, "request is null.");
            //3.参数校验
            callBack.checkParams(request);
            //4.执行业务逻辑前置操作，如参数转换，复杂参数检查
            callBack.beforeProcess(request);
            //5.对象转换：request -> dto
            Object args = callBack.convert(request);
            T apiResponse = callBack.doProcess(args);
            //6.出参转换：dto -> response
            response = callBack.assembleResult(apiResponse);
            log.info(buildResponseDigestLog(bizScene, response, ServiceContextHolder.getUserId()));
        } catch (BizException exception) {
            response = handleBizException(exception);
        } catch (Throwable t) {
            response = handleSystemException(t);
        } finally {
            long executeTime = System.currentTimeMillis() - startTime;
            callBack.afterProcess(response);
            if (null != response) {
                log.info(buildDigestLog(bizScene, response.isSuccess(), response.getErrorCode(), executeTime, ServiceContextHolder.getUserId()));
            }
            clearContext();
        }
        return response;
    }

    /**
     * 查询统一处理模板
     *
     * @param <T>
     */
    public abstract static class QueryApiCallBack<T, R> {

        /**
         * 基本参数校验
         *
         * @param request
         */
        protected void checkParams(Object request) {
        }

        /**
         * 参数转换：VO -> DTO
         *
         * @param request
         * @return
         */
        protected Object convert(Object request) {
            return request;
        }

        /**
         * 核心业务处理
         *
         * @param args
         * @return
         */
        protected abstract T doProcess(Object args);

        /**
         * 将服务层返回的对象转成controller层返回的对象:DTO -> VO
         *
         * @param apiResult
         * @return
         * @throws Throwable
         */
        protected ServiceResponse<R> assembleResult(T apiResult) throws Throwable {
            ServiceResponse<R> serviceResponse = ServiceResponse.valueOfSuccess();
            try {
                serviceResponse.setData(convertResult(apiResult));
            } catch (Exception e) {
                log.error("构造返回值发生异常，意异常信息：", e);
            }
            return serviceResponse;
        }

        /**
         * 返回值转换
         *
         * @param apiResult
         * @return
         */
        protected abstract R convertResult(T apiResult);

        /**
         * post-execution process 后置处理
         *
         * @param response
         */
        protected void afterProcess(BaseResponse response) {
        }
    }

    public abstract static class ApiCallBack<T, R> {

        /**
         * 基本参数校验
         *
         * @param request
         */
        protected void checkParams(BaseRequest request) {
        }

        /**
         * 处理请求的前置操作，比如数据模型转换等。
         *
         * @param request
         */
        protected void beforeProcess(BaseRequest request) {

        }

        /**
         * 参数转换：VO -> DTO
         *
         * @param request
         * @return
         */
        protected abstract Object convert(BaseRequest request);

        /**
         * 核心业务处理
         *
         * @param args
         * @return
         */
        protected abstract T doProcess(Object args);

        /**
         * 将服务层返回的对象转成controller层返回的对象:DTO -> VO
         *
         * @param apiResult
         * @return
         * @throws Throwable
         */
        protected ServiceResponse<R> assembleResult(T apiResult) throws Throwable {
            ServiceResponse<R> serviceResponse = ServiceResponse.valueOfSuccess();
            try {
                serviceResponse.setData(convertResult(apiResult));
            } catch (Exception e) {
                log.error("构造返回值发生异常，意异常信息：", e);
            }
            return serviceResponse;
        }

        /**
         * 返回值转换
         *
         * @param apiResult
         * @return
         */
        protected abstract R convertResult(T apiResult);


        /**
         * post-execution process 后置处理
         *
         * @param response
         */
        protected void afterProcess(BaseResponse response) {
        }
    }

    /**
     * 初始化请求上下文信息
     *
     * @param bizScene
     */
    private static void initContext(BizScene bizScene) {
        ServiceContextHolder.setSceneCode(bizScene);
    }

    /**
     * 清除上下文
     */
    private static void clearContext() {
        ServiceContextHolder.clear();
    }

    /**
     * 业务异常处理模板
     *
     * @param bizEx
     * @return
     */
    private ServiceResponse handleBizException(BizException bizEx) {
        log.error("biz exception occurred，error code: {}，error message: {}", bizEx.getErrorCode(), bizEx.getMessage());
        return generateBaseResult(bizEx);
    }


    /**
     * 异常信息统一处理方法
     *
     * @param throwable
     * @return
     */
    private ServiceResponse generateBaseResult(Throwable throwable) {
        BizErrorCode errorCode;
        String errorMsg = null;
        if (throwable instanceof BizException) {
            BizException exception = (BizException) throwable;
            errorCode = exception.getErrorCode();
            if (errorCode == BizErrorCode.PARAM_ILLEGAL) {
                errorMsg = throwable.getMessage();
            }
        } else {
            errorCode = BizErrorCode.SYSTEM_ERROR;
        }

        try {
            return ServiceResponse.valueOfError(
                    Objects.requireNonNull(errorCode).getErrorType(),
                    Objects.requireNonNull(errorCode).getErrorCode(),
                    errorMsg == null ? errorCode.getErrorContext() : errorMsg);
        } catch (Throwable t) {
            log.error("new system exception occurred, error message: {}", t.getMessage());
            return null;
        }
    }

    /**
     * 系统异常处理模板
     *
     * @param e
     * @return
     */
    private ServiceResponse handleSystemException(Throwable e) {
        log.error("unknown exception occurred, error message: ", e);
        return generateBaseResult(e);
    }

    private String buildRequestDigestLog(BizScene bizScene, Object request, String loginId) {
        return "[" + bizScene.getCode() + SEP +
                StringUtils.defaultIfBlank(loginId, NULL_REPLACE) + SEP +
                REQUEST + SEP + StringUtils.defaultIfBlank(JSON.toJSONString(request), NULL_REPLACE) + "]";

    }

    private String buildResponseDigestLog(BizScene bizScene, Object request, String loginId) {
        return "[" + bizScene.getCode() + SEP +
                StringUtils.defaultIfBlank(loginId, NULL_REPLACE) + SEP +
                RESPONSE + SEP + StringUtils.defaultIfBlank(JSON.toJSONString(request), NULL_REPLACE) + "]";

    }

    /**
     * 当前请求汇总日志
     *
     * @param isSuccess
     * @param resultCode
     * @param executeTime
     * @param loginId
     * @return
     */
    private String buildDigestLog(BizScene bizScene, boolean isSuccess, String resultCode, long executeTime, String loginId) {
        return "[" + bizScene.getCode() + SEP +
                StringUtils.defaultIfBlank(loginId, NULL_REPLACE) + SEP +
                isSuccess + SEP +
                StringUtils.defaultIfBlank(resultCode, NULL_REPLACE) + "]" +
                "(" + executeTime + "ms)";

    }
}
