package at.fhv.nsu3146.ea.therealejb.server;

import at.fhv.nsu3146.ea.therealejb.shared.Greeter;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello-world")
public class ServerRestController {

    @EJB
    private Greeter greeter;

    @GET
    @Produces("text/plain")
    public String hello() {
        return greeter.greet("asdf!");
    }
}