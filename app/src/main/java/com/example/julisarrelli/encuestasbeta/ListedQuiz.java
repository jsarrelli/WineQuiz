package com.example.julisarrelli.encuestasbeta;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.julisarrelli.encuestasbeta.Adapters.ListadoDeEncuesta;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ListedQuiz extends AppCompatActivity {


    private ArrayList<String> names;
    private ListadoDeEncuesta adapter;
    private Platform platform;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();
        setTitle("Listado de Encuestas");

        list = (ListView) findViewById(R.id.listView);
        names=new ArrayList<String>();
        platform=Platform.getInstance();
        cargarLista();

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home)
        {

            finish();
            return true;
        }




        return super.onOptionsItemSelected(item);
    }


    private void cargarLista() {

        HashMap<Integer,Quiz> encuestas=platform.getEncuestas();

        Set<Integer> keys=encuestas.keySet();

        for(Integer key:keys){
            names.add(encuestas.get(key).getNombreEncuesta());
        }

        adapter=new ListadoDeEncuesta(this,names);
        list.setAdapter(adapter);


    }

}
