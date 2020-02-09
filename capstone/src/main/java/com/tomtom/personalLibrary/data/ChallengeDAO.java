/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Challenge;
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
public class ChallengeDAO {

    private JdbcTemplate jdbc;

    @Autowired
    public ChallengeDAO(JdbcTemplate pants) {
        this.jdbc = pants;
    }

    public Challenge getChallenge(int challengeId){
        final String sql = "SELECT challengeId, badgeImageURL, xpValue, "
                + "challengeName, description "
                + "FROM Challenge "
                + "WHERE challengeId = ?;";
        return jdbc.queryForObject(sql, new ChallengeMapper(), challengeId);
    }
    
    /**
     * adds a challenge to the db
     *
     * @param challenge
     * @return
     */
    public Challenge addChallenge(Challenge challenge) {
        final String sql = "INSERT INTO Challenge(badgeImageURL, xpValue, "
                + "challengeName, description) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyRing = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, challenge.getBadgeURL());
            statement.setInt(2, challenge.getExperienceValue());
            statement.setString(3, challenge.getChallengeName());
            statement.setString(4, challenge.getChallengeDescription());
            return statement;
        }, keyRing);

        challenge.setChallengeId(keyRing.getKey().intValue());
        return challenge;
    }

    /**
     * available to admins, allows for removing challenges from the db
     *
     * @param challenge
     * @return
     */
    @Transactional
    public void removeChallenge(Challenge challenge) {
        final String uc = "DELETE FROM UserChallenge "
                + "WHERE challengeId = ?;";
        jdbc.update(uc, challenge.getChallengeId());
        
        final String sql = "DELETE FROM Challenge "
                + "WHERE challengeId = ?;";
        jdbc.update(sql, challenge.getChallengeId());
    }

    /**
     * Used to edit a challenge already in the db
     *
     * @param challenge
     * @return
     */
    public boolean editChallenge(Challenge challenge, int challengeId) {
        final String sql = "UPDATE Challenge SET "
                + "badgeImageURL = ?, "
                + "xpValue = ?, "
                + "challengeName = ?, "
                + "description = ? "
                + "WHERE challengeId = ?;";
        return jdbc.update(sql, challenge.getBadgeURL(),
                challenge.getExperienceValue(),
                challenge.getChallengeName(),
                challenge.getChallengeDescription(),
                challenge.getChallengeId()) > 0;
    }

    /**
     * Used for hydrating a user. This will grab any incomplete challenges a
     * user has attached to their profile.
     *
     * @param userName
     * @return
     */
    List<Challenge> getIncompleteChallenges(String userName) {
        final String sql = "SELECT c.challengeId, c.challengeName, "
                + "c.description, c.xpValue, c.badgeImageURL "
                + "FROM Challenge c "
                + "INNER JOIN UserChallenge uc ON uc.challengeId = c.ChallengeId "
                + "INNER JOIN User u ON uc.userName = u.userName "
                + "WHERE hasAccepted IS TRUE AND isComplete IS FALSE "
                + "AND u.userName = ?;";
        return jdbc.query(sql, new ChallengeMapper(), userName);
    }

    /**
     * Used for hydrating a User. this will grab any completed challenges a user
     * has attached to their profile
     *
     * @param userName
     * @return
     */
    List<Challenge> getCompleteChallenges(String userName) {
        final String sql = "SELECT c.challengeId, c.challengeName, "
                + "c.description, c.xpValue, c.badgeImageURL "
                + "FROM Challenge c "
                + "INNER JOIN UserChallenge uc ON uc.challengeId = c.ChallengeId "
                + "INNER JOIN User u ON uc.userName = u.userName "
                + "WHERE hasAccepted IS TRUE AND isComplete IS TRUE "
                + "AND u.userName = ?;";
        return jdbc.query(sql, new ChallengeMapper(), userName);
    }

    //need to check UserCahllengeTable when hydrating a user Object
    private static final class ChallengeMapper implements RowMapper<Challenge> {

        @Override
        public Challenge mapRow(ResultSet rs, int i) throws SQLException {
            Challenge ch = new Challenge();
            ch.setChallengeId(rs.getInt("challengeId"));
            ch.setBadgeURL(rs.getString("badgeImageURL"));
            ch.setExperienceValue(rs.getInt("xpValue"));
            ch.setChallengeDescription(rs.getString("description"));
            ch.setChallengeName(rs.getString("challengeName"));

            return ch;
        }

    }
}
