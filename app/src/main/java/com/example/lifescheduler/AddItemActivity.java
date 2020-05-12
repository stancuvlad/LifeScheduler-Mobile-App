package com.example.lifescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddItemActivity extends AppCompatActivity {
    Button addItemButton;
    EditText nameEditText;
    EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        nameEditText = findViewById(R.id.newToDoEditText);
        descriptionEditText = findViewById(R.id.newDescriptionEditText);
        addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POSTRequest(nameEditText.getText().toString().trim(), descriptionEditText.getText().toString().trim());
                Toast.makeText(getApplicationContext(), "New task created!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddItemActivity.this, ToDoActivity.class);
                String id = getIntent().getStringExtra("ID");
                intent.putExtra("ID", id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void POSTRequest(String name, String description) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String id = getIntent().getStringExtra("ID");
        try {
            String URL = "https://lifescheduler.run-eu-central1.goorm.io/users/" + id + "/items";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("user_id",id);
            jsonBody.put("name", name);
            if(description.isEmpty()){
                jsonBody.put("description", "NONE");
            }else{
                jsonBody.put("description", description);
            }


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
