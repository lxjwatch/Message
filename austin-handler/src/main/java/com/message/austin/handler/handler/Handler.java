package com.message.austin.handler.handler;

import com.message.austin.common.domain.RecallTaskInfo;
import com.message.austin.common.domain.TaskInfo;

/**
 * @author lxj
 * 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     *
     * @param recallTaskInfo
     * @return
     */
    void recall(RecallTaskInfo recallTaskInfo);

}
