DROP DATABASE IF EXISTS PersonalLibraryTest;

CREATE DATABASE PersonalLibraryTest;

USE PersonalLibraryTest;

CREATE TABLE Role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
    roleName VARCHAR(10) NOT NULL
);

CREATE TABLE User(
	userName VARCHAR(256) PRIMARY KEY,
    email VARCHAR(256) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    xp INT NOT NULL,
    passwordHash VARCHAR(1024) NOT NULL,
    roleId INT,
    FOREIGN KEY fk_role_Id (roleId)
		REFERENCES Role (roleId)
);

CREATE TABLE Book(
	isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    subtitle VARCHAR(100) NULL,
    publisher VARCHAR(100) NOT NULL,
    pageCount INT NOT NULL,
    releaseDate DATE NOT NULL,
    synopsis MEDIUMTEXT NOT NULL
);

CREATE TABLE Genre(
	genreId INT PRIMARY KEY AUTO_INCREMENT,
    genreName VARCHAR(50) NOT NULL
);

CREATE TABLE BookGenre(
	genreId INT,
    isbn VARCHAR(13),
    FOREIGN KEY fk_genre_Id (genreId)
		REFERENCES Genre (genreId),
	FOREIGN KEY fk_isbn (isbn)
		REFERENCES Book (isbn)
);

CREATE TABLE Author(
	authorId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL
);

CREATE TABLE BookAuthor(
	isbn VARCHAR(13),
    authorId INT,
    FOREIGN KEY fk_isbn (isbn)
		REFERENCES Book (isbn),
	FOREIGN KEY fk_author_Id (authorId)
		REFERENCES Author (authorId)
);

CREATE TABLE UserBook(
	userName VARCHAR(256),
    isbn VARCHAR(13),
    hasRead BOOLEAN,
    isReading BOOLEAN,
    wantsToRead BOOLEAN,
    FOREIGN KEY fk_user_Name (userName)
		REFERENCES User (userName),
	FOREIGN KEY fk_isbn (isbn)
		REFERENCES Book (isbn)
);

CREATE TABLE Recommendation(
	recommendationId INT PRIMARY KEY AUTO_INCREMENT,
    value TINYINT NOT NULL,
    isbn VARCHAR(13),
    userName VARCHAR(256),
    FOREIGN KEY fk_isbn (isbn)
		REFERENCES Book (isbn),
	FOREIGN KEY fk_user_Name (userName)
		REFERENCES User (userName)
);

CREATE TABLE Challenge(
	challengeId INT PRIMARY KEY AUTO_INCREMENT,
    badgeImageURL VARCHAR(1024) NOT NULL,
    xpValue INT NOT NULL,
    challengeName VARCHAR(100) NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE UserChallenge(
	challengeId INT,
    userName VARCHAR(256),
    hasAccepted BOOLEAN,
    isComplete BOOLEAN,
    FOREIGN KEY fk_challenge_Id (challengeId)
		REFERENCES Challenge (challengeId),
	FOREIGN KEY fk_user_Name (userName)
		REFERENCES User (userName)
);

CREATE TABLE Level(
	level INT PRIMARY KEY AUTO_INCREMENT,
    requiredXP INT NOT NULL,
    imageURL VARCHAR(200) NOT NULL,
    rankName VARCHAR(50) NOT NULL
);

CREATE TABLE UserLevel(
	userName VARCHAR(20),
    level INT,
    FOREIGN KEY fk_level (level)
		REFERENCES Level(level),
	FOREIGN KEY fk_user_Name (userName)
		REFERENCES User (userName)
);

INSERT INTO Role (roleId, roleName) VALUES
	(1, "ADMIN"),
    (2, "USER");
    
INSERT INTO User (userName, email, firstName, lastName, xp, passwordHash) VALUES 
	('weedLazerz', 'user@123.com', 'Chon', 'Xiong', 100, '***'),
    ('abandonedHouseFly', 'user@1234.com', 'Duncan', 'Henry', 10, '!@#$'),
    ('urToast', 'user@12345.com', 'Edward', 'Murray', 25, '*@(#'); 
    
INSERT INTO Book (isbn, title, publisher, pageCount, releaseDate, synopsis) VALUES
	('1234567890123', 'Redshirts', 'Tor', 323, '2013-03-03', 'if you watch StarTrek you know what a Redshirt is'),
    ('0987654321', 'Siddhartha', 'Penguin Classics', 111, '1961-12-01', 'story of the Buddha'),
    ('4815162342', 'A Brief Summary of WWII', 'Cambridge Press', 989, '1999-08-25', 'BOOM BOOM BOOM POW POW POW SPLASH TATATATATATATATA SCREW YOU HITLER');

INSERT INTO Genre (genreId, genreName) VALUES
	(1, 'SciFi'),
    (2, 'Classic'),
    (3, 'Textbook');

INSERT INTO BookGenre (genreId, isbn) VALUES
	(1, '1234567890123'),
    (2, '0987654321'),
    (3, '4815162342');

INSERT INTO Author (authorId, name) VALUES
	(1, 'John Scalzi'),
    (2, 'Herman Hesse'),
    (3, 'Historian Duiker');

INSERT INTO BookAuthor(isbn, authorId) VALUES
	('1234567890123', 1),
    ('0987654321', 2),
    ('4815162342', 3);

INSERT INTO UserBook (userName, isbn, hasRead, isReading, wantsToRead) VALUES
	('urToast', '4815162342', false, false, true),
    ('urToast', '0987654321', true, false, false),
    ('weedLazerz', '0987654321', false, false, true),
    ('weedLazerz', '1234567890123', false, false, true),
    ('abandonedHouseFly', '1234567890123', false, true, false),
    ('abandonedHouseFly', '0987654321', true, false, false);

INSERT INTO Recommendation (recommendationId, value, isbn) VALUES
	(1, 2, '0987654321'),
    (2, 1, '0987654321');
    
-- INSERT INTO UserRecommendation (userName, recommendationId) VALUES
-- 	('abandonedHouseFly', 2),
--     ('urToast', 1);
    
INSERT INTO  Challenge (challengeId, badgeImageURL, xpValue, challengeName, description) VALUES
	(1, 'pic.jpg', 5, 'Dreaming of Androids', 'Read a sci-fi book'),
    (2, 'pic.png', 5, 'He who does not learn from history', 'Read a history book'),
    (3, 'pic.gif', 5, 'Dragon\'s Breath', 'Read a fantasy novel'),
    (4, 'pic.txt', 100, 'So It Goes', 'Read Slaughterhouse 5 by Kurt Vonnegut');
    
INSERT INTO UserChallenge (challengeId, userName, hasAccepted, isComplete) VALUES
	(2, 'abandonedHouseFly', true, true),
    (4, 'weedLazerz', true, true),
    (1, 'urToast', true, false);

INSERT INTO Level (level, imageURL, rankName, requiredXP) VALUES
	(1, 'pic.jpg', 'Unskilled', 0),
    (2, 'pic.jpg', 'Novitiate', 10),
    (3, 'pic.jpg', 'Beginner', 20),
    (4, 'pic.jpg', 'Novice', 30),
    (5, 'pic.jpg', 'Advanced-Beginner', 40);
    
    
INSERT INTO UserLevel (userName, level) VALUES
    ('urToast', 2),
    ('weedLazerz', 3),
    ('abandonedHouseFly', 1);