package com.agriculturalapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.agriculturalapp.R;
import com.agriculturalapp.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.inEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Enter Phone Number",Toast.LENGTH_SHORT).show();
                }else if(binding.inEmail.getText().toString().trim().length()!=10){
                    Toast.makeText(LoginActivity.this,"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                }else{
                    otpSend();
                }
            }
        });

    }

    private void otpSend(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginButton.setVisibility(View.INVISIBLE);
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                binding.loginButton.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressBar.setVisibility(View.GONE);
                binding.loginButton.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this,Verify.class);
                intent.putExtra("vfId",verificationId);
                startActivity(intent);


            }
        };
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + binding.inEmail.getText().toString().trim())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallback)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}