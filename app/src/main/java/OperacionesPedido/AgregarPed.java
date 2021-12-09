package OperacionesPedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestorinventario.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import OperacionesProd.AgregarProd;

public class AgregarPed extends AppCompatActivity {

    EditText edtdirecped, edtnomprodped, edtnomperped, edtcantped, edtidpedid;
    Button btnagregped;

    private FirebaseDatabase database;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ped);

        //referencias
        edtdirecped = findViewById(R.id.edtdirecped);
        edtnomprodped = findViewById(R.id.edtnomprodped);
        edtnomperped = findViewById(R.id.edtnomperped);
        edtcantped = findViewById(R.id.edtcantped);
        edtidpedid = findViewById(R.id.edtidPedido);
        btnagregped = findViewById(R.id.btnagregarped);

        //database
        database = FirebaseDatabase.getInstance();

        btnagregped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPedido();
                Intent i2 = new Intent(AgregarPed.this, OperacionesPed.class);
                startActivity(i2);
            }
        });


    }

    private void agregarPedido() {

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference("Pedidos");

        String idped = edtidpedid.getText().toString();
        String dirped = edtdirecped.getText().toString();
        String nomprodped = edtnomprodped.getText().toString();
        String nomperped = edtnomperped.getText().toString();
        String cantped = edtcantped.getText().toString();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cantidad", cantped);
        hashMap.put("direccion", dirped);
        hashMap.put("nombreper", nomperped);
        hashMap.put("nombreprod", nomprodped);
        hashMap.put("estado", "pendiente");
        hashMap.put("id", idped);

        ref.child(idped).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                Toast.makeText(AgregarPed.this, "Pedido subido con éxito.",Toast.LENGTH_SHORT).show();
            }
        });






    }
}