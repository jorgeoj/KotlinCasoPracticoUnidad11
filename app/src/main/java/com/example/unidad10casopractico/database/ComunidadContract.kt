package com.example.unidad10casopractico.database

import android.provider.BaseColumns

class ComunidadContract {
    companion object{
        const val NOMBRE_BD = "Comunidades"
        const val VERSION = 1
        class Entrada: BaseColumns {
            companion object{
                const val NOMBRE_TABLA = "Comunidades"
                const val COLUMNA_ID = "id"
                const val COLUMNA_NOMBRE = "nombre"
                const val COLUMNA_IMAGEN = "imagen"
                const val COLUMNA_HABITANTES = "habitantes"
                const val COLUMNA_CAPITAL = "capital"
                const val COLUMNA_LATITUD = "latitud"
                const val COLUMNA_LONGITUD = "longitud"
                const val COLUMNA_ICONO = "icono"
                const val COLUMNA_URI = "uri"
            }
        }
    }
}