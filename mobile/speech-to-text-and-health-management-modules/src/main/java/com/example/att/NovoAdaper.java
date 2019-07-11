package com.example.att;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

class NovoAdapter extends RecyclerView.Adapter{

    /* Adapter RecyclerView*/
    private List<Historico_banco> historico_banco;
    pivate Context context;
    public NovoAdapter(List<Historico_banco> historico_banco,Context context){
        this.historico_banco = historico_banco;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NossoViewHolder holder = new NossoViewHolder();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_hitorico, parent, false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        NossoViewHolder holder = (NossoViewHolder) viewholder;

        Historico_banco historico = historico_banco.get(position);

        holder.nome.setText(historico.getNome());
        holder.data.setText(historico.getData());
        holder.quantidade.setText(historico.getQuantidade());
    }

    @Override
    public int getItemCount() {
        return historico_banco.size();
    }
}



public class NossoViewHolder extends RecyclerView.ViewHolder{

    /*
    * preencher com final TextView blabla e atributos do historico*/
    final TextView nome;
    final TextView data;
    final TextView quantidade;

    public NossoViewHolder(View view){
        super(view);
        /* vincular os atributos as views*/
        nome = (TextView) view.findViewById(R.id.item_historico_nome);
        data = (TextView) view.findViewById(R.id.item_historico_data);
        quantidade = (TextView) view.findViewById(R.id.item_historico_quantidade);
    }
}