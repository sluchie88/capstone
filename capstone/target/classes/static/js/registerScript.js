const baseURL = "http://localhost:8080/";

$("#registerUserButton").on("click", function () {
    var password = $("#password1").val();
    var matchPas = $("#password2").val();
    if (password == matchPas) {
        var user = {
            userName: $("#usernameRegister").val(),
            password: password,
            email: $("#emailRegister").val(),
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val()
        }
        $.ajax({
            type: "POST",
            url: baseURL + "register",
            data: JSON.stringify(user),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            success: function () {
                window.location.replace(baseURL + "login.html");
            },
            error: handleErr
        })
    } else {

    }

    $("#cancelRegistrationButton").on("click", function () {
        window.location.replace(baseURL);
    });

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

});

