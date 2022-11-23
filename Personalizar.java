package com.empresalogistica.aula2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Personalizar extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizar);

        mEditText = findViewById(R.id.editTextTextPersonName);
        mButton = findViewById(R.id.button2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeFantasia = mEditText.getText().toString();

                if(!nomeFantasia.isEmpty()){
                    getSharedPreferences(MainActivity.ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE).edit().putString("nomeFantasia",nomeFantasia).apply();
                }
            }
        });
    }
}