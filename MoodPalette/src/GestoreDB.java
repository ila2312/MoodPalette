//connessione con il database

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.*;

public class GestoreDB {
    private final int port;
    private final String ip;

    private final int maxClassifica;

    GestoreDB(int p, String i, int max){
        port = p;
        ip = i;
        maxClassifica = max;
    }

    public List<String> caricaPalette(String mood){
        List<String> pal =  new ArrayList<>();
         try( Connection co = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/moodpalette","root","");
            PreparedStatement ps = co.prepareStatement("SELECT CodPalette FROM Palette WHERE Mood = " + mood + "");
            )
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                pal.add(rs.getString("CodPalette"));
            }
        }
        catch (SQLException e) {System.err.println(e.getMessage());}

         return pal;

    }

    public void aggiornaLastMood(String username, String newMood) {
        try( Connection co = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/moodpalette","root","");
            PreparedStatement ps = co.prepareStatement("UPDATE LastMoos SET LastMood = " + newMood + " WHERE User = " + username);) {
                ps.executeQuery();
            }catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }

    //aggiorna il numero di like quando viene scelta una palette
    public void aggiornaPalette(String pal) {
        int nlike;

        try( Connection co = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/moodpalette","root","");
            PreparedStatement ps = co.prepareStatement("SELECT Likes FROM Palette WHERE CodPalette = " + pal + "");
            ){
            ResultSet rs = ps.executeQuery();
            nlike = rs.getInt("Likes");
            nlike++;

            PreparedStatement ps2 = co.prepareStatement("UPDATE Palette SET Likes = " + nlike + " WHERE CodPalette = " + pal + "");

            ps2.executeQuery();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //aggiunge un utente nel caso fosse la prima volta che logga
    public void aggiungiUtente(String name) {
        try( Connection co = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/moodpalette","root","");
            PreparedStatement ps = co.prepareStatement("INSERT INTO LastMood(User, LastMood) VALUES (" + name + ", ) ");
            ){
            ps.executeQuery();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //cerca l'username dell utente che ha appena loggato e rimanda indietro la stringa dell ultimo mood scelto
    public String cercaUtente(String name) {
        String moodDB = null;

        try( Connection co = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/moodpalette","root","");
            PreparedStatement ps = co.prepareStatement("SELECT LastMood FROM LastMood WHERE Username = " + name + "");){
            ResultSet rs = ps.executeQuery();
            moodDB = rs.getString("LastMood");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return moodDB;
    }

    //costruzione della classifica
    public ObservableList<Palette> caricaClassifica(){
        ObservableList<Palette> temp = FXCollections.observableArrayList();
        int i = maxClassifica;
        try( Connection co = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/moodpalette","root","");
            PreparedStatement ps = co.prepareStatement("SELECT * FROM Palette WHERE Likes > 0 ORDER BY Likes DESC");
            )
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next() && i > 0){
                temp.add(new Palette(rs.getInt("Likes"),rs.getString("Mood"),"img/"+rs.getString("CodPalette")+".png"));
                i--;
            }
        }
        catch (SQLException e) {System.err.println(e.getMessage());}

        return temp;
    }
}

