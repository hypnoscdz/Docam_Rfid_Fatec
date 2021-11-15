package com.douglasdeadfish1984.docam_rfid_fatec.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.douglasdeadfish1984.docam_rfid_fatec.MainActivity;
import com.douglasdeadfish1984.docam_rfid_fatec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_login;
    private Button btn_registrar;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgressBar;
    private CheckBox ckb_mostrar_senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        btn_login = findViewById(R.id.btn_login);
        btn_registrar = findViewById(R.id.btn_registrar);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        ckb_mostrar_senha = findViewById(R.id.ckb_mostrar_senha);

        btn_login.setOnClickListener(v -> {
            String loginEmail = edt_email.getText().toString();
            String loginSenha = edt_senha.getText().toString();


            if (!validate()){
                Toast.makeText( Login.this,"Complete os campos Obrigatórios", Toast.LENGTH_SHORT).show();

            }

            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginSenha)){
                loginProgressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(loginEmail,loginSenha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    abrirTelaPrincipal();
                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Login.this, "Senha ou usuário inválido", Toast.LENGTH_SHORT).show();
                                    loginProgressBar.setVisibility(View.INVISIBLE);
                                }

                            }
                        });
            }
        });

        btn_registrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
            finish();

        });
        ckb_mostrar_senha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                edt_senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                edt_senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


    }

    private void abrirTelaPrincipal() {
        Intent intent =new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean validate(){
        return (!edt_email.getText().toString().isEmpty()
                && !edt_senha.getText().toString().isEmpty());

    }

}

