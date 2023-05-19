package com.cc.ccso.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccso.model.entity.Picture;

public interface PictureService {
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
