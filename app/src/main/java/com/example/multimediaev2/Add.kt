package com.example.multimediaev2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multimediaev2.Data.Book
import com.example.multimediaev2.Data.DataDBHelper

class Add : AppCompatActivity() {

    //Declaramos los elementos del layout que nos haran falta
    private lateinit var titleText: EditText
    private lateinit var authorText: EditText
    private lateinit var editorialText: EditText

    private lateinit var addButton: Button
    private lateinit var backButton: Button

    //Declaramos el notification manager
    var notificationManager: NotificationManager? = null

    //Instanciamos la variable del id de notificacion
    var notificationID = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //Lo instanciamos
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Creamos el canal de notificaciones
        createNotificationChannel(
                "com.example.notificaciones.addNotis",
                "Registros Añadidos",
                "Canal de noticias de registros que han sido añadidos")

        //Asociamos las variables a su equivalente en el layout
        titleText = findViewById(R.id.title)
        authorText = findViewById(R.id.author)
        editorialText = findViewById(R.id.editorial)

        addButton = findViewById(R.id.addButton)
        backButton = findViewById(R.id.backButton)

        //Le definimos el evento onClick al boton de añadir
        addButton.setOnClickListener {
            //Instanciamos la base de datos
            var db = DataDBHelper(this)

            //String que contendra al titulo
            var titleFieldText = titleText.text.toString().trim()
            var authorFieldText = authorText.text.toString().trim()
            var editorialFieldText = editorialText.text.toString().trim()

            //Si los campos estan llenos y la base de datos no contiene ese titulo
            if (titleFieldText.isNotEmpty() && authorFieldText.isNotEmpty()
                    && editorialFieldText.isNotEmpty() &&
                    !db.contains(titleFieldText)) {
                //Instanciamos la variable libro con dichos datos
                var book = Book(titleFieldText, authorFieldText, editorialFieldText)
                //Insertamos el libro en la base de datos
                db.insert(book)
                //Lanzamos el mensaje
                Toast.makeText(this,"'" + titleFieldText + "' ha sido insertado",
                        Toast.LENGTH_SHORT).show()
                sendNotification(titleFieldText)
            } else if (db.contains(titleFieldText)) {
                //Si el titulo ya está en la base de datos lanzamos el mensaje
                Toast.makeText(this, "Ya se encuentra en los archivos Jedi",
                        Toast.LENGTH_LONG).show()
            } else {
                //Si algun campo no ha sido introducido lanzamos el mensaje
                Toast.makeText(this, "Error, faltan campos por rellenar",
                        Toast.LENGTH_LONG).show()
            }
        }

        //Le definimos el evento onClick al boton de volver
        backButton.setOnClickListener {
            //Instanciamos el intent
            val intent = Intent(this@Add, MainActivity::class.java)
            //Se iniciara la siguiente pantalla
            startActivity(intent)
        }
    }

    fun createNotificationChannel(id: String, name: String, description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }

    fun sendNotification(name: String) {
        val channelID = "com.example.notificaciones.addNotis"
        val icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)

        val notification = Notification.Builder(this,
                channelID)
                .setContentTitle("Notificacion del templo Jedi")
                .setContentText("El Holocron '"+ name +"' ha sido archivado.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .build()

        notificationManager?.notify(notificationID, notification)
        notificationID += 1
    }
}

