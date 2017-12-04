package com.example.demo.dao;

import com.example.demo.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleDao {
    String table_name = " article ";
    String insert_fields = " title, describes, content, created_Date, comment_Count, category ";
    String select_fields = " id, "+insert_fields;

    @Insert({"insert into",table_name,"(",insert_fields,") values (#{title},#{describes}" +
            ",#{content},#{created_Date},#{comment_Count},#{category}"})
    int insertArticle(Article article);

    @Select({"select",select_fields,"from",table_name,"where id=#{id}"})
    Article selectById(int id);

    @Select({"select",select_fields,"from",table_name,"order by id desc limit #{offset},#{limit}"})
    List<Article> selectLatestArticles(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select",select_fields,"from",table_name,"where category=#{category} order by id desc limit #{offset},#{limit}"})
    List<Article> selecttArticlesByCategory(@Param("category") String category,@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from",table_name,"where category=#{category}"})
    int getArticleCountByCategory(@Param("category") String category);

    @Select({"select count(id) from",table_name})
    int getArticleCount();

    @Update({"update",table_name,"set comment_count = #{commentCount} where id = #{questionId}"})
    void updateCommentCount(@Param("questionId") int questionId,@Param("commentCount") int commentCount);

    @Delete({"delete from",table_name,"where id=#{id}"})
    void deleteById(int id);
}
