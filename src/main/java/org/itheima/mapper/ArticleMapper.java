package org.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.itheima.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)"
            + " values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);
    // or 动态SQL
//    @Select({
//            "<script>",
//            "  SELECT * FROM article",
//            "  <where>",
//            "    <if test='categoryId != null'>",
//            "      category_id = #{categoryId}",
//            "    </if>",
//            "    <if test='state != null'>",
//            "      AND state = #{state}",
//            "    </if>",
//            "    AND create_user = #{userId}",
//            "  </where>",
//            "</script>"
//    })
//    List<Article> list( Integer userId, Integer categoryId, String state);
    @Select("select * from article where id=#{id}")
    Article findById(Integer id);

    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}
