let input = document.getElementById('search-username');

input.addEventListener('keyup', search);
let users = [];

async function fetchData() {
    if (input.value.length > 0) {
        let json = await fetch('http://localhost:8080/profile-rest/users/'
            + input.value.toLowerCase().trim());
        let data = await json.json();
        users = [];
        for (let user of data) {
            users.push(user);
        }
    }
}

async function search() {

    fetchData().catch(err => err.message);

    let username = input.value.toLowerCase().trim();
    let ul = document.getElementById('result');
    if (username.length > 0) {
        ul.innerHTML = '';
        let filtered = users.filter(user => {
            return user.firstName.toLowerCase().includes(username) ||
                user.email.toLowerCase().includes(username);
        });
        filtered = filtered.filter((u1, u2) => u1.username !== u2.username);

        filtered.forEach(u => {
            let li = document.createElement('li');
            li.classList = 'rest-user';
            let aTag = document.createElement('a');

            aTag.href = "http://localhost:8080/client/info/" + u.username;
            aTag.textContent = " click |HERE| to get info for user.";
            aTag.style.backgroundColor = '#ffda99';
            aTag.id = 'user-span';
            aTag.innerHTML += `<i class="fas fa-id-card"></i>`;
            li.textContent = u.firstName;
            li.appendChild(aTag);
            ul.appendChild(li);
        })
    } else {
        ul.innerHTML = '';
    }
}


search()
    .catch(err => err.message);


// if (input.value.length > 0) {
//         fetch('http://localhost:8080/profile-rest/users/' + input.value.toLowerCase())
//             .then(res => res.json())
//             .then(data => {
//                 users = [];
//                 for (let user of data) {
//                     users.push(user);
//                 }
//             })
//             .catch(err => err.message);
//     }

//let input = document.getElementById('search-username');
//
// input.addEventListener('keyup', search);
// let users = [];
//
// async function dataFetch() {
//     let json = await fetch('http://localhost:8080/profile-rest/users/' + input.value.toLowerCase());
//     let data = await json.json();
//     users = [];
//     for (let user of data) {
//         users.push(user);
//     }
// }
//
// async function search() {
//     let username = input.value.toLowerCase().trim();
//     let ul = document.getElementById('result');
//     if (username.length >= 1) {
//         ul.innerHTML = '';
//         let filtered = users.filter(user => {
//             return user.firstName.toLowerCase().includes(username)
//         });
//         console.log(filtered);
//         filtered = filtered.filter((u1, u2) => u1.firstName !== u2.firstName);
//
//         filtered.forEach(u => {
//             let li = document.createElement('li');
//             li.classList = 'rest-user';
//             let aTag = document.createElement('a');
//
//             aTag.href = "http://localhost:8080/client/info/" + u.firstName;
//             aTag.textContent = ": click HERE to get info for user.";
//             aTag.id = 'user-span';
//             aTag.innerHTML += `<i class="fas fa-id-card"></i>`;
//             li.textContent = u.firstName;
//             li.appendChild(aTag);
//             ul.appendChild(li);
//         })
//     } else {
//         ul.innerHTML = '';
//     }
// }
//
// dataFetch()
//     .catch(er => console.log(er.message));
//
// search()
//     .catch(er => console.log(er.message));