package com.message.austin.handler.deduplication.build;

import cn.hutool.core.date.DateUtil;
import com.message.austin.common.domain.TaskInfo;
import com.message.austin.common.enums.AnchorState;
import com.message.austin.common.enums.DeduplicationType;
import com.message.austin.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author huskey
 * @date 2022/1/18
 */

@Service
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder{
    public FrequencyDeduplicationBuilder() {
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        deduplicationParam.setAnchorState(AnchorState.RULE_DEDUPLICATION);
        return deduplicationParam;
    }
}
