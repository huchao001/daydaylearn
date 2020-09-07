package com.hug.log.enhances;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.util.StringUtils;

/**
 * consumer: SimpleRabbitListenerContainerFactory.setMessageConverter(new MdcJackson2JsonMessageConverter());
 */
@Slf4j
public class MdcJackson2JsonMessageConverter extends Jackson2JsonMessageConverter {

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        log.debug("fromMessage s:{}", message);

        String x_tid = (String) message.getMessageProperties().getHeaders().get(LogCont.X_TRANSACTION_ID);
        if (StringUtils.isEmpty(x_tid)) {
            x_tid = MDCUtil.createTno();
        }
        MDCUtil.putTno(x_tid);
        log.debug("fromMessage e:{}", message);
        return super.fromMessage(message);
    }

}