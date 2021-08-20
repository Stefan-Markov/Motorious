let mainTag = document.getElementById('info');
let pages = document.getElementById('pages');
let result = [];
let page = 1;

function getPage(page) {
    page = (page < 0) ? 1 : page;
    fetch(`http://localhost:8080/users/all-users?page=${page}`)
        .then(res => res.json())
        .then(data => {
            result = [];
            for (const element of data) {
                result.push(element);
            }
        }).then(fillInfo)
        .catch(err => console.log(err));
}


function fillInfo() {
    mainTag.innerText = '';
    pages.textContent = `Page: ${page}`;
    pages.style.color = 'white';
    if (result.length === 0) {
        let p = document.createElement('p');
        let i = document.createElement('i');
        i.classList = `fas fa-check`;
        p.id = 'last';
        p.textContent = 'That was all info!';
        p.appendChild(i);
        mainTag.appendChild(p);
        return;
    }

    result.forEach(el => {
        let section = document.createElement('section');
        section.classList = 'user-info';

        let innerSection = document.createElement('section');
        innerSection.classList = 'background-info';

        let image = document.createElement('img');
        image.classList = 'pic-style';
        image.src = el.imageUrl;

        let pFirstName = document.createElement('p');
        pFirstName.textContent = `First name: ${el.firstName}`;

        let pLastName = document.createElement('p');
        pLastName.textContent = `Last name: ${el.lastName}`;

        let pCreatedDate = document.createElement('p');
        let date = el.createdDate.substring(0, 10);
        let time = el.createdDate.substring(11, 19);
        pCreatedDate.textContent = `Created on: ${date} ${time}`;

        let pUsername = document.createElement('p');
        pUsername.textContent = `Username: ${el.username}`;

        let pEmail = document.createElement('p');
        pEmail.textContent = `Email: ${el.email}`;

        let pTitle = document.createElement('p');
        pTitle.textContent = `Title: ${el.title}`;

        let aTag = document.createElement('a');


        aTag.href = `http://localhost:8080/client/info/${el.username}`;
        aTag.textContent = `More info here`;
        aTag.classList = 'pagination';

        innerSection.appendChild(pFirstName);
        innerSection.appendChild(pLastName);
        innerSection.appendChild(pUsername);
        innerSection.appendChild(pCreatedDate);
        innerSection.appendChild(pEmail);
        innerSection.appendChild(pTitle);
        innerSection.appendChild(aTag);

        section.appendChild(image);
        section.appendChild(innerSection);
        mainTag.appendChild(section);
    });
}

let nextButton = document.getElementById('next');
let previousButton = document.getElementById('previous');

nextButton.addEventListener('click', () => {
    if (mainTag.children.length === 1) {
        return;
    }
    page = page + 1;
    getPage(page);
});

previousButton.addEventListener('click', () => {
    page = page - 1;
    page = page < 1 ? 1 : page;
    getPage(page);
})

getPage(page);