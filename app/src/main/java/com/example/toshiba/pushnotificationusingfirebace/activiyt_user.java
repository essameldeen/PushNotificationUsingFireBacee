package com.example.toshiba.pushnotificationusingfirebace;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activiyt_user extends AppCompatActivity implements View.OnClickListener {
    private TextView user_id;
    private String Id;
    private ProgressBar progressBar;
    private String current_id;
   private String user_name;
   private EditText message;
   private Button send;
   private FirebaseFirestore firestore;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiyt_user);
         Id=getIntent().getStringExtra("user_id");
         user_name=getIntent().getStringExtra("user_name");
         user_id=(TextView)findViewById(R.id.sendTo);
         send=(Button)findViewById(R.id.send);
          progressBar=(ProgressBar)findViewById(R.id.pB_progressBar_send);
        user_id.setText("Send To "+user_name);


        firestore=FirebaseFirestore.getInstance();
        current_id= FirebaseAuth.getInstance().getUid();

        message=(EditText)findViewById(R.id.notificationContent);

        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send){
            progressBar.setVisibility(View.VISIBLE);
            Map<String ,Object> noticationMap = new HashMap<>();
            noticationMap.put("message",message.getText().toString());
            noticationMap.put("from",current_id);

            firestore.collection("Users/"+Id+"/Notifications").add(noticationMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Notification Sent",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Notification Failed",Toast.LENGTH_LONG).show();
                }
            });


        }
    }
}
