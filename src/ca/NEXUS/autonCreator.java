package ca.NEXUS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class autonCreator extends JComponent
{
    private static JFrame window;
    private static JPanel controls;
    private static JPanel field;
    
    private static Image fieldImage;
    static Image fieldImage_resized;
    private static int picOffset;
    
    private static File cubeLocations = new File("data/cubeLocations.txt");
    
    private static cube[] cubes = new cube[60];

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
        
        JLabel picLabel = new JLabel(new ImageIcon(fieldImage_resized));
        picLabel.setBounds(new Rectangle(controls.getWidth(), picOffset, fieldImage_resized.getWidth(null), fieldImage_resized.getHeight(null)));
        
        field.add(picLabel);
        field.repaint();
        System.out.println(picLabel.getBounds());
        
        int cubeSize = (int)(Math.ceil(fieldImage_resized.getWidth(null) * (5.5 / 144)));
        
        for(int i = 0; i < cubes.length; i++)
        {
            cubes[i] = new cube(cubeSize); // creates 66 cubes with correct size with respect to the field length
        }//end of for
        System.out.println("Reading Cube Data");
        readCubeData();
        System.out.println("Drawing Cubes");
        drawCubes();
        field.repaint();
        window.setVisible(true);
    }//end of main
    
    private static void readCubeData()
    {
        try {
            Scanner sc = new Scanner(cubeLocations);
                try {
                    for(int i = 0; i < cubes.length; i++) {
                        String line = sc.nextLine(); //gets first line
                        String[] values = line.split(",");
                        double x = Double.parseDouble(values[0]);
                        double y = Double.parseDouble(values[1]);
                        //System.out.println(line);
                        //System.out.println(values[0] + " , " + values[1] + " , " + values[2]);
                        //System.out.println(x + " , " + y);
                        cubes[i].setLocation(x, y);
                        if(values[2].equals("G")) {
                            cubes[i].setColour("GREEN");
                        }//end of if
                        else if(values[2].equals("O")) {
                            cubes[i].setColour("ORANGE");
                        }//end of else if
                        else if(values[2].equals("P")) {
                            cubes[i].setColour("PURPLE");
                        }//end of else if
                        System.out.println("Cube " + (i + 1) + " : "+ cubes[i].getX() + " , " + cubes[i].getY() + " , " + cubes[i].getColour());
                    }//end of for
                }//end of try
            catch(NoSuchElementException e)
            {
                e.printStackTrace();
            }//end of catch
                sc.close();
                System.out.println("Finished all cube location reading and object creation");
        }//end of try
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }// end of catch
    }//end of readCubeData()
    
    private static void drawCubes() throws IOException
    {
        JLabel[] cubeLabel = new JLabel[cubes.length];
        Image Green = ImageIO.read(new File("images/TowerTakeoverCube_Green.png"));
        Image Orange = ImageIO.read(new File("images/TowerTakeoverCube_Orange.png"));
        Image Purple = ImageIO.read(new File("images/TowerTakeoverCube_Purple.png"));
        Image resized = Green;
        for(int i = 0; i < cubeLabel.length; i++)
        {
            if (cubes[i].colour.equals("GREEN"))
            {
                resized = Green.getScaledInstance(cubes[i].getSize(), cubes[i].getSize(), Image.SCALE_SMOOTH);
            }//end of if
            else if (cubes[i].colour.equals("ORANGE"))
            {
                resized = Orange.getScaledInstance(cubes[i].getSize(), cubes[i].getSize(), Image.SCALE_SMOOTH);
            }//end of else if
            else
            {
                resized = Purple.getScaledInstance(cubes[i].getSize(), cubes[i].getSize(), Image.SCALE_SMOOTH);
            }//end of else if
            
            cubeLabel[i] = new JLabel(new ImageIcon(resized));
            cubeLabel[i].setBounds((int)cubes[i].getX(), (int)cubes[i].getY(), resized.getWidth(null), resized.getHeight(null));
            System.out.println("Cube " + (i + 1) + ": " + cubeLabel[i].getBounds());
            controls.add(cubeLabel[i]);
        }//end of for
        System.out.println("Finished all cube drawing");
    }//end of drawCubes
}//end of class
