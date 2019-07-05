package ca.NEXUS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javafx.geometry.Point3D;

public class autonCreator extends JComponent
{
    private static JFrame window;
    private static JPanel controls;
    private static JPanel field;
    
    private static Image fieldImage;
    static Image fieldImage_resized;
    static int picOffset;
    static JLabel fieldLabel;

    private static File cubeLocations = new File("data/cubeLocations.txt");
    
    private static cube[] cubes = new cube[60];
    private static cube[] cubeGroups;

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
        
        fieldImage = ImageIO.read(new File(imagePath));
        float scalePercent = (float)field.getWidth() / (float) fieldImage.getWidth(null);
        fieldImage_resized = fieldImage.getScaledInstance((int)(fieldImage.getWidth(null) * scalePercent), (int) (fieldImage.getHeight(null) * scalePercent), Image.SCALE_SMOOTH);
        picOffset = (field.getHeight() - fieldImage_resized.getHeight(null)) / 2;
        
        fieldLabel = new JLabel(new ImageIcon(fieldImage_resized));
        fieldLabel.setBounds(new Rectangle(controls.getWidth(), picOffset, fieldImage_resized.getWidth(null), fieldImage_resized.getHeight(null)));
        
        field.add(fieldLabel);
        field.repaint();
        System.out.println(fieldLabel.getBounds());
        
        System.out.println("Reading and drawing Cube Data");
        readCubeData();
        field.repaint();
        window.setVisible(true);
    }//end of main

    private static void groupCubes(cube[] a)
    {
        for(int i = 0; i < a.length; i++)
        {
        
        }//end of for
    }//end of groupCubes

    private static void readCubeData()
    {
        double z = 2.75;
        int count = 0, cubeSize = (int)(Math.ceil(fieldImage_resized.getWidth(null) * (5.5 / 144)));
        
        while(count != 60)
        {
            try
            {
                Scanner sc = new Scanner(cubeLocations);
                try
                {
                    for(int i = 0; i < cubes.length; i++)
                    {
                        String line = sc.nextLine(); //gets first line
                        String[] values = line.split(",");
                        cubes[i] = new cube(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), cubeSize ,values[3]);
                    }//end of for
                    Arrays.sort(cubes);
                    groupCubes(cubes);
                }//end of try
                catch(NoSuchElementException e)
                {
                    e.printStackTrace();
                }//end of catch
                sc.close();
            }//end of try
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }// end of catch
        }//end of while
    }//end of readCubeData()
    
    private static void drawCubes(int cube) throws IOException
    {
        JLabel[] cubeLabel = new JLabel[cubes.length];
        Image Green = ImageIO.read(new File("images/TowerTakeoverCube_Green.png"));
        Image Orange = ImageIO.read(new File("images/TowerTakeoverCube_Orange.png"));
        Image Purple = ImageIO.read(new File("images/TowerTakeoverCube_Purple.png"));
        Image resized = Green;
        
        if (cubes[cube].colour.equals("GREEN"))
        {
            resized = Green.getScaledInstance(cubes[cube].getSize(), cubes[cube].getSize(), Image.SCALE_SMOOTH);
        }//end of if
        else if (cubes[cube].colour.equals("ORANGE"))
        {
            resized = Orange.getScaledInstance(cubes[cube].getSize(), cubes[cube].getSize(), Image.SCALE_SMOOTH);
        }//end of else if
        else
        {
            resized = Purple.getScaledInstance(cubes[cube].getSize(), cubes[cube].getSize(), Image.SCALE_SMOOTH);
        }//end of else if
            
        cubeLabel[cube] = new JLabel(new ImageIcon(resized));
        cubeLabel[cube].setBounds((int)cubes[cube].getX(), (int)cubes[cube].getY(), resized.getWidth(null), resized.getHeight(null));
        field.add(cubeLabel[cube]);
        System.out.println("Cube " + (cube + 1) + " : " + cubes[cube].getX() + " , " + cubes[cube].getY() + " , " + cubes[cube].getColour());
    }//end of drawCubes
}//end of class
