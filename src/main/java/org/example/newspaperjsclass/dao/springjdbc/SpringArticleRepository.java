package org.example.newspaperjsclass.dao.springjdbc;

import org.example.newspaperjsclass.dao.ArticleRepository;
import org.example.newspaperjsclass.dao.model.ArticleEntity;
import org.example.newspaperjsclass.dao.utils.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


import java.util.List;

@Profile("inUse")
@Repository
public class SpringArticleRepository implements ArticleRepository {

    @Autowired
    private JdbcClient jdbcClient;

    @Override
    public List<ArticleEntity> getAll() {
        System.out.println("Delete this message, returning null by default");
        return null;
        /*
        return jdbcClient.sql(SQLQueries.SELECT_ARTICLES_QUERY)
                .query(articleRowMapper).
                list();

         */
    }

    @Override
    public ArticleEntity get(int id) {
        return null;
    }

    @Override
    public int save(ArticleEntity article) {
        return 0;
    }

    @Override
    public void update(ArticleEntity article) {

    }

    @Override
    public void delete(ArticleEntity article, boolean confirmation) {

    }
}
