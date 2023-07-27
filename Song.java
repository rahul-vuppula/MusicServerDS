import java.io.Serializable;

public class Song implements Serializable {
    private String title;
    private String artist;
    private byte[] data;
    private int creditsRequired;

    public Song(String title, String artist, byte[] data, int creditsRequired) {
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.creditsRequired=creditsRequired;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public byte[] getData() {
        return data;
    }
    public int getCreditsRequired(){
        return creditsRequired;
    }
}
