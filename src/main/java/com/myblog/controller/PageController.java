package com.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }

    @GetMapping("/profile")
    public String profile() {
        return "forward:/profile.html";
    }

    @GetMapping("/profile/articles")
    public String myArticles() {
        return "forward:/my-articles.html";
    }

    @GetMapping("/create-article")
    public String createArticle() {
        return "forward:/create-article.html";
    }

    @GetMapping("/article/{id}")
    public String article() {
        return "forward:/article.html";
    }
}