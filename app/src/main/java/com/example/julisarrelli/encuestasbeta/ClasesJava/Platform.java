package com.example.julisarrelli.encuestasbeta.ClasesJava;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by julisarrelli on 8/24/17.
 */

public class Platform {



    public static Platform instance=null;

    public static HashMap<Integer,Quiz> encuestas;
    public static HashMap<Integer,User> users;

    public static int actualquiz;
    public static String actualclient;
    public static boolean userValidated;
    public static User loggedUser;

    public static String getActualclient() {
        return actualclient;
    }

    public static void setActualclient(String actualclient) {
        Platform.actualclient = actualclient;
    }

    public static Platform getInstance(){

        if(instance==null)
        {
            instance=new Platform();
            encuestas=new HashMap<Integer, Quiz>();
            users=new HashMap<Integer, User>();
            actualquiz=1;
            userValidated=false;

            PreguntasRandom();
            UsariosRandom();
        }
        return instance;


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




    public static void PreguntasRandom()
    {
        Quiz quiz=new Quiz(1,"Encuesta 1");

        HashMap<Integer,Question>preguntas=new HashMap<Integer,Question>();

        Question pregunta1=new Question(0,"Que le parecio la atencion?");
        preguntas.put(pregunta1.getIdQuestion(),pregunta1);


        Question pregunta2=new Question(1,"Que le parecio el sabor del vino?");
        preguntas.put(pregunta2.getIdQuestion(),pregunta2);

        Question pregunta3=new Question(2,"Que le parecio la guia?");
        preguntas.put(pregunta3.getIdQuestion(),pregunta3);


        quiz.setPreguntas(preguntas);

        encuestas.put(1,quiz);




        Quiz quiz2=new Quiz(2,"Encuesta 2");

        HashMap<Integer,Question>preguntas2=new HashMap<Integer,Question>();

        Question pregunta4=new Question(0,"Que opina de las intalaciones?");
        preguntas2.put(pregunta1.getIdQuestion(),pregunta1);


        Question pregunta5=new Question(1,"Como encontro las bodegas?");
        preguntas2.put(pregunta2.getIdQuestion(),pregunta2);

        Question pregunta6=new Question(2,"Le agrado el paisaje?");
        preguntas2.put(pregunta3.getIdQuestion(),pregunta3);


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
}




