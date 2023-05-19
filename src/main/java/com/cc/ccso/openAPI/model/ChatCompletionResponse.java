package com.cc.ccso.openAPI.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.theokanning.openai.Usage;

import java.util.List;

/**
 * chat答案类
 *
 * @author plexpt
 */
@Data
@Getter
@Setter
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
}