package com.freeziyou.newcoder.controller;

import com.freeziyou.newcoder.entity.DiscussPost;
import com.freeziyou.newcoder.entity.Page;
import com.freeziyou.newcoder.entity.User;
import com.freeziyou.newcoder.service.DiscussPostService;
import com.freeziyou.newcoder.service.LikeService;
import com.freeziyou.newcoder.service.UserService;
import com.freeziyou.newcoder.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 7/24/2020 14:45
 * @description TODO
 */
@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/index")
    public String getIndexPage(Model model, Page page) {
        // 方法调用之前, Spring MVC 会自动实例化 Model 和 Page, 并将 Page 注入 Model
        // 所以可以在 thymeleaf 中直接访问 Page 对象中的数据
        page.setRows(discussPostService.selectDiscussPostsRows(0));
        page.setPath("index");

        List<DiscussPost> list = discussPostService.selectDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        // discussPost 和 user 封装成一个 Map
        if (list != null) {
            for (DiscussPost discussPost : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user", user);

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPost.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }


}
