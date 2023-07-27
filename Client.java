import java.rmi.*;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Client {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int chancess=3;
	    String addr=args[0];
        while(chancess!=0){
            try {
                MusicInterface h=(MusicInterface)Naming.lookup("//"+addr+"/ttt");
                boolean isAuthenticated = loginAuthentication();            //LOGIN AUTHENTICATION
                if (isAuthenticated) {
                    chancess=0;
                    h.login(1,"Client");
                    int choice=0;
                    int clientcredits=10;           //INITIALLY CLIENTS HAVE 10 CREDITS
                    while(choice!=4){
                        System.out.println("Choose an action:");
                        System.out.println("1. Browse the songs");
                        System.out.println("2. download the song");
                        System.out.println("3. check your credits ");
                        System.out.println("4. Exit");
                        System.out.print("Enter your choice: ");
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                // BROWSE THE SONGS
                                h.browseSongs();
                                List<Song> songs = h.browseSongs();
                                if(songs.isEmpty()){
                                    System.out.println("----No songs available----");
                                }
                                else{
                                    System.out.println("----Available songs----");
                                    for (Song s : songs) {
                                        System.out.println("Title: "+s.getTitle()+", Artist: "+s.getArtist()+", Credits: "+s.getCreditsRequired());
                                    }
                                }
                                break;
                            case 2:
                                // DOWNLOAD THE SONGS
                                h.browseSongs();
                                List<Song> songs1 = h.browseSongs();
                                
                                if(songs1.isEmpty()){
                                    System.out.println("----No songs available to download----");
                                }
                                else{
                                    Scanner ss=new Scanner(System.in);
                                    System.out.println("enter the song name to download ");
                                    String title=ss.nextLine();
                                    System.out.println("choose the destination to download the song: ");
                                    String dpath=ss.nextLine();
                                    for (Song s1 : songs1) {
                                        if(s1.getTitle().equals(title)){
                                            if(clientcredits>=(s1.getCreditsRequired())){
                                                clientcredits=clientcredits-s1.getCreditsRequired();
                                                //h.downloadSong(title);
                                                download(s1,dpath,addr);
                                                System.out.println("----Song downloaded successfully----");
                                                System.out.println("----check your destination path to enjoy the music!!!----");
                                            }
                                            else
                                            System.out.println("----You don't have enough credit(s)-----");
                                        }
                                    }
                                }
                                break;
                            case 3:
                                //RETURN THE CREDITS REMAINING FOR THE CLIENT
                                System.out.println("---You have "+clientcredits+" credits----");
                                break;
                            case 4:
                                //LOGOUT
                                h.logout("Client");
                                System.out.println("You have exited successfully!!");
                                break;
                            default:
                                System.out.println("You have entered wrong choice!!.");
                                break;
                        }
                    }
                }else {
                    chancess=chancess-1;
                    if(chancess==0){
                        System.out.println("Sorry the program is terminated");
                        break;
                    }
                    else
                    System.out.println("----Authentication failed. You have "+chancess+" more chance(s) left---");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    //LOGIN AUTHENTICATION
    private static boolean loginAuthentication() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        String credfile="clientlogin.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(credfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String storedUsername = credentials[0].trim();
                String storedPassword = credentials[1].trim();
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true; // 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; 
    }
    //DOWNLOADING THE SONG FROM THE SERVER WHICH IS UPLOADED BY THE PUBLISHER
    private static void download(Song s,String dpath,String addr){
        try {
            MusicInterface h=(MusicInterface)Naming.lookup("//"+addr+"/ttt");
            byte[] songData = h.downloadSong(s.getTitle());
            if (songData != null) {
                Path destinationPath = Paths.get(dpath, s.getTitle() + ".mp3");
                FileOutputStream outputStream = new FileOutputStream(destinationPath.toFile());
                outputStream.write(songData);
                outputStream.close();
            } else {
                System.out.println("----Failed to download the song----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}