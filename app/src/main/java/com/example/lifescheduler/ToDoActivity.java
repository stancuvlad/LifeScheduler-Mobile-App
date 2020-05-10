package com.example.lifescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    private ListView itemListView;
    List<Item> listOfItems;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        itemListView = findViewById(R.id.itemsList);

        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute();
    }

    private class HttpRequestTask extends AsyncTask<String,String,List<Item>> {

        @Override
        protected List<Item> doInBackground(String... strings) {
            String id = getIntent().getStringExtra("ID");
            HttpRequest httpRequest = new HttpRequest();
            ItemJsonParser itemJsonParser = new ItemJsonParser();
            String result = httpRequest.request("https://lifescheduler.run-eu-central1.goorm.io/users/1/items");
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
}
