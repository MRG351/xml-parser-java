

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;



/*
import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
*/

public class RetinaParser extends JPanel implements ActionListener {
    
    private static final float BTN_SIZE = 12f;
    private static final float LABL_SIZE = 12f;

    private XMLFileParser xmlParser;
    
    private JPanel headerPanel;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private JPanel infoPanel;
    private JPanel checkBoxPanel;
    private JPanel optionsPanel;
    
    private JLabel headerLabel;
    private JLabel infoLabel;
    
    private JButton openButton;
    private JButton generateButton;
    
    private JFileChooser fc;
    
    private File file;
    
    private String fileName = "";
    
    public RetinaParser() {   
    
        super(new BorderLayout());
        
        initFilechooser();
        initHeaderPanel();
        initButtonPanel();     
        initDisplayPanel();
        
        add(headerPanel, BorderLayout.PAGE_START); // add the headerPanel to the top
        add(buttonPanel, BorderLayout.LINE_START);   // add the button panel to the left
        add(displayPanel, BorderLayout.CENTER); // add the display panel to the center
        
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); // add a border to the entire frame
        
        xmlParser = new XMLFileParser();
    }
    
    private void initFilechooser() {        
        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new XMLFilter());
    }
    
    private void initHeaderPanel() {
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerLabel = new JLabel("File: NA");
        
        headerPanel.add(headerLabel);            
    }
    
    /* create the button panel and create/attach its components */
    private void initButtonPanel() {        
        
        /* create the Button Panel */
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 1, 16));    // 2 rows, 1 column, 1 hgap 16 vgap              
                
            /* create the open button */
            //openButton = new JButton("Open a File...", createImageIcon("images/Open16.gif")); // can add an image next to the Button Text if you want
            openButton = new JButton("Open a File...");
            openButton.setFont(openButton.getFont().deriveFont(Font.BOLD, BTN_SIZE));
            openButton.addActionListener(this);
        
            /* create the generate button */
            generateButton = new JButton("Generate Report");
            generateButton.setFont(generateButton.getFont().deriveFont(Font.BOLD, BTN_SIZE));
            generateButton.addActionListener(this);
        
            /* create place holder panels */
            //use this if you need empty panels between your grid layout
            JPanel[] placeHolders = new JPanel[ ( ( (GridLayout)( buttonPanel.getLayout() ) ).getRows() ) - 2];
            for (int i=0; i<placeHolders.length; i++) {
                placeHolders[i] = new JPanel();
            }
        
            /* attach components to the button panel */
        buttonPanel.add(openButton);        
        buttonPanel.add(generateButton);        
        for (int i=0; i<placeHolders.length; i++) {
            buttonPanel.add(placeHolders[i]);
        }      
    }
    
     /* create the display panel and create/attach its inital components */
    private void initDisplayPanel() {
        
        /* create the display panel */
        displayPanel = new JPanel();    // gridbaglayout may be best here, as it allows for specification of component width/height
        displayPanel.setLayout(new GridLayout(3,0,1,16));
        
        displayPanel.setPreferredSize(new Dimension(400,300)); // get rid of this eventually, let the size of the components dictate the size of the window
        
            /* create the information panel and initialize its components */
            infoPanel = new JPanel();
            infoLabel = new JLabel("File: NA");
        
            infoPanel.add(infoLabel);
        
            /* create the checkBoxPanel and initialize its components */
            checkBoxPanel = new JPanel();
        
            /* create the optionsPanel and initialize its components */
            optionsPanel = new JPanel();
        
        /* attach components to the display panel */
        displayPanel.add(infoPanel, BorderLayout.PAGE_START);
        displayPanel.add(checkBoxPanel, BorderLayout.CENTER);
        displayPanel.add(optionsPanel, BorderLayout.PAGE_END);    
    }
    
    private void populate() {    
    
        /* populate header panel */
        headerPanel.remove(headerLabel);
        headerLabel = new JLabel("File: " + file.getName());
        headerPanel.add(headerLabel);
        
        /* populate display panel */
        infoPanel.remove(infoLabel);
        infoLabel = new JLabel("File: " + file.getName());
        infoPanel.add(infoLabel);
        
        updateUI();
    }
    
    private static void createAndShowGUI() {
        
        JFrame frame = new JFrame(" Retina Export Parser (XML)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new RetinaParser();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }
    
   	
	public static void main(String[] args) {		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}  
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(RetinaParser.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();   
                xmlParser.parse(file);
                populate(); // parse file and populate the gui with the updated information & components
                //log.append("Opening: " + file.getName() + "." + newline);
            } else {
                //log.append("Open command cancelled by user." + newline);
            }
            //log.setCaretPosition(log.getDocument().getLength());
        } else if (e.getSource() == generateButton) {
            // this is where the application would generate a file based upon the xml file used and options specified.
        }
    }    
    
    /* Filechooser .xml only filter */
    private class XMLFilter extends FileFilter {
    
        private static final String xml = "xml";
    
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
        
            String extension = getExtension(f);
            
            if (extension != null) { 
                if (extension.equals(xml)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    
        public String getDescription() {
            return ".xml";
        }
    
        private String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 &&  i < s.length() - 1) {
                ext = s.substring(i+1).toLowerCase();
            }
            return ext;
        }
    }
}
