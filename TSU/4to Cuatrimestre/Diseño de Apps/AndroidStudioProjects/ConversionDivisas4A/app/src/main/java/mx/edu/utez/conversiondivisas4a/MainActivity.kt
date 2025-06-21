package mx.edu.utez.conversiondivisas4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val datos =  listOf("MXN","USD","EUR", "CHF", "JPY")
        val edtdivisa = findViewById<EditText>(R.id.edtDivisa)
        val btnconvertir = findViewById<Button>(R.id.btnConvertir)
        val btnlimpiar = findViewById<Button>(R.id.idClean)
        val spMenu1 = findViewById<Spinner>(R.id.spDiv1)
        val spMenu2 = findViewById<Spinner>(R.id.spDiv2)
        val lbresult = findViewById<TextView>(R.id.lbResult)

        val adaptador = ArrayAdapter(this@MainActivity,
            android.R.layout.simple_list_item_1,
            datos)
        spMenu1.adapter = adaptador
        spMenu2.adapter = adaptador

        btnconvertir.setOnClickListener {
            //Acciones

            val cantidad = edtdivisa.text.toString().toInt()
            val origen = spMenu1.selectedItem.toString()
            val destino = spMenu2.selectedItem.toString()

            if (origen == "MXN" && destino=="USD"){
                lbresult.text = "La cantidad de MXN a USD es de " + (cantidad*0.05).toString()
            } else if (origen == "USD" && destino=="MXN"){
                lbresult.text = "La cantidad de USD a MXN es de " + (cantidad*19.99).toString()
            } else if (origen == "MXN" && destino=="EUR"){
                lbresult.text = "La cantidad de MXN a EUR es de " + (cantidad*0.051).toString()
            } else if (origen == "EUR" && destino=="MXN"){
                lbresult.text = "La cantidad de EUR a MXN es de " + (cantidad*19.66).toString()
            } else if (origen == "MXN" && destino=="CHF"){
                lbresult.text = "La cantidad de MXN a CHF es de " + (cantidad*0.05).toString()
            } else if (origen == "CHF" && destino=="MXN"){
                lbresult.text = "La cantidad de CHF a MXN es de " + (cantidad*20.05).toString()
            } else if (origen == "MXN" && destino=="JPY"){
                lbresult.text = "La cantidad de MXN a JPY es de " + (cantidad*7.45).toString()
            } else if (origen == "JPY" && destino=="MXN"){
                lbresult.text = "La cantidad de JPY a MXN es de " + (cantidad*0.13).toString()
            }

            else if (origen == "USD" && destino=="EUR"){
                lbresult.text = "La cantidad de USD a EUR es de " + (cantidad*1.02).toString()
            } else if (origen == "EUR" && destino=="USD"){
                lbresult.text = "La cantidad de EUR a USD es de " + (cantidad*0.98).toString()
            } else if (origen == "USD" && destino=="CHF"){
                lbresult.text = "La cantidad de USD a CHF es de " + (cantidad*1.00).toString()
            } else if (origen == "CHF" && destino=="USD"){
                lbresult.text = "La cantidad de CHF a USD es de " + (cantidad*1.00).toString()
            } else if (origen == "USD" && destino=="JPY"){
                lbresult.text = "La cantidad de USD a JPY es de " + (cantidad*148.83).toString()
            } else if (origen == "JPY" && destino=="USD"){
                lbresult.text = "La cantidad de JPY a USD es de " + (cantidad*0.0067).toString()
            }

            else if (origen == "EUR" && destino=="CHF"){
                lbresult.text = "La cantidad de EUR a CHF es de " + (cantidad*0.98).toString()
            } else if (origen == "CHF" && destino=="USD"){
                lbresult.text = "La cantidad de CHF a EUR es de " + (cantidad*1.02).toString()
            } else if (origen == "EUR" && destino=="JPY"){
                lbresult.text = "La cantidad de EUR a JPY es de " + (cantidad*146.42).toString()
            } else if (origen == "JPY" && destino=="EUR"){
                lbresult.text = "La cantidad de JPY a EUR es de " + (cantidad*0.0068).toString()
            }

            else if (origen == "CHF" && destino=="JPY"){
                lbresult.text = "La cantidad de CHF a JPY es de " + (cantidad*149.43).toString()
            } else if (origen == "JPY" && destino=="CHF"){
                lbresult.text = "La cantidad de JPY a CHF es de " + (cantidad*0.0067).toString()
            }

            else if (origen == "MXN" && destino=="MXN"){
                lbresult.text = "Es la misma moneda!"
            } else if (origen == "USD" && destino=="USD"){
                lbresult.text = "Es la misma moneda!"
            } else if (origen == "EUR" && destino=="EUR"){
                lbresult.text = "Es la misma moneda!"
            } else if (origen == "CHF" && destino=="CHF"){
                lbresult.text = "Es la misma moneda!"
            } else if (origen == "JPY" && destino=="JPY"){
                lbresult.text = "Es la misma moneda!"
            }
        }

        btnlimpiar.setOnClickListener{
            edtdivisa.text.clear()
            lbresult.text = ""
        }
    }
}