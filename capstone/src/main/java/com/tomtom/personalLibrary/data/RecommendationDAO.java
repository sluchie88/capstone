/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Recommendation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TomTom
 */
@Repository
public class RecommendationDAO {
    
    private JdbcTemplate jdbc;

    @Autowired
    public RecommendationDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }
    
    /**
     * 
     * @param rec
     * @param userName
     * @return 
     */
    public Recommendation addRecommendation(Recommendation rec, String userName){
        final String sql = "INSERT INTO Recommendation (value, isbn, userName) "
                + "VALUES (?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, rec.getRecommendationValue());
            statement.setString(2, rec.getIsbn());
            statement.setString(3, userName);
            return statement;
        }, keyRing);
        
        rec.setRecommendationId(keyRing.getKey().intValue());
        return rec;
    }
    
    /**
     * 
     * @param isbn
     * @return 
     */
    public List<Recommendation> findRecommendationByIsbn(String isbn){
        final String sql = "SELECT r.isbn, r.value, r.recommendationId "
                + "FROM Recommendation r "
                + "WHERE r.isbn = ?;";
        return jdbc.query(sql, new RecommendationMapper(), isbn);
    }
    
    /**
     * 
     * @param userName
     * @return 
     */
    public List<Recommendation> findAllUserRecommendations(String userName){
        final String sql = "SELECT recommendationId, value, isbn, userName "
                + "FROM Recommendation "
                + "WHERE userName = ?;";
        return jdbc.query(sql, new RecommendationMapper(), userName);
    }
    
    public Recommendation getRecommendation(String username, String isbn){
        final String sql = "SELECT recommendationId, value, isbn, userName "
                + "FROM Recommendation "
                + "WHERE username = ? AND isbn = ?;";
        return jdbc.queryForObject(sql, new RecommendationMapper(), username, isbn);
    }
    
    /**
     * 
     * @param username
     * @return 
     */
    public List<Recommendation> findRecommendationByUsername(String username){
        final String sql = "SELECT recommendationId, value, isbn, userName "
                + "FROM Recommendation "
                + "WHERE userName = ?;";
        return jdbc.query(sql, new RecommendationMapper(), username);
    }
    
        
    
    private final class RecommendationMapper implements RowMapper<Recommendation>{

        @Override
        public Recommendation mapRow(ResultSet rs, int i) throws SQLException {
            Recommendation rec = new Recommendation();
            rec.setRecommendationId(rs.getInt("recommendationId"));
            rec.setRecommendationValue(rs.getInt("value"));
            rec.setIsbn(rs.getString("isbn"));
            
            return rec;
        }
        
    }
}
