let requestURL = "https://www.googleapis.com/books/v1/volumes?q=";
let request = new XMLHttpRequest();
let apiKey = "&key=AIzaSyCFyXxr63Vw0UETFpTRl1J9dW74ldYsfvo";

const baseURL = "http://localhost:8080/";

var lessText = true;

document.onload($.ajax({
    type: "GET",
    url: baseURL + "bookInfo/" + data.isbn,
    'dataType': 'json',
    success: function (data) {
        displayBook(data)
    },
    error: handleErr()
})
);

function showText

$("#radioSubmit")

// $("#results").on("click", "#bookCover", function () {
//     console.log(library);
//     var isbn = this.dataset.isbn;
//     //need to make into my own JSON object
//     var jason = makeJSON(library[this.dataset.index]);
//     $.ajax({
//       type: "POST",
//       url: baseURL + "bookInfo/" + isbn,
//       data: JSON.stringify(jason),
//       headers: {
//         "Accept": "application/json",
//         "Content-Type": "application/json"
//       },
//       success: function () {
//         window.location.replace("bookInfo/" + isbn);
//       },
//       error: handleErr
//     });
//   });
