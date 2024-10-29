package com.example.app_tema1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var iv_gitHub : ImageView
    private lateinit var iv_maps : ImageView
    private lateinit var iv_phone : ImageView
    private lateinit var iv_email : ImageView
    private lateinit var intent :Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iv_gitHub = findViewById(R.id.iv6)
        iv_maps = findViewById(R.id.iv5)
        iv_phone = findViewById(R.id.iv3)
        iv_email = findViewById(R.id.iv4)




        /**
         * Hacemos que al pulsar el telefono nos mande a otra activity
         */

        iv_phone.setOnClickListener{view ->
            intent = Intent(this, ConfigPhone::class.java)
            startActivity(intent)
        }
        /**
         * Hacemos que al pulsar el boton de email nos mande a la pagina web de Email
         *  @param url es la URL donde se situa gmail
         * */
        iv_email.setOnClickListener{
            val url = "https://mail.google.com"
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }


        /**
         * Hacemos que al pulsar el boton de gitHub nos mande a la pagina web de GitHub
        *  @param url es la URL donde se situa gitHub
        * */
        iv_gitHub.setOnClickListener{ view ->
            val url = "https://github.com/"
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        /**
         * Hacemos que al pulsar el boton del mapa nos mande a la pagina web de google maps
         *  @param url es la URL donde se situa maps
         * */
        iv_maps.setOnClickListener{view ->
            val url = "https://www.google.com/maps"
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

    }




}