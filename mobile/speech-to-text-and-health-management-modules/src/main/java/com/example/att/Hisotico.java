package com.example.att;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import java.util.List;

public class Hisotico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisotico);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Histórico");

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recycler); // cria o recycler

        ConverteHistorico convertido_historico;
        List<Historico_banco> historico_banco = convertido_historico.pega_Matriz_historico(); //converte a matriz do Medicamentos em um List tipo historico_banco
        recyclerView.setAdapter(new NovoAdapter(historico_banco,this));

        LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        recyclerView.setLayoutManager(layout);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }
}
