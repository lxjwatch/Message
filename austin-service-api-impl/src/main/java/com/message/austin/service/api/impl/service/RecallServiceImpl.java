package com.message.austin.service.api.impl.service;

import com.message.austin.common.enums.RespStatusEnum;
import com.message.austin.common.pipeline.ProcessContext;
import com.message.austin.common.pipeline.ProcessController;
import com.message.austin.common.vo.BasicResultVO;
import com.message.austin.service.api.domain.SendRequest;
import com.message.austin.service.api.domain.SendResponse;
import com.message.austin.service.api.impl.domain.RecallTaskModel;
import com.message.austin.service.api.service.RecallService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * 撤回接口
 *
 * @author lxj
 */
@Service
public class RecallServiceImpl implements RecallService {
    @Autowired
    @Qualifier("apiProcessController")
    private ProcessController processController;

    @Override
    public SendResponse recall(SendRequest sendRequest) {

        if (ObjectUtils.isEmpty(sendRequest)) {
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }
        RecallTaskModel recallTaskModel = RecallTaskModel.builder().messageTemplateId(sendRequest.getMessageTemplateId()).recallMessageId(sendRequest.getRecallMessageIds()).build();
        ProcessContext context = ProcessContext.builder().code(sendRequest.getCode()).processModel(recallTaskModel).needBreak(false).response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);
        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg(), null);
    }
}
