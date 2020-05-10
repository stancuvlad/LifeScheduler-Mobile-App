package com.example.lifescheduler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemJsonParser {
    public List<Item> getRequestResponse(String result){
        JSONArray jsonArray;
        List<Item> itemsList = new ArrayList<>();
        try {
            if(result != null){
                jsonArray = new JSONArray(result);

                int count = 0;
                while(count < jsonArray.length()){
                    JSONObject finalObj = jsonArray.getJSONObject(count);

                    String id = finalObj.getString("id");
                    String name = finalObj.getString("name");
                    String description = finalObj.getString("description");

                    Item item = new Item(Integer.parseInt(id), name, description);
                    try{
                        itemsList.add(item);
                    }catch (NullPointerException e){
                        Log.e("Error: ","Attempt to invoke interface method 'boolean java.util.List.add(java.lang.Object)' on a null object reference");
                    }catch (Exception e){
                        Log.e("Another error: ",e.getMessage());
                    }
                    count++;
                }
                return itemsList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
