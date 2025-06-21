import java.util.*

fun main(args: Array<String>) {
    println("Menu de Ejercicios")
    println("Selecciona la opcion a ejecutar: ")
    println("1.- Ejercicio 1")
    println("2.- Ejercicio 2")
    println("3.- Ejercicio 3")
    println("4.- Ejercicio 4")
    println("5.- Ejercicio 5")
    println("6.- Ejercicio 6")
    print("Opcion: ")
    var opc:Int = readLine()?.toInt() as Int

    when(opc) {
        1 -> {
            print("Ingrese una cantidad en miligramos para una pocion multijugos: ")
            var mg:Int = readLine()?.toInt() as Int
            pocion(mg)
        }
        2 -> {
            print("Ingresa la Distancia del Conductor en Kilometros")
            var km:Double = readLine()?.toDouble() as Double
            println("Disponibilidad del Conductor")
            print("Si = 1 o No = 0: ")
            var dp:Int = readLine()?.toInt() as Int
            UBER(km, dp)
        }
        3 -> {
            print("Ingresa un numero para sacar su sumatoria: ")
            var num:Int = readLine()?.toInt() as Int
            println(sumatoria(num))
        }
        4 -> {
            print("Ingresa un numero del 1 al 5: ")
            var clima:Int = readLine()?.toInt() as Int
            climadehoy(clima)
        }
        5 -> {
            array()
        }
        6 -> {
            print("Ingresa un numero positivo mayor a 0: ")
            val md:Int = readLine()?.toInt() as Int
            pares(md)
        }
    }
}

//Metodo Ejercicio 1
fun pocion(mg: Int) {
    val value = 100
    if (mg > value) {
        println("Felicidades, es una buena pocion multijugos!")
    } else {
        println("La pocion es mediocre, sangre sucia inmunda!")
    }
}

//Metodo Ejercicio 2
fun UBER(km: Double, d: Int) {
    do {
        if (km <= 0.5 && d == 1){
            println("Listo para iniciar el recorrido!")
        }else if (km <= 0.5 && d != 1 ){
            println("Conductor cercano, pero no disponible!")
        }else if(km > 0.5 && d == 1){
            println("Conductor disponible pero muy lejos, aplicaran tarifas mas altas!")
        }else if (km > 0.5 && d != 1){
            println("No hay conductores disponibles!")
        }
    } while (km > 0.5 || d != 1)
}

//Metodo Ejercicio 3
fun sumatoria(num: Int): String {
    var c = 0
    var suma = 0
    while (c <= num){
        suma += c
        c++
    }
    return "El resultado de la suma es: " + suma
}

//Metodo Ejercicio 4
fun climadehoy(clima: Int){
    println("¿Como es el Clima hoy?: ")
    when(clima){
        1 -> print("Soleado")
        2 -> print("Nublado")
        3 -> print("Lluvioso")
        4 -> print("Tormentoso")
        5 -> print("Nevado")
        else -> print("Preguntale a Google")
    }
}

//Metodo Ejercicio 5
fun array(){
    val movies = arrayOf<String>(
        "Jumanji", "Toy Story", "Pulp Fiction",
        "Batman: El Caballero de la Noche",
        "Kill Bill"
    )
    var max = 0
    var longtitulo = ""
    var indexlong = 0
    for (index in movies.indices) {
        val tit = movies[index]
        val titlen = tit.length
        if (titlen > max) {
            max = titlen
            longtitulo = tit
            indexlong = index
        }
    }
    println("El titulo mas largo de este array es " + longtitulo + " y su posicion es " + indexlong)
}

//Metodo Ejercicio 6
fun pares(md: Int) {
        if (md <= 0){
            println("El numero es negativo, Inserta un numero positivo!")
        } else {
            println("Los numeros pares de " + md + " son: ")
            for (i in 1..md) {
                if (i % 2 == 0) {
                    println(i)
                }
            }
        }
}

