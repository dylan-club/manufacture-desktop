package com.nicklaus.view.validator;

public class CommonValidator {

    public static String notNullInput(String input){

        String notNull;

        if (input == null){
            notNull = "";
        }else{
            notNull = input.trim();
        }

        return notNull;
    }
}
