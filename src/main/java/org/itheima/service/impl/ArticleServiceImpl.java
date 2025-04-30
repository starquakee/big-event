package org.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Mapper;
import org.itheima.mapper.ArticleMapper;
import org.itheima.pojo.Article;
import org.itheima.pojo.PageBean;
import org.itheima.service.ArticleService;
import org.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String,Object> claims = ThreadLocalUtil.get();
        article.setCreateUser((Integer) claims.get("id"));
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建pageBean对象
        PageBean<Article> pageBean = new PageBean<>();
        //开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        //mapper层仅关心业务代码，与分页解耦，分页功能由PageHelper实现
        List<Article> articles = articleMapper.list(userId, categoryId, state);

        Page<Article> page = (Page<Article>) articles;
        pageBean.setTotal(page.getTotal()); //满足条件的总记录数
        pageBean.setItems(page.getResult()); //当前页数据
        return pageBean;
    }

    @Override
    public Article findById(Integer id) {
        return articleMapper.findById(id);
    }
}
