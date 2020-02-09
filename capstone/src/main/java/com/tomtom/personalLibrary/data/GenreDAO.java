/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Genre;
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
public class GenreDAO {

    private JdbcTemplate jdbc;

    @Autowired
    public GenreDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }
    
    /**
     * 
     * @param genre
     * @return 
     */
    public boolean addGenre(Genre genre){
        final String sql = "INSERT INTO Genre (genreName) VALUES (?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        int ret = jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, genre.getGenreName());
            return statement;
        }, keyRing);
        
        genre.setGenreId(keyRing.getKey().intValue());
        
        return ret > 0;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public boolean findGenreByName(String name){
        final String sql = "SELECT genreId, genreName "
                + "FROM Genre "
                + "WHERE genreName LIKE ?;";
        List<Genre> res = jdbc.query(sql, new GenreMapper(), name);
        return res.isEmpty();
    }
    
    /**
     * 
     * @param isbn
     * @return 
     */
    public List<Genre> getGenreForBook(String isbn) {
        final String sql = "SELECT g.genreId, g.genreName "
                + "FROM Genre g "
                + "INNER JOIN BookGenre bg ON g.genreId = bg.genreID "
                + "WHERE bg.isbn = ?;";
        return jdbc.query(sql, new GenreMapper(), isbn);
    }

    public int getIdByGenreName(String genreName) {
        final String sql = "SELECT genreId, genreName "
                + "FROM Genre "
                + "WHERE genreName LIKE ?;";
        Genre g = jdbc.queryForObject(sql, new GenreMapper(), genreName);
        return g.getGenreId();
    }

    public void addBookGenre(int genreId, String isbn) {
        final String sql = "INSERT INTO BookGenre (genreId, isbn) VALUES (?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, genreId);
            statement.setString(2, isbn);
            return statement;
        }, keyRing);
    }
    
    
    private static final class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            Genre genre = new Genre();
            genre.setGenreId(rs.getInt("genreId"));
            genre.setGenreName(rs.getString("genreName"));
            
            return genre;
        }
    }
}
