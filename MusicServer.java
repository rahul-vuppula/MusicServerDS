import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class MusicServer {
    public static void main(String[] args) {
        try {
            //CREATING THE RMI OBJECT AND BINDING WITH LOCALHOST
            MusicImplement robj=new MusicImplement();
	    LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("//"+args[0]+"/ttt", robj); 
            System.out.println("This is server and it is ready!!!");
        } catch (Exception e) {
            System.out.println("server exception "+e);
        }
    }
}
