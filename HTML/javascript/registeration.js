function validateForm() {
    var username = document.getElementById("username").value;
    if (username == "") {
        alert("Name must be filled out");
        return false;
    }
    var email = document.getElementById("email").value;

    var password = document.getElementById("password").value;
    var confirm_password = document.getElementById("confirm_password").value;
    var phone = document.getElementById("phone").value;
    var dob = document.getElementById("DOB").value;

}