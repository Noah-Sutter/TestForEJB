package at.fhv.nsu3146.ea.therealejb.shared;

import javax.ejb.Remote;
import java.io.Serializable;

@Remote
public interface Greeter extends Serializable {

    String greet(String name);

}
