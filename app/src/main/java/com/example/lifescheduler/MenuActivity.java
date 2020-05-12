package com.example.lifescheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private Button todoButton, togoButton, googleSignOut;
    private TextView nameTextView, emailTextView;
    private List<User> listaUtilizatori;
    GoogleSignInClient mGoogleSignInClient;
    public static int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        todoButton = findViewById(R.id.todo_section);
        togoButton = findViewById(R.id.togo_section);
        googleSignOut = findViewById(R.id.googleSignOut);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ToDoActivity.class);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                try {

                    List<User> lista = new HttpRequestTask().execute().get();
                    Log.d("------>>>>>>>", "Apelare");
                    if(lista != null){
                        boolean isRegistered = false;
                        int i = 0;
                        while (i < lista.size()){
                            if(acct.getEmail().equals(lista.get(i).getEmailUtilizator())){
                                isRegistered = true;
                                ID = lista.get(i).getId();
                            }
                            i++;
                        }
                        if(!isRegistered){
                            ID = lista.size() + 1;
                            POSTRequest(acct.getDisplayName(), acct.getEmail());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                intent.putExtra("ID", String.valueOf(ID));
                startActivity(intent);
            }
        });

        togoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ToGoActivity.class);
                startActivity(intent);
            }
        });

        googleSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.googleSignOut:
                        signOut();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            nameTextView.setText(personName);
            emailTextView.setText(personEmail);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MenuActivity.this,"Signed out successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private class HttpRequestTask extends AsyncTask<String, String, List<User>> {
        @Override
        protected List<User> doInBackground(String... strings) {
            listaUtilizatori = new ArrayList<>();
            HttpRequest httpRequest = new HttpRequest();
            UserJsonParser userJsonParser = new UserJsonParser();
            String result = httpRequest.request("https://lifescheduler.run-eu-central1.goorm.io/users");
            listaUtilizatori = userJsonParser.getRequestResponse(result);
            return listaUtilizatori;
        }
    }

    public void POSTRequest(String username, String email) {
        RequestQueue queue = Volley.newRequestQueue(this);
        try {
            String URL = "https://lifescheduler.run-eu-central1.goorm.io/users";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("username", username);
            jsonBody.put("parola", "UNREGISTERED");
            jsonBody.put("email", email);
            jsonBody.put("nivel_de_acces", 1);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onBackPressed();

                }
            }) {
//                @Override
////                public Map<String, String> getHeaders() throws AuthFailureError {
////                    final Map<String, String> headers = new HashMap<>();
////                    headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
////                    return headers;
////                }
            };
            queue.add(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }
}
