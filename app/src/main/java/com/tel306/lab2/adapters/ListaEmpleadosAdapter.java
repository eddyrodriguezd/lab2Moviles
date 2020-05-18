package com.tel306.lab2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.tel306.lab2.ClickListener;
import com.tel306.lab2.R;
import com.tel306.lab2.entidades.Empleado;

import java.lang.ref.WeakReference;

public class ListaEmpleadosAdapter extends RecyclerView.Adapter<ListaEmpleadosAdapter.EmpleadoViewHolder> {

    private final ClickListener listener;

    Empleado[] listaEmpleados;
    Context contexto;

    public ListaEmpleadosAdapter(Empleado[] lista, Context c, ClickListener clickListener) {
        this.listaEmpleados = lista;
        this.contexto = c;
        this.listener = listener;
    }

    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtViewNombreValor;
        TextView txtViewApellidoValor;
        TextView txtEmailValor;
        TextView txtNumeroValor;
        TextView txtTituloTrabajoValor;
        TextView txtSalarioValor;
        TextView txtDepartamento;

        private Button btnEditar;
        private Button btnEliminar;

        private WeakReference<ClickListener> listenerRef;

        public EmpleadoViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            txtViewNombreValor = itemView.findViewById(R.id.txtNombreEmpleadoValor);
            txtViewApellidoValor = itemView.findViewById(R.id.txtViewApellidoValor);
            txtEmailValor = itemView.findViewById(R.id.txtEmailValor);
            txtNumeroValor = itemView.findViewById(R.id.txtNumeroValor);
            txtTituloTrabajoValor = itemView.findViewById(R.id.txtTituloTrabajoValor);
            txtSalarioValor = itemView.findViewById(R.id.txtSalarioValor);
            txtDepartamento = itemView.findViewById(R.id.txtDepartamentoValor);

            btnEditar = itemView.findViewById(R.id.buttonEditarEmpleado);
            btnEliminar = itemView.findViewById(R.id.buttonBorrarEmpleado);
            btnEditar.setOnClickListener(this);
            btnEliminar.setOnClickListener(this);

            listenerRef = new WeakReference<>(listener);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == btnEditar.getId()) {
                listenerRef.get().onPositionClicked(false, getAdapterPosition());
            } else if (v.getId() == btnEliminar.getId()){
                listenerRef.get().onPositionClicked(true, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.single_empleado, parent, false);
        EmpleadoViewHolder empleadoViewHolder = new EmpleadoViewHolder(itemView, listener);
        return empleadoViewHolder;
    }

    @Override
    public void onBindViewHolder(EmpleadoViewHolder holder, int position) {
        Empleado empleado = listaEmpleados[position];
        holder.txtViewNombreValor.setText(empleado.getFirstName());
        holder.txtViewApellidoValor.setText(empleado.getLastName());
        holder.txtEmailValor.setText(empleado.getEmail());
        holder.txtNumeroValor.setText(String.valueOf(empleado.getPhoneNumber()));
        holder.txtTituloTrabajoValor.setText(empleado.getJobId().getJobTitle());
        holder.txtSalarioValor.setText(String.valueOf(empleado.getSalary()));
        holder.txtDepartamento.setText(empleado.getDepartmentId().getDepartmentName());
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.length;
    }
}