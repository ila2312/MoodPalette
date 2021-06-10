//defisce la struttura dei dati dentro la tabella della classifica

import javafx.beans.property.*;
import javafx.scene.image.*;

public class Palette {
    
    private final SimpleIntegerProperty like;
    private final SimpleStringProperty mood;
    private final ImageView image;
    
    Palette(int l, String m, String p){
        like = new SimpleIntegerProperty(l);
        mood = new SimpleStringProperty(m);
        image = new ImageView(new Image(p));
        image.setFitHeight(50);
        image.setFitWidth(50);
    }
    
    public int getLike(){
        return like.get();
    }
    
    public String getMood(){
        return mood.get();
    }
    
    public ImageView getImage(){
        return image;
    };
    
    
}