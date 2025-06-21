const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{8,}$/; //al menos un numero, al menos una minuscula, al menos una mayuscula, al menos 8 caracteres
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // diego@gmail.com

export {
  passwordRegex,
  emailRegex,
}