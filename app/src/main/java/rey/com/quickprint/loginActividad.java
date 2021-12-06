package rey.com.quickprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.aware.AwareResources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class loginActividad extends AppCompatActivity {
    Button btn_InicioSeion, btn_InicioRegistro, btn_InicioRecuperar;
    EditText edt_RegistroCorreo, edt_RegistroPassword ;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actividad);
        btn_InicioSeion = findViewById(R.id.btn_InicioSesion);
        btn_InicioRegistro = findViewById(R.id.btn_InicioRegistro);
        btn_InicioRecuperar = findViewById(R.id.btn_InicioRecuperar);
        edt_RegistroCorreo = findViewById(R.id.edt_RegistroCorreo);
        edt_RegistroPassword = findViewById(R.id.edt_RegistroPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            IraLogin();
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.edt_RegistroCorreo, Patterns.EMAIL_ADDRESS,R.string.invalid_emil);
        awesomeValidation.addValidation(this,R.id.edt_RegistroPassword, ".{6,}",R.string.invalid_password);

        btn_InicioRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginActividad.this, registroActividad.class);
                startActivity(i);
            }
        });

        btn_InicioSeion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    String correo = edt_RegistroCorreo.getText().toString();
                    String password = edt_RegistroPassword.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                    homeActivity();
                            }else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Error(errorCode);
                            }
                        }
                    });
                }
            }
        });

        btn_InicioRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActividad.this, recuperarPassword.class);
                startActivity(intent);
            }
        });
    }

    private void IraLogin() {
        Intent i = new Intent(loginActividad.this, home_activity.class);
        i.putExtra("correo", edt_RegistroCorreo.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void homeActivity() {
        Intent i = new Intent(loginActividad.this, home_activity.class);
        i.putExtra("correo",edt_RegistroCorreo.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void Error(String error){

        switch (error){

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(loginActividad.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(loginActividad.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(loginActividad.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(loginActividad.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                edt_RegistroCorreo.setError("La dirección de correo electrónico está mal formateada.");
                edt_RegistroCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(loginActividad.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                edt_RegistroPassword.setError("la contraseña es incorrecta ");
                edt_RegistroPassword.requestFocus();
                edt_RegistroPassword.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(loginActividad.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(loginActividad.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(loginActividad.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(loginActividad.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                edt_RegistroCorreo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                edt_RegistroCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(loginActividad.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(loginActividad.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(loginActividad.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(loginActividad.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(loginActividad.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(loginActividad.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(loginActividad.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                edt_RegistroPassword.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                edt_RegistroPassword.requestFocus();
                break;
        }
    }
}