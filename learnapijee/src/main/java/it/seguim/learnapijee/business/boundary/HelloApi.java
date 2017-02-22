package it.seguim.learnapijee.business.boundary;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Date;

/**
 * Created by fernando on 22/02/17.
 */
@Stateless
@Path("hello")
public class HelloApi {

    @GET
    public String hello(){
        return "Hello API, today is " + new Date();
    }
}
