package com.example.julisarrelli.encuestasbeta;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.julisarrelli.encuestasbeta.Adapters.ListadoDeEncuesta;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ListedQuiz extends AppCompatActivity {


    private ArrayList<String> names;
    private ArrayList<Integer>ids;
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
        ids=new ArrayList<Integer>();
        platform=Platform.getInstance();
        cargarLista();



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                platform.setIdSelectedQuizView((Integer) list.getItemAtPosition(position));
                Intent intent = new Intent(ListedQuiz.this, QuizView.class);
                startActivityForResult(intent, 0);




            }
        });


        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                new AlertDialog.Builder(ListedQuiz.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Desea eliminar este cuestionario?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteQuiz();

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();


                return false;


            } });

    }



    private void deleteQuiz() {

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home)
        {
            Intent intent = new Intent(ListedQuiz.this, MainActivity.class);
            startActivityForResult(intent, 0);
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
            ids.add(encuestas.get(key).getIdEncuesta());
        }

        adapter=new ListadoDeEncuesta(this,names,ids);
        list.setAdapter(adapter);


    }

}
