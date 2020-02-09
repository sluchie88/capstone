/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.TestApplicationConfiguration;
import com.tomtom.personalLibrary.models.Author;
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
public class AuthorDAOTest {

    @Autowired
    AuthorDAO dao;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addAuthor method, of class AuthorDAO.
     */
    @Test
    public void testAddEditDeleteAuthor() {
        Author auth = new Author();
        auth.setAuthorId(4);
        auth.setName("James Mangold");
        
        Author inDB = dao.addAuthor(auth);
        assertEquals(auth, inDB);
        
        auth.setName("Ginny Mangold");
        assertTrue(dao.editAuthor(auth));
        
        dao.deleteAuthor(auth);
        assertThrows(EmptyResultDataAccessException.class,
                () -> dao.findAuthor(inDB));
    }

    /**
     * Test of getAuthorsForBook method, of class AuthorDAO.
     */
    @Test
    public void testGetAuthorsForBook() {
        assertNotNull(dao.getAuthorsForBook("1234567890123"));
    }
}
