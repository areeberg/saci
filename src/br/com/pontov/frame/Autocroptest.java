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



public class Autocroptest {
	private Caractest arrtest;
	public Autocroptest(Caractest arrtest){
	this.arrtest=arrtest;
}
	
	public void start(Rect box)
	{
		Mat imgcro=Highgui.imread(arrtest.getURL2(),Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Rect ret= new Rect(box.x,box.y,box.width,box.height);
		Mat cropped = new Mat(imgcro,ret); 
		 
		 //chamar o register aqui
		
		// resize para padronizar as imagens
    	
    	Size sz=new Size(30,30);
    	Imgproc.resize(cropped, cropped,sz,1,1,Imgproc.INTER_CUBIC);
    
    	Highgui.imwrite(arrtest.getURL3(), cropped);
	
	}
}
