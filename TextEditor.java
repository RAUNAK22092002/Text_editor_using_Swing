import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

    JTextArea area;
    JScrollPane pane;
    JLabel fontLabel;
    JButton btn;
    JSpinner fontSize;
    JComboBox box;
    JMenuBar menuBar;
    JMenu file;
    JMenuItem open;
    JMenuItem exit;
    JMenuItem save;



    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Raunak Text Editor");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 20));

        pane = new JScrollPane(area);
        pane.setPreferredSize(new Dimension(450,450));
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel();




        fontSize = new JSpinner();
        fontSize.setPreferredSize(new Dimension(50,25));
        fontSize.setValue(20);
        fontSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                area.setFont(new Font(area.getFont().getFamily(), Font.PLAIN, (int) fontSize.getValue()));
            }
        });
        btn= new JButton("Color");
        btn.addActionListener(this);

        String[] fonts= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        box = new JComboBox(fonts);
        box.addActionListener(this);
        box.setSelectedItem("Arial");


        // -------------Menubar----------
        menuBar =  new JMenuBar();
        file= new JMenu("File");
        open= new JMenuItem("Open");
        exit= new JMenuItem("Exit");
        save= new JMenuItem("Save");

        open.addActionListener(this);
        exit.addActionListener(this);
        save.addActionListener(this);



        file.add(open);
        file.add(save);
        file.add(exit);
        menuBar.add(file);




        // -----------Menubar---------------


        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSize);
        this.add(btn);
        this.add(box);
        this.add(pane);


        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==btn){
                JColorChooser colorchoser = new JColorChooser();
                Color color = colorchoser.showDialog(null, "Choose a color", Color.black);
                area.setForeground(color);}
            if(e.getSource()==box){
                area.setFont(new Font((String)box.getSelectedItem(), Font.PLAIN, area.getFont().getSize()));
            }

            if(e.getSource()==open) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.setFileFilter(filter);
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file2 = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    Scanner filein = null;

                    try {
                        filein = new Scanner(file2);
                        if (file2.isFile()) {
                            while (filein.hasNextLine()) {
                                String str = filein.nextLine() + "\n";
                                area.setText(" ");
                                area.append(str);


                            }
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } finally {
                        filein.close();
                    }
                }
            }

        if(e.getSource()==save){
               JFileChooser fileChooser= new JFileChooser();
               fileChooser.setCurrentDirectory(new File("."));
               int response= fileChooser.showSaveDialog(null);
               if(response== JFileChooser.APPROVE_OPTION){
                   File file1;
                   PrintWriter fileOut=null;

                   file1= new File(fileChooser.getSelectedFile().getAbsolutePath());
                   try {
                       fileOut = new PrintWriter(file1);
                       fileOut.println(area.getText());

                   }
                   catch(FileNotFoundException e1){
                       e1.printStackTrace();
                   }
                   finally{
                       fileOut.close();
                   }

               }
        }
        if(e.getSource()==exit){
              System.exit(0);
        }
    }
}
