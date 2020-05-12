package com.example.lifescheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    private ListView itemListView;
    List<Item> listOfItems;
    ItemAdapter itemAdapter;
    Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        itemListView = findViewById(R.id.itemsList);
        addItemButton = findViewById(R.id.addItemButton);
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute();
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoActivity.this, AddItemActivity.class);
                String id = getIntent().getStringExtra("ID");
                Log.d("BBBBBB", id + "");
                intent.putExtra("ID", id);
                startActivity(intent);
                finish();
            }
        });

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ToDoActivity.this);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                view.setSelected(true);
                dialogBuilder.setCancelable(true);
                dialogBuilder.setTitle("Task selected");
                dialogBuilder.setMessage("Have you completed the selected item?");
                dialogBuilder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Item item = (Item) itemListView.getItemAtPosition(position);
                        DELETERequest(item.getId());
                    }
                });
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogBuilder.create().show();
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String,String,List<Item>> {

        @Override
        protected List<Item> doInBackground(String... strings) {
            String id = getIntent().getStringExtra("ID");
            HttpRequest httpRequest = new HttpRequest();
            ItemJsonParser itemJsonParser = new ItemJsonParser();
            String result = httpRequest.request("https://lifescheduler.run-eu-central1.goorm.io/users/" + id + "/items");
            listOfItems = itemJsonParser.getRequestResponse(result);
            return listOfItems;
        }

        @Override
        protected void onPostExecute(List<Item> lista) {
            super.onPostExecute(lista);
            if(lista != null){
                itemAdapter = new ItemAdapter(getApplicationContext(), lista);
                itemListView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
            }
        }
    }

    public void DELETERequest(Integer id) {
        String UserId = getIntent().getStringExtra("ID");
        String URL = "https://lifescheduler.run-eu-central1.goorm.io/users/" + UserId + "/items/" + id;
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Congratulations, you finished a task!", Toast.LENGTH_LONG).show();
                        Log.d("Response: ", response);
                        HttpRequestTask httpRequestTask = new HttpRequestTask();
                        httpRequestTask.execute();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Response:  " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        mQueue.add(postRequest);
    }
}
