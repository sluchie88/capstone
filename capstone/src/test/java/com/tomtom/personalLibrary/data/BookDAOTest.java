///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tomtom.personalLibrary.data;
//
//import com.tomtom.personalLibrary.TestApplicationConfiguration;
//import com.tomtom.personalLibrary.models.Book;
//import com.tomtom.personalLibrary.models.Genre;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
///**
// *
// * @author TomTom
// */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = TestApplicationConfiguration.class)
//public class BookDAOTest {
//
//    @Autowired
//    BookDAO dao;
//
//    @BeforeEach
//    public void setUp() {
//        
//    }
//
//    @AfterEach
//    public void tearDown() {
//    }
//
//    /**
//     * Test of addBook method, of class BookDAO.
//     */
//    @Test
//    public void testAddDeleteBook() {
//        Book book = new Book();
//        book.setIsbn("1231231231234");
//        book.setPageCount(457);
//        book.setPublisher("Random House");
//        book.setTitle("Coraline");
//        book.setSynopsis("Girl kills her other mother.");
//        book.setDatePublished(LocalDate.of(2007, 03, 18));
//        
//        List<Genre> genres = new ArrayList<>();
//        Genre fantasy = new Genre();
//        fantasy.setGenreId(1);
//        fantasy.setGenreName("Fantasy");
//        
//        genres.add(fantasy);
//        book.setGenre(genres);
//        
//        dao.addBook(book);
//        
//        Book inDB = dao.getBookByISBN("1231231231234");
//        assertNotNull(inDB);
//        
//        dao.deleteBook(inDB);
//        assertThrows(EmptyResultDataAccessException.class,
//                () -> dao.getBookByISBN(inDB.getIsbn()));
//    }
//
//    /**
//     * Test of addHasReadBook method, of class BookDAO.
//     */
//    @Test
//    public void testAddGetHasReadBook() {
//        Book book = new Book();
//        book.setIsbn("1231231231234");
//        book.setPageCount(457);
//        book.setPublisher("Random House");
//        book.setTitle("Coraline");
//        book.setSynopsis("Girl kills her other mother.");
//        book.setDatePublished(LocalDate.of(2007, 03, 18));
//        
//        List<Genre> genres = new ArrayList<>();
//        Genre fantasy = new Genre();
//        fantasy.setGenreId(1);
//        fantasy.setGenreName("Fantasy");
//        
//        genres.add(fantasy);
//        book.setGenre(genres);
//        
//        dao.addBook(book);
//        
//        assertTrue(dao.addHasReadBook("1231231231234", "urToast"));
//      
//        List<Book> inDB = (dao.getBooksHaveRead("urToast"));
//        assertEquals(inDB.size(), 2);
//        
//        dao.deleteBook(book);
//    }
//
//    /**
//     * Test of addWantToReadBook method, of class BookDAO.
//     */
//    @Test
//    public void testAddGetWantToReadBook() {
//        Book book = new Book();
//        book.setIsbn("1231231231234");
//        book.setPageCount(457);
//        book.setPublisher("Random House");
//        book.setTitle("Coraline");
//        book.setSynopsis("Girl kills her other mother.");
//        book.setDatePublished(LocalDate.of(2007, 03, 18));
//        
//        List<Genre> genres = new ArrayList<>();
//        Genre fantasy = new Genre();
//        fantasy.setGenreId(1);
//        fantasy.setGenreName("Fantasy");
//        
//        genres.add(fantasy);
//        book.setGenre(genres);
//        
//        dao.addBook(book);
//        
//        assertTrue(dao.addWantToReadBook("1231231231234", "urToast"));
//        
//        List<Book> inDB = (dao.getWantToRead("urToast"));
//        assertEquals(inDB.size(), 2);
//        
//        dao.deleteBook(book);
//    }
//
//    /**
//     * Test of addCurrentlyReading method, of class BookDAO.
//     */
//    @Test
//    public void testAddGetCurrentlyReading() {
//        Book book = new Book();
//        book.setIsbn("1231231231234");
//        book.setPageCount(457);
//        book.setPublisher("Random House");
//        book.setTitle("Coraline");
//        book.setSynopsis("Girl kills her other mother.");
//        book.setDatePublished(LocalDate.of(2007, 03, 18));
//        
//        List<Genre> genres = new ArrayList<>();
//        Genre fantasy = new Genre();
//        fantasy.setGenreId(1);
//        fantasy.setGenreName("Fantasy");
//        
//        genres.add(fantasy);
//        book.setGenre(genres);
//                
//        dao.addBook(book);
//        
//        assertTrue(dao.addCurrentlyReading("1231231231234", "urToast"));
//        
//        Book inDB = dao.getCurrentlyReading("urToast");
//        assertNotNull(inDB);
//        
//        dao.deleteBook(book);
//    }
//
//}
