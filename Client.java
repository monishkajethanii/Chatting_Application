package Chatting_Application;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Client implements MouseListener {
    JTextField t1;
    static JPanel panel2;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static DataInputStream din ;
    static Socket s;

    public Client() {
        // header panel
        JPanel headerJPanel = new JPanel();
        headerJPanel.setBackground(new Color(7, 94, 84));
        headerJPanel.setBounds(0, 0, 450, 70);

        // setting icon
        ImageIcon icon1 = new ImageIcon(
                "C:/Users/Monishka/OneDrive/Documents/Programs/AJP_Microproject/Chatting_Application/Chatting_Application/arrow.png");
        Image i2 = icon1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3, JLabel.LEFT);
        back.setBounds(5, 10, 30, 30);
        back.addMouseListener(this);
        headerJPanel.add(back);

        ImageIcon icon2 = new ImageIcon(
                "C:/Users/Monishka/OneDrive/Documents/Programs/AJP_Microproject/Chatting_Application/Chatting_Application/2.png");
        Image i4 = icon2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i5 = new ImageIcon(i4);
        JLabel profile = new JLabel(i5);
        profile.setBounds(30, 10, 30, 30);
        profile.addMouseListener(this);
        headerJPanel.add(profile);

        JLabel name = new JLabel("Monishka");
        name.setFont(new Font("Arial", Font.BOLD, 16));
        name.setBounds(110, 15, 100, 10);
        name.setForeground(Color.white);
        headerJPanel.add(name);

        JLabel status = new JLabel("Active Now");
        status.setFont(new Font("Arial", Font.BOLD, 12));
        status.setBounds(110, 35, 100, 10);
        status.setForeground(Color.white);
        headerJPanel.add(status);

        ImageIcon icon3 = new ImageIcon(
                "C:/Users/Monishka/OneDrive/Documents/Programs/AJP_Microproject/Chatting_Application/Chatting_Application/video.png");
        Image i6 = icon3.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i7 = new ImageIcon(i6);
        JLabel video = new JLabel(i7);
        video.setBounds(160, 10, 30, 30);
        headerJPanel.add(video);

        ImageIcon icon4 = new ImageIcon(
                "C:/Users/Monishka/OneDrive/Documents/Programs/AJP_Microproject/Chatting_Application/Chatting_Application/phone.png");
        Image i8 = icon4.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel call = new JLabel(i9);
        call.setBounds(190, 10, 30, 30);
        headerJPanel.add(call);

        ImageIcon icon5 = new ImageIcon(
                "C:/Users/Monishka/OneDrive/Documents/Programs/AJP_Microproject/Chatting_Application/Chatting_Application/3dot.jpeg");
        Image i10 = icon5.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i11 = new ImageIcon(i10);
        JLabel dots = new JLabel(i11);
        headerJPanel.add(dots);

        panel2 = new JPanel();
        panel2.setBounds(5, 75, 440, 570);
        t1 = new JTextField();
        t1.setBounds(5, 655, 310, 40);
        t1.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 14));
        f.add(t1);

        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.white);
        Send sendListener = new Send();
        send.addActionListener(sendListener);
        f.add(send);
        f.add(panel2);
        f.add(headerJPanel);
        f.setLayout(null);
        f.setBackground(Color.WHITE);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.setSize(450, 700);
        f.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
        System.exit(0);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'mouseReleased'");
    }

    public static void main(String[] args) throws Exception
    {
        new Client();
        try {
            Socket s = new Socket("localhost", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());    
           
            while (true) {
                String msg = din.readUTF();
                //System.out.println("Message received: " + msg);
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                panel2.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                din.close();
                dout.close();
                s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }    

    public class Send implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{
            String text = t1.getText();
            panel2.setLayout(new BorderLayout());
            JPanel p2 = formatLabel(text);
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createHorizontalStrut(15));
            panel2.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(text);
            t1.setText("");
            f.repaint();
            f.validate();
            f.invalidate();
        }
        catch(Exception ee){}
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Arial", Font.PLAIN, 14));
        output.setBackground(new Color(7, 94, 84));
        output.setForeground(Color.white);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
}
