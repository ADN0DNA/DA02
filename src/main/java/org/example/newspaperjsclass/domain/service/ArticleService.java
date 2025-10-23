// java
package org.example.newspaperjsclass.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.newspaperjsclass.dao.ArticleRepository;
import org.example.newspaperjsclass.dao.model.ArticleEntity;
import org.example.newspaperjsclass.domain.model.ArticleDTO;
import org.example.newspaperjsclass.domain.error.AppError;
import org.example.newspaperjsclass.domain.mappers.ArticleMapperService;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapperService articleMapperService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          ArticleMapperService articleMapperService) {
        this.articleRepository = articleRepository;
        this.articleMapperService = articleMapperService;
    }

    public List<ArticleDTO> getAllArticles() {
        List<ArticleEntity> articles = articleRepository.getAll();
        return articleMapperService.mapToDTOs(articles);
    }

    public int add(ArticleDTO articleDTO) {
        ArticleEntity article = articleMapperService.mapToEntity(articleDTO);
        articleRepository.save(article);
        return article.getId();
    }

    public void update(ArticleDTO articleDTO) {
        ArticleEntity article = articleMapperService.mapToEntity(articleDTO);
        articleRepository.update(article);
    }

    public ArticleDTO get(int id) {
        ArticleEntity article = articleRepository.get(id);
        if (article == null) {
            throw new AppError("Article not found with id: " + id);
        }
        return articleMapperService.mapToDTO(article);
    }

    public void deleteArticle(int i, boolean b) {
        articleRepository.delete(articleRepository.get(i), b);
    }
}
