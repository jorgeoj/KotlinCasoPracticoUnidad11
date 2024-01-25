package com.example.unidad10casopractico.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.unidad10casopractico.Comunidad
import com.example.unidad10casopractico.R

class DBOpenHelper private constructor(context: Context?) :
    SQLiteOpenHelper(context, ComunidadContract.NOMBRE_BD, null, ComunidadContract.VERSION){
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(
                "CREATE TABLE ${ComunidadContract.Companion.Entrada.NOMBRE_TABLA}"
                        +"(${ComunidadContract.Companion.Entrada.COLUMNA_ID} INTEGER NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE} NVARCHAR(30) NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN} INTEGER NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES} INTEGER NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL} NVARCHAR(30) NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_LATITUD} REAL NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_LONGITUD} REAL NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_ICONO} INTEGER NOT NULL"
                        +",${ComunidadContract.Companion.Entrada.COLUMNA_URI} NVARCHAR(100) NOT NULL);"
            )
            inicializarBBDD(sqLiteDatabase)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ComunidadContract.Companion.Entrada.NOMBRE_TABLA};")
        onCreate(db)
    }

    private fun inicializarBBDD(sqLiteDatabase: SQLiteDatabase) {
        val lista = cargarComunidades()
        for (comunidades in lista)
            sqLiteDatabase.execSQL(
                ("INSERT INTO ${ComunidadContract.Companion.Entrada.NOMBRE_TABLA}("+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_ID}," +
                        "${ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_LATITUD},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_LONGITUD},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_ICONO},"+
                        "${ComunidadContract.Companion.Entrada.COLUMNA_URI})"+
                        " VALUES (${comunidades.id},'${comunidades.nombre}',${comunidades.imagen}," +
                        "${comunidades.habitantes},'${comunidades.capital}',${comunidades.latitud}," +
                        "${comunidades.longitud},${comunidades.icono}, '${comunidades.uri}');")
            )
    }

    private fun cargarComunidades(): MutableList<Comunidad> {
        return mutableListOf(
            Comunidad(0,"Andalucía", R.drawable.andalucia,8472407 ,"Sevilla",37.56640275933285 ,-4.7406737719892265, R.drawable.andalucia_icon, ""),
            Comunidad(1,"Aragón",  R.drawable.aragon,1326261,"Zaragoza",41.61162981125681, -0.9738034948937436,R.drawable.aragon_icon, ""),
            Comunidad(2,"Asturias",  R.drawable.asturias,1011792,"Oviedo",43.45998093597627, -5.864665888274809,R.drawable.asturias_icon, ""),
            Comunidad(3,"Baleares", R.drawable.baleares,1173008,"Palma de Mallorca",39.57880491837696, 2.904506700284016,R.drawable.baleares_icon, ""),
            Comunidad(4,"Canarias",  R.drawable.canarias,2172944,"Las Palmas de GC y SC de Tenerife",28.334567287944736, -15.913870062646897,R.drawable.canarias_icon, ""),
            Comunidad(5,"Cantabria",  R.drawable.cantabria,584507,"Santander",43.36511077650701, -3.8398424912727958,R.drawable.cantabria_icon, ""),
            Comunidad(6,"Castilla y León", R.drawable.castillaleon,2383139,"No tiene (Valladolid)",41.82966675375594, -4.841538702082391,R.drawable.castillaleon_icon, ""),
            Comunidad(7,"Castilla La Mancha", R.drawable.castillamancha,2049562,"No tiene (Toledo)",39.42393852713387, -3.4784057150456764,R.drawable.castillamancha_icon, ""),
            Comunidad(8,"Cataluña",  R.drawable.catalunya,7763362,"Barcelona",42.07542633707148, 1.5197485699265891,R.drawable.catalunya_icon, ""),
            Comunidad(9,"Ceuta",  R.drawable.ceuta,83517,"Ceuta",35.90091766842379, -5.309980167928874,R.drawable.ceuta_icon, ""),
            Comunidad(10,"Extremadura",  R.drawable.extremadura,1059501,"Mérida",39.05050233766541, -6.351254430283863,R.drawable.extremadura_icon, ""),
            Comunidad(11,"Galicia",  R.drawable.galicia,2695645,"Santiago de Compostela",42.789055617025404, -7.996440102093343,R.drawable.galicia_icon, ""),
            Comunidad(12,"La Rioja",  R.drawable.larioja,319796,"Logroño",42.568072855089895, -2.470916178908127,R.drawable.larioja_icon, ""),
            Comunidad(13,"Madrid",  R.drawable.madrid,6751251,"Madrid",40.429642598652, -3.76167856716930,R.drawable.madrid_icon, ""),
            Comunidad(14,"Melilla",  R.drawable.melilla,86261,"Melilla",35.34689811596408, -2.957162284523383,R.drawable.melilla_icon, ""),
            Comunidad(15,"Murcia",  R.drawable.murcia,1518486,"Murcia",38.088904824462176, -1.4100155858243844,R.drawable.murcia_icon, ""),
            Comunidad(16,"Navarra", R.drawable.navarra,661537,"Pamplona",42.71764719490406, -1.657559057849277,R.drawable.navarra_icon, ""),
            Comunidad(17,"País Vasco", R.drawable.paisvasco,2213993,"Vitoria",43.11260202399828, -2.594687915428055,R.drawable.paisvasco_icon, ""),
            Comunidad(18,"Valencia",  R.drawable.valencia,5058138, "Valencia",39.515011403926145, -0.6939076854376838,R.drawable.valencia_icon, "")
        )

    }

    companion object{
        private var dbOpen: DBOpenHelper? = null
        fun getInstance(context: Context?): DBOpenHelper?{
            if (dbOpen == null) dbOpen = DBOpenHelper(context)
            return dbOpen
        }
    }
}