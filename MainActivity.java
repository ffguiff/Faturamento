package com.empresalogistica.aula2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.RadioAccessSpecifier;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //estanciando objetos
    Button mBotaoConfirmar;
    TextView mExibirSaldo;
    NumberPicker mNumberPicker;
    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mBotaoTitulo;

    //nome do arquivo sharedPreference
    public static final String ARQUIVO_MEUS_DADOS = "meu arquivo";
    // metodo sharedPreference
    //toda vez que adicionar um valor ira pegar o valor atual do sharedPreference e soma
    //com o valor que ja estava armazenado anteriormente
    private void adicionarValor(int ano, float valor){
        SharedPreferences sharedPreferences = getSharedPreferences(
            ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);
        float novoValor =  valorAtual + valor;
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();

    }
    private void excluirValor(int ano, float valor){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
    float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);
    float novoValor = valorAtual - valor;
    if (novoValor < 0){
        novoValor =0;
    }
    sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
    }
    private void exibirSaldo(int ano){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
    float saldo = sharedPreferences.getFloat(String.valueOf(ano), 0);
    mExibirSaldo.setText(String.format("R$ %.2f", saldo));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inserido id a os objetos
        mBotaoConfirmar = findViewById(R.id.button);
        mExibirSaldo = findViewById(R.id.textView2);
        mNumberPicker = findViewById(R.id.numberPicker);
        mEditText = findViewById(R.id.editTextTextPersonName2);
        mRadioGroup = findViewById(R.id.radioGroup);
        mBotaoTitulo = findViewById(R.id.button3);

        mNumberPicker.setMinValue(2000);
        mNumberPicker.setMaxValue(2022);

        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Toast.makeText(MainActivity.this, "Teste do listener!", Toast.LENGTH_SHORT).show();
            }
        });
        mBotaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditText.getText().toString().isEmpty()) {
                    //recupera o valor digitado e o converte para float
                    float valor = Float.parseFloat(mEditText.getText().toString());

                    //recupera o ano selecionado
                    int ano = mNumberPicker.getValue();

                    //verifica qual botão radio o usuario seleciono
                    //para definir qual operação q sera feita
                    //metodo q verifica qual radio foi usado getCheckedRadioButtonId()
                    switch (mRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radioButton3:
                            adicionarValor(ano, valor);
                            break;
                        case R.id.radioButton4:
                            excluirValor(ano, valor);
                            break;
                    }
                    exibirSaldo(ano);
                }
            }
        });
        mBotaoTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intente permite a paginação de telas dentro da sua aplicação
                Intent intent = new Intent(getBaseContext(),Personalizar.class);
                startActivity(intent);
            }
        });
    }

    @Override
        protected  void onResume(){
            super.onResume();
            SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
            String nomeFantasia = sharedPreferences.getString("nomeFantasia", null);
            if (nomeFantasia!=null){
                setTitle(nomeFantasia);
            }
            int ano = mNumberPicker.getValue();
            exibirSaldo(ano);
        }
    }
