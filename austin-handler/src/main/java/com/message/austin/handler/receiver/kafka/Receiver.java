package com.message.austin.handler.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.message.austin.common.domain.RecallTaskInfo;
import com.message.austin.common.domain.TaskInfo;
import com.message.austin.handler.receiver.MessageReceiver;
import com.message.austin.handler.receiver.service.ConsumeService;
import com.message.austin.handler.utils.GroupIdMappingUtils;
import com.message.austin.support.constans.MessageQueuePipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author lxj
 * 消费MQ的消息
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
public class Receiver implements MessageReceiver {
    @Autowired
    private ConsumeService consumeService;

    /**
     * 发送消息
     *
     * @param consumerRecord
     * @param topicGroupId
     */
    @KafkaListener(topics = "#{'${austin.business.topic.name}'}", containerFactory = "filterContainerFactory")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {

            List<TaskInfo> taskInfoLists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
            /**
             * 每个消费者组 只消费 他们自身关心的消息
             */
            if (topicGroupId.equals(messageGroupId)) {
                consumeService.consume2Send(taskInfoLists);
            }
        }
    }

    /**
     * 撤回消息
     *
     * @param consumerRecord
     */
    @KafkaListener(topics = "#{'${austin.business.recall.topic.name}'}", groupId = "#{'${austin.business.recall.group.name}'}", containerFactory = "filterContainerFactory")
    public void recall(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            RecallTaskInfo recallTaskInfo = JSON.parseObject(kafkaMessage.get(), RecallTaskInfo.class);
            consumeService.consume2recall(recallTaskInfo);
        }
    }
}
