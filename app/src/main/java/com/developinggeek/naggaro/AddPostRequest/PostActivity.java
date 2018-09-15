package com.developinggeek.naggaro.AddPostRequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.developinggeek.naggaro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    private DatabaseReference usersDatabase;

    ArrayList<PostModel> arrayList=new ArrayList<>();

    ApiInterface service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("add base url here ")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service=retrofit.create(ApiInterface.class);

        usersDatabase = FirebaseDatabase.getInstance().getReference().child("DataSet").child("Rice");
        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    PostModel postModel=new PostModel();
                    postModel=messageSnapshot.getValue(PostModel.class);


                    Call<PostModel> post_responseCall=service.getPostResponse(postModel);
                    post_responseCall.enqueue(new Callback<PostModel>() {
                        @Override
                        public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                            int statusCode=response.code();

                            Log.i("register","2");
                            Log.i("register"," "+statusCode);


                        }

                        @Override
                        public void onFailure(Call<PostModel> call, Throwable t) {
                            Log.i("code1",t.getMessage());

                        }
                    });

                    arrayList.add(postModel);

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
