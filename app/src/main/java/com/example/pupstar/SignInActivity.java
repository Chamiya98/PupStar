package com.example.pupstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private LinearLayout txtSignUp;
    private Button btnSignIn;
    private TextView signinEmail, passwordSignIn;
    String loginEmail;
    String loginPassword;
    String loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtSignUp = (LinearLayout) this.findViewById(R.id.txtSignUp);
        btnSignIn = (Button) this.findViewById(R.id.btnSignIn);
        signinEmail = (TextView) this.findViewById(R.id.signinEmail);
        passwordSignIn = (TextView) this.findViewById(R.id.passwordSignIn);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 loginEmail = signinEmail.getText().toString();
                 loginPassword = passwordSignIn.getText().toString();
                try {
                    ValidateLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //System.out.println("Username: " +loginEmail);
                //System.out.println("Password: " +loginPassword);

                }

        });

    }
    //String LoginStatus ;
    private void ValidateLogin() throws JSONException {


        String URL = API.BASE_URL + "/login";

        HashMap<String, String> params = new HashMap<>();
        params.put("username", loginEmail);
        params.put("password", loginPassword);

        JSONObject parameter = new JSONObject(params);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String loggedUser = "";
                    //String cmp = "abc";
                    //String status = response.getString("status");
                    String loginResult = response.getString("message").toString();
                    //String loggedUser = response.getString("user").toString();

                    //JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    //JSONArray arr = jsonObject.getJSONArray("user");


                    //String resp = String.valueOf(loginResult);
                    //for (int i = 0; i < arr.length(); i++) {
                        //loggedUser = arr.getJSONArray(i).getString(0);
                    //}
                    System.out.println("API Out:" +loginResult);
                    //System.out.println("API Out user:" +loggedUser);


                if(loginResult.equals("Login_Succeeded")) {
                    System.out.println("Result:" + loginResult);

                    PreferencesData.LOGGED_USERNAME = loginEmail;

                    Toast.makeText(SignInActivity.this, loginResult, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user", loggedUser);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();
                }

                else if(loginResult.equals("Login_Failed")){
                    Toast.makeText(SignInActivity.this, loginResult, Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(SignInActivity.this, loginResult, Toast.LENGTH_SHORT).show();
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObject);

    }
}