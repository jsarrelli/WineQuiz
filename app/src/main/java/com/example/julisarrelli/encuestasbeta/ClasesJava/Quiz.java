package com.example.julisarrelli.encuestasbeta.ClasesJava;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by julisarrelli on 8/24/17.
 */

public class Quiz {

    private int idEncuesta;
    private String nombreEncuesta;
    private String client;

    private ArrayList<Question> preguntas;



    public Quiz(int idEncuesta, String nombreEncuesta)
    {
        this.idEncuesta=idEncuesta;
        this.nombreEncuesta=nombreEncuesta;
        preguntas=new ArrayList<Question>();
    }


    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public String getNombreEncuesta() {
        return nombreEncuesta;
    }

    public void setNombreEncuesta(String nombreEncuesta) {
        this.nombreEncuesta = nombreEncuesta;
    }


    public ArrayList<Question> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Question> preguntas) {
        this.preguntas = preguntas;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public void addQuestion(Question question)
    {
        preguntas.add(question);
    }
}
