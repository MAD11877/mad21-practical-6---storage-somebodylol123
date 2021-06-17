package sg.edu.np.madpractical;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    public final static String TAG = "UserAdapter";
    ArrayList<User> data;
    public UserAdapter(ArrayList<User> input) { data = input; };

    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item;
        if (viewType == 0){
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.userrecyclerview2,parent,false);
        }
        else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.userrecyclerview,parent,false);
        }
        return new UserViewHolder(item);
    }

    public void onBindViewHolder(UserViewHolder holder, int position){
        User userobj = data.get(position);
        holder.txt.setText(userobj.getName() + "\n" + userobj.getDescription());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userQuery(holder, position, userobj);
            }
        });
        
    }

    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position){
        User userobj = data.get(position);
        Log.v(TAG, "" + userobj.getName().charAt(userobj.getName().length() - 1));
        if (userobj.getName().charAt(userobj.getName().length() - 1) == '7'){
            return 0;
        }
        else {
            return 1;
        }
    }


    private void userQuery(UserViewHolder holder, int position, User userobj){
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.image.getContext());
        builder.setMessage(userobj.getName());
        builder.setTitle("Profile");
        builder.setCancelable(true);
        builder.setNegativeButton("close", null);
        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("follow",userobj.getFollowed());
                bundle.putString("name", userobj.getName());
                bundle.putString("desc", userobj.getDescription());
                bundle.putInt("id", userobj.getId());
                Intent intent = new Intent(holder.image.getContext(), MainActivity.class);
                intent.putExtras(bundle);
                holder.image.getContext().startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
