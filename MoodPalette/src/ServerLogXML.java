import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.basic.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ServerLogXML {
    private static String pathFileLog;
    private static int port;
    
    public static void main(String[] args){
        Parametri param = null;
        try{
            File f = new File("parametri.xml");
            if(f.exists() && !f.isDirectory()){
                String x = new String(Files.readAllBytes(Paths.get("parametri.xml")));
                XStream xs = new XStream();
                xs.alias("Parametri", Parametri.class);
                param = (Parametri)xs.fromXML(x);
            }
            else{
                f.createNewFile();
                param = new Parametri();
                XStream xs = new XStream();
                xs.alias("Parametri", Parametri.class);
                Files.write(Paths.get("./parametri.xml"),((String)xs.toXML(param)).getBytes());
            }
        }catch(IOException e){e.printStackTrace();}
        
        pathFileLog = param.serverFilePath;
        port = param.portaLog;
        
        try(ServerSocket ss = new ServerSocket(port)){
            while(true){
                try(Socket s = ss.accept();
                    ObjectInputStream oin = new ObjectInputStream(s.getInputStream())){
                    String evento = (String)oin.readObject();
                    
                    FileWriter fw = new FileWriter(pathFileLog, true);
                    if(valida(evento)){
                        System.out.println(evento);
                        fw.write(evento +"\r\n \r\n");
                    }
                    else{
                        System.out.println("Evento non valido");
                        fw.write("Evento non valido \r\n \r\n");
                    }
                    fw.flush();
                    fw.close();
                }
            }
        }
        catch(Exception e1){e1.printStackTrace();}
    }
    
    private static boolean valida(String ei){
        try{
            Files.write(Paths.get("supp.xml"), ei.getBytes());
            
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document d = db.parse(new File("supp.xml"));
            
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Schema s = sf.newSchema(new StreamSource(new File("event.xsd")));
            
            s.newValidator().validate(new DOMSource(d));
        } catch (Exception e) {
        if (e instanceof SAXException){ 
            e.printStackTrace();
            return false;
            }
        else{
            e.printStackTrace();
            return false;
            }
        }
        return true;
    }
}