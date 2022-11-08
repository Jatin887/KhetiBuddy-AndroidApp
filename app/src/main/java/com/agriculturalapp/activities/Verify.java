package com.agriculturalapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.agriculturalapp.R;
import com.agriculturalapp.databinding.ActivityVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Verify extends AppCompatActivity {
    private ActivityVerifyBinding binding;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        verificationId = getIntent().getStringExtra("vfId");
        editTextInput();
        binding.tvResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Verify.this,"OTP sent Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        binding.verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBarVerify.setVisibility(View.VISIBLE);
                binding.verifyButton.setVisibility(View.INVISIBLE);
                if(binding.etC1.getText().toString().trim().isEmpty()||
                        binding.etC2.getText().toString().trim().isEmpty()||
                        binding.etC3.getText().toString().trim().isEmpty()||
                        binding.etC4.getText().toString().trim().isEmpty()||
                        binding.etC5.getText().toString().trim().isEmpty()||
                        binding.etC6.getText().toString().trim().isEmpty()){
                    Toast.makeText(Verify.this,"OTP is not Valid",Toast.LENGTH_SHORT).show();
                }else if(verificationId!=null){
                    String code = binding.etC1.getText().toString().trim()+
                            binding.etC2.getText().toString().trim()+
                            binding.etC3.getText().toString().trim()+
                            binding.etC4.getText().toString().trim()+
                            binding.etC5.getText().toString().trim()+
                            binding.etC6.getText().toString().trim();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    FirebaseAuth.
                            getInstance().
                            signInWithCredential(credential).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                binding.progressBarVerify.setVisibility(View.VISIBLE);
                                binding.verifyButton.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(Verify.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }else{
                                binding.progressBarVerify.setVisibility(View.GONE);
                                binding.verifyButton.setVisibility(View.VISIBLE);
                                Toast.makeText(Verify.this,"OTP is not Valid",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
    private void editTextInput(){
        binding.etC1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etC2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etC2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etC3.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etC3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etC4.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etC4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etC5.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etC5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etC6.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}