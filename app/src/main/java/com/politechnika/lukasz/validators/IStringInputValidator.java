package com.politechnika.lukasz.validators;

/**
 * Created by Lukasz on 12.10.2017.
 */

public interface IStringInputValidator {
    boolean validateLongitude(String value);
    boolean validateLatitude(String value);
}
