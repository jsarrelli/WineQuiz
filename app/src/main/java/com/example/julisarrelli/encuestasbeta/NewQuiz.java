package com.example.julisarrelli.encuestasbeta;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julisarrelli.encuestasbeta.ClasesJava.Option;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Question;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Quiz;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewQuiz extends AppCompatActivity {



    @InjectView(R.id.NameText) EditText textquestion;
    @InjectView(R.id.text1) EditText text1;
    @InjectView(R.id.text2) EditText text2;
    @InjectView(R.id.text3) EditText text3;
    @InjectView(R.id.text4) EditText text4;
    @InjectView(R.id.text5) EditText text5;
    @InjectView(R.id.text6) EditText text6;
    @InjectView(R.id.text7) EditText text7;
    @InjectView(R.id.text8) EditText text8;
    @InjectView(R.id.text9) EditText text9;
    @InjectView(R.id.text10) EditText text10;
    @InjectView(R.id.text11) EditText text11;
    @InjectView(R.id.text12) EditText text12;
    @InjectView(R.id.text13) EditText text13;
    @InjectView(R.id.text14) EditText text14;
    @InjectView(R.id.text15) EditText text15;
    @InjectView(R.id.text16) EditText text16;
    @InjectView(R.id.text17) EditText text17;
    @InjectView(R.id.text18) EditText text18;
    @InjectView(R.id.text19) EditText text19;
    @InjectView(R.id.text20) EditText text20;
    
    ArrayList<String> optionsTexts;
    ArrayList<View> options;



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

        loadOptions();

        questionhint=(EditText) findViewById(R.id.NameText);
        questionhint.setHint("Ingrese la pregunta numero N"+(platform.getQuestionNumber()+1));



        if(platform.isNewQuiz()) {


            insertQuizName();



        }


            try{
                setTitle("Encuesta: " + platform.getNewQuiz().getNombreEncuesta());

            }
            catch (Exception e){};






        btnNext=(Button) findViewById(R.id.newquestion) ;
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(validarCampos()){
                    cargarPregunta();

                    platform.setQuestionNumber(platform.getQuestionNumber()+1);
                    platform.setIsNewQuiz(false);

                    Intent intent=new Intent(NewQuiz.this,NewQuiz.class);
                    startActivityForResult(intent,0);

                    finish();
                }







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

                        quiz = new Quiz((platform.getLastQuizId() + 1), input.getText().toString());
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

        if(textquestion.getText().toString().isEmpty()){

            textquestion.requestFocus();
            textquestion.setError("Ingrese una pregunta");

        }
        else {
            if (text1.getText().toString().isEmpty() || text2.getText().toString().isEmpty()) {

                Toast.makeText(getBaseContext(), "Ingrese al menos dos preguntas", Toast.LENGTH_SHORT).show();

            } else {

                Question question = new Question(platform.getQuestionNumber(), textquestion.getText().toString());


                ArrayList<Option> optionsToQuestion = new ArrayList<Option>();

                loadoptionsTexts();


                for (int i = 0; i < 20; i++) {
                    //si la pregunta no esta vacia se agrega al array
                    if (!optionsTexts.get(i).isEmpty()) {
                        //questionsToDatabase.add(questionsTexts.get(i));
                        optionsToQuestion.add( new Option(i, optionsTexts.get(i)));
                    }

                }


                question.setOptions(optionsToQuestion);
                platform.getNewQuiz().addQuestion(question);
            }
        }



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
                                Intent intent = new Intent(NewQuiz.this, MainActivity.class);
                                startActivityForResult(intent, 0);
                                finish();
                            }

                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();


            return true;
        }

        if(id==android.R.id.home)
        {
            platform.setQuestionNumber(0);
            platform.setIsNewQuiz(true);
            Intent intent = new Intent(NewQuiz.this, MainActivity.class);
            startActivityForResult(intent, 0);
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

    private void loadoptionsTexts() {

        optionsTexts=new ArrayList<String>();

        optionsTexts.add(text1.getText().toString());
        optionsTexts.add(text2.getText().toString());
        optionsTexts.add(text3.getText().toString());
        optionsTexts.add(text4.getText().toString());
        optionsTexts.add(text5.getText().toString());
        optionsTexts.add(text6.getText().toString());
        optionsTexts.add(text7.getText().toString());
        optionsTexts.add(text8.getText().toString());
        optionsTexts.add(text9.getText().toString());
        optionsTexts.add(text10.getText().toString());
        optionsTexts.add(text11.getText().toString());
        optionsTexts.add(text12.getText().toString());
        optionsTexts.add(text13.getText().toString());
        optionsTexts.add(text14.getText().toString());
        optionsTexts.add(text15.getText().toString());
        optionsTexts.add(text16.getText().toString());
        optionsTexts.add(text17.getText().toString());
        optionsTexts.add(text18.getText().toString());
        optionsTexts.add(text19.getText().toString());
        optionsTexts.add(text20.getText().toString());
    }

    private void loadOptions() {

        options=new ArrayList<View>();

        options.add(findViewById(R.id.option1));
        options.add(findViewById(R.id.option2));
        options.add(findViewById(R.id.option3));
        options.add(findViewById(R.id.option4));
        options.add(findViewById(R.id.option5));
        options.add(findViewById(R.id.option6));
        options.add(findViewById(R.id.option7));
        options.add(findViewById(R.id.option8));
        options.add(findViewById(R.id.option9));
        options.add(findViewById(R.id.option10));
        options.add(findViewById(R.id.option11));
        options.add(findViewById(R.id.option12));
        options.add(findViewById(R.id.option13));
        options.add(findViewById(R.id.option14));
        options.add(findViewById(R.id.option15));
        options.add(findViewById(R.id.option16));
        options.add(findViewById(R.id.option17));
        options.add(findViewById(R.id.option18));
        options.add(findViewById(R.id.option19));
        options.add(findViewById(R.id.option20));

    }

}