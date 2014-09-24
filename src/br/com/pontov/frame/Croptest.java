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



public class Croptest  {
	private Caractest arrtest;
	public Croptest(Caractest arrtest){
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
		 
			int w = (int) (c1-c3); 
	   		int h = (int) (c2-c4);
	   	    w = Math.abs(w); 
	   	    h = Math.abs(h);

		    Mat imgfinal = Highgui.imread(arrtest.getURL(),Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		    Rect ret = new Rect((int)c1,(int)c2-30,w,h+15);
		   
		    cropped = new Mat(imgfinal,ret); 
		    Highgui.imwrite(arrtest.getURL2(), cropped);
		    //notify();
		 
		    System.out.println("Cropped image saved successfully.");
		   
		    
		
	}


}
