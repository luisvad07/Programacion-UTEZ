package mx.edu.utez.examenu1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PantallaJugar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantallajuego)

        val edt1 = findViewById<EditText>(R.id.edtText1)
        val edt2 = findViewById<EditText>(R.id.edtText2)
        val edt3 = findViewById<EditText>(R.id.edtText3)
        val btnAdivinar = findViewById<Button>(R.id.btnAdivinar)

        var random = (0..2).random()

        btnAdivinar.setOnClickListener {
            var valorEdt1 = edt1.text.toString()
            var valorEdt2 = edt2.text.toString()
            var valorEdt3 = edt3.text.toString()
            var mensaje = "Perdiste, Vuelve a intentar!"

            if(valorEdt1 != "" || valorEdt2 != "" || valorEdt3 != ""){
                if(valorEdt1 != "" && valorEdt2 == "" && valorEdt3 == "" && random == 0){
                    mensaje = "Ganaste! La bolita estaba en el primer cuadro :)"
                }else if(valorEdt1 == "" && valorEdt2 != "" && valorEdt3 == "" && random == 1){
                    mensaje = "Ganaste! La bolita estaba en el segundo cuadro :)"
                }else if(valorEdt1 == "" && valorEdt2 == "" && valorEdt3 != "" && random == 2) {
                    mensaje = "Ganaste! La bolita estaba en el tercer cuadro :)"
                }
            }else { mensaje = "Debes elegir al menos un cuadro >:(" }
            Toast.makeText(this@PantallaJugar,
                mensaje,
                Toast.LENGTH_SHORT).show()

            random = (0..2).random()
        }
    }
}