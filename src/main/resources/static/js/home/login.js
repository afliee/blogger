$(document).ready(function () {
    const btnLogin = $(".login100-form-btn");

    btnLogin.click(function (e) {
        e.preventDefault();

        const username = $("#username").val();
        const password = $("#pass").val();


        $.ajax({
            url: "/api/auth/authenticate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                username: username,
                password: password,
                role: "USER"
            }),
            success: function (data) {
                localStorage.setItem("token", data.token);
                window.location.href = "/";
            },
            error: function (error) {
                console.log(error);
            }
        })
    })
})