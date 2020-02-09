let requestURL = "https://www.googleapis.com/books/v1/volumes?q=";
let request = new XMLHttpRequest();
let apiKey = "&key=AIzaSyCFyXxr63Vw0UETFpTRl1J9dW74ldYsfvo";

const baseURL = "http://localhost:8080/userInfo/";

var lessText = true;
var currUser = $("#currentUser").val();

document.onload($.ajax({
    type: "GET",
    url: baseURL + currUser,
    'dataType': 'json',
    success: function (data) {
    },
    error: handleErr
})
);



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