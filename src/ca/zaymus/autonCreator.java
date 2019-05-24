package ca.zaymus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class autonCreator extends JComponent
{
    private autonCreator(){}
    
    private static JFrame window;
    
    private static JPanel controls;
    private static JPanel field;
    
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    
    public static void main(String[] args) throws IOException
    {
        String imagePath = "images/TowerTakeoverField.png";
        
        controls = new JPanel();
        controls.setLayout(null);
        field = new JPanel();
        field.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        window = new JFrame("NEXUS Robotics Auton Creator v1");
        window.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, screenSize.width, screenSize.height);
        window.getContentPane().add(new autonCreator());
        window.getContentPane().add(controls);
        window.getContentPane().add(field);
        
        controls.setBounds(0, 0, window.getWidth() / 2, window.getHeight());
        
        field.setBounds(controls.getWidth(), 0, controls.getWidth(), window.getHeight());
        
        Image image = ImageIO.read(imagePath);
        float scalePercent = (float)field.getHeight() / (float)image.getHeight(null);
        
        System.out.println(field.getHeight() + " , " + image.getHeight(null) + " , " + scalePercent);
        
        Image image_resized = image.getScaledInstance(image.getWidth * scalePercent, 
                                                        image.getHeight * scalePercent, Image.SCALE_SMOOTH);
        
                                                        JLabel picLabel = new JLabel(new ImageIcon(image_resized));
        picLabel.setBounds(0,0, field.getWidth(), field.getHeight());
        
        field.add(picLabel);
        field.repaint();
        window.setVisible(true);
    }//end of main
    
    public void paint(Graphics g)
    {
    
    }
}//end of class
