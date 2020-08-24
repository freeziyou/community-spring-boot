package com.freeziyou.newcoder.dao;

import com.freeziyou.newcoder.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 8/2/2020 10:19
 * @description TODO
 */
@Repository
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);

}
