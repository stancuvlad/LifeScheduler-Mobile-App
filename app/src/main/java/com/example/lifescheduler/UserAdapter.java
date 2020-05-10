package com.example.lifescheduler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    public Context context;
    public List<User> listaUsers;

    public UserAdapter(Context context, List<User> listaUsers) {
        this.context = context;
        this.listaUsers = listaUsers;
    }

    @Override
    public int getCount() {
        return listaUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return listaUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.users_list,null);
        TextView usernameTextView = v.findViewById(R.id.usernameTextView);
        TextView passwordTextView = v.findViewById(R.id.passwordTextView);
        TextView emailTextView = v.findViewById(R.id.emailTextView);
        TextView accessLevelTextView = v.findViewById(R.id.accessLevelTextView);

        usernameTextView.setText(String.valueOf(listaUsers.get(i).getNumeUtilizator()));
        passwordTextView.setText(String.valueOf(listaUsers.get(i).getParolaUtilizator()));
        emailTextView.setText(String.valueOf(listaUsers.get(i).getEmailUtilizator()));
        accessLevelTextView.setText(String.valueOf(listaUsers.get(i).getNivel_de_acces()));
        return v;
    }
}
