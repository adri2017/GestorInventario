package OperacionesProd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestorinventario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OperacionesProd extends AppCompatActivity {

    Button btnagregarprod, btnactprod, btnborrarprod,btnmostrarprod;

    private DatabaseReference ref;
    private FirebaseDatabase database;
    private StorageReference mStorageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_prod);

        btnagregarprod = findViewById(R.id.btnagregarprod);
        btnactprod = findViewById(R.id.btnactualizarprod);
        btnborrarprod = findViewById(R.id.btnborrarprod);
        btnmostrarprod = findViewById(R.id.btnmostrarprod);


        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReference();

        btnagregarprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperacionesProd.this, AgregarProd.class);
                startActivity(i);
            }
        });

        btnactprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperacionesProd.this, ActualizarProd.class);
                startActivity(i);
            }
        });

        btnmostrarprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperacionesProd.this, MostrarProd.class);
                startActivity(i);
            }
        });

        btnborrarprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nombreprod = new EditText(view.getContext());
                AlertDialog.Builder builderborrar = new AlertDialog.Builder(OperacionesProd.this);
                builderborrar.setTitle("Borrar producto");
                builderborrar.setMessage("Introduzca el nombre del objeto que desea borrar: ");
                builderborrar.setView(nombreprod);

                builderborrar.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String nombreedt = nombreprod.getText().toString();

                        if(!nombreedt.isEmpty()){
                            borrarprod(nombreedt);
                        }else{
                            Toast.makeText(OperacionesProd.this, "Escriba el nombre del producto\n" +
                                    "que quiera borrar", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = builderborrar.create();
                alert.show();
            }
        });
    }

    private void borrarprod(String nombre) {

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference();

        ref.child("Inventario").orderByChild("nombre").equalTo(nombre)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            String idfoto = ds.getKey();
                            ds.getRef().removeValue();

                            //codigo borrar imagen de storage
                            StorageReference refs = mStorageReference.child("ImagenProducto/"+idfoto);
                            refs.delete();

                            Toast.makeText(OperacionesProd.this, "Producto borrado con éxito."
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(OperacionesProd.this, "Error borrando producto."
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }





}