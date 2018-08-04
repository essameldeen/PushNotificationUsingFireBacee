package com.example.toshiba.pushnotificationusingfirebace.Apater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.pushnotificationusingfirebace.Fragment.Notifiaction;
import com.example.toshiba.pushnotificationusingfirebace.Model.Notification;
import com.example.toshiba.pushnotificationusingfirebace.Model.Users;
import com.example.toshiba.pushnotificationusingfirebace.R;
import com.example.toshiba.pushnotificationusingfirebace.activiyt_user;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationRecycleAdapter extends  RecyclerView.Adapter<NotificationRecycleAdapter.ViewHolder> {
    private List<Notification> usersList;
    Context context ;
    FirebaseFirestore firestore;
    public NotificationRecycleAdapter(Context context, List<Notification> usersList) {
        this.usersList = usersList;
        this.context =context;
    }

    @Override
    public NotificationRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificatiorecycle,parent,false);

        return  new NotificationRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationRecycleAdapter.ViewHolder holder, final int position) {
        firestore =FirebaseFirestore.getInstance();
        if(usersList.get(position).getMessage()!=null){
            holder.message.setText(usersList.get(position).getMessage());
        }else {
            holder.message.setText("Fady");
        }

        firestore.collection("Users").document(usersList.get(position).getFrom()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String image = documentSnapshot.getString("image");
                Picasso.get().load(image).into(holder.circleImageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  View  mView;
        private CircleImageView circleImageView;
        private TextView message;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            circleImageView=(CircleImageView)mView.findViewById(R.id.image_profile_not);
            message=(TextView)mView.findViewById(R.id.name_profile_not);
        }
    }
}
