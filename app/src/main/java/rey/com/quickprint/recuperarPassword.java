package rey.com.quickprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class recuperarPassword extends AppCompatActivity {

    EditText edt_CorreoRecuperar;
    Button btn_enviar;
    private String correo = "";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);
        btn_enviar = findViewById(R.id.btn_enviar);
        edt_CorreoRecuperar = findViewById(R.id.edt_correoRecuperar);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = edt_CorreoRecuperar.getText().toString();

                if (!correo.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }
                else {
                    Toast.makeText(recuperarPassword.this, "Dedebe ingresar un correo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(recuperarPassword.this, "Se ha enviado el correo de recuperacion", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(recuperarPassword.this, "No se encontro el correo", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
}