const findOne = (id) => {
  fetch(`http://localhost:3000/api/orders/${id}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
    })
    .catch((error) => {
      console.log(error);
    });
};

const url = new URL(window.location.href);
const id = url.searchParams.get("id");
console.log(id);
findOne(id);


const formElement = document.querySelector("form#updateOrder");
const getFormJSON = (form) => {
  const data = new FormData(form);
  return Array.from(data.keys()).reduce((result, key) => {
    result[key] = data.get(key);
    return result;
  }, {});
};

const handlerUpdate = (event) => {
  event.preventDefault();
  const data = getFormJSON(formElement);
  console.log(data);
  updateElemento(data);
};
const updateElemento = (elemento) => {
  const url = new URL(window.location.href);
  const id = url.searchParams.get("id");
  console.log(id);
  // we gonna send the id in req params
  fetch(`http://localhost:3000/api/orders/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(elemento),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

    })
    .catch((error) => {
      console.log(error);

    });

  parent.location.href = "orders.html";
};

formElement.addEventListener("submit", handlerUpdate);