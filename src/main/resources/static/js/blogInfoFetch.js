let section = document.getElementById('blog-class');
let blogs = [];
fetch('http://localhost:8080/blogs/info')
    .then(res => res.json())
    .then(data => blogs.push(data))
    .then(fillBlogs);

function fillBlogs() {
    for (const b of blogs) {
        for (const bElement of b) {
            let pTag = document.createElement('p');

            pTag.innerHTML = `<section class="single-content">
                <p class="blog-title"><i class="fas fa-text-height"></i></p>
                <section class="inner-section" style="display: block">
                <h5 class="blog-content-head left-padd">${bElement.title}</h5>
                <article><i class="fas fa-file-alt"></i></article>
                <p  class="left-padd">${bElement.content}</p>
                <p class="date-added">Date added: ${bElement.date}</p>
                <p  class="date-added">Author: ${bElement.author}</p>
                <p  class="date-added">Identification code: ${bElement.id}</p>
                </section>
                <p class="hide-blog" class="left-padd">Hide blog</p>
            </section>`;
            section.appendChild(pTag);
        }
        let pSizeTag = document.getElementById('total-size');
        pSizeTag.textContent = `Total blogs: ${b.length}`;
    }
}

document.getElementsByTagName('main')[0].addEventListener('click', toggle);

function toggle(e) {
    if (e.target.classList.contains('hide-blog')) {
        let section = e.target.previousElementSibling;
        if (section.style.display === 'none') {
            section.style.display = 'block';
            e.target.textContent = 'Hide blog'
        } else {
            section.style.display = 'none';
            e.target.textContent = 'Show blog';
        }
    }
}
















