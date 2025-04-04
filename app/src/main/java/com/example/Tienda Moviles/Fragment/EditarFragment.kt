package com.example.taller2.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.taller2.Activity.MainActivity
import com.example.taller2.R

class EditarFragment : Fragment() {

    private lateinit var etNombreApellido: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etNumero: EditText
    private lateinit var btnGuardarCambios: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNombreApellido = view.findViewById(R.id.nombre_apellido_editar)
        etCorreo = view.findViewById(R.id.correo_editar)
        etNumero = view.findViewById(R.id.numero_editar)
        btnGuardarCambios = view.findViewById(R.id.cambios_editar)

        cargarDatosUsuario()

        btnGuardarCambios.setOnClickListener {
            guardarCambios()
            (activity as? MainActivity)?.abrirFragmento(
                PerfilFragment()
            )
        }
    }

    private fun cargarDatosUsuario() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val correoActivo = sharedPreferences.getString("correo_activo", null)

        if (correoActivo != null) {
            val userPrefs = requireContext().getSharedPreferences("Usuarios", Context.MODE_PRIVATE)

            val nombre = userPrefs.getString("$correoActivo-nombre", "No disponible") ?: "No disponible"
            val apellido = userPrefs.getString("$correoActivo-apellido", "No disponible") ?: "No disponible"
            val numero = userPrefs.getString("$correoActivo-numero", "No disponible") ?: "No disponible"

            etNombreApellido.setText("$nombre $apellido")
            etCorreo.setText(correoActivo)
            etCorreo.isEnabled = false // No permitir edición del correo
            etNumero.setText(numero)
        }
    }

    private fun guardarCambios() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val correoActivo = sharedPreferences.getString("correo_activo", null)

        if (correoActivo != null) {
            val editor = requireContext().getSharedPreferences("Usuarios", Context.MODE_PRIVATE).edit()

            // Separar nombre y apellido
            val nombreApellido = etNombreApellido.text.toString().split(" ", limit = 2)
            val nombre = nombreApellido.getOrNull(0) ?: ""
            val apellido = nombreApellido.getOrNull(1) ?: ""

            editor.putString("$correoActivo-nombre", nombre)
            editor.putString("$correoActivo-apellido", apellido)
            editor.putString("$correoActivo-numero", etNumero.text.toString())
            editor.apply()
        }
    }
}


