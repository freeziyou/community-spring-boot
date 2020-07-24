package com.freeziyou.newcoder.controller;

import com.freeziyou.newcoder.entity.DiscussPost;
import com.freeziyou.newcoder.entity.Page;
import com.freeziyou.newcoder.entity.User;
import com.freeziyou.newcoder.service.DiscussPostService;
import com.freeziyou.newcoder.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dylan Guo
 * @date 7/24/2020 14:45
 * @description TODO
 */
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String getIndexPage(Model model, Page page) {
        // 方法调用之前, Spring MVC 会自动实例化 Model 和 Page, 并将 Page 注入 Model
        // 所以可以在 thymeleaf 中直接访问 Page 对象中的数据
        page.setRows(discussPostService.selectDiscussPostsRows(0));
        page.setPath("index");

        List<DiscussPost> list = discussPostService.selectDiscussPosts(0, page.getOffset(),page.getLimit());;
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        // discussPost 和 user 封装成一个 Map
        if (list != null) {
            for (DiscussPost discussPost : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                User user = userService.selectById(discussPost.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "index";
    }


}
