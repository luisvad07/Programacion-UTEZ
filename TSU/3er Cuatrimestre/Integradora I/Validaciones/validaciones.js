// Variable para acceder al formulario
const form = document.getElementById('form')

// Variable para acceder a las entradas de los formularios
const inputs = document.querySelectorAll('#form input')

// Objeto con las expresiones regulares
const expresiones = {
    email: /^[a-zA-Z0-9_.+-]+@utez.edu.mx+$/, // Correo Institucional
    password: /^.{4,8}$/, // 4 digitos.
    numeros:  /^[0-9]*(\.?)[ 0-9]+$/,
}

// Objeto con las banderas para validar si hay datos dentro del input
const campos = {
    email: false,
    password: false,
    numeros: false
}

// Funcion para validar el formulario completo
const validarFormulario = (e) => { 
    switch (e.target.name) { 
        case "email":
            validarCampo(expresiones.email, e.target, 'email')
        break;
        case "password":
            validarCampo(expresiones.password, e.target, 'password')
        break;
        case "numeros":
            validarCampo(expresiones.numeros, e.target, 'numeros')
        break;
    }
}

// Funcion para validar campo por campo
const validarCampo = (expresiones, input, campo) => { 
    if (expresiones.test(input.value)) {
        document.getElementById(`group-${campo}`).classList.remove('form-group-incorrecto')
        document.getElementById(`group-${campo}`).classList.add('form-group-correcto')
        document.querySelector(`#group-${campo} .invalid-feedback`).classList.remove('invalid-feedback-active')
        document.querySelector(`#group-${campo} .invalid-feedback`).classList.add('valid-feedback-active')
        campos[campo] = true
    } else { 
        document.getElementById(`group-${campo}`).classList.add('form-group-incorrecto')
        document.getElementById(`group-${campo}`).classList.remove('form-group-correcto')
        document.querySelector(`#group-${campo} .invalid-feedback`).classList.add('invalid-feedback-active')
        document.querySelector(`#group-${campo} .invalid-feedback`).classList.remove('valid-feedback-active')
        campos[campo] = false
    }
}

// Evento con los que se valida el Formulario
inputs.forEach((input) => { 
    input.addEventListener('keyup', validarFormulario)
    input.addEventListener('blur', validarFormulario)
})


