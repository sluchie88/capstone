let requestURL = "https://www.googleapis.com/books/v1/volumes?q=";
let request = new XMLHttpRequest();
let apiKey = "&key=AIzaSyCFyXxr63Vw0UETFpTRl1J9dW74ldYsfvo";
let resultsRestrictions = "&printType=books&maxResults=40"

const baseURL = "http://localhost:8080/";

var library = new Array();
var currMinIndex = 0;
var currMaxIndex = 10;
var numSearches = 0;
var clickedBook;

// document.onload = function () {
//   library = new Array();
//   currMinIndex = 0;
//   currMaxIndex = 10;
//   numSearches = 0;
// }


$("#authorSearchButton").on("click", function () {
  library = new Array();
  currMinIndex = 0;
  currMaxIndex = 10;
  var authorName = $("#authorSearchBar")
    .val()
    .replace(" ", "+");
  let authorRequest = "inauthor:" + authorName;
  request.open("GET", requestURL + authorRequest + resultsRestrictions + apiKey);
  request.responseType = "json";
  request.send();

  request.onload = function () {
    const books = request.response;
    addAllToLibrary(books);
    if (numSearches == 0) {
      $("#prev").toggle();
      $("#next").toggle();
    }
    numSearches++;
    showBooks();
  };
});

$("#titleSearchButton").on("click", function () {
  library = new Array();
  currMinIndex = 0;
  currMaxIndex = 10;
  var bookTitle = $("#titleSearchBar")
    .val()
    .replace(" ", "+");
  let titleRequest = "intitle:" + bookTitle;
  request.open("GET", requestURL + titleRequest + resultsRestrictions + apiKey);
  request.responseType = "json";
  request.send();

  request.onload = function () {
    const books = request.response;
    addAllToLibrary(books);
    if (numSearches == 0) {
      $("#prev").toggle();
      $("#next").toggle();
    }
    numSearches++;
    showBooks();
  };
});

//Displays books found in search
function showBooks() {
  if (currMinIndex <= 0) {
    $("#prev")
  }
  var tempLibrary = new Array();
  $("#results").empty();
  for (i = currMinIndex; i < currMaxIndex; i++) {
    tempLibrary.push(library[i]);
    title = $(
      '<h5 class="center-align white-text">' +
      library[i].volumeInfo.title +
      "</h5>"
    );
    author = $(
      '<h5 class="center-align white-text">By: ' +
      library[i].volumeInfo.authors +
      "</h5>"
    );
    //self-Link is a direct link to the JSON object
    img = $(
      '<img id="bookCover" data-isbn="' +
      library[i].volumeInfo.industryIdentifiers[0].identifier +
      '"' +
      ' data-json="' +
      library[i].volumeInfo +
      '"' +
      ' data-index="' +
      i +
      '"' +
      " src=" +
      library[i].volumeInfo.imageLinks.thumbnail +
      ">" +
      "</img>"
    );
    $("#results").append(title, author, img);
  }
  return false;
}

$("#next").on("click", function () {
  currMinIndex = currMaxIndex - 1;
  currMaxIndex = currMinIndex + 10;
  showBooks();
});

$("#prev").on("click", function () {
  currMaxIndex = currMinIndex + 1;
  currMinIndex = currMaxIndex - 10;
  showBooks();
});

$("#results").on("click", "#bookCover", function () {
  console.log(library);
  var isbn = this.dataset.isbn;
  //need to make into my own JSON object
  getGenres(library[this.dataset.index]);
});

//makes serialized JSON objects that work with my DB

function getGenres(gson) {
  var reqLink = gson.selfLink;
  var genres;
  request.open("GET", reqLink);

  request.responseType = "json";
  request.send();
  request.onload = function () {
    actuallyGetsGenres(request.response);
  };
}

function actuallyGetsGenres(gson) {
  clickedBook = {
    isbn: gson.volumeInfo.industryIdentifiers[0].identifier,
    title: gson.volumeInfo.title,
    subtitle: gson.volumeInfo.subtitle,
    publisher: gson.volumeInfo.publisher,
    authors: gson.volumeInfo.authors,
    datePublished: gson.volumeInfo.publishedDate,
    synopsis: gson.volumeInfo.description,
    genre: gson.volumeInfo.categories,
    pageCount: gson.volumeInfo.pageCount,
    imageURL: gson.volumeInfo.imageLinks.thumbnail
  };
  $.ajax({
    type: "POST",
    url: baseURL + "bookInfo/",
    data: JSON.stringify(clickedBook),
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    success: function (response) {
      window.location.replace(baseURL + "bookInfo/" + clickedBook.isbn);
    },
    error: handleErr
  });
}

function addAllToLibrary(bookList) {
  for (i = 0; i <= bookList.items.length; i++) {
    library.push(bookList.items[i]);
  }
}

//basic error handler. Need to expand for different errors
function handleErr(status, err, xhr) {
  $("#errorHeading")
    .empty()
    .append(status);
  $("#errorContents")
    .empty()
    .append(err);
  $("#errorAlert").show();
}
