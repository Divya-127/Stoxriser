package com.shahdivya.stoxriser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.shahdivya.stoxriser.Retrofit.INodeJS;
import com.shahdivya.stoxriser.Retrofit.RetroFitClient;
import com.shahdivya.stoxriser.models.loginParams;
import com.shahdivya.stoxriser.models.registerParams;
import com.shahdivya.stoxriser.models.shareHoldersParams;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
{
    INodeJS api;

    EditText email;
    EditText password;
    Button login;
    TextView register;
    public static PersonInfo personInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialise API
        Retrofit retrofit = RetroFitClient.getInstance();
        api = retrofit.create(INodeJS.class);
        personInfo = new PersonInfo();
        //Initialise parameters
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        //on Click for Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsers(email.getText().toString(),password.getText().toString());
            }
        });
        //on Click for register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.register_activity,null,false);
                final EditText nameUser = view.findViewById(R.id.nameRegister);
                final EditText emailUser = view.findViewById(R.id.emailRegister);
                final EditText passwordUser = view.findViewById(R.id.passwordRegister);
                //Display A Dialogue Box for taking register params
                new AlertDialog.Builder(MainActivity.this)
                        .setView(view)
                        .setTitle("\t\t\t\t\t\tREGISTER\t\t\t")
                        .setIcon(R.drawable.ic_baseline_account_circle_24)
                        .setPositiveButton("SIGN UP", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //check for password length to be >5
                                if (passwordUser.getText().toString().length()<5){
                                    Toast.makeText(MainActivity.this,"Registration Unsuccessful password length should be greater than 5",Toast.LENGTH_SHORT).show();
                                }else {
                                    //register the user
                                    registerUsers(nameUser.getText().toString(), emailUser.getText().toString(), passwordUser.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"Cancelled",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }

    private void registerUsers(String userName, String userEmail, String userPassword)
    {
        Call<registerParams> callRegisterUser = api.registerUser(userEmail,userName,userPassword);
        callRegisterUser.enqueue(new Callback<registerParams>() {
            @Override
            public void onResponse(Call<registerParams> call, Response<registerParams> response) {
                if (!response.isSuccessful())
                {
                    Log.i("TAG","Error :"+response.code());
                }else{
                    registerParams body = response.body();
                    //Toast.makeText(MainActivity.this,body.getStatus(),Toast.LENGTH_SHORT).show();
                    if (body.getIdR() > 0)
                    {
                        Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        //Insert into userBalance
                        insert(body.getIdR(),0,20000.00);
                    }
                    Log.i("Body Register",body.getIdR().toString());
                }
            }

            @Override
            public void onFailure(Call<registerParams> call, Throwable t) {

            }
        });
    }

    private void insert(Integer id, int i, double balance)
    {
        Call<shareHoldersParams> insertBal = api.insertBal(id,i,balance);
        insertBal.enqueue(new Callback<shareHoldersParams>() {
            @Override
            public void onResponse(Call<shareHoldersParams> call, Response<shareHoldersParams> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Successfully Created",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<shareHoldersParams> call, Throwable t) {

            }
        });
    }

    private void loginUsers(String emailId, String password)
    {
        Call<loginParams> callLoginUser = api.loginUser(emailId,password);
        callLoginUser.enqueue(new Callback<loginParams>() {
            @Override
            public void onResponse(Call<loginParams> call, Response<loginParams> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_SHORT).show();
                    Log.i("TAG","Error :"+response.code());
                }else {
                    loginParams body = response.body();
                    Intent intent = new Intent(MainActivity.this,stocksActivity.class);
                    intent.putExtra("emailID",body.getEmail());
                    intent.putExtra("userName",body.getName());
                    personInfo.id = body.getId();
                    personInfo.email = body.getEmail();
                    personInfo.name = body.getName();
                    startActivity(intent);
                    Log.i("Body",body.getName().toString());
                }
            }

            @Override
            public void onFailure(Call<loginParams> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("TAG","Error :"+t.getMessage());
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


}