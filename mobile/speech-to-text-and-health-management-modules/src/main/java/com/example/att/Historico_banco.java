package com.example.att;

public class Historico_banco {
    String nome,data;
    double quantidade;

    public void Historico_banco(String nome,String data, String quantidade){
        this.nome = nome;
        this.data = data;
        this.quantidade = Double.parseDouble(quantidade);
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setData(String data) {
        this.data = data;
    }
    public double getQuantidade() {
        return quantidade;
    }
    public String getData() {
        return data;
    }
    public String getNome() {
        return nome;
    }
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}

/* Crie essa classe separada no mesmo pacote desta*
  esta classe converte o a matriz em list<Historico_banco> para a view do historico
/
 */

public class ConverteHistorico{
    List<Historico_banco> convert_list;
    Medicamentos medic_historico;
    String[][] matriz_medicamento;
    String[] string_separada;

    public List<Historico_banco> pega_matriz_historico(){
        matriz_medicamento = medic_historico.pegaHistoricoDeohoje();

        for(int i = 0;i<matriz_medicamento.length;++i){
            for(int j =0;j<3;++j){ string_separada = matriz_medicamento[i][j].split(";");
               convert_list.add(new Historico_banco(string_separada[0],string_separada[0][1],string_separada[0][2]));
            }
            string_separada = null;
        }
        return convert_list;
    }


}

/*crie esse xml abaixo la na pasta por favor*/

    <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:orientation="horizontal">
<LinearLayout
        android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:orientation="vertical">
<TextView
            android:text="Nome do Exame"
                    android:layout_width="wrap_content"
                    android:textSize="20dp"
                    android:layout_height="wrap_content"
                    android:textColor="@color/colorPrimaryDark"
                    android:id="@+id/item_historico_nome"/>
<TextView
            android:text="Data"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:id="@+id/item_historico_data"/>
<TextView
            android:text="Descricao do Exame"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:id="@+id/item_historico_quantidade"/>
<TextView
            android:text="etc"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textColor="@color/colorAccent"
                    android:id="@+id/item_historico_etc"/>
<!--apenas uma linha divisoria -->
<View
            android:layout_width="wrap_content"
                    android:layout_height="1dp"
                    android:background="@android:color/darker_gray"/>
</LinearLayout>
</LinearLayout>