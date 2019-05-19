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
    
    private static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
        
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
        
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
    
    private static void resize(String inputImagePath,
                              String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
    
    public static void main(String[] args) throws IOException
    {
        String imagePath = "images/TowerTakeoverField.png";
        String outputImagePath = "images/TowerTakeoverField_Resized.png";
        
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
        
        Image image = ImageIO.read(new File(imagePath));
        float scalePercent = (float)field.getHeight() / (float)image.getHeight(null);
        System.out.println(field.getHeight() + " , " + image.getHeight(null) + " , " + scalePercent);
        resize(imagePath, outputImagePath, scalePercent);
        Image image_resized = ImageIO.read(new File(outputImagePath));
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
