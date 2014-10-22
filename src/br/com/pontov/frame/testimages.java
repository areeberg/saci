package br.com.pontov.frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.color.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.RotatedRect;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.text.html.ImageView;

import org.opencv.highgui.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.opencv.utils.Converters;

import weka.core.Instance;

import com.atul.JavaOpenCV.Imshow;  // Adicionar a biblioteca Imshow

import java.nio.file.*;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class testimages {

	  private static final int CV_LOAD_IMAGE_GRAYSCALE = 0;
		private static final boolean DEBUG_GRAPHICS_LOADED = false;
		private static final Object[] MatOfPoint2f = null;
		public static boolean control=false;
		public static Carac[] arr= new Carac[200];
		public static Caractest[] arrtest = new Caractest[200];
		public static int code=1;
		public static int codetest=1;
		public static File filet2 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesctest.txt");
		public static File fileconjc = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/infotest.txt");
		public static File filefourier = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/filefourier.txt");
		public static File fileinfo = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fileinfo.txt");
		private static boolean head=false;
	
				
		public void init(int click) throws IOException{

	    	   MyThreadctest t1t = new MyThreadctest(arrtest[click]);
	    	   MyThreadftest t2t = new MyThreadftest(arrtest[click]);

	       t1t.start();
	    

	        	try{
	        		t1t.join();
	        		
	        	}catch(InterruptedException ie){
	        		System.out.println("Erro durante o corte"); 
	        		
	        	}
	        	t2t.start();
	        try{
	        	
	    		t2t.join();
	    	}catch(InterruptedException ie){
	    		System.out.println("Erro ao extrair as caracteristicas");

	    		}

	        //-------------
	    
	    	PrintWriter outputfile = null;
	    	PrintWriter outputfile2 = null;
	    	PrintWriter outputfile3=null;

			try {
				
				outputfile = new PrintWriter(new FileWriter(filet2, true));
				outputfile2 = new PrintWriter(new FileWriter(fileconjc, true));
				outputfile3 = new PrintWriter(new FileWriter(fileinfo,true));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			

			//-----------------------------    
			
			
					
			
			if(head==false)
			{
				String header="";
				String headerf="";
				
				header="@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@attribute absdiff real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data";
				headerf="Descritores de Fourier para teste";
				outputfile.write(headerf);
				outputfile2.write(header);
				head=true;
			}
						
			String body="";	
			// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
			//content3 += "\n"+arr[i].getNumbins()+","+arr[i].getPct2pk()+","+arr[i].getBimodalap()+"";
			body="\n"+arrtest[click].getNumbins()+","+arrtest[click].getPct2pk()+","+arrtest[click].getBimodalap()+","+arrtest[click].getType()+"";
			
			
			outputfile2.write(body);
			outputfile2.close();	
			//---------------------------------------------------------------------------------
	
			// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
			String bodyf="";	
			//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
		
			bodyf="\n"+arrtest[click].getDescri()[0]+","+arrtest[click].getDescri()[1]+","+arrtest[click].getDescri()[2]+","+arrtest[click].getDescri()[3]+","+arrtest[click].getDescri()[4]+","+arrtest[click].getDescri()[5]+","+arrtest[click].getDescri()[6]+","+arrtest[click].getDescri()[7]+","+arrtest[click].getDescri()[8]+","+arrtest[click].getDescri()[9]+","+arrtest[click].getDescri()[10]+","+arrtest[click].getDescri()[11]+","+arrtest[click].getDescri()[12]+","+arrtest[click].getDescri()[13]+","+arrtest[click].getDescri()[14]+","+arrtest[click].getDescri()[15]+","+arrtest[click].getDescri()[16]+","+arrtest[click].getDescri()[17]+","+arrtest[click].getDescri()[18]+","+arrtest[click].getDescri()[19]+"";		
			outputfile.write(bodyf);
			outputfile.close();
			//---------------------------------------------------------------------------------
			String fileinfo="";
			fileinfo="\n"+arrtest[click].getAngle()+","+arrtest[click].getCentrox()+","+arrtest[click].getCentroy()+"";
			outputfile3.write(fileinfo);
			outputfile3.close();
			

			NNO.readFiletest(1, "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO2.txt", code);
			code++;
}
		

	public int start() throws IOException{
		System.loadLibrary("opencv_java249");
		

		 String dir;
	        int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages").listFiles().length;
	        System.out.println("Adquirindo imagem");
		for (int i=0;i<nfdir+200;i++)  //nfdir = numero de arquivos gold
	      {
	    	  for (int k=0;k<nfdir+50;k++)
	    	  {
	    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+".png";
	      File photo=new File (dir);
	    	 if (photo.isFile())
	    	 {
	    		 arrtest[codetest]= new Caractest();
	    		 arrtest[codetest].setName(dir);
	    		arrtest[codetest].setCode(codetest); 
	    		arrtest[codetest].setName2("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+"a.png");
	    		arrtest[codetest].setName3("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+"b.png");
	    		codetest++;
	    	 }//end bracket if
	    	 }//end bracket for k
	    	  
	      }// end bracket for i
		
	
		
		int resp = codetest-1;
		
		
		
		return (resp);
	  
	}}
