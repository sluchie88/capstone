/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.AppUser;
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
public class AppUserDAO {

    private final JdbcTemplate jdbc;

    @Autowired
    public AppUserDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }
    
    public List<AppUser> findAll(){
        final String sql = "SELECT * "
                + "FROM User;";
        return jdbc.query(sql, new UserMapper());
    }

    /**
     * Finds user by their username and passes back up
     *
     * @param userName
     * @return
     */
    public AppUser findUserByUsername(String userName) {
        final String sql = "SELECT username, "
                + "firstName, lastName, "
                + "xp, email, roleId, level FROM User "
                + "WHERE username = ?;";
        return jdbc.queryForObject(sql, new UserMapper(), userName);
    }
    
    /**
     * Used to update a user's information in the DB
     *
     * @param user
     * @return
     */
    public boolean editUser(AppUser user, String prevUserName) {
        final String sql = "UPDATE User SET "
                + "userName = ?, "
                + "firstName = ?, "
                + "lastName = ?, "
                + "xp = ?, "
                + "email = ?, "
                + "level = ? "
                + "WHERE userName = ?;";
        
        return jdbc.update(sql, user.getUserName(), 
                user.getFirstName(), 
                user.getLastName(), 
                user.getExperiencePoints(),
                user.getEmail(),
                prevUserName,
                user.getLevel()) > 0;
    }

    /**
     * Allows for a user to change their password. Returns true if successfully
     * changed, false if unsuccessful
     *
     * @param passwordHash
     * @return
     */
    public boolean changeUserPassword(String passwordHash) {
        //I have no idea how to accomplish this
        //will look around online
        return true;
    }

    /**
     * Adds user to the database and returns AppUser object if successful
     *
     * @param user
     * @param passwordHash
     * @return user
     */
    public AppUser addUser(AppUser user, String passwordHash) {
        final String sql = "INSERT INTO User(userName, firstName, lastName, "
                + "xp, passwordHash, email, roleId, level) "
                + "VALUES(?,?,?,?,?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setInt(4, user.getExperiencePoints());
            statement.setString(5, passwordHash);
            statement.setString(6, user.getEmail());
            statement.setInt(7, user.getRoleId());
            statement.setInt(8, 1);
            return statement;
        }, keyRing);
        return user;
    }

    /**
     * Removes a user from the database. Must input password in order to remove,
     * if password hash doesn't match, can't remove
     *
     * @param user
     * @param passwordHash
     * @return
     */
    @Transactional
    public void deleteUser(AppUser user, String passwordHash) {
        final String userChal = "DELETE FROM UserChallenge "
                + "WHERE userName = ?;";
        jdbc.update(userChal, user.getUserName());
        
        final String userLev = "DELETE FROM UserLevel "
                + "WHERE userName = ?;";
        jdbc.update(userLev, user.getUserName());
        
        final String userRec = "DELETE FROM UserRecommendation "
                + "WHERE userName = ?;";
        jdbc.update(userLev, user.getUserName());
        
        final String userBoo = "DELETE FROM UserBook "
                + "WHERE userName = ?;";
        jdbc.update(userLev, user.getUserName());
        
        final String sql = "DELETE FROM User "
                + "WHERE userName = ?;";
        jdbc.update(sql, user.getUserName());
    }

    private static final class UserMapper implements RowMapper<AppUser> {

        @Override
        public AppUser mapRow(ResultSet rs, int i) throws SQLException {
            AppUser us = new AppUser();
            us.setFirstName(rs.getString("firstName"));
            us.setLastName(rs.getString("lastName"));
            us.setUserName(rs.getString("userName"));
            us.setExperiencePoints(rs.getInt("xp"));
            us.setEmail(rs.getString("email"));
            us.setLevel(rs.getInt("level"));
            return us;
        }
    }
}
