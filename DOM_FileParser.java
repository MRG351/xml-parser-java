
// package xmlparser.parser;

import java.io.*;

import java.util.HashSet;
import java.util.Iterator;

// java DOM parser for opening and parsing xml files
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class DOM_FileParser {


    private static final int FAILURE = -1;
    
    private boolean fileParsed; /* true if the member file has been parsed */
    
    private File file;
        
    private HashSet<String> tagSet;
    
    
    public DOM_FileParser() {
        tagSet = new HashSet<String>();
        fileParsed = false;
    }
    
    public DOM_FileParser(File file) {
        tagSet = new HashSet<String>();
        parse(file);
    }
    
    /* parses out all the element tag names in the given xml file and stores them in a HashSet object. 
     * returns number of tags in success and -1 in failure. 
    */
    public int parse(File file) {
        
        this.file = file;        
        /* start fresh on each parse attempt */
        tagSet.clear();        
        /* set this to false in case an error occurs during parsing */
        this.fileParsed = false;                
                
                
        try {        
        
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);	
            Element root = doc.getDocumentElement();           
            //System.out.println(root.getTagName());
            root.normalize();  
            
            /* retrieve all the tag names in this xml file and store in HashSet hset */
            NodeList nList = root.getElementsByTagName("*"); // use "*" as a wildcard to get all Elements; evidently, recursion is not even necessary if we do it this way.
            for (int i=0; i < nList.getLength(); i++) {
                tagSet.add(((Element)nList.item(i)).getTagName()); // store the elements, uniquely.
            }
            
            /* add the root element tag if it is not already present */
            if (!tagSet.contains(root.getTagName())) {
                tagSet.add(root.getTagName());
            }             
            
            
            this.fileParsed = true;
            
        } catch (Exception e) {
            // have a dialog box pop up.
            e.printStackTrace();
            return FAILURE;
        }
        
        return tagSet.size();
    }
    
    
    /* return the File underlying the XMLFileParser object*/
    public File getFile() {
        return this.file;
    }
    
    /* return a reference to the underlying HashSet */
    public HashSet<String> getTagSet() {
        return tagSet;
    }
    
    /* return the attributes stored in the element with tag 'tag' */
    public String getAttributes(String tag) {
        NodeList nList = getElementsByTagName(tag);
        for (Node node : nList) {
            System.out.printl
            //for ( int i=0; i < nList.length(); i++ ) {
                //( (Element)node).getEleme   
            //}
        }
    }
    
    /* return true if there is currently a File associated with the parser */
    public boolean fileIsSet() {
            return (file != null) ? true : false;
    } 
    
   /* return true if the file has been successfully parsed */
    public boolean fileIsParsed() {
        return this.fileParsed;
    }
    
    /* return true if the provided File matches the File associated with the parser*/
    public boolean matches(File file) throws NullPointerException {
        return (this.file.toString() == file.toString()) ? true : false;
    }
    
    /* print the elements of the underlying HashSet*/
    public void printTagSet() {
        Iterator it = tagSet.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /* for using this class by inself */
    public static void main(String[] args) {
        
        DOM_FileParser parser = null;        
        
        try {
            parser = new DOM_FileParser(new File(args[0]));
        } catch (NullPointerException e) {
            System.out.println("error: file name required");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (parser.fileIsParsed())
                //parser.printTagSet();
            ;
            else
                System.exit(1);
        }
    }
  
}