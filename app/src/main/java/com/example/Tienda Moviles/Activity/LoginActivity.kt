package com.example.taller2.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.taller2.R


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Referencias a los elementos de la interfaz
        val etCorreo = findViewById<EditText>(R.id.et_username)
        val etContraseña = findViewById<EditText>(R.id.et_contraseña)
        val btnIngresar = findViewById<Button>(R.id.ingresa)
        val tvRegistro = findViewById<TextView>(R.id.registro)
        val tvRecuperar = findViewById<TextView>(R.id.recuperar)

        // Botón para iniciar sesión
        btnIngresar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contraseña = etContraseña.text.toString()

            // Validación básica
            if (correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Ingrese su correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificación de credenciales en SharedPreferences
            if (validarUsuario(correo, contraseña)) {
                guardarSesionActiva(correo)
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                // Redirige al usuario a MainActivity
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("correo", correo)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirecciona a la actividad de registro
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        // Redirecciona a la actividad de recuperación de contraseña
        tvRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuContraActivity::class.java))
        }
    }

    /**
     * Verifica si el usuario existe en SharedPreferences
     */
    private fun validarUsuario(correo: String, contraseña: String): Boolean {
        val sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
        val contraseñaGuardada = sharedPref.getString("$correo-contraseña", null)
        return contraseñaGuardada == contraseña
    }

    /**
     * Guarda el correo del usuario como sesión activa
     */
    private fun guardarSesionActiva(correo: String) {
        val sharedPref = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("correo_activo", correo)
            apply()
        }
    }
}

