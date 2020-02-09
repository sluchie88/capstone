/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TomTom
 */
@Repository
public class LevelDAO {
    
    private JdbcTemplate jdbc;

    @Autowired
    public LevelDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }
    
    /**
     * 
     * @param level
     * @return 
     */
    public Level addLevelToDB(Level level){
        final String sql = "INSERT INTO Level (level, requiredXP, imageURL, rankName) "
                + "VALUES (?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, level.getLevel());
            statement.setInt(2, level.getRequiredXP());
            statement.setString(3, level.getImageURL());
            statement.setString(4, level.getRankName());
            return statement;
        }, keyRing);
        
        return level;
    }
    
    /**
     * 
     * @param level
     * @param lvl
     * @return 
     */
    public boolean editLevel(Level level, int lvl){
        final String sql = "UPDATE Level SET "
                + "level = ?, "
                + "requiredXP = ?, "
                + "imageURL = ?, "
                + "rankName = ? "
                + "WHERE level = ?;";
        return jdbc.update(sql, level.getLevel(), 
                level.getRequiredXP(), 
                level.getImageURL(), 
                level.getRankName(), 
                lvl) > 0;
    }
    
    /**
     * 
     * @param lvl
     * @return 
     */
    public Level getLevel(int lvl){
        final String sql = "SELECT level, requiredXP, imageURL, rankName "
                + "FROM Level "
                + "WHERE level = ?;";
        return jdbc.queryForObject(sql, new LevelMapper(), lvl);
    }
    
    
    /**
     * 
     * @param lvl 
     */
    @Transactional
    public void removeLevel(Level lvl){
        final String sqlUserLvl = "DELETE FROM UserLevel "
                + "WHERE level = ?;";
        jdbc.update(sqlUserLvl, lvl.getLevel());
        
        final String sqlLevel = "DELETE FROM Level "
                + "WHERE level = ?;";
        jdbc.update(sqlLevel, lvl.getLevel());
    }

    public Level getUserLevel(String userName) {
        final String sql = "SELECT l.level, "
                + "l.requiredXP, l.rankName, l.imageURL "
                + "FROM Level l"
                + "INNER JOIN User u ON u.level = l.level "
                + "WHERE u.userName LIKE ?";
        return jdbc.queryForObject(sql, new LevelMapper(), userName);
    }
    
    private final class LevelMapper implements RowMapper<Level>{

        @Override
        public Level mapRow(ResultSet rs, int i) throws SQLException {
            Level level = new Level();
            level.setLevel(rs.getInt("level"));
            level.setRequiredXP(rs.getInt("requiredXP"));
            level.setRankName(rs.getString("rankName"));
            level.setImageURL(rs.getString("imageURL"));
            
            return level;
        }
        
    }
}
