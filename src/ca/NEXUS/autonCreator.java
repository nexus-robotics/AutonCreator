package ca.NEXUS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

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
        field = new JPanel();
        
        window = new JFrame("NEXUS Robotics Auton Creator v1");
        window.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, screenSize.width, screenSize.height);
        window.getContentPane().add(new autonCreator());
        window.getContentPane().add(controls);
        window.getContentPane().add(field);
        
        controls.setBounds(0, 0, window.getWidth() / 2, window.getHeight());
        
        field.setBounds(controls.getWidth(), 0, controls.getWidth(), window.getHeight());
        
        Image image = ImageIO.read(new File(imagePath));
        float scalePercent = (float)field.getWidth() / (float)image.getWidth(null);
        Image image_resized = image.getScaledInstance((int)(image.getWidth(null) * scalePercent),
                (int) (image.getHeight(null) * scalePercent), Image.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(image_resized));
        int picOffset = (window.getHeight() - image_resized.getHeight(null)) / 2;
        picLabel.setBounds(field.getWidth() + field.getX(), picOffset, field.getWidth(), field.getHeight());

        controls.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        field.setLayout(new FlowLayout(FlowLayout.CENTER, 10, picOffset));

        field.add(picLabel);
        System.out.println(picLabel.getX());
        field.repaint();
        window.setVisible(true);
    }//end of main

    public void paint(Graphics g)
    {
    
    }
}//end of class
