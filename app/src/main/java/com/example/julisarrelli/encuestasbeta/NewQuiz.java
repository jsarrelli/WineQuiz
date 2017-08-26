package com.example.julisarrelli.encuestasbeta;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Question;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Quiz;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewQuiz extends AppCompatActivity {



    @InjectView(R.id.NameText) EditText textquestion;
    @InjectView(R.id.text1) EditText text1;
    @InjectView(R.id.text2) EditText text2;
    @InjectView(R.id.text3) EditText text3;
    @InjectView(R.id.text4) EditText text4;
    @InjectView(R.id.text5) EditText text5;


    EditText input;
    Platform platform;
    Quiz quiz;

    EditText questionhint;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();

        platform = Platform.getInstance();
        ButterKnife.inject(this);
        input = new EditText(this);

        questionhint=(EditText) findViewById(R.id.NameText);
        questionhint.setHint("Ingrese la pregunta numero N"+(platform.getQuestionNumber()+1));



        if(platform.isNewQuiz()) {


            insertQuizName();



        }



        btnNext=(Button) findViewById(R.id.newquestion) ;
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub


                if(validarCampos()){
                    cargarPregunta();
                    platform.setQuestionNumber(platform.getQuestionNumber()+1);
                }


                Intent intent=new Intent(NewQuiz.this,NewQuiz.class);
                startActivityForResult(intent,0);

                finish();


            }
        });

    }

    private void insertQuizName() {

        setTitle("Nueva Encuesta");
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Nueva Encuesta")
                .setMessage("Ingrese el nombre de la encuesta")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        quiz = new Quiz(platform.getLastQuizId() + 1, input.getText().toString());
                        setTitle("Encuesta: " + quiz.getNombreEncuesta());

                        platform.setNewQuiz(quiz);
                        platform.setIsNewQuiz(false);
                    }
                })
                .setView(input)

                .show();
    }

    private boolean validarCampos() {

        if(platform.getNewQuiz()==null)
        {
            Toast.makeText(getBaseContext(), "Ingrese un nombre para la encuesta", Toast.LENGTH_LONG).show();
            return false;
        }

        if(text1.getText().toString().isEmpty()||textquestion.getText().toString().isEmpty()||text2.getText().toString().isEmpty())
        {
            Toast.makeText(getBaseContext(), "Complete la pregunta y al menos dos opciones", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void cargarPregunta() {


        Question question=new Question(platform.getQuestionNumber(),textquestion.getText().toString());

        ArrayList<String> options=new ArrayList<String >();
        options.add(text1.getText().toString());
        options.add(text2.getText().toString());

        if(!text3.getText().toString().isEmpty())options.add(text3.getText().toString());

        if(!text4.getText().toString().isEmpty())options.add(text4.getText().toString());

        if(!text5.getText().toString().isEmpty())options.add(text5.getText().toString());

        question.setOptions(options);
        platform.getNewQuiz().addQuestion(question);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {

            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Desea cargar este cuestionario?")
                    .setPositiveButton("Aceptar",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which) {
                            if(validarCampos()) {
                                cargarPregunta();
                                platform.addQuiz();
                            }
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();


            return true;
        }

        if(id==android.R.id.home)
        {
            platform.setQuestionNumber(0);
            platform.setIsNewQuiz(false);
            finish();
            return true;
        }




        return super.onOptionsItemSelected(item);
    }




    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.action_submit).setVisible(true);
        menu.findItem(R.id.action_account).setVisible(false);


        return true;
    }

}