package pl.edu.agh.soa.rest.jwt;


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyGeneratorImpl implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "keyString123456789";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }
}