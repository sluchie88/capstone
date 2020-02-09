/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.models.Author;
import com.tomtom.personalLibrary.models.Book;
import com.tomtom.personalLibrary.models.Challenge;
import com.tomtom.personalLibrary.models.AppUser;
import com.tomtom.personalLibrary.models.Level;
import com.tomtom.personalLibrary.models.Recommendation;
import com.tomtom.personalLibrary.models.UserDto;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

/**
 *
 * @author TomTom
 */
@Component
public class UnitOfWork {

    private final BookDAO bookDAO;
    private final ChallengeDAO challengeDAO;
    private final AppUserDAO userDAO;
    private final AuthorDAO authorDAO;
    private final LevelDAO levelDAO;
    private final RecommendationDAO recDAO;
    private final GenreDAO genreDAO;
    private final UserDtoDAO udtoDAO;

    public UnitOfWork(BookDAO bDAO,
            ChallengeDAO cDAO,
            AppUserDAO uDAO,
            AuthorDAO aDAO,
            RecommendationDAO rDao,
            LevelDAO lDao,
            GenreDAO gDao,
            UserDtoDAO udto) {

        this.bookDAO = bDAO;
        this.challengeDAO = cDAO;
        this.userDAO = uDAO;
        this.authorDAO = aDAO;
        this.levelDAO = lDao;
        this.recDAO = rDao;
        this.genreDAO = gDao;
        this.udtoDAO = udto;
    }

    /**
     * Pass through methods for books
     */
    public Book addBook(Book book) {
        Book ret = bookDAO.addBook(book);
        addGenre(ret);
        addAuthor(ret);
//        for (Genre g : book.getGenre()) {
//            if (!genreExists(g.getGenreName())) {
//                genreDAO.addGenre(g);
//            }
//        }
//        for (Genre zhe : book.getGenre()) {
//            zhe.setGenreId(genreDAO.getIdByGenreName(zhe.getGenreName()));
//            genreDAO.addBookGenre(zhe.getGenreId(), book.getIsbn());
//        }
        return ret;
    }

    /**
     *
     * @param book
     */
    private void addGenre(Book book) {
        book.getGenre().stream().filter((g) -> (!genreExists(g.getGenreName()))).forEachOrdered((g) -> {
            genreDAO.addGenre(g);
        });
        book.getGenre().stream().map((zhe) -> {
            zhe.setGenreId(genreDAO.getIdByGenreName(zhe.getGenreName()));
            return zhe;
        }).forEachOrdered((zhe) -> {
            genreDAO.addBookGenre(zhe.getGenreId(), book.getIsbn());
        });
    }

    /**
     *
     * @param book
     */
    private void addAuthor(Book book) {
        for (Author a : book.getAuthors()) {
            String currAuthName = a.getName();
            if (!authorExists(currAuthName)) {
                authorDAO.addAuthor(a);
            }
        }
        for (Author auth : book.getAuthors()) {
            auth.setAuthorId(authorDAO.getAuthorIdByName(auth.getName()));
            authorDAO.addBookAuthor(auth.getAuthorId(), book.getIsbn());
        }
    }

    /**
     * Finds a book and hydrates it
     *
     * Two catch blocks because recs and book could be empty, but a book could
     * exist with no recommendations
     *
     * @param isbn
     * @return
     */
    public Book getBookByISBN(String isbn) {
        Book ret = new Book();
        try {
            ret = bookDAO.getBookByISBN(isbn);
            ret.setAuthors((authorDAO.getAuthorsForBook(isbn)));
            ret.setGenre(genreDAO.getGenreForBook(isbn));
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
        try {
            ret.setRecommendations(recDAO.findRecommendationByIsbn(isbn));
        } catch (EmptyResultDataAccessException erdae) {
            ret.setRecommendations(null);
        }
        return ret;
    }

    public boolean addHasReadBook(Book book, String userName) {
        return bookDAO.addHasReadBook(book.getIsbn(), userName);
    }

    public boolean addWantToReadBook(Book book, String userName) {
        return bookDAO.addWantToReadBook(book.getIsbn(), userName);
    }

    public boolean addCurrentlyReadingBook(Book book, String userName) {
        return bookDAO.addCurrentlyReading(book.getIsbn(), userName);
    }

    /**
     * AppUser pass through methods
     */
    public AppUser addUser(AppUser user, String passwordHash) {
        return userDAO.addUser(user, passwordHash);
    }

    public boolean editUser(AppUser user, String passwordHash) throws ObjectNotInDatabaseException {

        return userDAO.editUser(user, passwordHash);

    }

    //may not need or is too far above my head at the moment
    public boolean changeUserPassword(AppUser user, String newPasswordHash) {
        //no idea how to do this exactly
        return false;
    }

    /**
     * Challenge pass through methods
     */
    public Challenge addChallenge(Challenge challenge) {
        return challengeDAO.addChallenge(challenge);
    }

    public boolean editChallenge(Challenge challenge, int challengeId) {
        return challengeDAO.editChallenge(challenge, challengeId);
    }

    /**
     * Reaches into UserDAO and finds user by their username and returns if the
     * user exists. If not, throws an exception that the user does not exist.
     *
     * @param userName
     * @return user
     * @throws ObjectNotInDatabaseException
     */
    public AppUser getUserInfo(String userName) throws ObjectNotInDatabaseException {
        AppUser user = userDAO.findUserByUsername(userName);
        if (user != null) {
            user = hydrateUserChallenges(user);
            user = hydrateUserBooks(user);
            user = hydrateUserRecommendations(user);
            Level level = hydrateLevelInfo(user.getLevel());
            user.setCurrentLevel(level);
        } else {
            throw new ObjectNotInDatabaseException("User does not exist.");
        }
        return user;
    }

    /**
     * This method reaches into the BookDAO and grabs all books associated with
     * a user.
     *
     * @param user
     * @return user
     */
    private AppUser hydrateUserBooks(AppUser user) {
        Book bk = new Book();
        List<Book> wantRead, haveRead;
        try {
            bk = bookDAO.getCurrentlyReading(user.getUserName());
        } catch (Exception erdae) {
            bk = null;
        }
        try{
            wantRead = bookDAO.getWantToRead(user.getUserName());
            
        }catch(Exception ex){
            wantRead = null;
        }try{
            haveRead = bookDAO.getBooksHaveRead(user.getUserName());
        }catch(Exception ex){
            haveRead = null;
        }
        user.setCurrentlyReading(bk);
        user.setHasRead(haveRead);
        user.setWantToRead(wantRead);
        return user;
    }

    /**
     * This method reaches into the ChallengeDAO to get the challenges
     * associated with a user and adds them to it
     *
     * @param user
     * @return user
     */
    private AppUser hydrateUserChallenges(AppUser user) {
        try {
            user.setIncompleteChallenges(challengeDAO.getIncompleteChallenges(user.getUserName()));
            user.setCompleteChallenges(challengeDAO.getCompleteChallenges(user.getUserName()));
            return user;
        } catch (Exception erdae) {
            return null;
        }

    }

    /**
     * Used to hydrate a user object and grab the list of all their
     * recommendations
     *
     * @param user
     * @return
     */
    private AppUser hydrateUserRecommendations(AppUser user) {
        try {
            user.setRecommendations(recDAO.findAllUserRecommendations(user.getUserName()));
            return user;
        } catch (Exception erdae) {
            return null;
        }
    }

    /**
     * Returns the full list of authors associated with a book. Used for
     * hydrating book objects
     *
     * @param book
     * @return List<Author>
     */
    private List<Author> hydrateBookAuthors(Book book) {
        List<Author> authors = authorDAO.getAuthorsForBook(book.getIsbn());
        if (authors.isEmpty()) {
            return null;
        } else {
            return authors;
        }
    }

    //check genreDAO for an existing genre and returns false if does not exist, true if exists
    private boolean genreExists(String genreName) {
        return !genreDAO.findGenreByName(genreName);
    }

    private boolean authorExists(String authorName) {
        try {
            authorDAO.findAuthorByName(authorName);
        } catch (EmptyResultDataAccessException erdae) {
            return false;
        }
        return true;
    }

    public UserDto findByUsername(String userName) {
        return udtoDAO.findByUsername(userName);
    }

    public boolean updateToHaveReadBook(String userName, Book finished) {
        return bookDAO.updateToHaveRead(userName, finished.getIsbn());
    }

    private Level hydrateLevelInfo(int level) {
        return levelDAO.getLevel(level);
    }

    public Recommendation addRecommendation(String username, Recommendation rec) {
        Recommendation existing = new Recommendation(rec.getIsbn());
        try{
            existing = recDAO.getRecommendation(username, rec.getIsbn());
            return existing;
        }catch (EmptyResultDataAccessException erdae){
            return recDAO.addRecommendation(rec, username);
        }
    }


}
