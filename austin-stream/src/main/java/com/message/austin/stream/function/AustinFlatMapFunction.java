package com.message.austin.stream.function;

import com.alibaba.fastjson.JSON;
import com.message.austin.common.domain.AnchorInfo;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @author lxj
 * @date 2022/2/22
 * process 处理
 */
public class AustinFlatMapFunction implements FlatMapFunction<String, AnchorInfo> {

    @Override
    public void flatMap(String value, Collector<AnchorInfo> collector) throws Exception {
        AnchorInfo anchorInfo = JSON.parseObject(value, AnchorInfo.class);
        collector.collect(anchorInfo);
    }
}
