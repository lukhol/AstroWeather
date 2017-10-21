package com.politechnika.lukasz.validators;

/**
 * Created by Lukasz on 12.10.2017.
 */

public class StringInputValidator implements  IStringInputValidator {

    @Override
    public boolean validateLonLang(String value) {
        if(value == null || value.length() == 0)
            return false;

        char[] valueAsCharArray = value.toCharArray();
        char lastChar = valueAsCharArray[valueAsCharArray.length - 1];

        if(dotAmmount(valueAsCharArray) > 1)
            return false;

        if(!checkMinus(valueAsCharArray))
            return false;

        if(!Character.isDigit(lastChar) && lastChar != '-' && lastChar != '.')
            return false;

        return true;
    }

    private boolean checkMinus(char[] input){
        if(input.length > 1 && input[input.length - 1] == '-')
            return false;

        return true;
    }

    private int dotAmmount(char[] input){
        int dotsAmmount = 0;

        for(char singleCharacter : input){
            if(singleCharacter == '.')
                dotsAmmount ++;
        }

        return dotsAmmount;
    }
}
