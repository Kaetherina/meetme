package hpe.com.SecIoT.rest;

import groovy.lang.Singleton;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by vrettos on 26.09.2016.
 */
@Path("/test2")
@Produces({"application/json"}) // mime type
@Singleton
public class TestService {
}
