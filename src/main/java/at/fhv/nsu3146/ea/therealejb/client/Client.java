package at.fhv.nsu3146.ea.therealejb.client;

import at.fhv.nsu3146.ea.therealejb.shared.Greeter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Client {

    public static void main(String[] args) {

        try {

            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            Context ctx = new InitialContext(props);

            //ejb:/[DeployedName]/Implementierungsname![packages + Interface of Bean]
            Greeter greeter = (Greeter) ctx.lookup("ejb:/TheRealEJB-1.0/GreeterBean!at.fhv.nsu3146.ea.therealejb.shared.Greeter");

            String greeting = greeter.greet("Wow");

            System.out.println(greeting);

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
