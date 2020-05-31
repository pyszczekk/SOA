package pl.edu.agh.soa.rest.jwt;
import java.security.Key;

public interface KeyGenerator {
    Key generateKey();
}
