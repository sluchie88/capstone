/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Author;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TomTom
 */
@Repository
public class AuthorDAO {
    
    private JdbcTemplate jdbc;
    
    @Autowired
    public AuthorDAO (JdbcTemplate pants){
        this.jdbc = pants;
    }
    
    /**
     * retrieves an author from the DB
     * @param author
     * @return 
     */
    public Author findAuthor(Author author){
        final String sql = "SELECT authorId "
                + "FROM Author "
                + "WHERE authorId = ?;";
        return jdbc.queryForObject(sql, new AuthorMapper(), author.getAuthorId());
    }
    
    public boolean findAuthorByName(String authorName){
        final String sql = "SELECT authorId, name "
                + "FROM Author "
                + "WHERE name = ?;"; 
        Author auth = jdbc.queryForObject(sql, new AuthorMapper(), authorName);
        return auth == null;
    }
    
    /**
     * Adds a new author to the database
     * @param author
     * @return 
     */
    public Author addAuthor(Author author){
        final String sql = "INSERT INTO Author(name) "
                + "VALUES (?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author.getName());
            return statement;
        }, keyRing);
        
        author.setAuthorId(keyRing.getKey().intValue());
        return author;
    }
    
    /**
     * Gets the list of authors associated with a book
     * @param isbn
     * @return 
     */
    public List<Author> getAuthorsForBook(String isbn){
        final String sql = "SELECT a.authorId, a.name "
                + "FROM Author a "
                + "INNER JOIN BookAuthor ba ON a.authorId = ba.authorId "
                + "WHERE ba.isbn = ?";
        return jdbc.query(sql, new AuthorMapper(), isbn);
    }
    
    /**
     * Admins allowed to edit authors information if they find 
     * something that happens to be wrong
     * @param author
     * @return 
     */
    public boolean editAuthor(Author author){
        final String sql = "UPDATE Author SET "
                + "name = ? "
                + "WHERE authorId = ?;";
        return jdbc.update(sql, author.getName(), author.getAuthorId()) > 0;
    }
    
    /**
     * Available to admins for removing an author from the DB
     * @param author 
     */
    @Transactional
    public void deleteAuthor(Author author){
        final String ab = "DELETE FROM BookAuthor "
                + "WHERE authorId = ?;";
        jdbc.update(ab, author.getAuthorId());
        
        final String sql = "DELETE FROM Author "
                + "WHERE authorId = ?;";
        jdbc.update(sql, author.getAuthorId());
    }

    public int getAuthorIdByName(String name) {
        final String sql = "SELECT authorId, name "
                + "FROM Author "
                + "WHERE name LIKE ?;";
        Author a = jdbc.queryForObject(sql, new AuthorMapper(), name);
        return a.getAuthorId();
    }

    public void addBookAuthor(int authorId, String isbn) {
        final String sql = "INSERT INTO BookAuthor (authorId, isbn) VALUES (?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, authorId);
            statement.setString(2, isbn);
            return statement;
        }, keyRing);
    }
    
    private static final class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            Author auth = new Author();
            auth.setAuthorId(rs.getInt("authorId"));
            auth.setName(rs.getString("name"));
            
            return auth;
        }
    }
}
