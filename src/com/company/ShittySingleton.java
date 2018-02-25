package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShittySingleton implements Serializable, AutoCloseable {

    ShittySingleton instance = null;
    private boolean badOne = false;
    List<String> variables = new ArrayList<>();
    final private String nameUniquizer = "varName_uniqUeIsWeArY0UByMyM0m12875386328/9-28461k#$%*"; //Что бы название переменной было уникальным
    final private String someError = "Это ошибка, но угадай какая :)";  //Немного фигни
    final private String filename = "veryNeededFileDoNotDeleteMePlz";

    ShittySingleton(){
        String s = null;
        try {
            s = readFile(filename, StandardCharsets.UTF_8);
            if(!s.equals("") && !s.equals("WiP")) {
                writeFile("WiP");
                instance = new GsonBuilder().create().fromJson(s, this.getClass());
                writeFile(s);
            } else if(s.equals("WiP")){
                badOne = true;
            }else{
                instance = this;
                writeFile(new GsonBuilder().create().toJson(this));
            }
        } catch (IOException e) {
            instance = this;
            writeFile(new GsonBuilder().create().toJson(this));
        }
        if(instance == null && !badOne)
            instance = this;
        if (instance != null && instance.badOne && s != null && s.isEmpty())
            instance = this;
    }

    public boolean addVariable(String name, String value){
        reloadInstance();
        if(instance.variables.contains(name + nameUniquizer))
            return false;
        else{
            instance.variables.add(name + nameUniquizer);
            instance.variables.add(value);
            saveSelf();
            return true;
        }
    }

    public String getVariable(String name){
        reloadInstance();
        if(instance.variables.contains(name + nameUniquizer)) {
            saveSelf();
            return instance.variables.get(instance.variables.indexOf(name + nameUniquizer) + 1);
        } else
            return someError;
    }

    public boolean setVariable(String name, String value){
        reloadInstance();
        if(instance.variables.contains(name + nameUniquizer)) {
            instance.variables.set(instance.variables.indexOf(name + nameUniquizer) + 1, value);
            saveSelf();
            return true;
        } else
            return false;
    }

    private void saveSelf(){
        File f = new File(filename);
        if(f.exists() && !f.isDirectory())
            writeFile(new GsonBuilder().create().toJson(instance));
        else throw new NullPointerException("Ресурс освобождён");
    }

    private void writeFile(String text){
        try (PrintWriter out = new PrintWriter(filename)) {
            out.print(text.replace("\"badOne\":false,","").replace("\"badOne\":false",""));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    @Override
    public void close() {
        new File(filename).delete();
    }

    public void reloadInstance(){
        try {
            String s = readFile(filename, StandardCharsets.UTF_8);
            writeFile("WiP");
            instance = new GsonBuilder().create().fromJson(s, this.getClass());
            writeFile(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
