import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
public class MusicImplement extends UnicastRemoteObject implements MusicInterface{
    //CREATING THE HASHMAP TO STORE THE SONG INFO
    private Map<String, Song> sMap;
    public MusicImplement() throws RemoteException{
        sMap=new HashMap<>();
    }

    //LOGIN ACKNOWLEDGEMENT
    @Override
    public void login(int flag, String user) throws RemoteException{
        if(flag==1)
        System.out.println(user+" login successfull");
        else
        System.out.println(user+" login failed!");
    }

    //LOGOUT ACKNOWLEDGEMENT
    @Override
    public void logout(String user) throws RemoteException{
        System.out.println(user+" logout successfull");
    }

    //UPLOADING SONG TO THE HASHMAP BY PUBLISHER
    @Override
    public void uploadSong(String title, String artist, byte[] data, int creditsRequired) throws RemoteException {
        Song song = new Song(title, artist, data, creditsRequired);
        sMap.put(title, song);
        System.out.println("Song uploaded: " + title);
    }

    //DOWNLOADING THE DATA OF THE SONG BY CLIENT
    @Override
    public byte[] downloadSong(String title) throws RemoteException {
        Song song = sMap.get(title);
        if (song != null) {
            System.out.println("Song is successfully downloaded by client");
            return song.getData();
        }
        return null;
    }

    //DELETING THE SONG BY REMOVING THE DATA FROM HASHMAP BY PUBLISHER
    @Override
    public int deleteSong(String title) throws RemoteException {
        if (sMap.containsKey(title)) {
            sMap.remove(title);
            System.out.println("Song deleted: " + title);
            return 1;
        } else {
            System.out.println("Song not found: " + title);
            return -1;
        }
    }

    //BROWSING THE SONGS BY RETURNING THE HASH MAP VALUES AS ARRAYLIST
    @Override
    public List<Song> browseSongs() throws RemoteException {
        return new ArrayList<>(sMap.values());
    }
}