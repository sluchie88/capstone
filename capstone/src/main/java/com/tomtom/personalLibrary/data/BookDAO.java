/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Book;
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
public class BookDAO {

    private final JdbcTemplate jdbc;

    @Autowired
    public BookDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }
    
    public Book addBook(Book book){
        final String sql = "INSERT INTO Book(isbn, publisher, pageCount, "
                + "synopsis, title, subtitle, imageURL) "
                + "VALUES (?,?,?,?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getPublisher());
            statement.setInt(3, book.getPageCount());
            statement.setString(4, book.getSynopsis());
            statement.setString(5, book.getTitle());
            statement.setString(6, book.getSubtitle());
            statement.setString(7, book.getImageURL());
            return statement;
        }, keyRing);
        return book;
    }

    /**
     * adds a book to the db that a user has read
     *
     * @param book
     * @param userName
     * @return
     */
    public boolean addHasReadBook(String isbn, String userName) {
        final String sql = "INSERT INTO UserBook(isbn, userName, wantsToRead, isReading, hasRead) "
                + "VALUES (?,?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        int ret = jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, isbn);
            statement.setString(2, userName);
            statement.setBoolean(3, false);
            statement.setBoolean(4, false);
            statement.setBoolean(5, true);
            return statement;
        }, keyRing);
        
        return ret > 0;
    }

    /**
     * Adds a book to the db that a user wants to read
     *
     * @param book
     * @param userName
     * @return
     */
    public boolean addWantToReadBook(String isbn, String userName) {
        final String sql = "INSERT INTO UserBook(isbn, userName, wantsToRead, isReading, hasRead) "
                + "VALUES (?,?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        int ret = jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, isbn);
            statement.setString(2, userName);
            statement.setBoolean(3, true);
            statement.setBoolean(4, false);
            statement.setBoolean(5, false);
            return statement;
        }, keyRing);
        
        return ret > 0;
    }

    /**
     * Adds a book a user is currently reading to the db
     *
     * @param book
     * @param userName
     * @return
     */
    public boolean addCurrentlyReading(String isbn, String userName) {
        final String sql = "INSERT INTO UserBook (isbn, userName, wantsToRead, isReading, hasRead) "
                + "VALUES (?,?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();
        
        int ret = jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, isbn);
            statement.setString(2, userName);
            statement.setBoolean(3, false);
            statement.setBoolean(4, true);
            statement.setBoolean(5, false);
            return statement;
        }, keyRing);
        
        return ret > 0;
    }

    /**
     * Used to hydrate User objects. Grabs the book marked as currently reading
     * and returns it
     *
     * @param userName
     * @return
     */
    public Book getCurrentlyReading(String userName) {
        final String sql = "SELECT b.isbn, b.title, b.subtitle, b.publisher, "
                + "b.pageCount, b.synopsis, b.imageURL "
                + "FROM Book b "
                + "INNER JOIN UserBook ub ON b.isbn = ub.isbn "
                + "WHERE ub.isReading = 1 AND ub.userName = ?;";
        return jdbc.queryForObject(sql, new BookMapper(), userName);
    }

    /**
     * Used to hydrate User objects. Grabs books marked as want to read and
     * returns them in a list
     *
     * @param userName
     * @return
     */
    public List<Book> getWantToRead(String userName) {
        final String sql = "SELECT b.isbn, b.title, b.subtitle, b.publisher, "
                + "b.pageCount, b.synopsis, b.imageURL "
                + "FROM Book b "
                + "INNER JOIN UserBook ub ON b.isbn = ub.isbn "
                + "WHERE ub.wantsToRead IS TRUE AND ub.userName = ?;";
        return jdbc.query(sql, new BookMapper(), userName);
    }

    /**
     * Used to hydrate user objects. Grabs books marked as having already read
     * and returns them in a list
     *
     * @param userName
     * @return
     */
    public List<Book> getBooksHaveRead(String userName) {
        final String sql = "SELECT b.isbn, b.title, b.subtitle, b.publisher, "
                + "b.pageCount, b.synopsis, b.imageURL "
                + "FROM Book b "
                + "INNER JOIN UserBook ub ON b.isbn = ub.isbn "
                + "WHERE ub.hasRead IS TRUE AND ub.userName = ?;";
        return jdbc.query(sql, new BookMapper(), userName);
    }
    
        public boolean updateToHaveRead(String userName, String isbn) {
        final String sql = "UPDATE UserBook "
                + "SET hasRead = ?, "
                + "isReading = ?, "
                + "wantsToRead = ? "
                + "WHERE isbn LIKE ? AND userName LIKE ?;";
        return jdbc.update(sql, true, false, false, isbn, userName) > 0;
    }
    
    public boolean updateToCurrentlyReading(String userName, String isbn){
        final String sql = "UPDATE UserBook "
                + "SET hasRead = ?, "
                + "isReading = ?, "
                + "wantsToRead = ? "
                + "WHERE isbn LIKE ? AND userName LIKE ?;";
        return jdbc.update(sql, false, true, false, isbn, userName) > 0;
    }
    
    @Transactional
    public void deleteBook(Book book){
        final String rec = "DELETE FROM Recommendation "
                + "WHERE isbn = ?;";
        jdbc.update(rec, book.getIsbn());
        
        final String usB = "DELETE FROM UserBook "
                + "WHERE isbn = ?;";
        jdbc.update(usB, book.getIsbn());
        
        final String auB = "DELETE FROM BookAuthor "
                + "WHERE isbn = ?;";
        jdbc.update(auB, book.getIsbn());
        
        final String boo = "DELETE FROM Book "
                + "WHERE isbn = ?;";
        jdbc.update(boo, book.getIsbn());
    }

    public Book getBookByISBN(String isbn) {
        final String sql = "SELECT b.isbn, b.title, b.subtitle, b.publisher, "
                + "b.pageCount, b.synopsis, b.imageURL "
                + "FROM Book b "
                + "WHERE isbn = ?;";
        return jdbc.queryForObject(sql, new BookMapper(), isbn);
    }

    private static final class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            Book bk = new Book();
            bk.setIsbn(rs.getString("isbn"));
            bk.setTitle(rs.getString("title"));
            bk.setSubtitle(rs.getString("subtitle"));
            bk.setPublisher(rs.getString("publisher"));
            bk.setPageCount(rs.getInt("pageCount"));
            bk.setSynopsis(rs.getString("synopsis"));
            bk.setImageURL(rs.getString("imageURL"));

            return bk;
        }
    }
}
