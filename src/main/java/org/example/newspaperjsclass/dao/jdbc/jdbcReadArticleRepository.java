package org.example.newspaperjsclass.dao.jdbc;

import jakarta.inject.Inject;
import org.example.newspaperjsclass.dao.ReadArticleRepository;

import org.example.newspaperjsclass.dao.mappers.jdbc_mappers.ReadArticleMapperDao;
import org.example.newspaperjsclass.dao.model.ReadArticleEntity;
import org.example.newspaperjsclass.dao.utils.DBConnectionPool;
import org.example.newspaperjsclass.dao.utils.SQLQueries;
import org.example.newspaperjsclass.domain.error.AppError;
import org.example.newspaperjsclass.domain.error.DatabaseError;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class jdbcReadArticleRepository implements ReadArticleRepository {

    private final DBConnectionPool dbConnectionPool;
    private final ReadArticleMapperDao readArticleMapperDao;
    private final SQLQueries sqlQueries;


    public jdbcReadArticleRepository(DBConnectionPool dbConnectionPool, ReadArticleMapperDao readArticleMapperDao, SQLQueries sqlQueries) {
        this.dbConnectionPool = dbConnectionPool;
        this.readArticleMapperDao = readArticleMapperDao;
        this.sqlQueries = sqlQueries;
    }

    @Override
    public List<ReadArticleEntity> getAll() {
        try (Connection con = dbConnectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_READARTICLES_QUERY);
            return readArticleMapperDao.mapReadArticles(rs);
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());

        }
    }

    @Override
    public ReadArticleEntity get(int id) {
        return null;
    }

    @Override
    public int save(ReadArticleEntity readArticle) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnectionPool.getConnection();
            pstmt = conn.prepareStatement(SQLQueries.INSERT_READARTICLE_QUERY);
            // Assuming the order: articleId, readerId, rating
            pstmt.setInt(1, readArticle.getArticleId());
            pstmt.setInt(2, readArticle.getReaderId());
            pstmt.setInt(3, readArticle.getRating());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int generatedId = rs.getInt(1);
                readArticle.setId(generatedId);
                return generatedId;
            } else {
                throw new DatabaseError("Failed to retrieve generated read-article ID.");
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());
        } finally {
            dbConnectionPool.releaseResource(rs);
            dbConnectionPool.releaseResource(pstmt);
            dbConnectionPool.closeConnection(conn);
        }
    }

    @Override
    public void update(ReadArticleEntity readArticle) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dbConnectionPool.getConnection();
            pstmt = conn.prepareStatement(SQLQueries.UPDATE_READARTICLE_QUERY);
            pstmt.setInt(1, readArticle.getRating());
            pstmt.setInt(2, readArticle.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        } finally {
            dbConnectionPool.releaseResource(pstmt);
            dbConnectionPool.closeConnection(conn);
        }
    }

    @Override
    public void delete(ReadArticleEntity readArticle) {
        try (Connection con = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READARTICLE_QUERY)) {
            preparedStatement.setInt(1, readArticle.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }
}
