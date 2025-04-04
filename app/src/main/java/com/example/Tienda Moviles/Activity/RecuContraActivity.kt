package com.example.taller2.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.taller2.R
class RecuContraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recucontra)

        val etCorreo = findViewById<EditText>(R.id.correo2)
        val btnEnviarSolicitud = findViewById<Button>(R.id.soli)

        btnEnviarSolicitud.setOnClickListener {
            val correo = etCorreo.text.toString().trim()

            // Validación del campo
            if (correo.isEmpty()) {
                Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificación del correo
            if (verificarCorreoRegistrado(correo)) {
                Toast.makeText(this, "Se envió la solicitud correctamente", Toast.LENGTH_SHORT).show()
                regresarALogin()
            } else {
                Toast.makeText(this, "El correo no está registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Verifica si el correo existe en SharedPreferences
     */
    private fun verificarCorreoRegistrado(correo: String): Boolean {
        val sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
        return sharedPref.contains("$correo-contraseña")
    }

    /**
     * Vuelve a la pantalla de login
     */
    private fun regresarALogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
