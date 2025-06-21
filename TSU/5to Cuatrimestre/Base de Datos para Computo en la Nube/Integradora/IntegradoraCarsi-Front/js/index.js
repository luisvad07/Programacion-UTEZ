fetch("http://localhost:3000/api/categories")
    .then((response) => response.json())
    .then((data) => {
        console.log(data)
        getCategories(data)
    })
    .catch((error) => {
        console.log(error)
    });

const getCategories = (data) => {
    const contenidoMenu = document.getElementById('categories');
    const contenidoNavbar = document.getElementById('categoriesNavbar');
    data.forEach((category) => {
        contenidoMenu.innerHTML = `
            ${contenidoMenu.innerHTML}
            <div class="row">
                <div class="col-12 my-3" id="category${category.id_category}">
                    <div class="card shadow-sm bg-body rounded">
                        <h5 class="card-header">${category.title}</h5>                                                    
                        <div class="card-body">
                            <div class="container">
                                <div class="row" id="product${category.id_category}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
        contenidoNavbar.innerHTML = `
            ${contenidoNavbar.innerHTML}
            <li><a class="dropdown-item" href="#category${category.id_category}">${category.title}</a></li>
        `;
        var id_category = category.id_category;
        findOne(id_category);
    });
};

const findOne = (id) => {
    fetch(`http://localhost:3000/api/products/category/${id}`)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            getProductsByCategory(data);
        })
        .catch((error) => {
            console.log(error);
        });
};

const getProductsByCategory = (data) => {
    const contenido = document.getElementById(`product${data[0].category}`);
    data.forEach((product) => {
        contenido.innerHTML = `
            ${contenido.innerHTML}
            <div class="col-12 col-lg-6 my-3">
                <div class="card">
                    <div class="card-body">
                        <div class="container">
                            <div class="row">
                                <div class="col-12 col-md-4 d-flex align-items-center">
                                    <h2 class="text-start text-md-center mx-md-auto mb-2 text-success">$${product.price}</h2>
                                </div>
                                <div class="col-12 col-md-8">
                                    <h5 class="card-title">${product.title}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">${product.category_title}</h6>
                                    <p class="card-text">${product.description}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    });
}

