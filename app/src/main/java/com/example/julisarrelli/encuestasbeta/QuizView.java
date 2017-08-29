package com.example.julisarrelli.encuestasbeta;

import android.app.ActionBar;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;

import android.widget.ListView;

import com.example.julisarrelli.encuestasbeta.Adapters.VistaEncuesta;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Option;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Question;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Quiz;

import java.util.ArrayList;


public class QuizView extends AppCompatActivity {


    private Platform platform;
    private ListView list;
    private VistaEncuesta adapter;
    private Quiz encuestaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        platform=Platform.getInstance();
        list = (ListView) findViewById(R.id.listView);


        encuestaSelecionada=platform.getEncuestas().get(platform.getIdSelectedQuizView());

        setTitle(encuestaSelecionada.getNombreEncuesta());
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

        ArrayList<Question> preguntas=encuestaSelecionada.getPreguntas();

        ArrayList<String> textosPreguntas=new ArrayList<String>();

        ArrayList<String>opciones=new ArrayList<String>();

                for(Question pregunta:preguntas){

                    textosPreguntas.add(pregunta.getQuestion());

                    String opcionesDePregunta="";
                    for(Option opcion:pregunta.getOptions())
                    {

                        opcionesDePregunta+="-"+opcion.getOption()+"\n";
                    }



                    opciones.add(opcionesDePregunta);

                }




        adapter=new VistaEncuesta(this,textosPreguntas,opciones);
        list.setAdapter(adapter);


    }
}
