package com.bit.turit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormularioActivity extends AppCompatActivity {

    //VARIABLES
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*----------findViewById----------*/
        button = findViewById(R.id.enviarFormularioButton);

        /*----------Botón que nos lleva a GraciasActivity y le suma puntos al contador----------*/
        button.setOnClickListener(v -> openGraciasActivity());
    } // FINAL DE ONCREATE

    private void openGraciasActivity() {
        Intent intent = new Intent(this, GraciasActivity.class);
        startActivity(intent);
    }
}