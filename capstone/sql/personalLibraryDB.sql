DROP DATABASE IF EXISTS PersonalLibrary;

CREATE DATABASE PersonalLibrary;

USE PersonalLibrary;

CREATE TABLE Role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
    roleName VARCHAR(10) NOT NULL
);

CREATE TABLE Level(
	level INT PRIMARY KEY AUTO_INCREMENT,
    requiredXP INT NOT NULL,
    imageURL VARCHAR(200) NULL,
    rankName VARCHAR(50) NOT NULL
);

CREATE TABLE User(
	userName VARCHAR(256) PRIMARY KEY,
    email VARCHAR(256) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    xp INT NOT NULL,
    passwordHash VARCHAR(1024) NOT NULL,
    roleId INT,
    level INT,
    FOREIGN KEY fk_role_Id (roleId)
		REFERENCES Role (roleId),
	FOREIGN KEY fk_level (level)
		REFERENCES Level (level)
);

CREATE TABLE Book(
	isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    subtitle VARCHAR(100) NULL,
    publisher VARCHAR(100) NOT NULL,
    pageCount INT NOT NULL,
    synopsis MEDIUMTEXT NOT NULL,
    imageURL VARCHAR(1024) NULL
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

INSERT INTO Role (roleId, roleName) VALUES
	(1, "ADMIN"),
    (2, "USER");
    
INSERT INTO Level (level, requiredXP, rankName) VALUES 
	(1, 10, "initiate"),
    (2, 20, "beginner"),
    (3, 30, "novitiate"),
    (4, 40, "literate"),
    (5, 50, "hobbyist"),
    (6, 60, "amature librarian"),
    (7, 70, "conniseur"),
    (8, 80, "avid"),
    (9, 90, "voracious");