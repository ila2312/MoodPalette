//frontend

import java.util.List;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
//import javafx.animation.*;


public class MoodPalette extends Application {

    private enum MOOD{
        DREAMY,
        MAGICAL,
        ROMANTIC,
        MYSTERIOUS,
        RETRO,
        DELICATE,
        NOSTALGIA;
    }

    private Scene scene;
    private Label lblu;
    private Label lblm;
    private Label lblp;
    private TextField username;
    private TextField lastmood;
    private Button start;
    private Button end;
    private ClassificaDB classifica;
    private GestoreDB database;
    private Group root;
    private MenuBar menu;
    private Menu moods;
    private List<MenuItem> moodsList;
   // private Logo logo;

    private GestioneEventi gestioneEventi;

    @Override
    public void start(Stage stage) {
        creaInterfaccia();
        database = new GestoreDB(3306,"localhost",5);
        classifica = new ClassificaDB(database);
        moodsList = new ArrayList<MenuItem>();
        gestioneEventi = new GestioneEventi(database);

        stage.setTitle("Mood Palette");
        stage.setScene(scene);
        stage.show();
    }


    public void creaInterfaccia(){

        lblu = new Label ("u s e r");
        lblm = new Label ("l a s t m o o d");
        lblp = new Label ("palette più gettonate");
        username = new TextField("");
        lastmood = new TextField(""); //non modificabile
        start = new Button("s t a r t");
        end = new Button("e n d");

        menu = new MenuBar();
        moods = new Menu("Choose your mood");


        for(MOOD name: MOOD.values()) {
            moodsList.add(new MenuItem(name.name().toLowerCase())); //.name() restituisce il nome dell enum
        }

        menu.getMenus().add(moods);
        moods.getItems().addAll(moodsList);

        menu.setLayoutX(900);
        menu.setLayoutY(300);

        lblu.setLayoutX(900);
        lblu.setLayoutY(20);
        lblu.setMaxWidth(100);

        username.setLayoutX(850);
        username.setLayoutY(40);
        username.setMaxWidth(150);

        lblm.setLayoutX(875);
        lblm.setLayoutY(80);
        lblm.setMaxWidth(200);

        lastmood.setEditable(false); //per non modificare mood
        lastmood.setLayoutX(850);
        lastmood.setLayoutY(100);
        lastmood.setMaxWidth(150);

        start.setLayoutX(1050);
        start.setLayoutY(40);
        start.setStyle("-fx-font-size: 18px");

        end.setLayoutX(1050);
        end.setLayoutY(90);
        end.setStyle("-fx-font-size: 18px");

        lblp.setLayoutX(30);
        lblp.setLayoutY(250);
        lblp.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: rgb(230,123,128);");

        classifica.caricaDB();

        classifica.tb.setLayoutX(30);
        classifica.tb.setLayoutY(300);
        classifica.tb.setStyle("-fx-border-width: 7px; "
                + "             -fx-border-radius: 10px; "
                + "             -fx-border-color:rgb(230,123,128); "
                + "             -fx-background-radius: 10px;");

        for(MenuItem item: moodsList){
            item.setOnAction(
                (ActionEvent event) -> {moodChoosing(moodsList.indexOf(item));}
            );
        }

        start.setOnMouseClicked(
                (MouseEvent event) -> login()
        );

        end.setOnMouseClicked(
                (MouseEvent event) -> logout()
        );

        root = new Group(username, start, end, lastmood, lblu, lblm, lblp, menu);
        root.getChildren().add(classifica.gettb());
        scene = new Scene(root,1200,720,Color.rgb(252, 226, 219));
    }

    public void login(){
        String susername = username.getText();
        System.out.println("Hello from login: " + susername);
        String mood = GestioneEventi.Login(susername);
        lastmood.setText(mood);

    }

    public void logout() {
        String susername = username.getText();
        System.out.println(("Bye bye: " + susername));
        username.setText("");
        lastmood.setText("");
    }

    public void moodChoosing(int mood) {
        System.out.println("mood index:" + mood + " mood: " + MOOD.values()[mood].name().toLowerCase());
    }

    public void paletteChoosing(int palete) {
        //manda a video una textbox per l utente
        //gestisci click su palete
        //in ingresso devo passarli la palette scelta - o come index di un array o come nome
        gestioneEventi.SceltaPalette("pal");
        //richiama il gestore della classifica
        classifica.caricaDB();
    }

}

