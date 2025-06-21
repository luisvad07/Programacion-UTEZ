package mx.edu.utez.ejerciciowidgets

import android.app.ActionBar
import android.content.Intent
import android.icu.lang.UCharacter.IndicPositionalCategory.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.edu.utez.ejerciciowidgets.databinding.ActivityMain2Binding
import mx.edu.utez.ejerciciowidgets.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        // Indicar determinado
        scope.launch {
            while (true)
                progress(binding.pgBarra)
        }

        binding.btnL1.setOnClickListener {
            val intent = Intent(this@MainActivity2, MainActivity3::class.java)
            startActivity(intent)
        }
    }

    private suspend fun progress(progressBar: ProgressBar) {
        while (progressBar.progress < progressBar.max) {
            delay(300)
            progressBar.incrementProgressBy(PROGRESS_INCREMENT)
        }
        progressBar.progress = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Destruimos el alcance de la corrutina
    }

    companion object {
        const val PROGRESS_INCREMENT = 5
    }
}