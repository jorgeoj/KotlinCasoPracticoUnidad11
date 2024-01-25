package com.example.unidad10casopractico.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unidad10casopractico.Comunidad
import com.example.unidad10casopractico.R
import com.example.unidad10casopractico.adapter.ComunidadAdapter
import com.example.unidad10casopractico.database.ComunidadDAO
import com.example.unidad10casopractico.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private lateinit var adapter: ComunidadAdapter
private lateinit var layoutManager: LinearLayoutManager
internal lateinit var lista:List<Comunidad>
private lateinit var intentLaunch: ActivityResultLauncher<Intent>
internal lateinit var DAO : ComunidadDAO
private var comunidadPosition = -1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                finish()
            }
        })

        setSupportActionBar(binding.toolbar)
        DAO = ComunidadDAO()
        lista = DAO.cargarLista(this)
        layoutManager = LinearLayoutManager(this)
        binding.rvComunidades.layoutManager = layoutManager
        adapter = ComunidadAdapter(lista) { Comunidad ->
            onItemSelected(Comunidad)
        }

        binding.rvComunidades.adapter = adapter

        binding.rvComunidades.setHasFixedSize(true)
        binding.rvComunidades.setOnCreateContextMenuListener(this)
        registerForContextMenu(binding.rvComunidades)

        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result : androidx.activity.result.ActivityResult ->
            if (result.resultCode == RESULT_OK){
                val posicion = result.data?.extras?.getInt("position", 0)
                lista[posicion!!].nombre=result.data?.extras?.getString("nuevoNombre").toString()
                adapter.notifyItemChanged(posicion)

            }
        }
    }

    private fun onItemSelected(comunidad : Comunidad) {
        Toast.makeText(this,"Yo soy de ${comunidad.nombre}", Toast.LENGTH_SHORT).show()
        // Crear un Intent para iniciar la actividad MapaActivity
        val intent = Intent(this, MapaActivity::class.java)

        intent.putExtra("comunidadNombre", comunidad.nombre)
        intent.putExtra("comunidadHabitantes", comunidad.habitantes)
        intent.putExtra("comunidadCapital", comunidad.capital)
        intent.putExtra("comunidadLatitud", comunidad.latitud)
        intent.putExtra("comunidadLongitud", comunidad.longitud)

        // Iniciar la actividad MapaActivity
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_reload -> {
                val newList = DAO.cargarLista(this)
                adapter.updateList(newList)
                return true
            }

            R.id.menu_clear -> {
                val emptyList = emptyList<Comunidad>()
                adapter.updateList(emptyList)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var comunidadAfectada: Comunidad = lista[item.groupId]
        when (item.itemId) {
            R.id.context_edit -> {
                comunidadPosition = item.groupId // Almacena la posición de la comunidad editada
                val intent = Intent(this, TwoActivity::class.java)
                val comunidad = lista[comunidadPosition]
                intent.putExtra("comunidadNombre", comunidad.nombre)
                intent.putExtra("position", item.groupId)
                intent.putExtra("imagenBandera", comunidad.imagen)
                intentLaunch.launch(intent)
                return true
            }

            R.id.context_delete -> {
                val alert =
                    AlertDialog.Builder(this).setTitle("Eliminar ${comunidadAfectada.nombre}")
                        .setMessage(
                            "¿Estas seguro de que desea eliminar ${comunidadAfectada.nombre}?"
                        )
                        .setNeutralButton("Cerrar", null).setPositiveButton("Aceptar")
                        { _, _ ->
                            //Para eliminar de la base de datos
                            DAO.eliminarComunidad(this, comunidadAfectada)
                            //lista.remove(comunidadEliminada)
                            val newList = DAO.cargarLista(this)
                            adapter.updateList(newList)
                            display("Se ha eliminado ${comunidadAfectada.nombre}")
                            comunidadPosition = -1

                        }.create()
                alert.show()
            }
            R.id.context_make_photo -> {
                val intent = Intent(this, FotoActivity::class.java)
                intent.putExtra("id", comunidadAfectada.id)
                intent.putExtra("comunidadNombre", comunidadAfectada.nombre)
                this.startActivity(intent)
            }

            R.id.context_see_photo -> {
                val intent = Intent(this, ImageActivity::class.java)
                intent.putExtra("id", comunidadAfectada.id)
                this.startActivity(intent)
            }

            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val nuevoNombre = data?.getStringExtra("nuevoNombre")

                if (comunidadPosition != -1 && nuevoNombre != null) {
                    val comunidad = lista[comunidadPosition]  // Obtén la comunidad de la lista
                    DAO.actualizarNombre(this, comunidad, nuevoNombre) //Con esto cambia el nombre en la base de datos
                    adapter.notifyItemChanged(comunidadPosition) // Notifica al adaptador del cambio
                }
            }
        }
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}