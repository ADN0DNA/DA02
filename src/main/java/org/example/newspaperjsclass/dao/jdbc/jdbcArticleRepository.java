package org.example.newspaperjsclass.dao.jdbc;


import org.example.newspaperjsclass.dao.ArticleRepository;
import org.example.newspaperjsclass.dao.mappers.jdbc_mappers.ArticleMapperDao;
import org.example.newspaperjsclass.dao.model.ArticleEntity;
import org.example.newspaperjsclass.dao.utils.DBConnectionPool;
import org.example.newspaperjsclass.dao.utils.SQLQueries;
import org.example.newspaperjsclass.domain.error.AppError;
import org.example.newspaperjsclass.domain.error.DatabaseError;
import org.example.newspaperjsclass.domain.error.ForeignKeyError;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.List;

@Repository
public class jdbcArticleRepository implements ArticleRepository {
    private final ArticleMapperDao articleMapperDao;
    private final SQLQueries sqlQueries;
    private final DBConnectionPool dbConnectionPool;


    public jdbcArticleRepository(ArticleMapperDao articleMapperDao, SQLQueries sqlQueries,
                                 DBConnectionPool dbConnectionPool) {
        this.articleMapperDao = articleMapperDao;
        this.sqlQueries = sqlQueries;
        this.dbConnectionPool = dbConnectionPool;
    }

    @Override
    public List<ArticleEntity> getAll() {

        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_ARTICLES_QUERY);
            return articleMapperDao.mapArticles(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }

    }


    @Override
    public ArticleEntity get(int id) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            return articleMapperDao.mapArticle(rs);

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }

    }

    @Override
    public int save(ArticleEntity article) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQLQueries.INSERT_ARTICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            // Assuming the order: name, type_id, newspaper_id, rating
            pstmt.setString(1, article.getName());
            pstmt.setInt(2, article.getType().getId());
            pstmt.setInt(3, article.getNPaperId());
            //pstmt.setInt(4, 0);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    article.setId(generatedId);
                    return generatedId;
                } else {
                    throw new DatabaseError("Failed to retrieve generated article ID.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());
        }

    }

    @Override
    public void update(ArticleEntity article) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_ARTICLE_QUERY)) {
            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    @Override
    public void delete(ArticleEntity article, boolean confirmation) {

        try (Connection con =dbConnectionPool.getConnection();
        PreparedStatement deleteArticle = con.prepareStatement(SQLQueries.DELETE_ARTICLE_QUERY);
        PreparedStatement deleteReadArticle = con.prepareStatement(SQLQueries.DELETE_READARTICLE_QUERY))
        {
        try {
            con.setAutoCommit(false);
            int articleId =article.getId();
            if (confirmation) {
                deleteReadArticle.setInt(1, articleId);
                deleteReadArticle.executeUpdate();}

                deleteArticle.setInt(1, articleId);
                deleteArticle.executeUpdate();
            con.commit();
        }
        catch (SQLIntegrityConstraintViolationException e){
            con.rollback();
            throw new ForeignKeyError("###  SQLIntegrityConstraintViolationException  ###");
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
