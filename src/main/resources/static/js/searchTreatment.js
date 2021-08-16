let input = document.getElementById('search-treatment');
input.addEventListener('keyup', search);
let treatments = [];


async function fetchData() {
    if (input.value.length > 0) {
        let json = await fetch('http://localhost:8080/tr/'
            + input.value.toLowerCase().trim());
        let data = await json.json();
        treatments = [];
        for (let treatment of data) {
            treatments.push(treatment);
        }
    }
}

async function search() {

    fetchData().catch(err => err.message);

    let data = input.value.toLowerCase().trim();
    let ul = document.getElementById('result-treatment');
    if (data.length > 0) {
        ul.innerHTML = '';
        let filtered = treatments.filter(treatment => {
            return treatment.disease.toLowerCase().includes(data);
        });

        filtered.forEach(u => {
            let innerUl = document.createElement('ul');
            innerUl.classList = 'rest-data';

            let liContent = document.createElement('li');
            let liGoal = document.createElement('li');
            let liDisease = document.createElement('li');
            let liDateAdded = document.createElement('li');
            let liKtFullName = document.createElement('li');

            liDisease.textContent = `Disease: ${u.disease}`;
            liDisease.classList = 'class-bold';

            liContent.textContent = `Content: ${u.content}`;
            liDateAdded.textContent = `Date added: ${u.dateAdded}`;
            liKtFullName.textContent = `KT name: ${u.ktFullName}`;
            liGoal.textContent = `Goal: ${u.goal}`;

            innerUl.appendChild(liDisease);
            innerUl.appendChild(liContent);
            innerUl.appendChild(liGoal);
            innerUl.appendChild(liKtFullName);
            innerUl.appendChild(liDateAdded);
            ul.appendChild(innerUl);
        })
    } else {
        ul.innerHTML = '';
    }
}


search()
    .catch(err => err.message);
