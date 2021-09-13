let minLength = 6;
let maxLength = 50;

const LOWER_REGEX = /[a-z]{3,}/;

const UPPER_REGEX = /[A-Z]{3,}/;
let result = document.createElement("p");

const DIGIT_REGEX = /[\d]+/;

let input = document.getElementById("inputPassword");
let parent = input.parentElement;

parent.appendChild(result);
result.style.alignItems = "center";
result.classList = "pass";
input.addEventListener("blur", () => {

    result.textContent = '';

    let password = input.value;
    if (password.length < minLength || password.length > maxLength) {
        result.textContent = "";
        return;
    }
    console.log(password)
    console.log(isPasswordStrong(password));
    result.textContent = isPasswordStrong(password) ? "strong level of password" : "weak level of password";

    function isPasswordStrong(pass) {
        let upperMatch = pass.match(UPPER_REGEX);
        let lowerMatch = pass.match(LOWER_REGEX);
        let numMatch = pass.match(DIGIT_REGEX);

        return upperMatch && lowerMatch && numMatch;
    }
});
