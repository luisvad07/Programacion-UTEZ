//Porcentaje

var resultPromedio = document.getElementById("promedio");
var promedio = 0;

var calificar = {
    funcionalidad : 0,
    confiabilidad : 0,
    usabilidad : 0,
    rendimiento : 0,
    mantenimineto : 0,
    portabilidad : 0,
    seguridad : 0,
    compatibilidad : 0
}


var test = document.querySelector(".test")

test.addEventListener('click', a=>{
    //console.log(a.target.classList)

    switch (a.target.classList[0] ){
        case "funcional":
            limpiarFuncional("funcional");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.funcionalidad=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.funcionalidad=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.funcionalidad=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.funcionalidad=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.funcionalidad=5;
                    break
            }
            break;


        case "confiabilidad":
            limpiarFuncional("confiabilidad");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.confiabilidad=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.confiabilidad=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.confiabilidad=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.confiabilidad=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.confiabilidad=5;
                    break
            }
            break;

        case "usabilidad":
            limpiarFuncional("usabilidad");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.usabilidad=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.usabilidad=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.usabilidad=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.usabilidad=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.usabilidad=5;
                    break
            }
            break;

        case "rendimiento":
            limpiarFuncional("rendimiento");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.rendimiento=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.rendimiento=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.rendimiento=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.rendimiento=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.rendimiento=5;
                    break
            }
            break;

        case "mantenimiento":
            limpiarFuncional("mantenimiento");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.mantenimineto=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.mantenimineto=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.mantenimineto=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.mantenimineto=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.mantenimineto=5;
                    break
            }
            break;

        case "portabilidad":
            limpiarFuncional("portabilidad");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.portabilidad=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.portabilidad=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.portabilidad=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.portabilidad=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.portabilidad=5;
                    break
            }
            break;

        case "seguridad":
            limpiarFuncional("seguridad");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.seguridad=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.seguridad=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.seguridad=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.seguridad=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.seguridad=5;
                    break
            }
            break;

        case "compatibilidad":
            limpiarFuncional("compatibilidad");
            switch (a.target.classList[1]){
                case "bad" :
                    a.target.src = "emojis/badColor.svg";
                    calificar.compatibilidad=1;
                    break
                case "regular" :
                    a.target.src = "emojis/regularColor.svg";
                    calificar.compatibilidad=2;
                    break
                case "fine" :
                    a.target.src = "emojis/buenoColor.svg";
                    calificar.compatibilidad=3;
                    break
                case "veryFine" :
                    a.target.src = "emojis/muyBuenoColor.svg";
                    calificar.compatibilidad=4;
                    break
                case "exelent" :
                    a.target.src = "emojis/exelenteColor.svg";
                    calificar.compatibilidad=5;
                    break
            }
            break;

    }

    document.querySelector("#save").type="button"

    promedio = (calificar.funcionalidad + calificar.confiabilidad +calificar.usabilidad +calificar.rendimiento+calificar.mantenimineto + calificar.portabilidad + calificar.seguridad +calificar.compatibilidad);
    promedio = promedio*100/40;

    resultPromedio.innerHTML = promedio + " %"
    document.querySelector('.average').value = promedio
    document.querySelector('.funcionality').value = calificar.funcionalidad
    document.querySelector('.conf').value = calificar.confiabilidad
    document.querySelector('.usability').value = calificar.usabilidad
    document.querySelector('.efficiency').value = calificar.rendimiento
    document.querySelector('.maintenance').value = calificar.mantenimineto
    document.querySelector('.portability').value = calificar.portabilidad
    document.querySelector('.safety').value = calificar.seguridad
    document.querySelector('.compatibility').value = calificar.compatibilidad

})

document.querySelector("#save").addEventListener('click', ()=>{
    if (calificar.compatibilidad===0){
        alert('"Compatibilidad"')
    }else if (calificar.seguridad ===0){
        alert('"Seguridad"')
    }else if (calificar.portabilidad ===0){
        alert('"Portabilidad"')
    }else if (calificar.mantenimineto ===0){
        alert('"Mantenimiento"')
    }else if (calificar.rendimiento ===0){
        alert('"Rendimiento"')
    }else if (calificar.usabilidad ===0){
        alert('"Usabilidad"')
    }else if (calificar.confiabilidad ===0){
        alert('"Confiabilidad"')
    }else if (calificar.funcionalidad ===0) {
        alert('"Funcionalidad"')
    }else if (document.querySelectorAll(".name")[0].value==="") {
        //console.log(document.querySelectorAll(".name")[0].value)
        alert('"Nombre de la escuela"')
    }else if (document.querySelectorAll(".name")[1].value ===""){
        alert('"Nombre"')
    }else{
        document.querySelector("#save").type="submit"

    }
})


function limpiarFuncional(a) {
    //console.log(a)
    if (a==="funcional"){
        document.querySelector(".funcional.bad").src = "emojis/badBlack.svg";
        document.querySelector(".funcional.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".funcional.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".funcional.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".funcional.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "confiabilidad"){
        document.querySelector(".confiabilidad.bad").src = "emojis/badBlack.svg";
        document.querySelector(".confiabilidad.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".confiabilidad.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".confiabilidad.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".confiabilidad.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "usabilidad"){
        document.querySelector(".usabilidad.bad").src = "emojis/badBlack.svg";
        document.querySelector(".usabilidad.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".usabilidad.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".usabilidad.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".usabilidad.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "rendimiento"){
        document.querySelector(".rendimiento.bad").src = "emojis/badBlack.svg";
        document.querySelector(".rendimiento.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".rendimiento.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".rendimiento.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".rendimiento.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "mantenimiento"){
        document.querySelector(".mantenimiento.bad").src = "emojis/badBlack.svg";
        document.querySelector(".mantenimiento.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".mantenimiento.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".mantenimiento.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".mantenimiento.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "portabilidad"){
        document.querySelector(".portabilidad.bad").src = "emojis/badBlack.svg";
        document.querySelector(".portabilidad.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".portabilidad.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".portabilidad.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".portabilidad.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "seguridad"){
        document.querySelector(".seguridad.bad").src = "emojis/badBlack.svg";
        document.querySelector(".seguridad.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".seguridad.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".seguridad.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".seguridad.exelent").src = "emojis/exelenteBlack.svg";

    }else if (a=== "compatibilidad"){
        document.querySelector(".compatibilidad.bad").src = "emojis/badBlack.svg";
        document.querySelector(".compatibilidad.regular").src = "emojis/regularBlack.svg";
        document.querySelector(".compatibilidad.fine").src = "emojis/buenoBlack.svg";
        document.querySelector(".compatibilidad.veryFine").src = "emojis/muyBuenoBlack.svg";
        document.querySelector(".compatibilidad.exelent").src = "emojis/exelenteBlack.svg";
    }
}

function alert(a) {
    Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Todos los campos deben ser contestados.',
        text: 'Campo vacío: ' + a,
        showConfirmButton: false,
        timer: 1500
    })
}


function alertsucces() {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Guardado exitosamente.',
        showConfirmButton: false,
        timer: 1500
    }).then(() => {
        window.location.href = "../index.php"
    })
}