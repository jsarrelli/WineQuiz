package com.example.julisarrelli.encuestasbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.julisarrelli.encuestasbeta.R;

import java.util.ArrayList;

/**
 * Created by julisarrelli on 8/25/17.
 */

public class ListadoDeEncuesta extends BaseAdapter {


    // Declare Variables
    Context context;
    ArrayList<String>nombres;
    LayoutInflater inflater;

    public ListadoDeEncuesta(Context context, ArrayList<String > nombres) {
        this.context = context;
        this.nombres=nombres;
    }

    @Override
    public int getCount() {
        return nombres.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    public View getView(int position, View convertView, ViewGroup parent) {


        TextView txtNombre;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.quiz_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtNombre = (TextView) itemView.findViewById(R.id.quizname);

        // Capture position and set to the TextViews

        txtNombre.setText(nombres.get(position));

        return itemView;
    }


}
