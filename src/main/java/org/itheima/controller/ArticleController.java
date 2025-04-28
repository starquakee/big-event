package org.itheima.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.itheima.pojo.Article;
import org.itheima.pojo.Result;
import org.itheima.service.ArticleService;
import org.itheima.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

}
