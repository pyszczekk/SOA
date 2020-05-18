package pl.edu.agh.soa.rest.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.edu.agh.soa.models.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.jboss.ws.api.Log.LOGGER;

@Path("/auth")
public class Auth {

    @Inject
    private KeyGenerator keyGenerator;

    @Context
    private UriInfo uriInfo;

    //
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
//    public Response authenticateUser(@FormParam("login") String login,
//                                     @FormParam("password") String password) {
    public Response authenticateUser(User user) {
        try {
            String token = null;

            String login = user.getLogin();
            String password = user.getPassword();
            System.out.println("login = " + login + "; password = " + password);

            boolean isValid = authenticate(login, password);

            if (isValid)
                token = issueToken(login);

            if (token != null) {
                return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer "+ token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String login, String password) throws SecurityException {
        if ("User".equals(login) && "user".equals(password)) {
            LOGGER.info("Valid UserName and Password");
            return true;
        } else {
            throw new SecurityException("Invalid user/password");
        }
    }

    private String issueToken(String login) {
        keyGenerator = new KeyGeneratorImpl();
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(30L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println("generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}