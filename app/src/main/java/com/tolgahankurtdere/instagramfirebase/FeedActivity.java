package com.tolgahankurtdere.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userEmailsArrayList,imagesArrayList,commentsArrayList;
    RecyclerAdapter recyclerAdapter; //my adapter class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        this.setTitle("Feed");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getData();

        userEmailsArrayList = new ArrayList<>();
        imagesArrayList = new ArrayList<>();
        commentsArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter(userEmailsArrayList,imagesArrayList,commentsArrayList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void getData(){
        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() { //guncellenmesini istedigimiz icin SnapshotListener kullandık
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null) Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                else{
                    if(queryDocumentSnapshots != null){
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                            Map<String,Object> dataFromSnapshot = documentSnapshot.getData();
                            String userEmail = (String) dataFromSnapshot.get("userEmail");
                            String downloadURL = (String) dataFromSnapshot.get("downloadURL");
                            String comment = (String) dataFromSnapshot.get("comment");

                            userEmailsArrayList.add(userEmail);
                            imagesArrayList.add(downloadURL);
                            commentsArrayList.add(comment);

                            recyclerAdapter.notifyDataSetChanged(); //notify adapter
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_post){
            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.sign_out){
            firebaseAuth.signOut();

            Intent intent = new Intent(FeedActivity.this,LogInActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
