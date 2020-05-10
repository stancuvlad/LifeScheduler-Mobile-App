package com.example.lifescheduler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserJsonParser {
    public List<User> getRequestResponse(String result){
        JSONArray jsonArray;
        List<User> listaUtilizatori = new ArrayList<>();
        try {
            if(result != null){
                jsonArray = new JSONArray(result);

                int count = 0;
                while(count < jsonArray.length()){
                    JSONObject finalObj = jsonArray.getJSONObject(count);

                    String id = finalObj.getString("id");
                    String username = finalObj.getString("username");
                    String parola = finalObj.getString("parola");
                    String email = finalObj.getString("email");
                    Integer nivel_de_acces = Integer.parseInt(finalObj.getString("nivel_de_acces"));

                    User user = new User(Integer.parseInt(id), username, parola, email, nivel_de_acces);
                    try{
                        listaUtilizatori.add(user);
                    }catch (NullPointerException e){
                        Log.e("Error: ","Attempt to invoke interface method 'boolean java.util.List.add(java.lang.Object)' on a null object reference");
                    }catch (Exception e){
                        Log.e("Another error: ",e.getMessage());
                    }
                    count++;
                }
                return listaUtilizatori;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
