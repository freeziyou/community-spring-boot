package com.freeziyou.newcoder.controller;

import com.freeziyou.newcoder.entity.User;
import com.freeziyou.newcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dylan Guo
 * @date 7/24/2020 11:22
 * @description TODO
 */
@Controller
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping("/test")
    public String test(Integer id, Model model) {
        User user = userService.selectById(id);
        System.out.println(user);
        model.addAttribute("user", user);

        return "test";
    }


}
