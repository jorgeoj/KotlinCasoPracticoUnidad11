package com.example.unidad10casopractico.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.example.unidad10casopractico.R
import com.example.unidad10casopractico.database.ComunidadDAO
import com.example.unidad10casopractico.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.extras!!.getInt("id")
        val miDAO = ComunidadDAO()
        val comunidad = miDAO.obtenerComunidad(this, id)
        if(comunidad.uri.isNotEmpty()) {
            val uri = Uri.parse(comunidad.uri)
            binding.wholeImage.load(uri)
        } else {
            Toast.makeText(this, "${comunidad.nombre} no tiene foto asociada", Toast.LENGTH_SHORT).show()
        }
    }
}