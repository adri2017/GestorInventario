package OperacionesPedido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gestorinventario.MenuPrincipal;
import com.example.gestorinventario.R;




public class OperacionesPed extends AppCompatActivity {

    Button btnagregarped;
    Button btnactped;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_ped);

        btnagregarped = findViewById(R.id.btnAgregarPed);
        btnactped = findViewById(R.id.btnactped);


        btnagregarped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(OperacionesPed.this, AgregarPed.class);
                startActivity(i2);
            }
        });


        btnactped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(OperacionesPed.this, ActualizarPed.class);
                startActivity(i2);
            }
        });




    }
}