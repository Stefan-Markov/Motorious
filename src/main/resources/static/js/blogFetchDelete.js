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
                <h5 class="blog-content-head left-padd">${bElement.title}</h5>
                <article><i class="fas fa-file-alt"></i></article>
                <p  class="left-padd">${bElement.content}</p>
                <p class="date-added">Date added: ${bElement.date}</p>
                <p  class="date-added">Author: ${bElement.author}</p>
                <p  class="date-added">Identification code: ${bElement.id}</p>
               <ul>
                    <li class="delete-blog" >
                        <a href="/blog/delete/${bElement.id}"  type="button">
                        <i class="fas fa-trash"></i> Delete blog</a>
                    </li>
                </ul>
            </section>`;
            section.appendChild(pTag);
        }
        let pSizeTag = document.getElementById('total-size');
        pSizeTag.textContent = `Total blogs: ${b.length}`;
    }
}



