package com.cc.ccso.model.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author plexpt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Messages implements Serializable {
    /**
     * 目前支持三中角色参考官网，进行情景输入：https://platform.openai.com/docs/guides/chat/introduction
     */
    private String role;
    private String content;

    public static Messages of(String content) {

        return new Messages(Role.USER.getValue(), content);
    }

    public static Messages ofSystem(String content) {

        return new Messages(Role.SYSTEM.getValue(), content);
    }

    public static Messages ofAssistant(String content) {

        return new Messages(Role.ASSISTANT.getValue(), content);
    }

    @Getter
    @AllArgsConstructor
    public enum Role {

        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant"),
        ;
        private String value;
    }

}
