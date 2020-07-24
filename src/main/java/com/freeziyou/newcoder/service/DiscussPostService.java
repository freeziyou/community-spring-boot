package com.freeziyou.newcoder.service;

import com.freeziyou.newcoder.dao.DiscussPostMapper;
import com.freeziyou.newcoder.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 7/24/2020 13:36
 * @description TODO
 */
@Service
public class DiscussPostService implements DiscussPostMapper{

    @Autowired
    DiscussPostMapper discussPostMapper;


    @Override
    public List<DiscussPost> selectDiscussPosts(Integer userId, Integer offset, Integer limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    @Override
    public Integer selectDiscussPostsRows(Integer userId) {
        return discussPostMapper.selectDiscussPostsRows(userId);
    }
}
