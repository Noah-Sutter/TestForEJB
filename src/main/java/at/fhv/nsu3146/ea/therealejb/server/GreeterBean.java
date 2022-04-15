package at.fhv.nsu3146.ea.therealejb.server;

import at.fhv.nsu3146.ea.therealejb.shared.Greeter;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Remote(Greeter.class)
@Stateful
public class GreeterBean implements Greeter {

    @Override
    public String greet(String name) {
        return "heyoo " + name;
    }
}
