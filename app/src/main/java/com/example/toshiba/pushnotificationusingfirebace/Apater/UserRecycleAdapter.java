package com.example.toshiba.pushnotificationusingfirebace.Apater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.pushnotificationusingfirebace.Model.Users;
import com.example.toshiba.pushnotificationusingfirebace.R;
import com.example.toshiba.pushnotificationusingfirebace.activiyt_user;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.ViewHolder> {
        private List<Users> usersList;
         Context context ;
    public UserRecycleAdapter(Context context,List<Users> usersList) {
        this.usersList = usersList;
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(usersList.get(position).getName());
        final  String name =usersList.get(position).getName();
        Picasso.get().load(usersList.get(position).getImage()).into(holder.circleImageView);
        final String user_id = usersList.get(position).userId;
              holder.mView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent= new Intent(context,activiyt_user.class);
                      intent.putExtra("user_id",user_id);
                      intent.putExtra("user_name",name);
                      context.startActivity(intent);

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
        private TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            circleImageView=(CircleImageView)mView.findViewById(R.id.image_profile);
            name=(TextView)mView.findViewById(R.id.name_profile);
        }
    }
}
