package mx.edu.utez.estilos4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    //Drawable - Shape, Vector y Selector -> Son archivos XML: Rectangulo, Ovalo, Linea y Aro

    // Selector = Permite cambiar la aparencia de un control de acuerdo a su estado
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}