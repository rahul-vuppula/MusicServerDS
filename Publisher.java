import java.rmi.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Publisher {
    public static void main(String[] args) {
        int chances=3;
        Scanner scanner=new Scanner(System.in);
        while(chances!=0){
            try {
                MusicInterface h=(MusicInterface)Naming.lookup("//"+args[0]+"/ttt"); 
                boolean isAuthenticated = loginAuthentication();            //LOGIN AUTHENTICATION
                if (isAuthenticated) {
                    chances=0;
                    h.login(1,"Publisher");
                    int choice=0;
                    while(choice!=4){                                       //MENU FOR THE PUBLISHER
                        System.out.println("Choose an action:");
                        System.out.println("1. Upload a song");
                        System.out.println("2. Delete a song");
                        System.out.println("3. Browse the songs");
                        System.out.println("4. To exit");
                        System.out.print("Enter your choice: ");
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                // UPLOAD A SONG
                                System.out.println("Enter the song path to upload the song");
                                String fpath = scanner.nextLine();
                                Song song = createSong(fpath);
                                h.uploadSong(song.getTitle(),song.getArtist(),song.getData(),song.getCreditsRequired());
                                break;
                            case 2:
                                // DELETE A SONG
                                System.out.print("Enter the title of the song to delete: ");
                                Scanner ss=new Scanner(System.in);
                                String titleToDelete=ss.nextLine();
                                if(h.deleteSong(titleToDelete)==1)
                                {
                                    System.out.println("----Song deleted successfullyy----");
                                }
                                else{
                                    System.out.println("----Song not found‼----");
                                }
                                break;
                            case 3:
                                // BROWSE ALL THE SONGS
                                h.browseSongs();
                                List<Song> songs = h.browseSongs();
                                if(songs.isEmpty()){
                                    System.out.println("----No songs available----");
                                }
                                else{
                                    System.out.println("---Available songs-----");
                                    for (Song s : songs) {
                                        System.out.println("Title: "+s.getTitle()+", Artist: "+s.getArtist()+", Credits: "+s.getCreditsRequired());
                                    }
                                }
                                break;
                            case 4:
                                //LOGOUT
                                h.logout("Publisher");
                                System.out.println("You have exited successfully!!");
                                break;
                            default:
                                System.out.println("You have entered wrong choice!!.");
                                break;
                        }
                    }
                }else {
                    chances=chances-1;
                    if(chances==0){
                        System.out.println("Sorry the program is terminated");
                        break;
                    }
                    else
                    System.out.println("----Authentication failed. You have "+chances+" more chance(s) left---");
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
        String credfile="publisherlogin.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(credfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String storedUsername = credentials[0].trim();
                String storedPassword = credentials[1].trim();
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    //CREATING A SONG INFO TO UPLOAD TO THE SERVER
    private static Song createSong(String fpath) {
        
        Scanner scanner=new Scanner(System.in);
        String f=scanner.nextLine();

        System.out.print("Enter the title of the song: ");
        String title = scanner.nextLine();

        System.out.print("Enter the artist name: ");
        String artist = scanner.nextLine();

        System.out.println("entert the credits required for song: ");
        int credit=scanner.nextInt();

        byte[] data = readSongDataFromFile(f);
        System.out.println("------Song uploaded successfully!!!-----");
        return new Song(title, artist, data, credit);
    }
    private static byte[] readSongDataFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}