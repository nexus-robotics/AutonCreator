package ca.NEXUS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class autonCreator extends JComponent
{
    private static Image image;
    
    private static JFrame window;
    private static JPanel controls;
    private static JPanel field;
    
    private static Image image_resized;
    private static int picOffset;
    
    

    public static void main(String[] args) throws IOException
    {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        final Dimension screenBounds = toolkit.getScreenSize();
        final Insets screenInsets = toolkit.getScreenInsets(graphicsDevice.getDefaultConfiguration());
        final Rectangle screenSize = new Rectangle(screenBounds);
        
        screenSize.x += screenInsets.left;
        screenSize.y += screenInsets.top;
        screenSize.width -= (screenInsets.left + screenInsets.right);
        screenSize.height -= (screenInsets.top + screenInsets.bottom);
        
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
        controls.setLayout(null);
        field.setLayout(null);
        
        image = ImageIO.read(new File(imagePath));
        float scalePercent = (float)field.getWidth() / (float)image.getWidth(null);
        image_resized = image.getScaledInstance((int)(image.getWidth(null) * scalePercent), (int) (image.getHeight(null) * scalePercent), Image.SCALE_SMOOTH);
        picOffset = (field.getHeight() - image_resized.getHeight(null)) / 2;
        
        JLabel picLabel = new JLabel(new ImageIcon(image_resized));
        picLabel.setBounds(new Rectangle(controls.getWidth(), picOffset, image_resized.getWidth(null), image_resized.getHeight(null)));
        
        field.add(picLabel);
        field.repaint();
        window.setVisible(true);
    }//end of main
}//end of class
