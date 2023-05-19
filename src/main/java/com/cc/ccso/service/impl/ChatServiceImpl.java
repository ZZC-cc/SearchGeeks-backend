package com.cc.ccso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccso.common.ResultUtils;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.openAPI.ChatGPT;
import com.cc.ccso.openAPI.model.ChatChoice;
import com.cc.ccso.openAPI.model.ChatCompletionRequest;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import com.cc.ccso.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * chatgpt服务实现类
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatGPT chatGPT;

    @Value("${chatgpt.apiKey}")
    private String apiKey;

    @Override
    public Page<Messages> searchChat(String searchText, long pageNum, long pageSize) {
        chatGPT = ChatGPT.builder()
                .apiKey(apiKey)
                .timeout(600)
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
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletionRequest);
        List<Messages> messages = new ArrayList<>();
        messages.add(response.getChoices().get(0).getMessagesMsg());
        Page<Messages> chatPage = new Page<>(pageNum, pageSize);
        chatPage.setRecords(messages);
        return chatPage;
    }
}
