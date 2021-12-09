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
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestorinventario.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class ActualizarProd extends AppCompatActivity {

    EditText edtactnombre, edtactactcantidad, edtactdescrip, edtactprecio;
    Button btnactprod;
    String nombreact, idnombre="";
    TextView tvnombreact, tvdescripact, tvprecioact, tvcantiact;

    private DatabaseReference ref;
    private FirebaseDatabase database;
    private StorageReference mStorageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_prod);

        edtactnombre = findViewById(R.id.edtactNombre);
        edtactactcantidad = findViewById(R.id.edtactcantidad);
        edtactdescrip = findViewById(R.id.edtactescrip);
        edtactprecio = findViewById(R.id.edtactprecio);
        btnactprod = findViewById(R.id.btnactprod);
        tvnombreact = findViewById(R.id.tvnombreact);
        tvdescripact = findViewById(R.id.tvdescripact);
        tvcantiact = findViewById(R.id.tvcantact);
        tvprecioact = findViewById(R.id.tvprecioact);

        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        EditText actprod = new EditText(this);
        AlertDialog.Builder actdialog = new AlertDialog.Builder(this);
        actdialog.setTitle("Actualizar producto");
        actdialog.setMessage("Introduce el nombre del producto\n" +
                "que desee actualizar.");
        actdialog.setView(actprod);

        actdialog.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombreact = actprod.getText().toString().trim();
                Toast.makeText(ActualizarProd.this, "Ingrese los datos nuevos.", Toast.LENGTH_SHORT).show();

                //codigo para conseguir id del nombre
                database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
                ref = database.getReference();
                ref.child("Inventario").orderByChild("nombre").equalTo(nombreact)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds : snapshot.getChildren()){
                                    idnombre = ds.getKey();
                                    cargarDatos(idnombre);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent i2 = new Intent(ActualizarProd.this, OperacionesProd.class);
                startActivity(i2);
            }
        });

        AlertDialog alert = actdialog.create();
        alert.show();


        btnactprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = edtactnombre.getText().toString().trim();
                String descrip = edtactdescrip.getText().toString().trim();
                String cantid = edtactactcantidad.getText().toString().trim();
                String precio = edtactprecio.getText().toString().trim();

                actualizarProd(idnombre, nombre, descrip, cantid, precio);
            }
        });
    }

    private void actualizarProd(String idnombre, String nombre, String descrip, String cantid, String precio) {

        HashMap Producto = new HashMap();
        if(!nombre.isEmpty()){
            Producto.put("nombre", nombre);
        }
        if(!descrip.isEmpty()){
            Producto.put("descripcion", descrip);
        }
        if(!cantid.isEmpty()){
            Producto.put("cantidad", cantid);
        }
        if(!precio.isEmpty()){
            Producto.put("precio", precio);
        }

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference("Inventario");
        ref.child(idnombre).updateChildren(Producto).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(ActualizarProd.this, "Producto actualizado con éxito.", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(ActualizarProd.this, OperacionesProd.class);
                    startActivity(i2);
                }else{
                    Toast.makeText(ActualizarProd.this, "Error actualizando el producto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cargarDatos(String idnombre){

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference().child("Inventario").child(idnombre);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String cant = snapshot.child("cantidad").getValue(String.class);
                    String des =  snapshot.child("descripcion").getValue(String.class);
                    String nom =  snapshot.child("nombre").getValue().toString();
                    String prec =  snapshot.child("precio").getValue(String.class);

                    tvnombreact.setText("Nombre: "+nom);
                    tvdescripact.setText("Descripción: "+des);
                    tvprecioact.setText("Precio: "+prec);
                    tvcantiact.setText("Cantidad: "+cant);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}