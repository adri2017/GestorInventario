package OperacionesProd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.gestorinventario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import Clases.Producto;
import OperacionesProd.AdapterMostrar;

public class MostrarProd extends AppCompatActivity {

    RecyclerView recyclerprods;
    FirebaseDatabase database;
    StorageReference storageReference;



    ArrayList<Producto>list;
    DatabaseReference ref;
    AdapterMostrar adapterMostrar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_prod);

        recyclerprods = findViewById(R.id.recyclerinventario);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerprods.setLayoutManager(layoutManager);
        recyclerprods.setHasFixedSize(true);

        //Firebase
        ref = database.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Inventario");

        //Arraylist
        list = new ArrayList<>();

        //metodo get data
        getDataFromFirebase();



    }

    private void getDataFromFirebase() {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Producto producto = new Producto();
                    producto.setUrlimagen(snapshot.child("urlimagen").getValue().toString());
                    producto.setNombre(snapshot.child("nombre").getValue().toString());
                    producto.setDescripcion(snapshot.child("descripcion").getValue().toString());
                    producto.setCantidad(snapshot.child("cantidad").getValue().toString());
                    producto.setPrecio(snapshot.child("precio").getValue().toString());

                    list.add(producto);
                }
                adapterMostrar = new AdapterMostrar(getApplicationContext(), list);
                adapterMostrar.notifyDataSetChanged();
                recyclerprods.setAdapter(adapterMostrar);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}