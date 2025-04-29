package com.message.austin.handler.receiver.eventbus;

import com.google.common.eventbus.Subscribe;
import com.message.austin.common.domain.RecallTaskInfo;
import com.message.austin.common.domain.TaskInfo;
import com.message.austin.handler.receiver.MessageReceiver;
import com.message.austin.handler.receiver.service.ConsumeService;
import com.message.austin.support.constans.MessageQueuePipeline;
import com.message.austin.support.mq.eventbus.EventBusListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lxj
 */
@Component
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
public class EventBusReceiver implements EventBusListener, MessageReceiver {

    @Autowired
    private ConsumeService consumeService;

    @Override
    @Subscribe
    public void consume(List<TaskInfo> lists) {
        consumeService.consume2Send(lists);

    }

    @Override
    @Subscribe
    public void recall(RecallTaskInfo recallTaskInfo) {
        consumeService.consume2recall(recallTaskInfo);
    }
}
