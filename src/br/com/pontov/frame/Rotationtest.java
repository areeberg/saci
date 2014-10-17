package br.com.pontov.frame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Rotationtest  {
	private Caractest arrtest;
	public Rotationtest(Caractest arrtest){
	this.arrtest=arrtest;
}
	private Carac arr;
	public void Crop(Carac arr){
	this.arr=arr;
}
	
	double drag_status=0,c1,c2,c3,c4,code;
	Mat cropped=null;
	boolean go=false;
	
	
	

	public synchronized void start() throws InterruptedException, IOException
	{
		
		//setSize(640,480);
		String info = "";
		code=arrtest.getCode();

		String[] vetor = new String[10];
		double[] datas = new double[10];  //15 = training samples; 10 numero de caracteristicas
		 BufferedReader inputfile =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info.txt"));
		 for (int a = 0; a <= 10+code; a++)
         { info = inputfile.readLine(); }

		    vetor = info.split(",");     
	        for (int p = 0; p < vetor.length-1; p++) 
	        { datas[p] =  Double.parseDouble(vetor[p]);}
         arrtest.setType(vetor[8]);
         c1=datas[4];
         c2=datas[5];
         c3=datas[6];
         c4=datas[7];
		 
			

		    Mat imgfinal = Highgui.imread(arrtest.getURL(),Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		    
		    double x1,x2,y1,y2;
		    x1=c1;
		    y1=c2;
		    x2=c3;
		    y2=c4;
		    
		    
		    x1=((x1*Math.cos(Math.toDegrees(90)))-(y1*Math.sin(Math.toDegrees(90))));
		    y1=((x1*Math.sin(Math.toDegrees(90)))+(y1*Math.cos(Math.toDegrees(90))));
		    x2=((x2*Math.cos(Math.toDegrees(90)))-(y2*Math.sin(Math.toDegrees(90))));
		    y2=((x2*Math.sin(Math.toDegrees(90)))+(y2*Math.cos(Math.toDegrees(90))));
		    int w = (int) (x1-x2); 
	   		int h = (int) (y1-y2);
	   	    w = Math.abs(w); 
	   	    h = Math.abs(h);
		    
		   Rect ret = new Rect((int)c1,(int)c2-30,w,h+15);
		  
		  // Rectangle rect = new Rectangle();
		    //rect.setFrameFromDiagonal(x1, y1, x2, y2);
		    
		    
		    
		    cropped = new Mat(imgfinal,ret); 
		    Highgui.imwrite(arrtest.getURL2(), cropped);
		    //notify();
		    
		    
		 
		    System.out.println('\n'+"Cropped image saved successfully.");
		   
		    
		
	}


}
