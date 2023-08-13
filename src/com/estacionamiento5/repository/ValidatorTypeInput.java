
package com.estacionamiento5.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rhoades - pc
 */
public class ValidatorTypeInput {
    
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
    
    private static ValidatorTypeInput instance = null;
 
    protected ValidatorTypeInput() {}
    
    public static ValidatorTypeInput getInstance() {
        if (instance == null) instance = new ValidatorTypeInput(); 
        return instance;
    }
    
    public boolean isDate(String value)
    {
        try { 
            formatDate.parse(value);
            return true; 
        } catch (ParseException e) { 
            return false; 
        }
    }
    
    public boolean isInteger(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    
    public boolean isNumeric(String cadena){
        try {
            BigDecimal decimal = new BigDecimal(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    
    public boolean isHour(String cadena){
        try { 
            formatHour.parse(cadena); 
            return true; 
        } catch (ParseException e) { 
            return false; 
        }
    }
    
    public boolean validateDatesString(String date1, String date2) throws ParseException
    {
        Date date1P = formatDate.parse(date1);
        Date date2P = formatDate.parse(date2);
        if(date1P.after(date2P)) return false;
        return true;
    }
    
    public boolean validateHoursString(String hour1, String hour2) throws ParseException
    {
        Date date1P = formatHour.parse(hour1);
        Date date2P = formatHour.parse(hour2);
        if(date1P.after(date2P)) return false;
        return true;
    }
    
}
