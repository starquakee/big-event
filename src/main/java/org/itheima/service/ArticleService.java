package org.itheima.service;

import org.itheima.pojo.Article;
import org.itheima.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article findById(Integer id);

    void update(Article article);
}
