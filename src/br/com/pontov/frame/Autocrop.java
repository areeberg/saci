package br.com.pontov.frame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Autocrop {
	private Carac arr;
	public Autocrop(Carac arr){
	this.arr=arr;
}
	
	public void start(Rect box)
	{
		Mat imgcro=Highgui.imread(arr.getURL2(),Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		//Rect ret = new Rect(c1,c2-30,w,h+15);
		Rect ret= new Rect(box.x,box.y,box.width,box.height);
		Mat cropped = new Mat(imgcro,ret); 
		 
		 //chamar o register aqui
		
		// resize para padronizar as imagens
    	
    	Size sz=new Size(30,30);
    	Imgproc.resize(cropped, cropped,sz,1,1,Imgproc.INTER_CUBIC);
    
    	Highgui.imwrite(arr.getURL3(), cropped);
	
		
		
		 JDialog.setDefaultLookAndFeelDecorated(true);
		 
		 
		
		    Object[] selectionValues = { "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor" };
		    String initialSelection = "Capacitor";
		    Object selection = JOptionPane.showInputDialog(null, "Please select the type of your component",
		        "Component Type", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
		   // System.out.println(selection);
		    
		
		   
		/* JPanel panel = new JPanel();
		 BufferedImage mypic=null;
		 File arquivo = new File(arr.getURL3());
		 mypic=ImageIO.read(arquivo);
		*/ 
		
		 ImageIcon icon = new ImageIcon(arr.getURL3());
		 
		    int response = JOptionPane.showConfirmDialog(null, "Angle "+arr.getAngle()+"\nContour "+arr.getContour()+"\nElongation "+arr.getElongation()+"\n", "Confirm",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,icon);
		   
		    if (response == JOptionPane.NO_OPTION) {
		    	//retirar foto, decrementar arr
		      System.out.println("No button clicked");
		    } else if (response == JOptionPane.YES_OPTION) {
		    	//mostrar foto + info 
		    	 arr.setType(selection.toString());
				 System.out.print(selection.toString());
		      System.out.println("Yes button clicked");
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		    	
		      System.out.println("JOptionPane closed");
		    }
		 
		    
	}
}
