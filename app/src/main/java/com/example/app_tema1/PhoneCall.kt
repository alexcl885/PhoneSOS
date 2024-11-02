package com.example.app_tema1

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.example.app_tema1.databinding.ActivityPhoneCallBinding

class PhoneCall : AppCompatActivity() {
    private lateinit var confBinding: ActivityPhoneCallBinding
    private lateinit var numero_telefono_compartido: String
    private lateinit var fichero_compartido: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_phone_call)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        confBinding = ActivityPhoneCallBinding.inflate(layoutInflater) //inflamos el binding
        setContentView(confBinding.root)

        iniciarPreferenciasCompartidas()

        confBinding.btLlamar.setOnClickListener {
            requerirPermisos()
        }
        confBinding.ivAtras.setOnClickListener {
            intent = Intent(this, ConfigPhone::class.java)
            Toast.makeText(this, "Introduce un nuevo numero de teléfono!", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
        mostrarNumeroTelefono()


    }

    /**Inicializamos las preferencias compartidas*/
    private fun iniciarPreferenciasCompartidas() {
        val nombreFicheroCompartido = getString(R.string.nombre_fichero_preferencia_compartida)
        this.numero_telefono_compartido = getString(R.string.numero_telefono)

        this.fichero_compartido = getSharedPreferences(nombreFicheroCompartido, MODE_PRIVATE)

    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { //lambda
            isGranted ->
        if (isGranted) {
            call()

        } else {
            Toast.makeText(
                this, "Necesitas habilitar los permisos",
                Toast.LENGTH_LONG
            ).show()
            goToConfiguration()

        }
    }

    /*Con este metodo vemos si estan activados los permisos de llamada*/
    private fun permisosDelTelefono(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * Metodo que gestiona que si el sdk es mayor 23 tendra que pedir los permisos para
     * que el usuario los acepte y si no pues no pedimos permisos al usuario.
     * */
    private fun requerirPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Verificar si el SDK es mayor que 23
            if (permisosDelTelefono()) { // Si los permisos ya están concedidos
                call() // Realiza la llamada
            } else { // Solicitar permisos
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)

            }
        } else {
            call() // No es necesario solicitar permisos, ya que es una API < 23
        }
    }

    /**Metodo que realiza la llamada*/
    private fun call() {
        val numeroTelefonoCompartido =
            fichero_compartido.getString(getString(R.string.numero_telefono), "") ?: ""
        val intent = Intent(Intent.ACTION_CALL).apply {
            data =
                Uri.parse("tel:${numeroTelefonoCompartido.toString()}")
        }
        startActivity(intent)
    }

    /**Metodo que muestra el numero de telefono del anterior activity
     * a este activity
     * */
    private fun mostrarNumeroTelefono() {
        // Recupera el número de teléfono desde SharedPreferences
        val numeroTelefono =
            fichero_compartido.getString(getString(R.string.numero_telefono), "Numero no valido")

        // Asigna el número al TextView
        confBinding.textView.text = numeroTelefono
    }

    /** Metodo para actualizar el intent en aquellas instancias reutilizables
     *  y creadas desde otro Activity
     * */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    /**Metodo que te manda directo a los ajustes de la aplicacion*/
    private fun goToConfiguration() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null);
        }
        startActivity(intent)
    }
}