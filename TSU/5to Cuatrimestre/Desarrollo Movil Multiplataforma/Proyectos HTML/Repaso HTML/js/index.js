//let var const 
//const genera una constante 
//var y let generan una variable
//$scope = fragmento de codigo

//$scope primero
function suma (){
    const numero = 2;
    let num2 = 4;
    var num3 = 5;
    //$scope segundo
    function sumasegun() {
        const numero=3;
    }
}


//arrow function (funcion flecha)
function suma(numero1, numero2) { 
    return numero1+numero2;
}

const suma = function (numero1, numero2) {
    return numero1+numero2;
}

const suma = (numero1, numero2) => numero1+numero2;

//template String
const name = "Luis"
const lastname = "Valladolid"
const fullname = name + ' ' + lastname
const fullname2 = `${name} ${lastname}`

const person = {
    name: '',
    lastname: ''
}

(()=>{
    const name = person.name.toString();
    const name2 = `${person.name} ${person.lastname ? person.lastname: ''}`
})()

//ArrayList Array List
//Array
const roles = [1,2,3,{name: '', surname: ''}]
//Array List
//ArrayList<Rol>()
//key, name
const roles2 = [
    {
        key: 'ADMIN',
        name: 'Super Admin'       
    },
    {
        key: 'USER',
        name: 'Usuario'       
    }
]
//List
const roles3 = {}

// push, indexOf, splice, find, filter, map, foreach, length

roles.forEach((element, index)=> {
    console.log(element.name);
})

for (let i = 0; i < roles.length; i++) {
    console.log(roles[i].name);
}
/*
    while () {
    }

    do {
    } while ();
*/

//Desestructuracion de objetos
const person2 = {
    name: 'Luis',
    age: 19,
    address: {
        street: 'Antonio Barona',
        number: 29
    }
}
console.log(person2.name);
console.log(person2.address.street);

(()=>{
   const {name, address:{street}} = person2 
})
