package rey.com.quickprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class PagoActivity extends AppCompatActivity {
    private Spinner spinnerOpciones;
    private Button btn_pago;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        spinnerOpciones = (Spinner) findViewById(R.id.spinnerOpciones);
        btn_pago = (Button) findViewById(R.id.btn_pago);

        String[] opciones = {"Efectivo","Tarjeta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,opciones);
        spinnerOpciones.setAdapter(adapter);


        btn_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PagoActivity.this, "Forma de pago seleccionada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void Seleccion(View view){
        String Seleccion = spinnerOpciones.getSelectedItem().toString();
        switch (Seleccion){
            case "Efectivo":{
                Toast.makeText(this, "Tu metodo de pago se cambio a efectivo ", Toast.LENGTH_LONG).show();
                break;
            }
            case "Tarjeta":{
                Toast.makeText(this, "Tu metodo de pago se cambio a tarjeta", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}