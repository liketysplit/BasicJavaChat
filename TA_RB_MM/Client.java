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



public class Client {

    public BufferedReader in;
    public PrintWriter out;
    private String name = "Chat";
    private JFrame window = new JFrame(name);
    JTextField tField = new JTextField(50);
    JTextArea tArea = new JTextArea(10, 50);
    JPanel bPanel = new JPanel();
    JButton button = new JButton("SEND");


    public Client() {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        tArea.setEditable(false);
        tField.setEditable(false);
        bPanel.setLayout(new GridLayout(2,1));
        button.setPreferredSize(new Dimension(50, 10));
        bPanel.add(tField);
        bPanel.add(button);
        window.getContentPane().add(bPanel, "South");
        window.getContentPane().add(new JScrollPane(tArea), "Center");

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println(tField.getText());
                tField.setText("");
            }
        });

        tField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println(tField.getText());
                tField.setText("");
            }
        });

        window.pack();
    }

    private String getUserName() {
        String temp = JOptionPane.showInputDialog(window,"Username:","Login-Dialog",JOptionPane.PLAIN_MESSAGE);
        name += " - " + temp;
        window.setTitle(name);
        return temp;
    }

    private void run() throws IOException {

        Socket socket = new Socket("localhost", 3000);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (line.startsWith("REQ")) {
                out.println(getUserName());
            } else if (line.startsWith("RES")) {
                tField.setEditable(true);
            } else if (line.startsWith("TX")) {
                tArea.append(line.substring(3) + "\n");
                tArea.setCaretPosition(tArea.getDocument().getLength());
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.run();
    }
}