package com.example.toshiba.pushnotificationusingfirebace;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class acticity_register extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE = 1;
    private EditText email;
    private  EditText name;
    private  EditText passWord;
    private  Button logIn;
    private Button create;
    private  Uri imageUri;
    private  ProgressBar progressBar ;
    private   CircleImageView circleImageView ;
     FirebaseAuth mauth ;
     StorageReference mstorage;
     FirebaseFirestore mfireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticity_register);
        imageUri =null;
        // init FireBase Component
         mstorage = FirebaseStorage.getInstance().getReference().child("images");
         mauth = FirebaseAuth.getInstance();
         mfireStore=FirebaseFirestore.getInstance();
        //
        progressBar =(ProgressBar)findViewById(R.id.pB_progressBar);
        email = (EditText)findViewById(R.id.et_email_rg);
        name = (EditText)findViewById(R.id.et_name_rg);
        passWord = (EditText)findViewById(R.id.et_passWord_rg);
        logIn=(Button)findViewById(R.id.bt_backToLogIn);
        create=(Button)findViewById(R.id.bt_register_rg);
        circleImageView=(CircleImageView)findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);
        logIn.setOnClickListener(this);
        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_backToLogIn){
           finish();
        }else if(view.getId()==R.id.profile_image){
            Intent getImage= new Intent();
            getImage.setType("image/*");
            getImage.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(getImage,"Select Image"),PICK_IMAGE);
        }else if(view.getId()==R.id.bt_register_rg){
                    progressBar.setVisibility(View.VISIBLE);
                    String email_ = email.getText().toString();
                    String passWord_ = passWord.getText().toString();
      mauth.createUserWithEmailAndPassword(email_, passWord_)
              .addOnCompleteListener(acticity_register.this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          final String user_id= mauth.getCurrentUser().getUid();
                          StorageReference my_profile=mstorage.child(user_id+".jpg");
                          my_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                              @Override
                              public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                           if(task.isSuccessful()){
                                               final String downloadUrl = task.getResult().getDownloadUrl().toString();
                                                       String token_id= FirebaseInstanceId.getInstance().getToken();
                                                       Map<String,Object> user_map  = new HashMap<>();
                                                       user_map.put("name",name.getText().toString());
                                                       user_map.put("image",downloadUrl);
                                                       user_map.put("token_id",token_id);
                                                       mfireStore.collection("Users").document(user_id).set(user_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void aVoid) {
                                                               progressBar.setVisibility(View.INVISIBLE);
                                                               GoToMainActivity();
                                                           }
                                                       });
                                           }else {
                                               progressBar.setVisibility(View.INVISIBLE);
                                               Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                       Toast.LENGTH_SHORT).show();
                                           }
                              }
                          });
                      } else {
                          progressBar.setVisibility(View.INVISIBLE);
                          Toast.makeText(getApplicationContext(), "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                      }
                  }
              });
            }
    }

    private void GoToMainActivity() {
        Intent intent= new Intent(acticity_register.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           if(requestCode==PICK_IMAGE && resultCode==RESULT_OK ){
              imageUri=data.getData();
               circleImageView.setImageURI(imageUri);
           }

    }
}
