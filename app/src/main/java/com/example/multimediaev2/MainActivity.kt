package com.example.multimediaev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    //Declaramos los elementos del layout que nos haran falta
    private lateinit var selectAllButton: Button
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Asociamos las variables a los elementos del layout
        selectAllButton = findViewById(R.id.showButton)
        addButton = findViewById(R.id.addButton)
        deleteButton = findViewById(R.id.deleteButton)

        //Le definimos el evento onClick al boton de mostrar
        selectAllButton.setOnClickListener {
            //Instanciamos el intent
            val intent = Intent(this@MainActivity, SelectAll::class.java)
            //Se iniciara la siguiente pantalla
            startActivity(intent)
        }

        //Le definimos el evento onClick al boton de añadir
        addButton.setOnClickListener {
            //Instanciamos el intent
            val intent = Intent(this@MainActivity, Add::class.java)
            //Se iniciara la siguiente pantalla
            startActivity(intent)
        }

        //Le definimos el evento onClick al boton de borrar
        deleteButton.setOnClickListener {
            //Instanciamos el intent
            val intent = Intent(this@MainActivity, Delete::class.java)
            //Se iniciara la siguiente pantalla
            startActivity(intent)
        }
    }
}