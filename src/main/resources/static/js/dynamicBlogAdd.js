let nameInput = document.getElementById('name');
let contentInput = document.getElementsByClassName('content-blog')[0];
let smallContent = document.createElement('small');
smallContent.textContent = 'Minimum six characters have to fill.';
smallContent.style.display = 'none';
smallContent.style.color = 'red';
let labelContent = contentInput.closest('label');
labelContent.appendChild(smallContent);

let small = document.createElement('small');
small.textContent = 'Minimum six characters have to fill.';
small.style.display = 'none';
small.style.color = 'red';
let label = nameInput.closest('label');
label.appendChild(small);

nameInput.addEventListener('focusout', () => {
    let nameValue = nameInput.value;

    if (nameValue.length < 6 || !nameValue) {
        nameInput.style.border = '3px solid red';
        small.style.display = 'block';

    } else {
        nameInput.style.border = '3px solid green';
        small.style.display = 'none';
    }
});

contentInput.addEventListener('focusout', () => {
    let contentValue = contentInput.value;
    if (contentValue.length < 6 || !contentValue) {
        contentInput.style.border = '3px solid red';
        smallContent.style.display = 'block';

    } else {
        contentInput.style.border = '3px solid green';
        smallContent.style.display = 'none';
    }
});