package com.message.austin.handler.receipt;


import com.google.common.base.Throwables;
import com.message.austin.handler.receipt.stater.ReceiptMessageStater;
import com.message.austin.support.config.SupportThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 拉取回执信息 入口
 *
 * @author lxj
 */
@Component
@Slf4j
public class MessageReceipt {

    @Autowired
    private List<ReceiptMessageStater> receiptMessageStaterList;

    @PostConstruct
    private void init() {
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
            while (true) {
                try {
                    for (ReceiptMessageStater receiptMessageStater : receiptMessageStaterList) {
                        //receiptMessageStater.start();
                    }
                    Thread.sleep(2000);
                } catch (Exception e) {
                    log.error("MessageReceipt#init fail:{}", Throwables.getStackTraceAsString(e));
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
