package com.message.austin.cron.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.message.austin.common.constant.CommonConstant;
import com.message.austin.common.dto.account.DingDingWorkNoticeAccount;
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
 * 刷新钉钉的access_token
 * <p>
 * https://open.dingtalk.com/document/orgapp-server/obtain-orgapp-token
 *
 * @author lxj
 */
@Service
@Slf4j
public class RefreshDingDingAccessTokenHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Autowired
    private AccessTokenUtils accessTokenUtils;


    /**
     * 每小时请求一次接口刷新（以防失效)
     */
    @XxlJob("refreshAccessTokenJob")
    public void execute() {
        log.info("refreshAccessTokenJob#execute!");
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
            List<ChannelAccount> accountList = channelAccountDao.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.DING_DING_WORK_NOTICE.getCode());
            for (ChannelAccount channelAccount : accountList) {
                DingDingWorkNoticeAccount account = JSON.parseObject(channelAccount.getAccountConfig(), DingDingWorkNoticeAccount.class);
                String accessToken = accessTokenUtils.getAccessToken(ChannelType.DING_DING_WORK_NOTICE.getCode(), channelAccount.getId().intValue(), account, true);
                if (CharSequenceUtil.isNotBlank(accessToken)) {
                    redisTemplate.opsForValue().set(ChannelType.DING_DING_WORK_NOTICE.getAccessTokenPrefix() + channelAccount.getId(), accessToken);
                }
            }
        });
    }

}
