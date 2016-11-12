package com.talismanov.testpoint;

/**
 * Created by Александр on 12.11.2016.
 */
public class HelloWorld {
    private String message;

    public void setMessage(String message){
        this.message  = message;
    }

    public void getMessage(){
        System.out.println("Your Message : " + message);
    }
}