public class GestioneEventi {
    //login
    //sceltamood
    //sceltapalette -> caricaclassifica
    //logout -> messo dentro MoodPalette

    private static GestoreDB database;

    public GestioneEventi(GestoreDB db) {
        database = db;
    }

    public static String Login(String username) {
        String pallete = database.cercaUtente(username);

        if (pallete == null) {
            database.aggiungiUtente(username);
            return "";
        }

        return pallete;

    }

    public static void SceltaMood() {

    }

    GestioneEventi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void SceltaPalette(String scelta) { //prende in ingresso il nome o l indice della palette scelta
        //richiama il gestoreDB per aggiornare il numero di like della palette scelta
        database.aggiornaPalette("scelta");
    }


}