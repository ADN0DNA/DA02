package org.example.newspaperjsclass.dao.mappers.jdbc_mappers;


import org.example.newspaperjsclass.dao.model.ArticleEntity;
import org.example.newspaperjsclass.dao.model.TypeEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleMapperDao {

    public List<ArticleEntity> mapArticles(ResultSet rs) throws SQLException {
        List<ArticleEntity> list = new ArrayList<>();
        while (rs.next()) {
            ArticleEntity article = new ArticleEntity();
            article.setId(rs.getInt("id"));
            article.setName(rs.getString("name"));
            article.setNPaperId(rs.getInt("newspaper_id"));
            article.setType(new TypeEntity(rs.getInt("type_id"), ""));

            list.add(article);
        }
        return list;
    }

    public ArticleEntity mapArticle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            ArticleEntity article = new ArticleEntity();
            article.setId(rs.getInt("id"));
            article.setName(rs.getString("name"));
            article.setNPaperId(rs.getInt("newspaper_id"));
            article.setType(new TypeEntity(rs.getInt("type_id"), ""));
            return article;
        }
        return null;
    }
}//TODO Finish the setters, fix the typeEntityShenanigans