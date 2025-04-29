package com.message.austin.common.pipeline;

import com.message.austin.common.enums.RespStatusEnum;

import java.util.Objects;

/**
 * @author SamLee
 * @since 2022-03-29
 */
public class ProcessException extends RuntimeException {

    /**
     * 流程处理上下文
     */
    private final ProcessContext processContext;

    public ProcessException(ProcessContext processContext) {
        super();
        this.processContext = processContext;
    }

    public ProcessException(ProcessContext processContext, Throwable cause) {
        super(cause);
        this.processContext = processContext;
    }

    @Override
    public String getMessage() {
        if (Objects.nonNull(this.processContext)) {
            return this.processContext.getResponse().getMsg();
        }
        return RespStatusEnum.CONTEXT_IS_NULL.getMsg();

    }

    public ProcessContext getProcessContext() {
        return processContext;
    }
}
