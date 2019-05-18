package ca.zaymus;

import javax.swing.*;
import java.awt.*;

public class autonCreator extends JComponent
{
    public autonCreator(){}
    
    static JFrame window;
    static JPanel field;
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static void main(String[] args)
    {
        field = new JPanel();
        
        window = new JFrame("NEXUS Robotics Auton Creator v1");
        window.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, screenSize.width, screenSize.height);
        window.getContentPane().add(new autonCreator());
        window.getContentPane().add(field);
        window.setVisible(true);
        
        field.setBounds((int)(2 * screenSize.getWidth()) / 3, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
        field.setBackground(new Color(100,200,30));
    }//end of main
    
    public void paint(Graphics g)
    {
    
    }
}//end of class
