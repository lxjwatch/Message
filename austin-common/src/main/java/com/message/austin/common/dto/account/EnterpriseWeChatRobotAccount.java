package com.message.austin.common.dto.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业微信 机器人 账号信息
 *
 * @author lxj
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseWeChatRobotAccount {
    /**
     * 自定义群机器人中的 webhook
     */
    private String webhook;

}
