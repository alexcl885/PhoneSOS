package com.example.app_tema1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app_tema1.databinding.ActivityConfigPhoneBinding

class ConfigPhone : AppCompatActivity() {
    private lateinit var confBinding : ActivityConfigPhoneBinding //para preferencias compartidas
    private lateinit var numero_telefono_compartido : String
    private lateinit var fichero_compartido : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_config_phone)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        confBinding = ActivityConfigPhoneBinding.inflate(layoutInflater) //inflamos el binding
        setContentView(confBinding.root) //carga el binding (el layout).root es el punto principal


        iniciarPreferenciasCompartidas()
        start()

    }

    /**
     * Metodo para inicializar el archivo de las preferencias compartidas
     *
    * */
    private fun iniciarPreferenciasCompartidas(){
        val nombreFicheroCompartido = getString(R.string.nombre_fichero_preferencia_compartida)
        this.numero_telefono_compartido = getString(R.string.numero_telefono)

        this.fichero_compartido = getSharedPreferences(nombreFicheroCompartido, MODE_PRIVATE)
    }

    private fun start() {
        /*val telefono_compartido :String? = fichero_compartido.getString(numero_telefono_compartido, null)

        telefono_compartido?.let {
            startMainActivity(it)
        }*/

        /**
         * Al pulsar el boton al poner un numero de telefono, lo que hace es.
         * 1. Ver si el texto esta vacio; en este caso mostrara un mensaje de error
         * 2. Si no esta vacio; comprobara ->
         *  2.1 Si el numero de telefono no es valido como numero español; cascara un mensaje de error
         *  2.2 Si es valido pues mostrara la siguiente pantalla para poder llamar con ese numero
         * */
        confBinding.btEnviar.setOnClickListener{
            val numero_telefono = confBinding.editTextPhone.text.toString() //cogemos el numero de telefono que habremos escrito

            if (numero_telefono.isEmpty()){
                Toast.makeText(this, "Numero no valido", Toast.LENGTH_LONG).show()
            }
            else{
                if (!comprobarNumeroEspañol(numero_telefono)){
                    Toast.makeText(this, "FORMATO INVALIDO!", Toast.LENGTH_LONG).show()
                }
                else{
                    val edit = fichero_compartido.edit()
                    edit.putString(numero_telefono_compartido, numero_telefono)
                    edit.apply()
                    startMainActivity(numero_telefono)
                }
            }
        }
        /*Boton para volver atras al pulsarlo*/
        confBinding.btAtras.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * Metodo que comprueba si el numero que le pasamos es español
     *
     * */
    private fun comprobarNumeroEspañol(numero_telefono: String): Boolean {
        // Quitar espacios y guiones para simplificar
        val numeroLimpio = numero_telefono.replace(" ", "").replace("-", "")
        // Expresión regular para números españoles de 9 dígitos que comienzan con 6, 7, 8 o 9
        val regex = Regex("^[6789]\\d{8}$")
        return regex.matches(numeroLimpio)

    }

    private fun startMainActivity(numero_telefono: String) {
        val intent = Intent(this, PhoneCall::class.java)
//   estamos llevando informacion de un lado a otro con esto
        intent.apply {
            putExtra("phone", numero_telefono) //mandamos el telefono al otro activity con los extra
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)//no te crea el activity te coge el creado
        }
        startActivity(intent)
    }
}
