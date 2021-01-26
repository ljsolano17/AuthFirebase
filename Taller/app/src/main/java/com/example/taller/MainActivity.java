package com.example.taller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Variables para mapear los controles del layout
    private EditText etCorreo;
    private EditText etClave;

    //Variable para crear la instancia de autenticacion
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapeo el control visual a las variables locales
        etCorreo=findViewById(R.id.etCorreo);
        etClave=findViewById(R.id.etClave);

        //Obtengo la instancia desde mi firebase
        mAuth=FirebaseAuth.getInstance();
    }

    //Este metodo se ejecuta cada vez que la app se muestra en la pantalla
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);  //Actualizo la interfaz... me paso de pantalla
    }

    public void registra(View view){
        //Se obtiene el texto de lo que se escribio en etCorreo
        String email = etCorreo.getText().toString();
        //Se obtiene el texto de lo que se escribio en etClave
        String password=etClave.getText().toString();

        //Se invoca la tarea para crear un nuevo usuario con correo/contrase;a
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //obtengo el usuario creado...
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);  //Actualizo la interfaz... me paso de pantalla
                        } else {
                            Toast.makeText(getApplicationContext(), "Falló la autentizacion",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null) {  // todo bien se puede pasar a la pagina principal
            //Crea un intento a partir de la clase printipal
            Intent intent = new Intent(this,Principal.class);

            //Inicio el intento... para que se muestre en la pantalla
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Intente de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view){
        //Se obtiene el texto de lo que se escribio en etCorreo
        String email = etCorreo.getText().toString();
        //Se obtiene el texto de lo que se escribio en etClave
        String password=etClave.getText().toString();

        //Se invoca la tarea para autenticar un usuario con correo/contrase;a
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //obtengo el usuario autenticado
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);  //Actualizo la interfaz... me paso de pantalla
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario no existe...",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


}