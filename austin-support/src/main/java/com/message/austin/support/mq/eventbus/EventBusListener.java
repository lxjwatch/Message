package com.message.austin.support.mq.eventbus;


import com.message.austin.common.domain.RecallTaskInfo;
import com.message.austin.common.domain.TaskInfo;

import java.util.List;

/**
 * @author lxj
 * 监听器
 */
public interface EventBusListener {


    /**
     * 消费消息
     *
     * @param lists
     */
    void consume(List<TaskInfo> lists);

    /**
     * 撤回消息
     *
     * @param recallTaskInfo
     */
    void recall(RecallTaskInfo recallTaskInfo);
}
