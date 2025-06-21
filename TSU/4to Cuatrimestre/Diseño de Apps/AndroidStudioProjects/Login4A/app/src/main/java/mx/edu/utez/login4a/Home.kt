package mx.edu.utez.login4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Declarar Variables
        val chkBob = findViewById<CheckBox>(R.id.chkBob) //Llama al ID del check
        val rgOpciones = findViewById<RadioGroup>(R.id.rgOpciones) //Llama al ID del Radio Group
        val rbSquidward = findViewById<RadioButton>(R.id.rbSquidward) //Llama al ID del Radio Button Squidward
        val rbPatrick = findViewById<RadioButton>(R.id.rbPatrick) //Llama al ID del Radio Button Patrick
        val btnAceptar = findViewById<Button>(R.id.btnAceptar) //Llama al ID del Boton Aceptar

        btnAceptar.setOnClickListener {
            val valorCheck = chkBob.isChecked
            println(valorCheck.toString()) //Muestra el llamado de Check al seleccionar la opcion
            val valorGroup = rgOpciones.checkedRadioButtonId
            when(valorGroup){
                R.id.rbSquidward -> println(R.string.rb_ch1)
                R.id.rbPatrick -> println(R.string.rb_ch2)
            }
            resources.getString(R.string.rb_ch1)
            val valRadioPatrick = rbPatrick.isChecked
        }
    }
}