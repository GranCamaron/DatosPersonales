package com.example.datospersonales;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText editNombre, editApellidos, editEdad;
    RadioGroup radioGenero;
    Spinner spinnerEstado;
    Switch switchHijos;
    TextView textoMensaje;
    Button botonMostrar, botonLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNombre = findViewById(R.id.editNombre);
        editApellidos = findViewById(R.id.editApellidos);
        editEdad = findViewById(R.id.editEdad);

        radioGenero = findViewById(R.id.radioGenero);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        switchHijos = findViewById(R.id.switchHijos);

        textoMensaje = findViewById(R.id.textoMensaje);
        botonMostrar = findViewById(R.id.botonMostrar);
        botonLimpiar = findViewById(R.id.botonLimpiar);

        ArrayAdapter<CharSequence> adaptador =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.opciones_estado_civil,
                        android.R.layout.simple_spinner_item);

        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinnerEstado.setAdapter(adaptador);

        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatos();
            }
        });

        botonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarFormulario();
            }
        });
    }

    private void mostrarDatos() {

        String nombre = editNombre.getText().toString().trim();
        String apellidos = editApellidos.getText().toString().trim();
        String edadTexto = editEdad.getText().toString().trim();

        String faltan = "";

        if (nombre.isEmpty()) {
            faltan = getString(R.string.campo_nombre);
        }

        if (apellidos.isEmpty()) {
            if (!faltan.isEmpty()) {
                faltan = faltan + ", ";
            }
            faltan = faltan + getString(R.string.campo_apellidos);
        }

        if (edadTexto.isEmpty()) {
            if (!faltan.isEmpty()) {
                faltan = faltan + ", ";
            }
            faltan = faltan + getString(R.string.campo_edad);
        }

        if (!faltan.isEmpty()) {

            textoMensaje.setTextColor(
                    getResources().getColor(R.color.rojo_error));

            textoMensaje.setText(
                    getString(R.string.error_datos, faltan));

            return;
        }

        int edad;

        try {
            edad = Integer.parseInt(edadTexto);
        } catch (NumberFormatException e) {

            textoMensaje.setTextColor(
                    getResources().getColor(R.color.rojo_error));

            textoMensaje.setText(
                    R.string.error_edad_invalida);

            return;
        }

        String edadMensaje;

        if (edad >= 18) {
            edadMensaje = getString(R.string.mayor_edad);
        } else {
            edadMensaje = getString(R.string.menor_edad);
        }

        int generoElegido = radioGenero.getCheckedRadioButtonId();

        String genero;

        if (generoElegido == R.id.radioHombre) {
            genero = getString(R.string.hombre_mensaje);
        } else if (generoElegido == R.id.radioMujer) {
            genero = getString(R.string.mujer_mensaje);
        } else {
            genero = "género no indicado";
        }

        int posicion = spinnerEstado.getSelectedItemPosition();

        String estado = getString(R.string.estado_no_indicado);

        if (posicion > 0) {

            String[] estados;

            if (generoElegido == R.id.radioHombre) {
                estados = getResources().getStringArray(
                        R.array.estados_masculinos);
            } else if (generoElegido == R.id.radioMujer) {
                estados = getResources().getStringArray(
                        R.array.estados_femeninos);
            } else {
                estados = getResources().getStringArray(
                        R.array.estados_neutros);
            }

            estado = estados[posicion - 1];
        }

        String hijos;

        if (switchHijos.isChecked()) {
            hijos = getString(R.string.con_hijos);
        } else {
            hijos = getString(R.string.sin_hijos);
        }

        String mensaje = apellidos + ", " + nombre + ", "
                + edadMensaje + ", " + genero + ", "
                + estado + " y " + hijos + ".";

        textoMensaje.setText("");

        Toast.makeText(
                MainActivity.this,
                mensaje,
                Toast.LENGTH_LONG).show();
    }

    private void limpiarFormulario() {

        editNombre.setText("");
        editApellidos.setText("");
        editEdad.setText("");

        radioGenero.clearCheck();
        spinnerEstado.setSelection(0);
        switchHijos.setChecked(false);

        textoMensaje.setText("");
    }
}