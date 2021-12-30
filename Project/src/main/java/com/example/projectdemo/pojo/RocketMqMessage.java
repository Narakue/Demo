package com.example.projectdemo.pojo;

import lombok.Data;
import java.io.Serializable;

/**
 * @author Zero
 */
@Data
public class RocketMqMessage<T> implements Serializable {
    /**
     * 消息内容
     */
    private T content;


    /**
     * 消息的key
     */
    private String msgKey;

    /**
     * topic
     */
    private String producerTopic;
    /**
     * group
     */
    private String producerGroup;
    /**
     * tag
     */
    private String producerTag;
}

