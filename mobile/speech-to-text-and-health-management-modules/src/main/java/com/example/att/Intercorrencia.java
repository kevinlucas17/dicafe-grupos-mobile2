package com.example.att;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Intercorrencia extends AppCompatActivity {

    String intercorrencia,observacoes,tt;


    /*EditText inter_Input ;
    EditText observa_Input;
    EditText data_Input;
    Button salvarButton;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercorrencia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Intercorrência");

        /*inter_Input = (EditText) findViewById(R.id.input_intercorrencia);
        observa_Input = (EditText) findViewById(R.id.input_observacao);

        data_Input = (EditText) findViewById(R.id.input_data);
        salvarButton = (Button)findViewById(R.id.button_salvar);*/

        /*salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intercorrencia = inter_Input.getText().toString();
                observacoes = observa_Input.getText().toString();


            }
        });*/

    }


}
