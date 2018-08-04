package com.example.toshiba.pushnotificationusingfirebace.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.pushnotificationusingfirebace.Apater.NotificationRecycleAdapter;
import com.example.toshiba.pushnotificationusingfirebace.Apater.UserRecycleAdapter;
import com.example.toshiba.pushnotificationusingfirebace.Model.Notification;
import com.example.toshiba.pushnotificationusingfirebace.Model.Users;
import com.example.toshiba.pushnotificationusingfirebace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notifiaction extends Fragment {
    private List<Notification> userList;
     private RecyclerView recyclerView;
     private NotificationRecycleAdapter notificationRecycleAdapter ;
     private FirebaseFirestore  firestore;


    public Notifiaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userList=new ArrayList<>();
        firestore=FirebaseFirestore.getInstance();
         String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        View view =inflater.inflate(R.layout.fragment_notifiaction, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.notificationRecycle);
        notificationRecycleAdapter  = new NotificationRecycleAdapter(getContext(),userList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notificationRecycleAdapter);
       firestore.collection("Users").document(user_id).collection("Notifications").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
               if(documentSnapshots!=null){
                   for(DocumentChange documentChange :documentSnapshots.getDocumentChanges()){
                           Notification users =new Notification();
                            users = documentChange.getDocument().toObject(Notification.class);
                           userList.add(users);
                           notificationRecycleAdapter.notifyDataSetChanged();
                   }
               }
           }
       });


     return  view;
    }


}
