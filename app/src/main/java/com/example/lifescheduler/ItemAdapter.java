package com.example.lifescheduler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    public Context context;
    public List<Item> listaItems;

    public ItemAdapter(Context context, List<Item> listaItems) {
        this.context = context;
        this.listaItems = listaItems;
    }

    @Override
    public int getCount() {
        return listaItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listaItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.items_list,null);
        TextView nameTextView = v.findViewById(R.id.nameTextView);
        TextView descriptionTextView = v.findViewById(R.id.descriptionTextView);

        nameTextView.setText(String.valueOf(listaItems.get(i).getName()));
        descriptionTextView.setText(String.valueOf(listaItems.get(i).getDescription()));
        return v;
    }
}
