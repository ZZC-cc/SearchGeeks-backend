package com.cc.ccso.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.openAPI.model.ChatCompletionRequest;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;

public interface ChatService {
    Page<Messages> searchChat(String searchText, long pageNum, long pageSize);
}
