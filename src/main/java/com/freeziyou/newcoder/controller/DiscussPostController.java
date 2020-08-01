package com.freeziyou.newcoder.controller;

import com.freeziyou.newcoder.entity.DiscussPost;
import com.freeziyou.newcoder.entity.User;
import com.freeziyou.newcoder.service.DiscussPostService;
import com.freeziyou.newcoder.service.UserService;
import com.freeziyou.newcoder.util.CommunityUtil;
import com.freeziyou.newcoder.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Dylan Guo
 * @date 7/31/2020 18:23
 * @description TODO
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJsonString(403, "未登录!");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJsonString(0, "发布成功!");
    }

    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPost(@PathVariable int discussPostId, Model model) {
        DiscussPost discussPost = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", discussPost);

        // 将 user_id 转换成 username
        User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user", user);

        return "site/discuss-detail";
    }

}
