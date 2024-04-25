package com.studentdz.testdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
EditText etname,etphone,etpassword;
Button etregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etname =findViewById(R.id.appCompatEditText);
        etphone =findViewById(R.id.appCompatEditText1);
        etpassword =findViewById(R.id.appCompatEditText2);

        etregister =findViewById(R.id.appCompatButton);

        etregister.setOnClickListener(View ->{
            String name =etname.getText().toString();
            String phone =etphone.getText().toString();
            String password =etpassword.getText().toString();
            if(isValied(name,phone,password)){
                registerUser(name,phone,password);
            }
        });
    }



    public  void SignIn(View view){
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
    }

    private void showMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private boolean isValied(String name, String phone, String password) {
        if(name.isEmpty()){
            showMessage("pleas entre user name");
            return false;
        }
        if(name.length() > 20){
            showMessage("user name istoo long");
            return false;
        }
        if(phone.isEmpty()){
            showMessage("pleas entre phone number");
            return false;
        }
        if(password.isEmpty()){
            showMessage("pleas entre password");
            return false;
        }
        /*if(password.length() > 0 || password.length() < 8){
            showMessage("password between 0 and 8 caracter");
            return false;
        }*/
        return true;
    }



    private void registerUser(final String name, final String phone, final String password) {
        StringRequest stringRequest =new StringRequest(Request.Method.POST,Endspoint.register_url, response -> {
            if(response.equals("success")){
                showMessage("registration successfully");
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
            else{
                showMessage("user name already exists");
                Intent intent = new Intent(register.this, register.class);
                startActivity(intent);
            }
        }, error -> {
            showMessage("please chek your internet connection");
            Log.d("VOLLEY",error.getMessage());
            Intent intent = new Intent(register.this, register.class);
            startActivity(intent);
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("phone",phone);
                params.put("password",password);

                return params;

            }

        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}