package com.tel306.lab2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tel306.lab2.ClickListener;
import com.tel306.lab2.R;
import com.tel306.lab2.entidades.Departamento;
import com.tel306.lab2.entidades.Trabajo;

import java.lang.ref.WeakReference;

public class ListaTrabajosAdapter extends RecyclerView.Adapter<ListaTrabajosAdapter.TrabajoViewHolder> {

    private final ClickListener listener;

    Trabajo[] listaTrabajos;
    Departamento[] listaDepartamentos;
    Context contexto;

    public ListaTrabajosAdapter(Trabajo[] lista, Departamento[] listaDepas, Context c, ClickListener listener){
        this.listaTrabajos = lista;
        this.listaDepartamentos = listaDepas;
        this.contexto = c;
        this.listener = listener;
    }

    public static class TrabajoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView txtNombreValor;
        private TextView txtSalarioMaxValor;
        private TextView txtSalarioMinValor;
        private TextView txtDepartamentoValor;

        private Button btnEditar;
        private Button btnEliminar;

        private WeakReference<ClickListener> listenerRef;

        public TrabajoViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);

            txtNombreValor = itemView.findViewById(R.id.txtNombreTrabajoValor);
            txtSalarioMaxValor = itemView.findViewById(R.id.txtSalarioMaxValor);
            txtSalarioMinValor = itemView.findViewById(R.id.txtSalarioMinValor);
            txtDepartamentoValor = itemView.findViewById(R.id.txtDepartamentoValor);

            btnEditar = itemView.findViewById(R.id.buttonEditarTrabajo);
            btnEliminar = itemView.findViewById(R.id.buttonBorrarTrabajo);
            btnEditar.setOnClickListener(this);
            btnEliminar.setOnClickListener(this);

            listenerRef = new WeakReference<>(listener);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == btnEditar.getId()) {
                Toast.makeText(v.getContext(), "Editar elemento de la fila = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else if (v.getId() == btnEliminar.getId()){
                Toast.makeText(v.getContext(), "Eliminar elemento de la fila = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    @NonNull
    @Override
    public TrabajoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.single_trabajo, parent, false);
        TrabajoViewHolder trabajoViewHolder = new TrabajoViewHolder(itemView, listener);
        return trabajoViewHolder;
    }

    @Override
    public void onBindViewHolder(TrabajoViewHolder holder, int position) {
        Trabajo trabajo = listaTrabajos[position];
        holder.txtNombreValor.setText(trabajo.getJobTitle());
        holder.txtSalarioMaxValor.setText(String.valueOf(trabajo.getMaxSalary()));
        holder.txtSalarioMinValor.setText(String.valueOf(trabajo.getMinSalary()));

        for(int i=0; i<listaDepartamentos.length; i++){
            if(listaDepartamentos[i].getDepartmentShortName().equals(trabajo.getJobId().substring(0,2))){
                holder.txtDepartamentoValor.setText(listaDepartamentos[i].getDepartmentName());
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return listaTrabajos.length;
    }
}
