package com.example.julisarrelli.encuestasbeta.ClasesJava;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by julisarrelli on 8/24/17.
 */

public class Platform {



    public static Platform instance=null;

    public static HashMap<Integer,Quiz> encuestas;
    public static HashMap<Integer,User> users;


    public static int actualquiz;//me dice cual el id de la encuesta que esta respondiendo

    public static String actualclient;//nombre del cliente que esta respondiendo

    public static boolean userValidated;
    public static User loggedUser;

    //estos tres se entran cuando creo una nueva encuenta
    public static boolean isNewQuiz;
    public static Quiz newQuiz;
    public static int questionNumber;

    public static int idSelectedQuizView;





    public static Platform getInstance(){

        if(instance==null)
        {
            instance=new Platform();
            encuestas=new HashMap<Integer, Quiz>();
            users=new HashMap<Integer, User>();
            actualquiz=1;
            userValidated=false;

            isNewQuiz=true;
            questionNumber=0;

            PreguntasRandom();
            UsariosRandom();
        }
        return instance;


    }

    public static int getIdSelectedQuizView() {
        return idSelectedQuizView;
    }

    public static void setIdSelectedQuizView(int idSelectedQuizView) {
        Platform.idSelectedQuizView = idSelectedQuizView;
    }

    private static void UsariosRandom() {

        User user1= new User("julian","julian","admin",null);
        users.put(0,user1);

        User user2= new User("colo","colo","classic",null);
        users.put(0,user1);

        User user3= new User("cami","cami","classic",null);
        users.put(0,user1);
    }


    public static HashMap<Integer, Quiz> getEncuestas() {
        return encuestas;
    }

    public static void setEncuestas(HashMap<Integer, Quiz> encuestas) {
        Platform.encuestas = encuestas;
    }

    public static int getActualquiz() {
        return actualquiz;
    }

    public static void setActualquiz(int actualquiz) {
        Platform.actualquiz = actualquiz;
    }


    public static Quiz getActualQuiz()
    {


        return encuestas.get(actualquiz);
    }


    public static String getActualclient() {
        return actualclient;
    }

    public static void setActualclient(String actualclient) {
        Platform.actualclient = actualclient;
    }

    public static boolean isNewQuiz() {
        return isNewQuiz;
    }

    public static void setIsNewQuiz(boolean isNewQuiz) {
        Platform.isNewQuiz = isNewQuiz;
    }

    public static Quiz getNewQuiz() {
        return newQuiz;
    }

    public static void setNewQuiz(Quiz newQuiz) {
        Platform.newQuiz = newQuiz;
    }

    public static void PreguntasRandom()
    {
        Quiz quiz=new Quiz(1,"Encuesta 1");

        ArrayList<Question> preguntas=new ArrayList<Question>();

        Question pregunta1=new Question(0,"Que le parecio la atencion?");

        pregunta1.addOption(new Option(0,"Mala"));
        pregunta1.addOption(new Option(1,"Buena"));
        pregunta1.addOption(new Option(2,"Muy Buena"));
        pregunta1.addOption(new Option(3,"Excelente"));

        preguntas.add(pregunta1);


        Question pregunta2=new Question(1,"Que le parecio el sabor del vino?");

        pregunta2.addOption(new Option(0,"Dulce"));
        pregunta2.addOption(new Option(1,"Amargo"));
        pregunta2.addOption(new Option(2,"Fuerte"));
        pregunta2.addOption(new Option(3,"Suave"));
        preguntas.add(pregunta2);

        Question pregunta3=new Question(2,"Que le parecio la guia?");
        pregunta3.addOption(new Option(0,"Amable"));
        pregunta3.addOption(new Option(1,"Gracioso"));
        pregunta3.addOption(new Option(2,"Molesto"));
        pregunta3.addOption(new Option(3,"Timido"));
        pregunta3.addOption(new Option(4,"Lindo"));
        pregunta3.addOption(new Option(5,"Feo"));
        pregunta3.addOption(new Option(6,"Mal Educado"));

        preguntas.add(pregunta3);


        quiz.setPreguntas(preguntas);

        encuestas.put(1,quiz);




        Quiz quiz2=new Quiz(2,"Encuesta 2");

        ArrayList<Question>preguntas2=new ArrayList<Question>();

        Question pregunta4=new Question(0,"Que opina de las intalaciones?");


        preguntas2.add(pregunta4);


        Question pregunta5=new Question(1,"Como encontro las bodegas?");
        preguntas2.add(pregunta5);

        Question pregunta6=new Question(2,"Le agrado el paisaje?");

        preguntas2.add(pregunta6);


        quiz2.setPreguntas(preguntas2);

        encuestas.put(2,quiz2);




    }

    public boolean ValidateUser(String username, String pass){

        Set<Integer> keys=users.keySet();

        for(Integer key:keys)
        {
            if((users.get(key).getUsername().equals(username)&&(users.get(key).getPass().equals(pass))))
            {
                userValidated=true;
                loggedUser=users.get(key);
                return true;

            }

        }

        return false;

    }

    public int getLastQuizId() {

        Set<Integer> keys=encuestas.keySet();
        int mayor=encuestas.get(1).getIdEncuesta();
        for(Integer key:keys)
        {
            if(encuestas.get(key).getIdEncuesta()>=mayor) {
                mayor = encuestas.get(key).getIdEncuesta();
            }
        }


        return mayor;
    }

    public static int getQuestionNumber() {
        return questionNumber;
    }

    public static void setQuestionNumber(int questionNumber) {
        Platform.questionNumber = questionNumber;
    }

    public void addQuiz()
    {

        encuestas.put(newQuiz.getIdEncuesta(),newQuiz);
        Log.v("id", encuestas.get(newQuiz.getIdEncuesta()).getNombreEncuesta());

        isNewQuiz=true;
        questionNumber=0;

    }
}




