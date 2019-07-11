package com.example.att;

import com.app.speechtotext.ProcessadorResultado;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Identificacao extends AppCompatActivity {
    String inter,data,observacoes ;

    EditText input_inter;
    EditText input_data;
    EditText input_observacoes;
    private EditText resultTextAlim;
    private EditText resultTextQntdAlim;
    private RadioButton radioBtnG;
    private RadioButton radioBtnC;
    private ImageButton btnMic;

    private Double quantidade;
    private String unidade;
    private String alimento;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final String NOME_ARQUIVO_JSON = "alimentos.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Identificação");

        resultTextAlim = findViewById(R.id.editTextNomeAlimento);
        resultTextQntdAlim = findViewById(R.id.editTextQntdAlimento);
        radioBtnG = findViewById(R.id.radioButtonG);
        radioBtnC = findViewById(R.id.radioButtonC);
        btnMic = findViewById(R.id.btnMic);

        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechInput();
            }
        });
    }
    private void speechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, new Locale("pt", "BR"));
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga o alimento e a quantidade que está consumindo...");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }catch(ActivityNotFoundException a){
            Toast.makeText(com.example.att.Identificacao.this, "Desculpe! Seu aparelho não suporta este serviço.", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent intent){
        super.onActivityResult(request_code, result_code, intent);
        switch(request_code){
            case REQ_CODE_SPEECH_INPUT: {
                if(result_code == RESULT_OK && intent != null){
                    List<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    extrairInformacoesDoResultado(result);
                }
                break;
            }
        }
    }
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

    private void extrairInformacoesDoResultado(List<String> resultados){
        List<String> catalogoAlimentos = getFoods(NOME_ARQUIVO_JSON, "descricao");
        ProcessadorResultado processador = new ProcessadorResultado(resultados, catalogoAlimentos);

        this.quantidade = processador.obterQuantidadeDoAlimento();
        this.unidade = processador.obterUnidadeDeMedida();
        List<String> nomesEncontrados = processador.obterDescricaoAlimento();

        if(nomesEncontrados.size() > 1){
            AlertDialog dialog = mountChoiceFoods(nomesEncontrados);
            dialog.show();

        } else if(nomesEncontrados.size() == 1){
            this.alimento = nomesEncontrados.get(0);
            mostrarValores();
        }
        else{
            mostrarValores();
        }

    }

    private void mostrarValores() {
        if(alimento != null){
            resultTextAlim.setText(alimento);
            alimento = null;
        }
        else{
            resultTextAlim.setText("");
            Toast.makeText(com.example.att.Identificacao.this, "Alimento não encontrado!", Toast.LENGTH_SHORT).show();
        }

        if(quantidade != null){ resultTextQntdAlim.setText(quantidade.toString()); }
        else{
            resultTextQntdAlim.setText("");
            Toast.makeText(com.example.att.Identificacao.this, "Quantidade não reconhecida!", Toast.LENGTH_SHORT).show();
        }

        if(null != unidade){
            if(unidade.equals("g")){ radioBtnG.setChecked(true); }
            else if(unidade.equals("kg")){ radioBtnC.setChecked(true); }
            else {
                Toast.makeText(com.example.att.Identificacao.this, "Unidade não reconhecida!", Toast.LENGTH_SHORT
                ).show();
                radioBtnG.setChecked(false);
                radioBtnC.setChecked(false);
            }
        }

    }

    private AlertDialog mountChoiceFoods(List<String> foundFoodArray){
        final String[] foodsChoices = foundFoodArray.toArray(new String[foundFoodArray.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(Identificacao.this)
                .setTitle("Foram Encontrados " + foodsChoices.length + " Alimentos")
                .setIcon(R.drawable.icon_alert_dialog)
                .setSingleChoiceItems(foodsChoices,-1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alimento = foodsChoices[i];
                        mostrarValores();
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }



    private List<String> getFoods(String fileName, String obj){
        List<String> foodList = new ArrayList<String>();

        try{
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            if(jsonArray != null){
                for(int i = 0; i < jsonArray.length(); i++){
                    foodList.add(jsonArray.getJSONObject(i)
                            .getString(obj)
                            .replaceAll(", ", " "));
                }
            }
        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return foodList;
    }
}
