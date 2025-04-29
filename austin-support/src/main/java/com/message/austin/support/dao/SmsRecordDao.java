package com.message.austin.support.dao;


import com.message.austin.support.domain.SmsRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 短信记录的Dao
 *
 * @author lxj
 */
public interface SmsRecordDao extends CrudRepository<SmsRecord, Long> {

    /**
     * 通过日期和手机号找到发送记录
     *
     * @param phone
     * @param sendDate
     * @return
     */
    List<SmsRecord> findByPhoneAndSendDate(Long phone, Integer sendDate);
}
