/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.UserDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TomTom
 */
@Repository
public class UserDtoDAO {

    JdbcTemplate jdbc;

    @Autowired
    public UserDtoDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }
    
    public UserDto findByUsername(String userName) {
        final String sql = "SELECT userName, email, "
                + "passwordHash "
                + "FROM User "
                + "WHERE userName LIKE ?;";
        return jdbc.queryForObject(sql, new UserDtoMapper(), userName);
    }
    
    private final class UserDtoMapper implements RowMapper<UserDto>{

        @Override
        public UserDto mapRow(ResultSet rs, int i) throws SQLException {
            UserDto ud = new UserDto();
            ud.setUserName(rs.getString("userName"));
            ud.setEmail(rs.getString("email"));
            ud.setPassword(rs.getString("passwordHash"));
            return ud;
        }
        
    }
}
