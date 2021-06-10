//gestisce la tabella della classifica -  chiede i dati al database

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


class ClassificaDB {
    public TableView<Palette> tb;
    public ObservableList<Palette> olClassifica;
    private GestoreDB dbmanager;
    
    ClassificaDB(GestoreDB gdb){
      dbmanager = gdb;
      this.tb = new TableView<>();
             
      TableColumn likeCol = new TableColumn("Likes");
      TableColumn imgCol = new TableColumn("Palette");
      TableColumn nameCol = new TableColumn("Mood");
      
      likeCol.setCellValueFactory(new PropertyValueFactory<>("Like"));
      nameCol.setCellValueFactory(new PropertyValueFactory<>("Mood"));
      imgCol.setCellValueFactory(new PropertyValueFactory<>("Image"));
      
      imgCol.setPrefWidth(100);
      nameCol.setPrefWidth(100);
      likeCol.setPrefWidth(100);
      
      imgCol.setEditable(false);
      nameCol.setEditable(false);
      likeCol.setEditable(false);
      
      likeCol.setStyle("-fx-background-color:rgb(226,225,171);");
      nameCol.setStyle("-fx-background-color:rgb(243,172,148);");
      imgCol.setStyle("-fx-background-color:rgb(254,236,217);");
      
      tb.getColumns().addAll(likeCol, imgCol,nameCol);
      
    }
    
    void caricaDB() {
        olClassifica = dbmanager.caricaClassifica();
        tb.setItems(olClassifica);
    }
    
    public TableView gettb(){
        return tb;
    };
    
    
}