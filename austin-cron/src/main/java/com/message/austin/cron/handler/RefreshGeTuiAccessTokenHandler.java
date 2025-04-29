package com.message.austin.cron.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.message.austin.common.constant.CommonConstant;
import com.message.austin.common.dto.account.GeTuiAccount;
import com.message.austin.common.enums.ChannelType;
import com.message.austin.support.config.SupportThreadPoolConfig;
import com.message.austin.support.dao.ChannelAccountDao;
import com.message.austin.support.domain.ChannelAccount;
import com.message.austin.support.utils.AccessTokenUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 刷新个推的token
 * <p>
 * https://docs.getui.com/getui/server/rest_v2/token/
 *
 * @author lxj
 */
@Service
@Slf4j
public class RefreshGeTuiAccessTokenHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Autowired
    private AccessTokenUtils accessTokenUtils;


    /**
     * 每小时请求一次接口刷新（以防失效)
     */
    @XxlJob("refreshGeTuiAccessTokenJob")
    public void execute() {
        log.info("refreshGeTuiAccessTokenJob#execute!");
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
            List<ChannelAccount> accountList = channelAccountDao.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.PUSH.getCode());
            for (ChannelAccount channelAccount : accountList) {
                GeTuiAccount account = JSON.parseObject(channelAccount.getAccountConfig(), GeTuiAccount.class);
                String accessToken = accessTokenUtils.getAccessToken(ChannelType.PUSH.getCode(), channelAccount.getId().intValue(), account, true);
                if (CharSequenceUtil.isNotBlank(accessToken)) {
                    redisTemplate.opsForValue().set(ChannelType.PUSH.getAccessTokenPrefix() + channelAccount.getId(), accessToken);
                }
            }
        });
    }


}
