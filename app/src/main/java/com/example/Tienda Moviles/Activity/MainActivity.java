package com.example.taller2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.taller2.Fragment.CarritoFragment;
import com.example.taller2.Fragment.CategoriaFragment;
import com.example.taller2.Fragment.EditarFragment;
import com.example.taller2.Fragment.MensajeFragment;
import com.example.taller2.Fragment.PerfilFragment;
import com.example.taller2.Fragment.ProductoFragment;
import com.example.taller2.R;

public class MainActivity extends AppCompatActivity {

    private Button btnVerCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicialización de botones
        ImageButton btnPerfil = findViewById(R.id.button);
        ImageButton btnLogo = findViewById(R.id.button_logo);
        ImageButton btnVerProductos = findViewById(R.id.btn_ver_productos);
        ImageButton btnVerCarrito = findViewById(R.id.btn_ver_carrito);
        btnVerCategorias = findViewById(R.id.btn_ver_categorias);

        // Oculta el botón de categorías al iniciar
        btnVerCategorias.setVisibility(View.GONE);

        // Muestra productos y botón de categorías
        btnVerProductos.setOnClickListener(v -> {
            abrirFragmento(new ProductoFragment());
            btnVerCategorias.setVisibility(View.VISIBLE);
        });

        // Muestra categorías
        btnVerCategorias.setOnClickListener(v -> abrirFragmento(new CategoriaFragment()));

        // Muestra el perfil
        btnPerfil.setOnClickListener(v -> {
            abrirFragmento(new PerfilFragment());
            btnVerCategorias.setVisibility(View.GONE);
        });

        // Muestra el carrito
        btnVerCarrito.setOnClickListener(v -> {
            abrirFragmento(new CarritoFragment());
            btnVerCategorias.setVisibility(View.GONE);
        });

        // Muestra el logo con mensaje
        btnLogo.setOnClickListener(v -> {
            abrirFragmento(new MensajeFragment());
            btnVerCategorias.setVisibility(View.GONE);
        });
    }

    // Método público para abrir fragmentos
    public void abrirFragmento(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Acceso desde PerfilFragment a EditarFragment
    public void abrirEditarFragment() {
        abrirFragmento(new EditarFragment());
    }
}
