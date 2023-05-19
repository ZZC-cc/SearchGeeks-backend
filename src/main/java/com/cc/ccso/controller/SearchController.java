package com.cc.ccso.controller;

import com.cc.ccso.common.BaseResponse;
import com.cc.ccso.common.ResultUtils;
import com.cc.ccso.manager.SearchFacade;
import com.cc.ccso.model.dto.search.SerachRequest;
import com.cc.ccso.model.vo.SearchVO;
import com.cc.ccso.service.PictureService;
import com.cc.ccso.service.PostService;
import com.cc.ccso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 查询接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SerachRequest serachRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(serachRequest, request));
    }
}
