package com.cc.ccso.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccso.common.ErrorCode;
import com.cc.ccso.datasource.*;
import com.cc.ccso.exception.BusinessException;
import com.cc.ccso.exception.ThrowUtils;
import com.cc.ccso.model.dto.post.PostQueryRequest;
import com.cc.ccso.model.dto.search.SerachRequest;
import com.cc.ccso.model.dto.user.UserQueryRequest;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.model.entity.Picture;
import com.cc.ccso.model.enums.SearchTypeEnum;
import com.cc.ccso.model.vo.PostVO;
import com.cc.ccso.model.vo.SearchVO;
import com.cc.ccso.model.vo.UserVO;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 */
@Slf4j
@Component
public class SearchFacade {
    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private ChatDataSource chatDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;


    public SearchVO searchAll(@RequestBody SerachRequest serachRequest, HttpServletRequest request) {
        String type = serachRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);

        String searchText = serachRequest.getSearchText();
        long current = serachRequest.getCurrent();
        long pageSize = serachRequest.getPageSize();
        if (searchTypeEnum == null) {

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText,current,pageSize);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText,current,pageSize);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText,current,pageSize);
                return picturePage;
            });

            CompletableFuture<Page<Messages>> chatTask = CompletableFuture.supplyAsync(() -> {
                Page<Messages> chatPage = chatDataSource.doSearch(searchText,current,pageSize);
                return chatPage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();
            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                Page<Messages> chatPage = chatTask.get();

                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setChatList(chatPage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}
