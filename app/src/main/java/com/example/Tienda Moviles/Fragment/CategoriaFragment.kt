package com.example.taller2.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.taller2.R

class CategoriaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categoria, container, false)

        val categorias = listOf("Electrónica", "Ropa", "Hogar", "Deportes", "Accesorios")

        val listView: ListView = view.findViewById(R.id.lista_categorias)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categorias)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val categoriaSeleccionada = categorias[position]
            abrirProductoFragment(categoriaSeleccionada)
        }

        return view
    }

    private fun abrirProductoFragment(categoria: String) {
        val fragmentoProducto = ProductoFragment()
        val bundle = Bundle()
        bundle.putString("categoria", categoria)
        fragmentoProducto.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentoProducto)
            .addToBackStack(null)
            .commit()
    }
}
