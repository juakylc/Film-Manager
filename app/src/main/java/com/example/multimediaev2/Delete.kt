package com.example.multimediaev2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multimediaev2.Data.DataDBHelper

class Delete : AppCompatActivity() {

    //Declaramos los elementos del layout que nos haran falta
    private lateinit var titleText: EditText
    private lateinit var destroyButton: Button
    private lateinit var backButton: Button

    //Declaramos el notification manager
    var notificationManager: NotificationManager? = null

    //Instanciamos la variable del id de notificacion
    var notificationID = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_del)

        //Lo instanciamos
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Creamos el canal de notificaciones
        createNotificationChannel(
                "com.example.notificaciones.deleteNotis",
                "Registros Eliminados",
                "Canal de noticias de registros que han sido eliminados")

        //Asociamos las variables a su equivalente en el layout
        titleText = findViewById(R.id.title)
        destroyButton = findViewById(R.id.destroyButton)
        backButton = findViewById(R.id.backButton)

        //Le definimos el evento onClick al boton de eliminar
        destroyButton.setOnClickListener {
            //Instanciamos la base de datos y el titulo del registro a eliminar
            var db = DataDBHelper(this)
            var title = titleText.text.toString()

            //Si el titulo esta en la tabla, se borra. Si no, se muestra el mensaje de que no esta
            if (db.contains(title)) {
                db.delete(title)
                Toast.makeText(this,
                        "Siento una perturbación en la fuerza. El holocron '" + title + "' ha sido destruido",
                        Toast.LENGTH_SHORT).show()
                sendNotification(title)
            } else if (title.isEmpty()) {
                Toast.makeText(this,
                        "No has rellenado el campo",
                        Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                        "No percibo que ese holocron exista",
                        Toast.LENGTH_SHORT).show()
            }
        }

        //Le definimos el evento onClick al boton de volver
        backButton.setOnClickListener {
            //Instanciamos el intent
            val intent = Intent(this@Delete, MainActivity::class.java)
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
        val channelID = "com.example.notificaciones.deleteNotis"
        val icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)

        val notification = Notification.Builder(this,
                channelID)
                .setContentTitle("Notificacion del templo Jedi")
                .setContentText("El Holocron '"+ name +"' ha sido destruido.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .build()

        notificationManager?.notify(notificationID, notification)
        notificationID += 1
    }
}
