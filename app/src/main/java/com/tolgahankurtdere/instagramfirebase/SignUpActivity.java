package com.tolgahankurtdere.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText,passwordText,passwordText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Intent intent = getIntent();

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.editText_email);
        passwordText = findViewById(R.id.editText_password);
        passwordText2= findViewById(R.id.editText_password2);
    }

    public void click_signUp(View view){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String password2 = passwordText2.getText().toString();

        if(password.matches(password2)){
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUpActivity.this,"User Created!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(SignUpActivity.this,"Check Passwords!",Toast.LENGTH_LONG).show();
        }
    }
}
