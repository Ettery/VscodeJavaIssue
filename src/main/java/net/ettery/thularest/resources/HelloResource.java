package net.ettery.thularest.resources;

import java.util.Random;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    public HelloResource(){
        Random rand = new Random();
        int n = rand.nextInt(50);    
        System.out.println("HelloResource initialised: " + n);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        return "Hello world!";
    }

}
