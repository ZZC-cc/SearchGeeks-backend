package com.cc.ccso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccso.common.ResultUtils;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.openAPI.ChatGPT;
import com.cc.ccso.openAPI.model.ChatCompletionRequest;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import com.cc.ccso.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.CacheResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天服务实现
 *
 */
@Service
@Slf4j
public class ChatDataSource implements DataSource<Messages> {

    @Resource
    private ChatService chatService;

    private String apiKey = "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";

    @Resource
    private ChatGPT chatGPT;

    @Override
    public Page<Messages> doSearch(String searchText, long pageNum, long pageSize) {
        Page<Messages> chatPage = chatService.searchChat(searchText, pageNum, pageSize);
        return chatPage;
    }
}
