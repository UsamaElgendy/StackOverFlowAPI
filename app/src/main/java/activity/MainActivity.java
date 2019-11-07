package activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.movie.stackoverflowapi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.UsersAdapter;
import model.User;
import model.UsersReceived;
import rest.APIClient;
import rest.UserEndPoints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<User> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView =  findViewById(R.id.usersRecyslerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new UsersAdapter(myDataSource, R.layout.list_item_user,
                getApplicationContext());

        mRecyclerView.setAdapter(myAdapter);

        loadUsers();
    }

    private void loadUsers() {

        UserEndPoints apiService =
                APIClient.getClient().create(UserEndPoints.class);

        Call<UsersReceived> call = apiService.getUsers("reputation");
        call.enqueue(new Callback<UsersReceived>() {
            @Override
            public void onResponse(Call<UsersReceived> call, Response<UsersReceived> response) {


                myDataSource.clear();
                myDataSource.addAll(response.body().getUsers());
                myAdapter.notifyDataSetChanged();

                List<User> users = response.body().getUsers();
                for (User user : users) {
                    System.out.println(
                            "Name: " + user.getUserName() +
                                    "; Location: " + user.getLocation() +
                                    "; Reputation:  " + user.getReputation()
                    );

                    System.out.println("Badges: ");

                    for (Map.Entry<String, Integer> entry : user.getBadges().entrySet()) {
                        String key = entry.getKey().toString();
                        Integer value = entry.getValue();
                        System.out.println(key + " : " + value);
                    }
                }

            }

            @Override
            public void onFailure(Call<UsersReceived> call, Throwable t) {
                System.out.println("FAILURE");

            }
        });

    }
}