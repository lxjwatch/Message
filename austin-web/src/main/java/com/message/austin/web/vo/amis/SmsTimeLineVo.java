package com.message.austin.web.vo.amis;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lxj
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsTimeLineVo {

    /**
     * items
     */
    private List<SmsTimeLineVo.ItemsVO> items;

    /**
     * ItemsVO
     */
    @Data
    @Builder
    public static class ItemsVO {
        /**
         * 业务ID
         */
        private String businessId;
        /**
         * detail 发送内容
         */
        private String content;

        /**
         * 发送状态
         */
        private String sendType;

        /**
         * 回执状态
         */
        private String receiveType;

        /**
         * 回执报告
         */
        private String receiveContent;

        /**
         * 发送时间
         */
        private String sendTime;

        /**
         * 回执时间
         */
        private String receiveTime;


    }
}
