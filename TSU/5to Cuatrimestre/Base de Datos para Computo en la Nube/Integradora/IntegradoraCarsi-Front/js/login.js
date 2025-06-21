    
  /*const elementos = {
    email: document.querySelector("#email"),
    password: document.querySelector("#password"),
    submit: document.querySelector("#btn"),
};
    let button = elementos.submit.addEventListener("click", (e)=> { e.preventDefault();
        fetch('http://localhost:3000/api/auth/', {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                email: elementos.email.value,
                password: elementos.password.value,
              }),
            })
            .then((response) => response.json())
            .then((data) => {
              console.log(data);
              if (!data.token) {
                Swal.fire({
                    icon: "warning",
                    title: "Oops...",
                    text: "Credenciales incorrectas",
                })
              }
              if (data.token) {
                console.log(data.token.toString());
                localStorage.setItem("token", data.token.toString());
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                      toast.addEventListener('mouseenter', Swal.stopTimer)
                      toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                  })
                  Toast.fire({
                    icon: 'success',
                    title: 'Signed in successfully'
                  })
                setTimeout(() => {
                    window.location.href = "/menu/menu.html"; //correcto
                  }, 800);
                
              }
            })
            .catch((error) => {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error interno",
                })
            });
    });*/
    