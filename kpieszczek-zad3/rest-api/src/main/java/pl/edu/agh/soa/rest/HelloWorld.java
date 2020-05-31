package pl.edu.agh.soa.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hellomvn")
public class HelloWorld {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "HELLO WORLD";
    }
}
