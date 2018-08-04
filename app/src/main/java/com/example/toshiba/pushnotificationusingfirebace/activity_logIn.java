package com.example.toshiba.pushnotificationusingfirebace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class activity_logIn extends AppCompatActivity implements View.OnClickListener {
     private EditText email;
     private EditText passWord;
     private Button logIn;
     private Button register;
     private ProgressBar progressBar;
     private FirebaseAuth auth;
     private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //
        auth= FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        //
        progressBar=(ProgressBar)findViewById(R.id.pB_progressBar_logIn);
         email = (EditText)findViewById(R.id.et_email);
         passWord = (EditText)findViewById(R.id.et_passWord);
        logIn=(Button)findViewById(R.id.bt_logIn);
        register=(Button)findViewById(R.id.bt_Register);
        register.setOnClickListener(this);

        logIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_Register){
            Intent registerIntent=new Intent(this,acticity_register.class);
            startActivity(registerIntent);
        }else if(view.getId()==R.id.bt_logIn){
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email.getText().toString(),passWord.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                              String token_id = FirebaseInstanceId.getInstance().getToken();
                                              String current_id=auth.getCurrentUser().getUid();

                                              Map<String ,Object> token_map=new HashMap<>();
                                              token_map.put("token_id",token_id);
                                              firestore.collection("Users").document(current_id).update(token_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      progressBar.setVisibility(View.INVISIBLE);
                                                       sendToMain();
                                                  }
                                              });

                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Failed To Log In ",Toast.LENGTH_LONG).show();
                        }
                }
            });


        }
    }

    private void sendToMain() {
        Intent intent = new Intent(activity_logIn.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
