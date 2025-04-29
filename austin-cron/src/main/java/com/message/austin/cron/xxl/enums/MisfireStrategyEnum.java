package com.message.austin.cron.xxl.enums;

/**
 * 调度过期策略
 *
 * @author lxj
 */
public enum MisfireStrategyEnum {

    /**
     * do nothing
     */
    DO_NOTHING,

    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    MisfireStrategyEnum() {
    }
}
