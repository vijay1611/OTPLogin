package com.vijay.otplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {
            private EditText input1,input2,input3,input4,input5,input6;
            private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

            final ProgressBar progressBar = findViewById(R.id.progressbar1);
            final Button btnVerify = findViewById(R.id.buttonVerifyOtp);


        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+91-%s",getIntent().getStringExtra("mobile")
        ));

        input1 = findViewById(R.id.inputCode1);
        input2 = findViewById(R.id.inputCode2);
        input3 = findViewById(R.id.inputCode3);
        input4 = findViewById(R.id.inputCode4);
        input5 = findViewById(R.id.inputCode5);
        input6 = findViewById(R.id.inputCode6);

        verificationId = getIntent().getStringExtra("verificationId");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input1.getText().toString().trim().isEmpty()
                || input2.getText().toString().trim().isEmpty()
                        || input3.getText().toString().trim().isEmpty()
                        || input4.getText().toString().trim().isEmpty()
                        || input5.getText().toString().trim().isEmpty()
                        || input6.getText().toString().trim().isEmpty()){
                    Toast.makeText(VerifyOtpActivity.this, "please enter valid code", Toast.LENGTH_SHORT).show();
                return;
                }

                String code = input1.getText().toString()+
                        input2.getText().toString()+
                        input3.getText().toString()+
                        input4.getText().toString()+
                        input5.getText().toString()+
                        input6.getText().toString();
                if(verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    btnVerify.setVisibility(View.GONE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    btnVerify.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(VerifyOtpActivity.this, "the verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });
        findViewById(R.id.resendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+getIntent().getStringExtra("mobile"),
                            60,
                            TimeUnit.SECONDS,
                            VerifyOtpActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(VerifyOtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                     verificationId = newVerificationId;
                                    Toast.makeText(VerifyOtpActivity.this, "otp resent", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                }
        });



        setupOtpInputs();


    }
    private void setupOtpInputs(){
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}