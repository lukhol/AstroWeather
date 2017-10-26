package com.politechnika.lukasz.validators;

public class StringInputValidator implements  IStringInputValidator {

    @Override
    public boolean validateLongitude(String value) {
        if(validateLonLang(value)){
            try{
                float longitude = Float.parseFloat(value);
                if(longitude < 180 && longitude > -180)
                    return true;
            } catch (Exception e){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean validateLatitude(String value) {
        if(validateLonLang(value)){
            try{
                float latitude = Float.parseFloat(value);
                if(latitude < 90 && latitude > -90)
                    return true;
            } catch (Exception e){
                return false;
            }
        }
        return false;
    }

    private boolean validateLonLang(String value) {
        if(value == null || value.length() == 0 || value.length() > 15)
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
