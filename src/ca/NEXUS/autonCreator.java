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
    
    public static double scalePercent;

    private static Image fieldImage;
    static Image fieldImage_resized;
    static int picOffset;
    static JLabel fieldLabel;

    private static File cubeLocations = new File("data/cubeLocations.txt");
    
    private static ArrayList<ArrayList<cube>> groups = new ArrayList<ArrayList<cube>>();

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
        scalePercent = (float)field.getWidth() / (float) fieldImage.getWidth(null);
        fieldImage_resized = fieldImage.getScaledInstance((int)(fieldImage.getWidth(null) * scalePercent), (int) (fieldImage.getHeight(null) * scalePercent), Image.SCALE_SMOOTH);
        picOffset = (field.getHeight() - fieldImage_resized.getHeight(null)) / 2;
        System.out.println("scale percent: " + scalePercent);

        fieldLabel = new JLabel(new ImageIcon(fieldImage_resized));
        fieldLabel.setBounds(new Rectangle(controls.getWidth(), picOffset, fieldImage_resized.getWidth(null), fieldImage_resized.getHeight(null)));
        
        System.out.println("Reading Cube Data");
        readCubeData();
        System.out.println("Drawing Cubes");
        drawCubes();
        field.add(fieldLabel);
        window.setVisible(true);
    }//end of main

    private static void groupCubes(cube[] a)
    {
        ArrayList<cube> addgroup = new ArrayList<cube>();
        for(int i = 0; i < a.length; i++)
        {
            addgroup.add(a[i]);
        }//end of for
        Collections.sort(addgroup);
        groups.add(addgroup);

    }//end of groupCubes

    private static void readCubeData()
    {
        int cubeSize = (int)(Math.ceil(fieldImage_resized.getWidth(null) * (5.5 / 144))), count = 0;
        String[] vals;
        cube[] cubeTemp;
	        try
	        {
		        Scanner sc = new Scanner(cubeLocations);
		        try
		        {
		        	while(true) {
				        String line = sc.nextLine(); //gets first line
				        String[] temp = line.split("-");
				        vals = new String[Integer.parseInt(temp[1])];
				        cubeTemp = new cube[Integer.parseInt(temp[1])];
				        for(int i = 0; i < Integer.parseInt(temp[1]); i++) {
					        line = sc.nextLine();
					        vals[i] = line;
				        }//end of for

				        for(int j = 0; j < vals.length; j++) {
					        String[] values = vals[j].split(",");
					        cubeTemp[j] = new cube(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), cubeSize, values[3]);
				        }//end of for
				        groupCubes(cubeTemp);
				        count++;
				        if(count == 19) {
					        break;
				        }//end of if
			        }//end of while
		        }//end of try
		        catch(NoSuchElementException e)
		        {		        }//end of catch
		        sc.close();
	        }//end of try
	        catch(FileNotFoundException e) {
		        e.printStackTrace();
	        }// end of catch
    }//end of readCubeData()
    
    private static void drawCubes() throws IOException
    {
        JLabel[] cubeLabel = new JLabel[60];
        Image Green = ImageIO.read(new File("images/TowerTakeoverCube_Green.png"));
        Image Orange = ImageIO.read(new File("images/TowerTakeoverCube_Orange.png"));
        Image Purple = ImageIO.read(new File("images/TowerTakeoverCube_Purple.png"));
        Image resized = Green;
        for(int i = 0; i < groups.size(); i++)
        {
	        for(int j = 0; j < groups.get(i).size(); j++)
	        {
		        if(groups.get(i).get(j).colour.equals("G")) {
			        resized = Green.getScaledInstance(groups.get(i).get(j).getSize(), groups.get(i).get(j).getSize(), Image.SCALE_SMOOTH);
		        }//end of if
		        else if(groups.get(i).get(j).colour.equals("O")) {
			        resized = Orange.getScaledInstance(groups.get(i).get(j).getSize(), groups.get(i).get(j).getSize(), Image.SCALE_SMOOTH);
		        }//end of else if
		        else {
			        resized = Purple.getScaledInstance(groups.get(i).get(j).getSize(), groups.get(i).get(j).getSize(), Image.SCALE_SMOOTH);
		        }//end of else if
		        cubeLabel[i] = new JLabel(new ImageIcon(resized));
		        cubeLabel[i].setBounds(controls.getWidth()+(int)groups.get(i).get(j).getX(), (int)groups.get(i).get(j).getY(), resized.getWidth(null), resized.getHeight(null));
		        System.out.println(cubeLabel[i].getX() + " , " + cubeLabel[i].getY());
		        field.add(cubeLabel[i]);
	        }//end of for
        }//end of for
    }//end of drawCubes
}//end of class
