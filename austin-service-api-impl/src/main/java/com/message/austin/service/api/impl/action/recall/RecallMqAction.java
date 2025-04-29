package com.message.austin.service.api.impl.action.recall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.message.austin.common.domain.RecallTaskInfo;
import com.message.austin.common.enums.RespStatusEnum;
import com.message.austin.common.pipeline.BusinessProcess;
import com.message.austin.common.pipeline.ProcessContext;
import com.message.austin.common.vo.BasicResultVO;
import com.message.austin.service.api.impl.domain.RecallTaskModel;
import com.message.austin.support.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lxj
 * 将撤回消息发送到MQ
 */
@Slf4j
@Service
public class RecallMqAction implements BusinessProcess<RecallTaskModel> {
    @Autowired
    private SendMqService sendMqService;

    @Value("${austin.business.recall.topic.name}")
    private String austinRecall;
    @Value("${austin.business.tagId.value}")
    private String tagId;

    @Value("${austin.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext<RecallTaskModel> context) {
        RecallTaskInfo recallTaskInfo = context.getProcessModel().getRecallTaskInfo();
        try {
            String message = JSON.toJSONString(recallTaskInfo, new SerializerFeature[]{SerializerFeature.WriteClassName});
            sendMqService.send(austinRecall, message, tagId);
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send {} fail! e:{},params:{}", mqPipeline, Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(recallTaskInfo));
        }
    }

}
