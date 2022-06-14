package com.example.multimediaev2.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Constantes que usaremos para nuestra base de datos
val DATABASE_NAME = "JediArchives"
val TABLE_NAME = "Books"
val COLUMN_TITLE = "Title"
val COLUMN_AUTHOR = "Author"
val COLUMN_EDITORIAL = "Editorial"

class DataDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        //Ejecutamos la statement para crear la tabla
        db?.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_TITLE + " VARCHAR(100) PRIMARY KEY," +
                COLUMN_AUTHOR + " VARCHAR(45)," +
                COLUMN_EDITORIAL + " VARCHAR(45)" + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Ejecutamos el borrar tabla si existe statement
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        //La volvemos a crear
        onCreate(db)
    }

    //Statement para insertar un objeto en la base de datos
    fun insert(book: Book) {
        //Instanciamos la variable db que contendrá la base de datos para su escritura
        val db = this.writableDatabase
        //Instanciamos un contentValues que es un diccionario clave/valor que usa kotlin para insertar valores en una db
        var newBook = ContentValues()
        //Metemos los valores en el diccionario
        newBook.put(COLUMN_TITLE, book.title)
        newBook.put(COLUMN_AUTHOR, book.author)
        newBook.put(COLUMN_EDITORIAL, book.editorial)
        //Y los insertamos en la base de datos
        db.insert(TABLE_NAME, null, newBook)
    }

    //Statement para borrar un registro de la base de datos
    fun delete(titulo: String) {
        //Instanciamos la variable db que contendrá la base de datos para su escritura
        val db = this.writableDatabase
        //Ejecutamos la statement para borrar un registro de la tabla
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = '" + titulo + "'")
        db.close()
    }

    //Metodo contains para ver si la tabla contiene a la clave primaria
    fun contains(title: String): Boolean {
        //Instanciamos la variable db que contendrá la base de datos para su lectura
        val db = this.readableDatabase

        //Instanciamos la variable bool que será el valor que retornara este metodo
        var bool = false

        //Instanciamos la query
        var q = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME + " WHERE "
                + COLUMN_TITLE + " = '" + title + "'", null)
        q.moveToNext()

        //Si el resultado de la query es 1, la base de datos contiene el titulo
        if (q.getString(0).equals("1")) {
            bool = true
        }
        //Si no pues retornamos false
        return bool
    }
}