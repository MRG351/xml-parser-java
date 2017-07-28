
// package xmlparser.gui;

import java.io.File;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

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




public class RetinaParser extends JPanel implements ActionListener {        
    
    
    private static final int BRD_SIZE = 12;             /* boarder size */
    
    private static final int LABL_STYLE = Font.PLAIN;           /* font style - labels      */
    private static final int BTN_STYLE = Font.PLAIN;            /* font style - buttons */
    private static final int CHK_BTN_STYLE = Font.PLAIN;     /* font style - checkButtons */
    
    private static final float LABL_FSIZE = 12f;                /* font size of labels */    
    private static final float BTN_FSIZE = 11f;                 /* font size of button text */    
    private static final float CHK_BTN_FSIZE = 10f;          /* font size of check-button text*/

        
    private static final String ERROR_MSG1 = " triggered by iterator.next() in ";
    private static final String ERROR_MSG2 = " triggered during array assignment in ";
    private static final String ERROR_MSG3 = "";
    
    
    private DOM_FileParser parser;
    
    private File file;    
    private String fileName = "";        

    private JPanel headerPanel, buttonPanel, displayPanel;      /* main panels */
    private JPanel checkBoxPanel, optionsPanel;      /* sub panels */
    
    private JLabel headerLabel;
    
    private JButton openButton;
    private JButton generateButton;
    
    private JCheckBox[] tagBoxes;
    
    private JFileChooser fc;    
    
        
        
    public RetinaParser() {       

        super(new BorderLayout());
        
        initFilechooser();
        initHeaderPanel();
        initButtonPanel();     
        initDisplayPanel();
        
        add(headerPanel, BorderLayout.PAGE_START); // TOP
        add(buttonPanel, BorderLayout.LINE_START);   // LEFT
        add(displayPanel, BorderLayout.CENTER);         // CENTER
        
        setBorder(BorderFactory.createEmptyBorder(BRD_SIZE, BRD_SIZE, BRD_SIZE, BRD_SIZE));       
        
        parser = new DOM_FileParser(); 
        
    }
    
    
    /* initalize the xml file chooser (xml files only! perhaps csv later)*/
    private void initFilechooser() {        
        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new XMLFilter());
    }
    
    
    /* initialize the header panel and label */
    private void initHeaderPanel() {
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            headerLabel = new JLabel("File: NA");
            headerLabel.setFont(headerLabel.getFont().deriveFont(LABL_STYLE, LABL_FSIZE));
        
        headerPanel.add(headerLabel);            
    }
    
    
    /* create the button panel and create/attach its components */
    private void initButtonPanel() {        
        
        /* create the Button Panel */
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 0, 16));    // 2 rows (6 pads), 1 column, 0p hgap 16p vgap              
                
            /* create the open button */
            openButton = new JButton("Open a File...");
            openButton.setFont(openButton.getFont().deriveFont(BTN_STYLE, BTN_FSIZE));
            openButton.addActionListener(this);
        
            /* create the generate button */
            generateButton = new JButton("Generate Report");
            generateButton.setFont(generateButton.getFont().deriveFont(BTN_STYLE, BTN_FSIZE));
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
        displayPanel = new JPanel();    
        displayPanel.setLayout(new GridLayout(2,1,0,0)); /* 2 rows, 1 column, 0p hgap 0p vgap */
        
        displayPanel.setPreferredSize(new Dimension(500,400)); // get rid of this eventually, let the size of the components dictate the size of the window
        
            /* create the checkBoxPanel and initialize its components */
            checkBoxPanel = new JPanel();
        
            /* create the optionsPanel and initialize its components */
            optionsPanel = new JPanel();
        
        /* attach components to the display panel */
        displayPanel.add(checkBoxPanel);
        //displayPanel.add(checkBoxPanel, BorderLayout.CENTER);
        displayPanel.add(optionsPanel);
        //displayPanel.add(optionsPanel, BorderLayout.PAGE_END);    
    }
    
    
    private void populate(int num_tags) {    
    
        /* populate header panel */
        headerPanel.remove(headerLabel);
        headerLabel = new JLabel("File: " + file.getName());
        headerLabel.setFont(headerLabel.getFont().deriveFont(LABL_STYLE, LABL_FSIZE));
        headerPanel.add(headerLabel);
        
        
        /* populate display panel */
        displayPanel.remove(checkBoxPanel);       
        displayPanel.remove(optionsPanel);
        
        /* populate the checkbox panel*/
        checkBoxPanel = new JPanel();
        optionsPanel = new JPanel();
        if (num_tags > 0) {  
            
            /* create check buttons*/
            { /* place within inner scope to hide the index variable i*/            
                int i = 0;
                tagBoxes = new JCheckBox[num_tags]; 
              
                for (Iterator it = parser.getTagSet().iterator(); it.hasNext(); i++) {                    
                    try {
                        tagBoxes[i] = new JCheckBox((String)it.next(), false);
                        tagBoxes[i].setFont(tagBoxes[i].getFont().deriveFont(CHK_BTN_STYLE, CHK_BTN_FSIZE));
                    } catch (NoSuchElementException e) {
                        error(e, ERROR_MSG1, "populate()");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        error(e, ERROR_MSG2, "populate()");
                    }
                }             
            }
            
            /* setlayout configuration */
            int dim = Utilities.fitInSquare(num_tags); 
            int gap = 4;
            checkBoxPanel.setLayout(new GridLayout(dim, dim, gap, gap));
            checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(BRD_SIZE, BRD_SIZE, BRD_SIZE, BRD_SIZE));
            
            // add check boxes to the panel
            for (int i=0; i < tagBoxes.length; i++) {
                checkBoxPanel.add(tagBoxes[i]);
            }
            
            // pad the rest of the panel with white space         
            for (int i = dim*dim - tagBoxes.length; i > 0; i--) {
                checkBoxPanel.add(new JPanel());
            }
            
             
        } // else { fill up space}
        displayPanel.add(checkBoxPanel);  
        displayPanel.add(optionsPanel);
        
        updateUI();     /* refresh the gui */
    }
    
     
    private static void createAndShowGUI() {
        
        JFrame frame = new JFrame(" Retina Export Parser (XML)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new RetinaParser();
        newContentPane.setOpaque(true);     //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }
    
    
	public static void main(String[] args) {		
            
        /* Set System L&F: This is the L&F style that uses the native system look */
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.exit(1);
        } catch (InstantiationException e) {
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.exit(1);
        } 
        
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
    

     /* report on errors/exceptions, will open a dialog box eventually. For now just print to terminal */
    private void error(Exception e, String error_msg, String method) {
        System.out.println(e.toString() + error_msg + method);
        e.printStackTrace();
    }
    
    
    /* Handle action events from the interface */
    public void actionPerformed(ActionEvent a) {
        
        if (a.getSource() == openButton) { /* parse the file for xml tags and populate the gui */            
        
            int returnVal = fc.showOpenDialog(RetinaParser.this);                        
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {     /* if a file is selected */
            
                file = fc.getSelectedFile();                
                populate(parser.parse(file));

                //log.append("Opening: " + file.getName() + "." + newline);
                
            } else { /* if the cancel button is pressed */
               ;//log.append("Open command cancelled by user." + newline);
            }
            //log.setCaretPosition(log.getDocument().getLength());
        } else if (a.getSource() == generateButton) {
            
            for (JCheckBox tagBox : tagBoxes) {
                if (tagBox.isSelected()) {
                        System.out.println(tagBox.getText());
                        System.out.println(parser.getAttributes(tagBox.getText()));
                }
            }
            
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
