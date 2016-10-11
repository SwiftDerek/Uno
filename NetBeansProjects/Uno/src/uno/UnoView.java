package uno;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UnoView extends JFrame{
    
    JButton start = new JButton("Start");
    JButton help = new JButton("Help");
    
    public UnoView(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("UNO Card Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel Panel = new JPanel();
        
        Panel.add(start);
        Panel.add(help);
        
        this.add(Panel);
        
        this.setVisible(true);
    }
    
    public void addStartListener(ActionListener listenForStartButton){
        start.addActionListener(listenForStartButton);
    }
}
