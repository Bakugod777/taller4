package com.example.taller2.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.taller2.R

class ProductoFragment : Fragment() {

    private val productos = listOf(
        Producto("Kit Tenis", "Deportes", R.drawable.kit_teniis, "Un Kit profesional Para jugar Teniis", "$200,000", 5),
        Producto("Ropa tenis", "Deportes", R.drawable.ropa_tenis, "Ropa super confort", "$300,000", 3),
        Producto("Microondas", "Electrónica", R.drawable.microondas, "Super util, barato y comodo de 400 whz", "$200,000", 10),
        Producto("Tostadora", "Electrónica", R.drawable.tostadora, "Tostadora de ultima generacion", "$800,000", 7),
        Producto("Camiseta xl", "Ropa", R.drawable.camisa, "Tela transpirable, diseño moderno", "$120,000", 15),
        Producto("lampara Grande", "Hogar", R.drawable.lampara, "Lampara premiun de ultima generacion", "$250,000", 2),
        Producto("Comedor", "hogar", R.drawable.comedor,"Comedor para 4 personas","400,000",4)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_producto, container, false)
        val contenedorProductos: LinearLayout = view.findViewById(R.id.contenedor_productos)

        val categoriaSeleccionada = arguments?.getString("categoria") ?: "Todos"

        val productosFiltrados = if (categoriaSeleccionada == "Todos") {
            productos
        } else {
            productos.filter { it.categoria == categoriaSeleccionada }
        }

        contenedorProductos.removeAllViews()

        if (productosFiltrados.isEmpty()) {
            val mensaje = TextView(requireContext())
            mensaje.text = "No hay productos en esta categoría"
            contenedorProductos.addView(mensaje)
        } else {
            for (producto in productosFiltrados) {
                contenedorProductos.addView(crearCardProducto(producto))
            }
        }

        return view
    }

    private fun crearCardProducto(producto: Producto): View {
        val cardView = layoutInflater.inflate(R.layout.item_producto, null) as CardView
        val imgProducto: ImageView = cardView.findViewById(R.id.img_producto)
        val txtNombre: TextView = cardView.findViewById(R.id.txt_nombre)
        val txtDescripcion: TextView = cardView.findViewById(R.id.txt_descripcion)
        val txtPrecio: TextView = cardView.findViewById(R.id.txt_precio)
        val txtCantidad: TextView = cardView.findViewById(R.id.txt_cantidad)
        val btnAgregar: Button = cardView.findViewById(R.id.btn_agregar)

        imgProducto.setImageResource(producto.imagen)
        txtNombre.text = producto.nombre
        txtDescripcion.text = producto.descripcion
        txtPrecio.text = "Precio: ${producto.precio}"
        txtCantidad.text = "Cantidad: ${producto.cantidad}"

        btnAgregar.setOnClickListener {
            CarritoManager.agregarProducto(
                producto
            )
        }

        return cardView
    }
}

data class Producto(val nombre: String, val categoria: String, val imagen: Int, val descripcion: String, val precio: String, var cantidad: Int)
