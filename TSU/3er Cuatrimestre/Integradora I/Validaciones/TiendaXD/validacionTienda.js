// Variable para acceder al formulario
const form = document.getElementById('form')

// Variable para acceder a las entradas de los formularios
const inputs = document.querySelectorAll('#form input')



// Objeto con las expresiones regulares
const expresiones = {
    username: /^[a-zA-Z0-9\_\-]{4,8}$/, // Letras, numeros, guion y guion_bajo
    email: /^[a-zA-Z0-9_.+-]+@utez.edu.mx+$/, // Correo Institucional
    password: /^.{4}$/, // 4 digitos.
    confirmpass: /^.{4}$/, // 4 digitos.
}

// Objeto con las banderas para validar si hay datos dentro del input
const campos = {
    username: false,
    email: false,
    password: false
}

// Funcion para validar el formulario completo
const validarFormulario = (e) => { 
    switch (e.target.name) { 
        case "username":
            validarCampo(expresiones.username, e.target, 'username');
        break;
        case "email":
            validarCampo(expresiones.email, e.target, 'email');
        break;
        case "password":
            validarCampo(expresiones.password, e.target, 'password');
            validarPassword();
        break;
        case "confirmpass":
            validarPassword();
        break;
    }
}

// Funcion para validar campo por campo
const validarCampo = (expresiones, input, campo) => { 
    if (expresiones.test(input.value)) {
        document.getElementById(`group_${campo}`).classList.remove('form-group-incorrecto')
        document.getElementById(`group_${campo}`).classList.add('form-group-correcto')
        document.querySelector(`#group_${campo} .invalid-feedback`).classList.remove('invalid-feedback-active')
        document.querySelector(`#group_${campo} .invalid-feedback`).classList.add('valid-feedback-active')
        campos[campo] = true
    } else { 
        document.getElementById(`group_${campo}`).classList.add('form-group-incorrecto')
        document.getElementById(`group_${campo}`).classList.remove('form-group-correcto')
        document.querySelector(`#group_${campo} .invalid-feedback`).classList.add('invalid-feedback-active')
        document.querySelector(`#group_${campo} .invalid-feedback`).classList.remove('valid-feedback-active')
        campos[campo] = false
    }
}

const validarPassword = () => {
    const pass1 = document.getElementById('password');
    const pass2 = document.getElementById('confirmpass');

    if(pass1.value !== pass2.value){
        document.getElementById(`group_confirmpass`).classList.add('form-group-incorrecto');
        document.getElementById(`group_confirmpass`).classList.remove('form-group-correcto');
        document.querySelector(`#group_confirmpass .invalid-feedback`).classList.add('invalid-feedback-active');
        document.querySelector(`#group_confirmpass .invalid-feedback`).classList.remove('valid-feedback-active');
        campos['password'] = false;
    } else {
        document.getElementById(`group_confirmpass`).classList.remove('form-group-incorrecto');
        document.getElementById(`group_confirmpass`).classList.add('form-group-correcto');
        document.querySelector(`#group_confirmpass .invalid-feedback`).classList.remove('invalid-feedback-active');
        document.querySelector(`#group_confirmpass .invalid-feedback`).classList.add('valid-feedback-active');
        campos['password'] = true;
    }
}


    
 
// Evento con los que se valida el Formulario
inputs.forEach((input) => { 
    input.addEventListener('keyup', validarFormulario);
    input.addEventListener('blur', validarFormulario);
})

