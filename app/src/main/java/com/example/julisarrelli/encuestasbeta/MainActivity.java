package com.example.julisarrelli.encuestasbeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.julisarrelli.encuestasbeta.Adapters.ListadoDeEncuesta;
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
    RadioGroup radioGroup;
    ImageButton btnNext;
    ImageButton btnBack;
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
                .setView(input);

        //.show();

        position=0;

        platform = Platform.getInstance();


        quiz=platform.getActualQuiz();


        questions=quiz.getPreguntas();
        loadItemsNavDrawer();



        option1= (RadioButton) findViewById(R.id.option1);
        option2= (RadioButton) findViewById(R.id.option2);
        option3= (RadioButton) findViewById(R.id.option3);
        option4= (RadioButton) findViewById(R.id.option4);
        option5= (RadioButton) findViewById(R.id.option5);


        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
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



                position++;

                clearButtons();
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

                clearButtons();


                if(position!=0) {
                    position--;
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


    private void clearButtons() {


        option1.setChecked(false);
        option2.setChecked(false);
        option3.setChecked(false);
        option4.setChecked(false);
        option5.setChecked(false);
    }

    private void VisibleAnswer() {


        try{
            switch (questions.get(position).getAnswer()){

                case "1":
                    option1.setChecked(true);
                    break;

                case "2":
                    option2.setChecked(true);
                    break;

                case "3":
                    option3.setChecked(true);
                    break;

                case "4":
                    option4.setChecked(true);
                    break;

                case "5":
                    option5.setChecked(true);
                    break;
            }


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


    public void onRadioButtonClicked(View v)
    {



        //is the current radio button now checked?
        boolean  checked = ((RadioButton) v).isChecked();

        //now check which radio button is selected
        //android switch statement
        switch(v.getId()){

            case R.id.option1:
                if(checked)
                    option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);
                option5.setChecked(false);
                questions.get(position).setAnswer(option1.getText().toString());
                break;

            case R.id.option2:
                if(checked)
                    option3.setChecked(false);
                option1.setChecked(false);
                option4.setChecked(false);
                option5.setChecked(false);
                questions.get(position).setAnswer(option2.getText().toString());
                break;

            case R.id.option3:
                if(checked)
                    option2.setChecked(false);
                option1.setChecked(false);
                option4.setChecked(false);
                option5.setChecked(false);
                questions.get(position).setAnswer(option3.getText().toString());
                break;

            case R.id.option4:
                if(checked)
                    option3.setChecked(false);
                option1.setChecked(false);
                option2.setChecked(false);
                option5.setChecked(false);
                questions.get(position).setAnswer(option4.getText().toString());
                break;

            case R.id.option5:
                if(checked)
                    option3.setChecked(false);
                option1.setChecked(false);
                option4.setChecked(false);
                option2.setChecked(false);
                questions.get(position).setAnswer(option5.getText().toString());
                break;
        }
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);



        return true;
    }


}
