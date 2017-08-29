package com.example.julisarrelli.encuestasbeta.ClasesJava;

/**
 * Created by julisarrelli on 8/29/17.
 */

public class Option {

    int idOption;
    String option;

    public Option(int idOption, String option) {
        this.idOption = idOption;
        this.option = option;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
