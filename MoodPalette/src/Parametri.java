import java.io.*;
import com.thoughtworks.xstream.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class Parametri implements Serializable {
    public String serverFilePath;
    public int portaLog;
    public int portaDB;
    public String ipServer;
    public String validSchema;
    public int numPalette; //numero di palette nella classifica
    public String pathLogo1;
    public String pathLogo2;
    public String pathLogo3;
    public String pathLogo4;
    public String pathLogo5;
    public String pathLogo6;

    
    public void deserializza(){
        Parametri p;
        String s;
        boolean valid = false;
        XStream xs = new XStream();
        try{
            s = new String(Files.readAllBytes(Paths.get("parametri.xml")));
            valid = valida();
            if(valid){
                p = (Parametri) xs.fromXML(s);
                pathLogo1 = p.pathLogo1;
                pathLogo2 = p.pathLogo2;
                pathLogo3 = p.pathLogo3;
                pathLogo4 = p.pathLogo4;
                pathLogo5 = p.pathLogo5;
                pathLogo6 = p.pathLogo6;
                
                ipServer = p.ipServer; //localhost
                portaLog = p.portaLog; //8080
                portaDB = p.portaDB; //3306
                serverFilePath = p.serverFilePath; //log.txt
                validSchema = p.validSchema; //event.xsd
                numPalette = p.numPalette; //10
            }
               
        } catch(IOException e){
            System.out.println("Errore deserializzazione file");
            }           
    }
    
    private static boolean valida(){
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File("parametri.xml"));
            Schema s = sf.newSchema(new StreamSource(new File("event.xsd")));
            s.newValidator().validate(new DOMSource(d));
        }catch(ParserConfigurationException | SAXException | IOException e){
            if(e instanceof SAXException)
                System.out.println("Errore di validazione: " + e.getMessage());
            else System.out.println(e.getMessage());
            return false;
        }
        return true;  
    }
    
}
