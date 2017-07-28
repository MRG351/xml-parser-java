
// package xmlparser.parser;

import java.io.*;

import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAX_FileParser {
    
    
    private static final int FAILURE = -1;
    
    private boolean fileParsed; /* true if the member file has been parsed */
    
    private File file;
    
    private HashSet<String> tagSet;
    
    
    public SAX_FileParser() {
        tagSet = new HashSet<String>();
        fileParsed = false;
    }
    
    public SAX_FileParser(File file) {
        tagSet = new HashSet<String>();
        parse(file);
    }
    
}

