package com.message.austin.common.pipeline;

/**
 * 业务执行器
 *
 * @author lxj
 */
public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     *
     * @param context
     */
    void process(ProcessContext<T> context);
}
