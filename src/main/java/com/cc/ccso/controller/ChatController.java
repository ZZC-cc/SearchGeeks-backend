package com.cc.ccso.controller;

import com.cc.ccso.common.BaseResponse;
import com.cc.ccso.common.ResultUtils;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.openAPI.ChatGPT;
import com.cc.ccso.openAPI.model.ChatCompletionRequest;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import com.cc.ccso.service.ChatService;
import com.cc.ccso.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天接口
 *
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Resource
    private ChatService chatService;

    private ChatGPT chatGPT;

    @Resource
    private UserService userService;

    private String apiKey = "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";

    private final static Gson GSON = new Gson();



    @PostMapping("/getChat")
    public BaseResponse<Messages> getChatRequest(String searchText) {
        chatGPT = ChatGPT.builder()
                .apiKey(apiKey)
                .timeout(1000)
                .build()
                .init();
        log.info("初始化成功");
        Messages msg = new Messages();
        msg.setContent(searchText);
        msg.setRole("user");
        List<Messages> list = new ArrayList<>();
        list.add(msg);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getName())
                .messages(list)
                .maxTokens(2000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletionRequest);
        Messages messages = response.getChoices().get(0).getMessagesMsg();
        System.out.println(messages);
        return ResultUtils.success(messages);
    }


}
