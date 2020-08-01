package com.freeziyou.newcoder.dao;

import com.freeziyou.newcoder.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 7/24/2020 13:35
 * @description TODO
 */
@Repository
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(Integer userId, Integer offset, Integer limit);

    /**
     * 如果只有一个参数, 并且在动态 SQL 中会使用, 则一定要加别名
     */
    int selectDiscussPostsRows(@Param("userId") Integer userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);
}
