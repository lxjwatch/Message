package com.message.austin.cron.xxl.enums;

/**
 * 调度类型
 *
 * @author lxj
 */
public enum ScheduleTypeEnum {

    /**
     * NONE
     */
    NONE,
    /**
     * schedule by cron
     */
    CRON,

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE;

    ScheduleTypeEnum() {
    }

}
