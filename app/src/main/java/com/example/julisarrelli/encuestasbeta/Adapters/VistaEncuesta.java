package com.example.julisarrelli.encuestasbeta.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.julisarrelli.encuestasbeta.R;

import java.util.ArrayList;

/**
 * Created by julisarrelli on 8/26/17.
 */

public class VistaEncuesta extends BaseAdapter {




    // Declare Variables
    Context context;
    ArrayList<String> preguntas;
    ArrayList<String>opciones;
    LayoutInflater inflater;

    public VistaEncuesta(Context context, ArrayList<String > preguntas,ArrayList<String> opciones) {
        this.context = context;
        this.preguntas=preguntas;
        this.opciones=opciones;
    }

    public int getCount() {
        return preguntas.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }



    public View getView(int position, View convertView, ViewGroup parent) {


        TextView txtPregunta;
        TextView txtOpciones;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.question_item, parent, false);

        txtPregunta = (TextView) itemView.findViewById(R.id.question);
        txtOpciones= (TextView) itemView.findViewById(R.id.options);

        Log.v("error", String.valueOf(opciones));

        Log.v("error", String.valueOf(preguntas));



        try {

            txtPregunta.setText(preguntas.get(position));



            txtOpciones.setText(opciones.get(position));
        }
        catch (Exception e){}




        return itemView;
    }


}

