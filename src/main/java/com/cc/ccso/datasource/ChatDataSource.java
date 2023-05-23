package com.cc.ccso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.openAPI.ChatGPT;
import com.cc.ccso.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 聊天服务实现
 *
 */
@Service
@Slf4j
public class ChatDataSource implements DataSource<Messages> {

    @Resource
    private ChatService chatService;

    @Value("${chatgpt.apiKey}")
    private String apiKey;

    @Resource
    private ChatGPT chatGPT;

    @Override
    public Page<Messages> doSearch(String searchText, long pageNum, long pageSize) {
        Page<Messages> chatPage = chatService.searchChat(searchText, pageNum, pageSize);
        return chatPage;
    }
}
