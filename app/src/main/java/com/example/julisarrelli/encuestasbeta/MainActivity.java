package com.example.julisarrelli.encuestasbeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.julisarrelli.encuestasbeta.ClasesJava.Option;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Question;
import com.example.julisarrelli.encuestasbeta.ClasesJava.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    RadioButton option5;
    ImageButton btnNext;
    ImageButton btnBack;
    RadioGroup mRadioGroup;
    EditText input;

    Platform platform;

    private TextSwitcher mSwitcher;
    private ArrayList<Question> questions;
    private Quiz quiz;
    private int position;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //se instancia el navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        //llama al on prepare option menu
        invalidateOptionsMenu();




        position=0;

        platform = Platform.getInstance();


        quiz=platform.getActualQuiz();
        questions=quiz.getPreguntas();




        loadItemsNavDrawer();

        input=new EditText(this);

        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Cliente")
                .setMessage("Ingrese el nombre del cliente a encuestar")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        platform.setActualclient(input.getText().toString());
                        setTitle(quiz.getNombreEncuesta()+" - "+platform.getActualclient());
                    }
                })
                .setView(input)

                .show();

            loadOptions();


//        option1= (RadioButton) findViewById(R.id.option1);
//        option2= (RadioButton) findViewById(R.id.option2);
//        option3= (RadioButton) findViewById(R.id.option3);
//        option4= (RadioButton) findViewById(R.id.option4);
//        option5= (RadioButton) findViewById(R.id.option5);
//
//
//        //radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        btnNext=(ImageButton)findViewById(R.id.next);
        btnBack=(ImageButton)findViewById(R.id.back);

        mSwitcher = (TextSwitcher) findViewById(R.id.TextSwitcher_question);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(MainActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(35);
                myText.setTextColor(Color.WHITE);


                return myText;
            }

        });

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);


        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);


        if(questions.size()<=1){
            btnNext.setVisibility(View.INVISIBLE);
        }
        btnBack.setVisibility(View.INVISIBLE);
        mSwitcher.setText(questions.get(position).getQuestion());


        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Log.v("position: ", String.valueOf(position));

                mRadioGroup.removeAllViews();

                position++;
                loadOptions();


                VisibleAnswer();
                btnBack.setVisibility(View.VISIBLE);







                mSwitcher.setText(questions.get(position).getQuestion());

                if(position>=questions.size()-1){
                    btnNext.setVisibility(View.GONE);
                    btnBack.setVisibility(View.VISIBLE);
                }

                else {
                    btnNext.setVisibility(View.VISIBLE);

                }



            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Log.v("position: ", String.valueOf(position));

                mRadioGroup.removeAllViews();




                if(position!=0) {
                    position--;
                    loadOptions();
                    VisibleAnswer();
                    mSwitcher.setText(questions.get(position).getQuestion());
                }
                if(position<=0){
                    btnBack.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.VISIBLE);


                }
                else{
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);



                }

            }
        });


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);

                String selectedOption = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                questions.get(position).setAnswer(selectedOption);
                Log.v("option",selectedOption);
            }
        });


    }

    private void loadOptions() {

        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);

        ArrayList<Option> opciones=questions.get(position).getOptions();


        for(Option opcion:opciones) {

            RadioButton newRadioButton = new RadioButton(this);
            newRadioButton.setId(opcion.getIdOption());
            newRadioButton.setText(opcion.getOption());
            newRadioButton.setTextSize(25);
            newRadioButton.setTextColor(getResources().getColor(R.color.white));


            mRadioGroup.addView(newRadioButton, 0, layoutParams);



        }
    }

    private void loadItemsNavDrawer() {



        Menu menu = navigationView.getMenu();

        Menu submenu = menu.addSubMenu("Encuestas");


        HashMap<Integer,Quiz> encuestas=platform.getEncuestas();

        Set<Integer> keys=encuestas.keySet();
        for(Integer key:keys)
        {
            submenu.add(Menu.NONE,encuestas.get(key).getIdEncuesta(), 0, encuestas.get(key).getNombreEncuesta()).setIcon(R.drawable.bullet);
        }


    }


    private void VisibleAnswer() {


        try{
        // RadioButton rb=(RadioButton) findViewById(questions.get(position).);


        }
        catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir de la Aplicacion")
                    .setMessage("Desea salir de la aplicacion?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }

                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        menu.findItem(R.id.action_submit).setVisible(false);
        menu.findItem(R.id.action_account).setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;



        if(id==R.id.nuevaEncuesta) {

            intent = new Intent(MainActivity.this, NewQuiz.class);
            startActivityForResult(intent, 0);
            platform.setIsNewQuiz(true);
        }

                   else if(id==R.id.listado)
        {
            intent=new Intent(MainActivity.this,ListedQuiz.class);
            startActivityForResult(intent,0);


        }


        else {
            try {
                platform.setActualquiz(id);

                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivityForResult(intent, 0);


            } catch (Exception e) {
            }
        }






        this.finish();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);



        return true;
    }


}
