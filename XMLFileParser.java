
import java.io.*;

// java DOM parser for opening and parsing xml files
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLFileParser {

    public static void main(String[] args) {
        
        String fileName;
        File xmlFile = null;
        
        if (args[0] == null) {
            System.out.println("error: file name required");
            System.exit(1);
        }
        
        fileName = args[0];
        
        XMLFileParser parser = new XMLFileParser();
           
        try {
            xmlFile = new File(fileName);
        } catch (Exception e) {
            System.out.println("error: could not open " + fileName);
            System.exit(1);
        }
        
        parser.parse(xmlFile);
    }
    
    public void parse(File file) {
        try {
            
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);			
            doc.getDocumentElement().normalize();
            printTagNames(doc.getDocumentElement());       
            
            /*
            System.out.println(doc.getDocumentElement().getTagName());
            //System.out.println( ((Element)( doc.getDocumentElement().getFirstChild() )).getTagName());
            
            if (doc.getDocumentElement().hasChildNodes())
                System.out.println("true");
            else
                System.out.println("false");
            
            if (doc.getDocumentElement().hasChildNodes())
                System.out.println("true");
            else
                System.out.println("false");
            /*
            NodeList children = doc.getDocumentElement().getChildNodes();
            for (int i=0; i < children.getLength(); i++) {
                System.out.println(((Element)children.item(i)).getTagName());
            } */
            
        } catch (Exception e) {
            System.exit(1);
        }	
    }
  
    private void printTagNames(Element elm) {
        printTagNames(elm, 0);
    }
    
    private void printTagNames(Element elm, int depth) {
        
        // print a tab (4 space tab)  for each level of depth we are in
        for (int i=0; i < depth; i++) {
            System.out.print("    ");
        }
        
        System.out.println(elm.getTagName());
        if (elm.hasChildNodes()) {
            System.out.println("elm has child nodes");
        
            NodeList children = elm.getChildNodes();
            System.out.println("children.length == " + children.getLength());
            for (int i=0; i < children.getLength(); i++) {
                System.out.println("evaluating child " + i);
                printTagNames((Element)children.item(i), depth);        
                System.out.println("finished evaluating child " + i);
            }
        } else {
            System.out.println("elm does not have child nodes");
        }
    }
}