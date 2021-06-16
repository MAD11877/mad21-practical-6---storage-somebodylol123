package sg.edu.np.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    ArrayList<User> userList = new ArrayList<>();
    DBHandler dbHandler = new DBHandler(this);
    private ImageView imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        /*imageButton = findViewById(R.id.imageView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userQuery();
            }
        });*/
        userList = dbHandler.getUsers();
        RecyclerView recyclerView = findViewById(R.id.rv);
        UserAdapter userAdapter = new UserAdapter(userList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);
    }

//    private void userQuery(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("I AM GOING MAD. THIS IS ACTUALLY MADness!");
//        builder.setTitle("Profile");
//        builder.setCancelable(false);
//        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                int ranVal = rng();
//                Intent intent = new Intent(ListActivity.this, MainActivity.class);
//                intent.putExtra("ranVal",ranVal);
//                startActivity(intent);
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    private int rng(){
        Random ran = new Random();
        int ranVal = ran.nextInt();
        return ranVal;
    }
    private boolean rngBool() {
        Random ran = new Random();
        int ranVal = ran.nextInt(2);
        if (ranVal == 1) {
            return true;
        } else {
            return false;
        }
    }
}