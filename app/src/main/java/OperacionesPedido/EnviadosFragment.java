package OperacionesPedido;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gestorinventario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import Clases.Pedido;

public class EnviadosFragment extends Fragment {


    FirebaseDatabase database;
    DatabaseReference ref;

    View v;
    RecyclerView recyclerenviados;
    ArrayList<Pedido>listaPedidosEnviados;
    PedidosAdapter pedidosAdapter;


    public EnviadosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_enviados, container,false);
        listaPedidosEnviados = new ArrayList<>();
        recyclerenviados =  v.findViewById(R.id.recyclerenviados);
        recyclerenviados.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarListaEnviados();

        return v;
    }

    private void llenarListaEnviados() {

        ref = database.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Pedidos");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Pedido pedido = new Pedido();
                    pedido.setEstado(snapshot.child("estado").getValue().toString());

                    if(pedido.getEstado().equals("enviado")){

                        pedido.setId(snapshot.child("id").getValue().toString());
                        pedido.setNombreper(snapshot.child("nombreper").getValue().toString());
                        pedido.setDireccion(snapshot.child("direccion").getValue().toString());
                        pedido.setNombreprod(snapshot.child("nombreprod").getValue().toString());
                        pedido.setCantidad(snapshot.child("cantidad").getValue().toString());
                        listaPedidosEnviados.add(pedido);
                    }
                }
                pedidosAdapter = new PedidosAdapter(listaPedidosEnviados);
                pedidosAdapter.notifyDataSetChanged();
                recyclerenviados.setAdapter(pedidosAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}