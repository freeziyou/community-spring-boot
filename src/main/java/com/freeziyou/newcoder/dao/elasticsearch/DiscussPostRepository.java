package com.freeziyou.newcoder.dao.elasticsearch;

import com.freeziyou.newcoder.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dylan Guo
 * @date 8/26/2020 11:36
 * @description TODO
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

    
}
