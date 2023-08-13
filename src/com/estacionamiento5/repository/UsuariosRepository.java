
package com.estacionamiento5.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.estacionamiento5.pojos.Usuarios;

/**
 *
 * @author Rhoades - pc
 */
public class UsuariosRepository extends Repository {
    
    public boolean permisoAdministrador(String passwd) throws NoSuchAlgorithmException, Exception {
        Usuarios usr = (Usuarios) getObject(new Usuarios().getClass(), "nombre", "ADMINISTRADOR");
        if (usr.getContraseña().equals(getMD5(passwd))) return true;
        else return false;
    }
    
    public String getMD5(String cadena) throws NoSuchAlgorithmException {
        String hastext;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md5.digest(cadena.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        hastext = number.toString(16);
            
        while(hastext.length() < 32) {
            hastext = "0"+hastext;
        }
        return hastext;
    }
    
}
