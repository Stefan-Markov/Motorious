let username = document.getElementById('username').value;

async function dataFetch() {
    let response = await fetch(`http://localhost:8080/profile-rest/edit/${username}`);
    let data = await response.json();
    await fillInputs(data);
}

async function fillInputs(data) {

    let firstNameInput = document.getElementById('inputFirstName');
    let lastNameInput = document.getElementById('inputLastName');
    let emailNameInput = document.getElementById('inputEmail');

    firstNameInput.value = data.firstName;
    lastNameInput.value = data.lastName;
    emailNameInput.value = data.email;
}

dataFetch();