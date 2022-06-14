package com.example.multimediaev2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.multimediaev2.Data.DataDBHelper

class SelectAll : AppCompatActivity() {

    //Declaramos los elementos del layout que nos haran falta
    private lateinit var tableText: TextView
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        //Asociamos las variables a su equivalente en el layout
        tableText = findViewById(R.id.table)
        buttonBack = findViewById(R.id.backButton)

        //Instanciamos la base de datos y la query sobre dicha base de datos
        val db = DataDBHelper(this).readableDatabase
        var q = db.rawQuery("SELECT * FROM Books", null)

        //Mientras haya lineas, las leemos y la añadimos al textView "table" del layout
        while (q.moveToNext()) {
            tableText.append("\nTitulo: " + q.getString(q.getColumnIndex("Title"))
                    + "\nAutor: " + q.getString(q.getColumnIndex("Author")) + "\nEditorial: "
                    + q.getString(q.getColumnIndex("Editorial")) + "\n"
                    + "----------------------------------------------------------")
        }

        //Le definimos el evento onClick de volver a la pantalla inicial
        buttonBack.setOnClickListener {
            //Instanciamos el intent
            val intent = Intent(this@SelectAll, MainActivity::class.java)
            //Se iniciara la siguiente pantalla
            startActivity(intent)
        }
    }
}
