import java.rmi.*;
import java.util.*;
public interface MusicInterface extends Remote{
    //Creating all the methods which need to be implemented
    public void login(int flag, String user) throws RemoteException;
    public void logout(String user) throws RemoteException;
    void uploadSong(String title, String artist, byte[] data, int creditsRequired) throws RemoteException;
    byte[] downloadSong(String title) throws RemoteException;
    public int deleteSong(String title) throws RemoteException;
    public List<Song> browseSongs() throws RemoteException;
}
