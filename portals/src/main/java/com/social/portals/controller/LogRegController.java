package com.social.portals.controller;

import common.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LogRegController {
    @Autowired
    UserFeign userFeign;

    @GetMapping(value = {"/logreg", "/relogin"})
    public String logReg(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "logreg";
    }

    //处理登录
    @ResponseBody
    @PostMapping(value = "/login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        int ticketId = userFeign.login(account, password);
        if (ticketId != -1) {
            Cookie cookie = new Cookie("ticket", ticketId + "");
            cookie.setMaxAge(3600 * 24 * 7);
            response.addCookie(cookie);
            return "登录成功";
        }
        return "登录失败";
    }

    //处理注册
    @ResponseBody
    @PostMapping(value = "/registry")
    public String registry(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("mailbox") String mailbox
            , @RequestParam("activeCode") String activeCode, HttpServletRequest request, HttpServletResponse response) {
        int ticketId = Integer.valueOf(userFeign.reg(account, password, mailbox, activeCode));
        if (ticketId != -1) {
            Cookie cookie = new Cookie("ticket", ticketId + "");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
            return "注册成功";
        }
        return "注册失败";
    }

    //退出
    @GetMapping(value = "/exit")
    public String exit(Model model, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        return "redirect:/";
    }
}
