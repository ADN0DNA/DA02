package org.example.newspaperjsclass.dao;


import org.example.newspaperjsclass.dao.model.ReadArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadArticleRepository {
    List<ReadArticleEntity> getAll();

    ReadArticleEntity get(int id);

    int save(ReadArticleEntity readArticle);

    void update(ReadArticleEntity readArticle);

    void delete(ReadArticleEntity readArticle);
}
