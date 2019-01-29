//Tyler Angelier
//Rick Boles
//Michael Montgomery
//Course Number:(CS4345)
//Semester-Year:(Spring 2018)
//Assignment:(Program 2)

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import javax.swing.border.Border;
import javax.swing.event.*;




public class Client {

    public BufferedReader in;
    public PrintWriter out;
    private String name = "Chat";
    private JFrame window = new JFrame(name);
    JTextField tField = new JTextField(75);
    JTextArea tArea = new JTextArea(30, 75);
    JLabel chatLabel = new JLabel("General");
    private String username = "";

    JPanel jpMain = new JPanel();
    JPanel jpLeft = new JPanel();
    JPanel jpTopLeft = new JPanel();
    JPanel jpRight = new JPanel();
    JPanel jpTop = new JPanel();
    JPanel jpBottom = new JPanel();
    JPanel jpbTop = new JPanel();
    JPanel jpbCenter = new JPanel();
    JPanel jpbBottom = new JPanel();
    JScrollPane jpCenter = new JScrollPane(tArea);

    Color g1 = Color.decode("#353940"); //Top & Message Bar BG
    Color g2 = Color.decode("#35393f"); //Center
    Color g3 = Color.decode("#303136"); //Left & Right & Center Scroll
    Color g4 = Color.decode("#1e2124"); //Scroll Bar
    Color g5 = Color.decode("#484b53"); //Message Bar
    Color g6 = Color.decode("#42464d"); //Highlight & Font
    Color g7 = Color.decode("#0c0c0c");

    GridBagConstraints c = new GridBagConstraints();
    GridBagConstraints c2 = new GridBagConstraints();
    GridBagConstraints c3 = new GridBagConstraints();
    GridBagConstraints c4 = new GridBagConstraints();
    GridBagConstraints c5 = new GridBagConstraints();

    DefaultListModel listModel1 = new DefaultListModel();
    DefaultListModel listModel2 = new DefaultListModel();  
                        
    JList Userlist = new JList(listModel1);
    JList Grouplist = new JList(listModel2); 
    
    String history = "";

    public Client() {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
        
        jpMain.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jpMain.setPreferredSize(new Dimension(800,600));
        jpMain.setLayout(new GridBagLayout());

        addGroup("General");
        c.fill = GridBagConstraints.BOTH;
        c3.fill = GridBagConstraints.BOTH;
        c4.fill = GridBagConstraints.BOTH;
        
        c.weighty = .5;
        c.weightx = 2;
        c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 3;
        c.gridwidth = 1;
        jpLeft.setBackground(g3);
        jpLeft.setLayout(new GridBagLayout());
            c3.insets = new Insets(0, 5, 0, 5);
            c3.weighty = 1;
            c3.weightx = 1;
            c3.anchor = GridBagConstraints.CENTER;
            c3.gridx = 0;
            c3.gridy = 0;
            c3.gridheight = 1;
            c3.gridwidth = 1;
        Grouplist.setSelectionInterval(0, 0);
        Grouplist.setBackground(g3);
        Grouplist.setForeground(g7);
        Grouplist.setSelectionBackground(g6);
        Grouplist.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jpLeft.add(Grouplist,c3);
        jpMain.add(jpLeft, c);
        
        c.weighty = .5;
        c.weightx = 4;
        c.anchor = GridBagConstraints.NORTH;
		c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 3;
        jpTop.setBackground(g1);
        jpTop.setLayout(new GridBagLayout());
            c5.insets = new Insets(0, 5, 0, 5);
            c5.weighty = 1;
            c5.weightx = 1;
            c5.anchor = GridBagConstraints.SOUTHWEST;
            c5.gridx = 0;
            c5.gridy = 0;
            c5.gridheight = 1;
            c5.gridwidth = 1;
        chatLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        jpTop.add(chatLabel, c5);
        jpMain.add(jpTop, c);
        
        c.weighty = .5;
        c.weightx = 2;
        c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        jpTopLeft.setBackground(Color.decode("#303136"));
        jpMain.add(jpTopLeft, c);

        c.weighty = .5;
        c.weightx = 2;
        c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 2;
        c.gridy = 1;
        c.gridheight = 3;
        c.gridwidth = 1;
        jpRight.setBackground(g3);
        jpRight.setLayout(new GridBagLayout());
            c4.insets = new Insets(0, 5, 0, 5);
            c4.weighty = 1;
            c4.weightx = 1;
            c4.anchor = GridBagConstraints.CENTER;
            c4.gridx = 0;
            c4.gridy = 0;
            c4.gridheight = 1;
            c4.gridwidth = 1;
        Userlist.setBackground(g3);
        Userlist.setForeground(g7);
        Userlist.setSelectionBackground(g6);
        Userlist.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jpRight.add(Userlist,c4);
        jpMain.add(jpRight, c);
        
        c.weighty = 1;
        c.weightx = 4;
        c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        jpBottom.setLayout(new GridBagLayout());
        jpBottom.setBackground(g1);
            c2.weighty = 1;
            c2.weightx = 1;
            c2.anchor = GridBagConstraints.CENTER;
            c2.gridx = 0;
            c2.gridy = 0;
            c2.gridheight = 1;
            c2.gridwidth = 10;
            jpbTop.setBackground(g1);
        jpBottom.add(jpbTop,c2);
            c2.weighty = 1;
            c2.weightx = 1;
            c2.anchor = GridBagConstraints.SOUTH;
            c2.gridx = 0;
            c2.gridy = 3;
            c2.gridheight = 1;
            c2.gridwidth = 10;
            jpbBottom.setBackground(g1);
        jpBottom.add(jpbBottom,c2);
            c2.weighty = 4;
            c2.weightx = 10;
            c2.anchor = GridBagConstraints.SOUTH;
            c2.gridx = 1;
            c2.gridy = 1;
            c2.gridheight = 2;
            c2.gridwidth = 8;
            c2.fill = GridBagConstraints.HORIZONTAL;
            c2.insets = new Insets(0, 5, 0, 5);
            jpbCenter.setLayout(new BorderLayout());
            jpbCenter.add(tField);
        jpBottom.add(jpbCenter, c2);
        tField.setSize(75, 30);
        tField.setBackground(g5);
        tField.setForeground(g7);
        tField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jpMain.add(jpBottom, c);
        
        c.weighty = 8;
        c.weightx = 8;
        c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        jpCenter.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jpCenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jpCenter.getVerticalScrollBar().setForeground(g4);
        jpCenter.getVerticalScrollBar().setBackground(g3);
        tArea.setBackground(g2);
        tArea.setForeground(g7);
        jpCenter.setBackground(g2);
        Border tbBorder = BorderFactory.createMatteBorder(2, 0, 2, 0, g3);
        jpCenter.setBorder(tbBorder);
        jpMain.add(jpCenter, c);
        

        tField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

 
                     
                if(tField.getText().startsWith("/w "))
                    out.println(username.toString() + "+" + tField.getText());
                else
                    out.println(tField.getText());
                tField.setText("");
            }
        });

        Grouplist.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();
                    String selected = source.getSelectedValue().toString();
                    chatLabel.setText(selected);
                }
            }
        });

        Userlist.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();
                    String selected = source.getSelectedValue().toString();
                    tField.setText("/w " + selected);
                }
            }
        });

        window.add(jpMain);
        window.pack();
    }


    private String getUserName() {
        String temp = JOptionPane.showInputDialog(window,"Username:","Login-Dialog",JOptionPane.PLAIN_MESSAGE);
        name += " - " + temp;
        window.setTitle(name);
        username =temp;
        return temp;
    }

    private void run() throws IOException {

        Socket socket = new Socket("localhost", 3000);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            System.out.println(line);
            if (line.startsWith("REQ")) {
                out.println(getUserName());
            } else if (line.startsWith("RES")) {
                tField.setEditable(true);
            }else if (line.startsWith("RM")) {
                removeUser(line.substring(3));
            }else if (line.startsWith("RG")) {
                removeGroup(line.substring(3));
            }else if (line.startsWith("TX")) {
                tArea.append(line.substring(3) + "\n");
                tArea.setCaretPosition(tArea.getDocument().getLength());
            }else if (line.startsWith("GP")) {
                addGroup(line.substring(3));
            }else if (line.startsWith("US")) {
                addUser(line.substring(3));
            }
        }

    }

    public void addGroup(String s){

        if(!listModel2.contains(s))
            listModel2.addElement(s);
        
    }

    public void addUser(String s){
        if(!listModel1.contains(s))
            listModel1.addElement(s);

    }

    public void removeUser(String s){
        System.out.println("Ran RU");
        if(listModel1.contains(s)){
            listModel1.removeElement(s);
            System.out.println("RU Con Met");
        }

    }

    public void removeGroup(String s){
        if(listModel2.contains(s))
            listModel2.removeElement(s);

    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.run();
    }
}