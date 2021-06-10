public class GestioneEventi {
    //login
    //sceltamood
    //sceltapalette -> caricaclassifica
    //logout -> messo dentro MoodPalette
    
    private GestoreDB database;
    
    public void Login() {
        
    }
        
    public void SceltaMood() {
        
    }
    
    public void SceltaPalette(String scelta) { //prende in ingresso il nome o l indice della palette scelta
        //richiama il gestoreDB per aggiornare il numero di like della palette scelta
        database.aggiornaPalette("scelta");
    }
    
}
