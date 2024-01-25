package com.example.unidad10casopractico.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.unidad10casopractico.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        val nombreComunidad = intent.getStringExtra("comunidadNombre")
        val imagenBandera = intent.getIntExtra("imagenBandera", 0)
        val posicion = intent.getIntExtra("position", 0)

        val imagen = findViewById<ImageView>(R.id.imageView)
        imagen.setImageResource(imagenBandera)

        val editTextInputLayout = findViewById<TextInputLayout>(R.id.editText)
        val edit = editTextInputLayout.findViewById<TextInputEditText>(R.id.textInputEditText)
        edit.hint = nombreComunidad

        val btnGuardarCambios = findViewById<Button>(R.id.buttoncambiar)
        btnGuardarCambios.setOnClickListener{
            val nuevoNombre = edit.text.toString()
            //val nuevoNombre = edit.editableText.toString()
            val comunidad = lista[posicion]

            //Actualiza el nombre en la base de datos
            DAO.actualizarNombre(this, comunidad, nuevoNombre)

            val returnIntent = Intent()
            returnIntent.putExtra("nuevoNombre", nuevoNombre)
            returnIntent.putExtra("position", posicion)

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        val btnCancelar = findViewById<Button>(R.id.buttoncancelar)
        btnCancelar.setOnClickListener {
            finish()
        }
    }
}