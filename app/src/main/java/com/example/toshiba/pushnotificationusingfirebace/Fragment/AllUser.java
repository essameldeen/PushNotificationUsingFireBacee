package com.example.toshiba.pushnotificationusingfirebace.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.pushnotificationusingfirebace.Apater.UserRecycleAdapter;
import com.example.toshiba.pushnotificationusingfirebace.Model.Users;
import com.example.toshiba.pushnotificationusingfirebace.R;
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
public class AllUser extends Fragment {
    private RecyclerView recyclerView;
   private List<Users> userList;
   private UserRecycleAdapter userRecycleAdapter;
   private FirebaseFirestore firestore;

    public AllUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         userList  =  new ArrayList<>();

          firestore=FirebaseFirestore.getInstance();
         //
         View view = inflater.inflate(R.layout.fragment_all_user, container, false);
         recyclerView=(RecyclerView)view.findViewById(R.id.rv_recycleUser);
         userRecycleAdapter  = new UserRecycleAdapter(getContext(),userList);

         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setAdapter(userRecycleAdapter);


        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        userList.clear();
        firestore.collection("Users").addSnapshotListener (getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(documentSnapshots!=null){
                    for(DocumentChange documentChange :documentSnapshots.getDocumentChanges()){
                        if(documentChange.getType()==DocumentChange.Type.ADDED){
                            String user_id = documentChange.getDocument().getId();

                            Users users = documentChange.getDocument().toObject(Users.class).withId(user_id);
                            userList.add(users);
                            userRecycleAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
