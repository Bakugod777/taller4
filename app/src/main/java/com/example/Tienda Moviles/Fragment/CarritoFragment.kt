package com.example.taller2.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taller2.R

class CarritoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        val contenedorCarrito: LinearLayout = view.findViewById(R.id.contenedor_carrito)
        val txtTotal: TextView = view.findViewById(R.id.txt_total)
        val btnPagar: Button = view.findViewById(R.id.btn_pagar)

        actualizarCarrito(contenedorCarrito, txtTotal)

        btnPagar.setOnClickListener {
            if (CarritoManager.obtenerProductos().isNotEmpty()) {
                CarritoManager.limpiarCarrito()
                actualizarCarrito(contenedorCarrito, txtTotal)
                Toast.makeText(requireContext(), "✅ ¡Compra realizada con éxito!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "⚠️ El carrito está vacío", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun actualizarCarrito(contenedorCarrito: LinearLayout, txtTotal: TextView) {
        contenedorCarrito.removeAllViews()
        var total = 0.0

        for ((producto, cantidad) in CarritoManager.obtenerProductos()) {
            val itemView = layoutInflater.inflate(R.layout.item_carrito, null)
            val imgProducto: ImageView = itemView.findViewById(R.id.img_producto_carrito)
            val txtNombre: TextView = itemView.findViewById(R.id.txt_nombre_carrito)
            val txtCantidad: TextView = itemView.findViewById(R.id.txt_cantidad_carrito)
            val txtPrecio: TextView = itemView.findViewById(R.id.txt_precio_carrito)
            val txtSubtotal: TextView = itemView.findViewById(R.id.txt_subtotal_carrito)
            val btnEliminar: Button = itemView.findViewById(R.id.btn_eliminar)

            imgProducto.setImageResource(producto.imagen)
            txtNombre.text = producto.nombre
            txtCantidad.text = "Cantidad: $cantidad"

            // Limpiar el precio y convertirlo a Double
            val precioLimpio = producto.precio.replace("[^0-9.]".toRegex(), "").toDoubleOrNull() ?: 0.0
            txtPrecio.text = "Precio: $${String.format("%.2f", precioLimpio)}"

            val subtotal = precioLimpio * cantidad
            txtSubtotal.text = "Subtotal: $${String.format("%.2f", subtotal)}"
            total += subtotal

            btnEliminar.setOnClickListener {
                CarritoManager.eliminarProducto(producto)
                actualizarCarrito(contenedorCarrito, txtTotal)
            }

            contenedorCarrito.addView(itemView)
        }

        txtTotal.text = "Total: $${String.format("%.2f", total)}"
    }
}

object CarritoManager {
    private val carrito = mutableMapOf<Producto, Int>()

    fun agregarProducto(producto: Producto) {
        carrito[producto] = (carrito[producto] ?: 0) + 1
    }

    fun eliminarProducto(producto: Producto) {
        carrito[producto]?.let {
            if (it > 1) carrito[producto] = it - 1 else carrito.remove(producto)
        }
    }

    fun obtenerProductos(): Map<Producto, Int> = carrito

    fun limpiarCarrito() {
        carrito.clear()
    }
}
