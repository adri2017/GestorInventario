package OperacionesProd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestorinventario.R;

import java.util.ArrayList;
import Clases.Producto;

public class AdapterMostrar extends RecyclerView.Adapter<AdapterMostrar.ViewHolder>{

    private Context context;
    private  ArrayList<Producto> listproductos;

    public AdapterMostrar(Context context, ArrayList<Producto>listproductos){
        this.context = context;
        this.listproductos = listproductos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).
                inflate(R.layout.item_inventario, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Producto producto = listproductos.get(position);
        holder.nombre.setText(producto.getNombre());
        holder.descripcion.setText(producto.getDescripcion());
        holder.cantidad.setText(producto.getCantidad());
        holder.precio.setText(producto.getPrecio());

        //Glide.with(context).load(listproductos.get(position).getUrlimagen()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return listproductos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nombre, descripcion, precio, cantidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageprod);
            nombre = itemView.findViewById(R.id.tvnombre);
            descripcion = itemView.findViewById(R.id.tvdescrip);
            precio = itemView.findViewById(R.id.tvprecio);
            cantidad = itemView.findViewById(R.id.tvcantidad);
        }
    }


}
