package com.cc.ccso.model.vo;

import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.model.entity.Picture;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class SearchVO implements Serializable {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<Messages> chatList;

//    private List<Chat> chatList;

    private List<?> dataList;

    private static final long serialVersionUID = 1L;
}