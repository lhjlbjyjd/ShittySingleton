package com.company;

import com.google.gson.*;

public class Main {

    public static void main(String[] args) throws Exception {
        ShittySingleton s = new ShittySingleton();
        s.addVariable("SomeVar", "123841");
        s.addVariable("varoi", "Lolez");
        ShittySingleton s1 = new ShittySingleton();
        s.setVariable("varoi", "Lolestshtx");
        s1.setVariable("SomeVar", "852");
        System.out.println(s1.getVariable("varoi") + " " + s1.getVariable("SomeVar"));
        System.out.println(s.getVariable("varoi") + " " + s.getVariable("SomeVar"));
        s.close();
        s1.addVariable("lol", "kek");
        Gson gson = new Gson();
    }
}
