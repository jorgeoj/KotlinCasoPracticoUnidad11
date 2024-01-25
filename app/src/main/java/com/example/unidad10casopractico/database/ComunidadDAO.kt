package com.example.unidad10casopractico.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.unidad10casopractico.Comunidad

class ComunidadDAO {
    fun cargarLista(context: Context?): MutableList<Comunidad> {
        lateinit var res: MutableList<Comunidad>
        lateinit var cursor: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val sql = "SELECT * FROM comunidades;"
            cursor = db.rawQuery(sql, null)
            val columnas = arrayOf(
                ComunidadContract.Companion.Entrada.COLUMNA_ID,
                ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE,
                ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN,
                ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES,
                ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL,
                ComunidadContract.Companion.Entrada.COLUMNA_LATITUD,
                ComunidadContract.Companion.Entrada.COLUMNA_LONGITUD,
                ComunidadContract.Companion.Entrada.COLUMNA_ICONO,
                ComunidadContract.Companion.Entrada.COLUMNA_URI
            )

            cursor = db.query(
                ComunidadContract.Companion.Entrada.NOMBRE_TABLA,
                columnas, null, null, null, null, null
            )
            res = mutableListOf()

            while (cursor.moveToNext()) {
                val nueva = Comunidad(
                    cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3),
                    cursor.getString(4), cursor.getDouble(5),
                    cursor.getDouble(6), cursor.getInt(7),
                    cursor.getString(8)
                )
                res.add(nueva)
            }
        } finally {
            cursor.close()
        }
        return res
    }

    fun eliminarComunidad(context: Context, comunidades: Comunidad) {
        val db = DBOpenHelper.getInstance(context)?.writableDatabase

        if (db != null) {
            try {
                // Definir la condición para la eliminación
                val whereClause = "${ComunidadContract.Companion.Entrada.COLUMNA_ID} = ?"
                val whereArgs = arrayOf(comunidades.id.toString())

                // Realizar la eliminación en la base de datos
                db.delete(
                    ComunidadContract.Companion.Entrada.NOMBRE_TABLA,
                    whereClause,
                    whereArgs
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.close()
            }
        }
    }

    fun actualizarNombre(context: Context, comunidad: Comunidad, nuevoNombre: String) {
        val db = DBOpenHelper.getInstance(context)?.writableDatabase

        if (db != null) {
            try {
                db.beginTransaction() // Inicia una transacción
                val values = ContentValues()
                values.put(ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE, nuevoNombre)

                // Actualiza el nombre de la comunidad en la base de datos
                val whereClause = "${ComunidadContract.Companion.Entrada.COLUMNA_ID} = ?"
                val whereArgs = arrayOf(comunidad.id.toString())
                db.update(
                    ComunidadContract.Companion.Entrada.NOMBRE_TABLA,
                    values,
                    whereClause,
                    whereArgs
                )
                db.setTransactionSuccessful() // Marca la transacción como exitosa
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction() // Finaliza la transacción
                db.close()
            }
        }
    }

    fun obtenerComunidad(context: Context?, id: Int): Comunidad {
        lateinit var comunidadAutonoma: Comunidad
        lateinit var c: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val sql = "SELECT * FROM comunidades WHERE id = ?;"
            val selectionArgs = arrayOf(id.toString())
            c = db.rawQuery(sql, selectionArgs)

            val columnas = arrayOf(
                ComunidadContract.Companion.Entrada.COLUMNA_ID,
                ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE,
                ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN,
                ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES,
                ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL,
                ComunidadContract.Companion.Entrada.COLUMNA_LATITUD,
                ComunidadContract.Companion.Entrada.COLUMNA_LONGITUD,
                ComunidadContract.Companion.Entrada.COLUMNA_ICONO,
                ComunidadContract.Companion.Entrada.COLUMNA_URI
            )

            val identificador = id.toString()
            val valores = arrayOf(identificador)
            c = db.query(
                ComunidadContract.Companion.Entrada.NOMBRE_TABLA,
                columnas, "id=?", valores, null, null, null)

            // Leer resultados del cursor e insertarlos en la lista
            while (c.moveToNext()) {
                comunidadAutonoma = Comunidad(c.getInt(0), c.getString(1),
                    c.getInt(2), c.getInt(3),
                    c.getString(4), c.getDouble(5),
                    c.getDouble(6), c.getInt(7), c.getString(8))
            }
        } finally {
            c.close()
        }
        return comunidadAutonoma
    }

    fun actualizarBBDD(context: Context?, comunidad: Comunidad){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase

        val values = ContentValues()
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_ID, comunidad.id)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE, comunidad.nombre)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN, comunidad.imagen)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES, comunidad.habitantes)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL, comunidad.capital)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_LATITUD, comunidad.latitud)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_LONGITUD, comunidad.longitud)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_ICONO, comunidad.icono)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_URI, comunidad.uri)

        db.update(ComunidadContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            "id=?",
            arrayOf(comunidad.id.toString())
        )
        db.close()
    }
}