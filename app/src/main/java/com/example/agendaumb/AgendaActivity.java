package com.example.agendaumb;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import java.util.*;

/**
 * AgendaActivity — Pantalla de la agenda.
 * Implementa los metodos del diagrama UML:
 *   agregar():   POST a agregar.php → MySQL INSERT
 *   consultar(): GET  a consultar.php → MySQL SELECT
 *
 * Elementos UI requeridos:
 *   Spinner     → spinnerCategoria (al agregar) y spinnerFiltro (al consultar)
 *   CheckBox    → checkActivo (evento vigente o no)
 *   RadioButton → radioGroupPrioridad (Alta / Media / Baja)
 *   ImageView   → imgIconoEvento (icono del formulario)
 */
public class AgendaActivity extends AppCompatActivity {

    // Vistas del formulario
    private EditText    editFecha, editAsunto, editActividad;
    private Spinner     spinnerCategoria, spinnerFiltro;
    private RadioGroup  radioGroupPrioridad;
    private RadioButton radioAlta, radioMedia, radioBaja;
    private CheckBox    checkActivo;
    private Button      btnAgregar, btnConsultar, btnLimpiar, btnVolver;
    private TextView    tvResultados;
    private ProgressBar progressBar;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        // Vincular vistas
        editFecha           = findViewById(R.id.editFecha);
        editAsunto          = findViewById(R.id.editAsunto);
        editActividad       = findViewById(R.id.editActividad);
        spinnerCategoria    = findViewById(R.id.spinnerCategoria);
        spinnerFiltro       = findViewById(R.id.spinnerFiltro);
        radioGroupPrioridad = findViewById(R.id.radioGroupPrioridad);
        radioAlta           = findViewById(R.id.radioAlta);
        radioMedia          = findViewById(R.id.radioMedia);
        radioBaja           = findViewById(R.id.radioBaja);
        checkActivo         = findViewById(R.id.checkActivo);
        btnAgregar          = findViewById(R.id.btnAgregar);
        btnConsultar        = findViewById(R.id.btnConsultar);
        btnLimpiar          = findViewById(R.id.btnLimpiar);
        btnVolver           = findViewById(R.id.btnVolver);
        tvResultados        = findViewById(R.id.tvResultados);
        progressBar         = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        // Boton AGREGAR → metodo agregar() del UML
        btnAgregar.setOnClickListener(v -> agregar());

        // Boton CONSULTAR → metodo consultar() del UML
        btnConsultar.setOnClickListener(v -> consultar());

        // Boton LIMPIAR
        btnLimpiar.setOnClickListener(v -> limpiarCampos());

        // Boton VOLVER
        btnVolver.setOnClickListener(v -> finish());
    }


    // =====================================================
    // METODO: agregar() — UML: + agregar(): void
    // =====================================================
    private void agregar() {

        String fecha     = editFecha.getText().toString().trim();
        String asunto    = editAsunto.getText().toString().trim();
        String actividad = editActividad.getText().toString().trim();

        if (fecha.isEmpty() || asunto.isEmpty() || actividad.isEmpty()) {
            Toast.makeText(this, "Completa fecha, asunto y actividad",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerCategoria.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Selecciona una categoria",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (radioGroupPrioridad.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Selecciona la prioridad",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String categoria = spinnerCategoria.getSelectedItem().toString();
        String prioridad = obtenerPrioridad();
        int    activo    = checkActivo.isChecked() ? 1 : 0;

        btnAgregar.setEnabled(false);
        btnAgregar.setText("Agregando...");

        StringRequest req = new StringRequest(
                Request.Method.POST,
                Constantes.URL_AGREGAR,
                response -> {
                    btnAgregar.setEnabled(true);
                    btnAgregar.setText(getString(R.string.btn_agregar));
                    try {
                        JSONObject json = new JSONObject(response);
                        if ("success".equals(json.getString("status"))) {
                            Toast.makeText(this,
                                    "Evento agregado. ID: " + json.getString("id_agenda"),
                                    Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        } else {
                            Toast.makeText(this,
                                    "Error: " + json.getString("message"),
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error al procesar respuesta",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    btnAgregar.setEnabled(true);
                    btnAgregar.setText(getString(R.string.btn_agregar));
                    Toast.makeText(this,
                            "Sin conexion. Verifica XAMPP y la IP 10.0.2.2",
                            Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fecha",     editFecha.getText().toString().trim());
                params.put("asunto",    editAsunto.getText().toString().trim());
                params.put("actividad", editActividad.getText().toString().trim());
                params.put("categoria", spinnerCategoria.getSelectedItem().toString());
                params.put("prioridad", obtenerPrioridad());
                params.put("activo",    String.valueOf(checkActivo.isChecked() ? 1 : 0));
                return params;
            }
        };
        requestQueue.add(req);
    }


    // =====================================================
    // METODO: consultar() — UML: + consultar(): void
    // =====================================================
    private void consultar() {

        progressBar.setVisibility(View.VISIBLE);
        tvResultados.setText("Consultando...");

        // Filtro por categoria del segundo Spinner
        String filtro = spinnerFiltro.getSelectedItem().toString();
        String url = Constantes.URL_CONSULTAR
                + ("Todas".equals(filtro) ? "" : "?categoria=" + filtro);

        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray  data = json.getJSONArray("data");

                        if (data.length() == 0) {
                            tvResultados.setText("No hay eventos registrados.");
                            return;
                        }

                        StringBuilder sb = new StringBuilder();
                        sb.append("Total: ").append(data.length()).append(" eventos\n\n");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject ev = data.getJSONObject(i);
                            String estado = ev.getInt("activo") == 1
                                    ? "[ACTIVO]" : "[INACTIVO]";
                            sb.append("============================\n");
                            sb.append("ID: ").append(ev.getInt("id_agenda")).append("  ").append(estado).append("\n");
                            sb.append("Fecha:     ").append(ev.getString("fecha")).append("\n");
                            sb.append("Asunto:    ").append(ev.getString("asunto")).append("\n");
                            sb.append("Actividad: ").append(ev.getString("actividad")).append("\n");
                            sb.append("Categoria: ").append(ev.getString("categoria")).append("\n");
                            sb.append("Prioridad: ").append(ev.getString("prioridad")).append("\n");
                        }
                        sb.append("============================");
                        tvResultados.setText(sb.toString());

                    } catch (JSONException e) {
                        tvResultados.setText("Error al leer los datos");
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    tvResultados.setText(
                            "Error de conexion.\n\n" +
                                    "Verifica que:\n" +
                                    "1. Apache en XAMPP este corriendo\n" +
                                    "2. MySQL Workbench este corriendo\n" +
                                    "3. La IP 10.0.2.2 es correcta para emulador"
                    );
                }
        );
        requestQueue.add(req);
    }


    // Obtener la prioridad del RadioGroup
    private String obtenerPrioridad() {
        int id = radioGroupPrioridad.getCheckedRadioButtonId();
        if (id == R.id.radioAlta) return "Alta";
        if (id == R.id.radioBaja) return "Baja";
        return "Media";
    }

    // Limpiar todos los campos del formulario
    private void limpiarCampos() {
        editFecha.setText("");
        editAsunto.setText("");
        editActividad.setText("");
        spinnerCategoria.setSelection(0);
        radioMedia.setChecked(true);
        checkActivo.setChecked(true);
    }
}
