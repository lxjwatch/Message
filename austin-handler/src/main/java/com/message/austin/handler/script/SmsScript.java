package com.message.austin.handler.script;


import com.message.austin.handler.domain.sms.SmsParam;
import com.message.austin.support.domain.SmsRecord;

import java.util.List;


/**
 * 短信脚本 接口
 *
 * @author lxj
 */
public interface SmsScript {

    /**
     * 发送短信
     *
     * @param smsParam
     * @return 渠道商发送接口返回值
     */
    List<SmsRecord> send(SmsParam smsParam);


    /**
     * 拉取回执
     *
     * @param id 渠道账号的ID
     * @return 渠道商回执接口返回值
     */
    List<SmsRecord> pull(Integer id);

}
