package com.example.toshiba.pushnotificationusingfirebace.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.toshiba.pushnotificationusingfirebace.R;
import com.example.toshiba.pushnotificationusingfirebace.activity_logIn;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment implements View.OnClickListener {

        private Button btnLogin;
        private FirebaseAuth auth;
        private TextView profileName;
        private CircleImageView profileImage;
        private FirebaseFirestore firestore;
        private String user_id;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.fragment_profile, container, false);

          auth=FirebaseAuth.getInstance();
          user_id=auth.getCurrentUser().getUid();

          btnLogin=(Button)view.findViewById(R.id.bt_logOut_pr);
          profileImage=(CircleImageView)view.findViewById(R.id.profile_image_pr);
          profileName=(TextView)view.findViewById(R.id.tv_profileName);

          btnLogin.setOnClickListener(this);

        firestore=FirebaseFirestore.getInstance();
        firestore.collection("Users").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                  String name=documentSnapshot.getString("name");
                  String profile_Image=documentSnapshot.getString("image");
                  profileName.setText(name);
                  Picasso.get().load(profile_Image).placeholder(R.drawable.essam).into(profileImage);



            }
        });

      return  view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_logOut_pr){
                    auth.signOut();
                    Intent intent = new Intent(getContext(),activity_logIn.class);
                    startActivity(intent);
                }




        }
    }

