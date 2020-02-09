/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.TestApplicationConfiguration;
import com.tomtom.personalLibrary.models.Challenge;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
/**
 *
 * @author TomTom
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ChallengeDAOTest {
    
    @Autowired
    ChallengeDAO dao;
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetChallenge(){
        Challenge fromDB = dao.getChallenge(1);
        assertTrue(fromDB.getChallengeName().equals("Dreaming of Androids"));
        assertThrows(EmptyResultDataAccessException.class,
                () -> dao.getChallenge(100));
    }    
    
    /**
     * Test of removeChallenge method, of class ChallengeDAO.
     */
    @Test
    public void testAddEditRemoveChallenge() {
        Challenge chall = new Challenge();
        chall.setChallengeId(5);
        chall.setBadgeURL("pic.doc");
        chall.setExperienceValue(10);
        chall.setChallengeName("Doorstopper");
        chall.setChallengeDescription("Read a 1000+ page book");
        
        Challenge inDB = dao.addChallenge(chall);
        assertEquals(chall, inDB);
        
        chall.setChallengeDescription("Read a book that is more than one thousand pages");
        assertTrue(dao.editChallenge(chall, chall.getChallengeId()));
        
        dao.removeChallenge(chall);
        assertThrows(EmptyResultDataAccessException.class,
                () -> dao.getChallenge(chall.getChallengeId()));
    }


    /**
     * Test of getIncompleteChallenges method, of class ChallengeDAO.
     */
    @Test
    public void testGetIncompleteChallenges() {
        assertTrue((dao.getIncompleteChallenges("urToast")).size() == 1);
    }

    /**
     * Test of getCompleteChallenges method, of class ChallengeDAO.
     */
    @Test
    public void testGetCompleteChallenges() {
        assertTrue((dao.getCompleteChallenges("weedLazerz")).size() == 1);
    }
    
}
