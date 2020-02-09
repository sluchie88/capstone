let requestURL = "https://www.googleapis.com/books/v1/volumes?q=";
let request = new XMLHttpRequest();
let apiKey = "&key=AIzaSyCFyXxr63Vw0UETFpTRl1J9dW74ldYsfvo";

const baseURL = "http://localhost:8080/";
const currISBN = $("#bookISBN").val();

var lessText = true;
var currUser = $("#currentUser").val();

// document.onload($.ajax({
//     type: "GET",
//     url: baseURL + "bookInfo/" + data.isbn,
//     'dataType': 'json',
//     success: function (data) {
//     },
//     error: handleErr()
// })
// );

//need to send all at once in Ajax, OR split into two ajax calls
$("#submitAll").on("click", ".bookChoices", ".rating", function () {
    var status = $("#haveWantCurr").val();
    if (status != null) {
        $.ajax({
            type: "POST",
            url: baseURL + "addWantCurr/" + currUser,
            'dataType': 'json',
            data: JSON.stringify({
                status: status,
                isbn: currISBN
            }),
            success: function (data) {
                displayAlert("Book successfully added to your library!");
            },
            error: handleErr()
        });
    }

    var review = $("#dropDownMenu").val();
    if (review != null) {
        $.ajax({
            type: "POST",
            url: baseURL + "review/" + currUser,
            'dataType': 'json',
            success: function (data) {
                displayAlert("Your opinion has been recorded. Thanks for sharing!");
            },
            error: handleErr()
        });
    }
});

function displayAlert(message) {
    $("#accountUpdatedAlert").append(message).toggle();
    setTimeout(hideAlert, 1200);
}

function hideAlert() {
    $("#accountUpdatedAlert").toggle();
}

$("#mainNavBar").on("click", "#navLink", function () {
    var endURL = $("#navLink").val();
    window.location.replace(baseURL + endURL);
});
