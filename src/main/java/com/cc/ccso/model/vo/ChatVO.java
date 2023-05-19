package com.cc.ccso.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天视图
 *
 */
@Data
public class ChatVO implements Serializable {

    /**
     * 回复
     */
    private String content;

    private static final long serialVersionUID = 1L;
}