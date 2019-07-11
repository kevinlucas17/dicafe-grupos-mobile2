package com.example.att;

import com.app.speechtotext.ProcessadorResultado;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import dicafe-grupos-mobile.mobile.libs.br.uerj.dicafe.info.Exames;
import mobile/libs/br/uerj/dicafe/info/Exames.java;

public class exame extends AppCompatActivity {

    /*private String exame,observacoes;
    private Date data;

    EditText input_exame;
    EditText input_observacoes;
    EditText input_data;*/
    private String[] nome_exame = {"Glicose em jejum","Glicosúria","Hemoglobina glicada","Triglicerídeos","Creatinina","Colesterol Total","URINA - Uréia",};

    Exames ex = new Exames();
    private Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Exame");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nome_exame);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s = (Spinner) findViewById(R.id.spinner_exame);
        s.setAdapter(adapter);

        /*input_exame = (EditText)findViewById(R.id.input_exame);
        input_observacoes =(EditText)findViewById(R.id.input_observacao);*/
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

