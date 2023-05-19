package com.cc.ccso.openAPI.model;

import com.cc.ccso.model.entity.Messages;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author plexpt
 */
@Data
@Setter
@Getter
public class ChatChoice {
    private long index;
    /**
     * 请求参数stream为true返回是delta
     */
    @JsonProperty("delta")
    private Messages delta;
    /**
     * 请求参数stream为false返回是message
     */
    @JsonProperty("message")
    private Messages messagesMsg;
    @JsonProperty("finish_reason")
    private String finishReason;
}
