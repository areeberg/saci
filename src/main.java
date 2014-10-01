


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
import javax.vecmath.Point2f;

import org.opencv.highgui.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.opencv.utils.Converters;

import weka.core.Instance;
import br.com.pontov.frame.Autocrop;
import br.com.pontov.frame.Carac;
import br.com.pontov.frame.Caractest;
import br.com.pontov.frame.Crop;
import br.com.pontov.frame.Features;
import br.com.pontov.frame.Histogram;
import br.com.pontov.frame.MyThreadc;
import br.com.pontov.frame.MyThreadctest;
import br.com.pontov.frame.MyThreadf;
import br.com.pontov.frame.MyThreadftest;

import com.atul.JavaOpenCV.Imshow;  // Adicionar a biblioteca Imshow

import java.nio.file.*;

public class main extends JPanel {
	
   
    private static final int CV_LOAD_IMAGE_GRAYSCALE = 0;
	private static final boolean DEBUG_GRAPHICS_LOADED = false;
	private static final Object[] MatOfPoint2f = null;
	public static boolean control=false;
	public static Carac[] arr= new Carac[200];
	public static Caractest[] arrtest = new Caractest[200];
	

	@SuppressWarnings("unchecked")
	public static <PVector, Contour> void main(String[] args) throws IOException, AWTException {
        System.loadLibrary("opencv_java249");
  
        //CRIAR UM FOR PARA PREENCHER TODOS OS ARR[X], VERIFICAR A EXISTENCIA ANTES
        
        int code=1;
        int codetest=1;
        String dir;
        int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
          
        //System.out.println("Yes button clicked");
        File filegi = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt");
	       
		// if file doesnt exists, then create it
		if (!filegi.exists()) {
		
      //READ FILES;
      for (int i=0;i<nfdir+200;i++)
      {
    	  for (int k=0;k<nfdir+10;k++)
    	  {
    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+".png";
      File photo=new File (dir);
    	 if (photo.isFile())
    	 {
    		 arr[code]= new Carac();
    		 arr[code].setName(dir);
    		arr[code].setCode(code); 
    		arr[code].setName2("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"a.png");
    		arr[code].setName3("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"b.png");
    		code++;
    	 }//end bracket if
    	 }//end bracket for k
    	  
      }// end bracket for i
     
      System.out.print(nfdir);
      
      //CREATE THREADS and 	RUN---------------------------------------------------------------------------------------- 
       for (int i=1;i<code;i++)
       {

    	   MyThreadc t1 = new MyThreadc(arr[i]);
    	   MyThreadf t2 = new MyThreadf(arr[i]);

//CRIAR UM FOR PAR A LER TODOS, VARIA O INDICE DO ARR[X] PARA DOIS T       
       t1.start();
        	
        	
        	try{
        		t1.join();
        	}catch(InterruptedException ie){
        		System.out.println("Erro durante o corte");
        	}
       
    
        	t2.start();
        try{
        	
    		t2.join();
    	}catch(InterruptedException ie){
    		System.out.println("Erro ao extrair as caracteristicas");
    	}

       }     
           
     //---------------------------------------------------------------------------------
       File fileg = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info.txt");
       
		// if file doesnt exists, then create it
		if (!fileg.exists()) {
			fileg.createNewFile();
		}
		FileWriter fw = new FileWriter(fileg.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String content = new String();

		content = "@relation info\n@attribute code numeric\n@attribute contour numeric\n@attribute elongation numeric\n@attribute angle numeric\n@attribute c1 numeric\n@attribute c2 numeric\n@attribute c3 numeric\n@attribute c4 numeric\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data";

		for (int i=1;i<code;i++)  //numero de arquivos salvos para treinamento
		{
		content += "\n"+arr[i].getCode()+","+arr[i].getContour()+","+arr[i].getElongation()+","+arr[i].getAngle()+","+arr[i].getC1()+","+arr[i].getC2()+","+arr[i].getC3()+","+arr[i].getC4()+","+arr[i].getType()+"";		
		}
		System.out.print(content);
		bw.write(content);
		bw.close();
		//---------------------------------------------------------------------------------
		
		File fileg2 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info2.txt");
	       
		// if file doesnt exists, then create it
		if (!fileg2.exists()) {
			fileg2.createNewFile();
		}
		FileWriter fw2 = new FileWriter(fileg2.getAbsoluteFile());
		BufferedWriter bw2 = new BufferedWriter(fw2);
		String content2 = new String();
		content2 = "@relation info2\n@attribute histograma numeric\n\n@data";
		for (int i=1;i<code;i++)  //numero de arquivos salvos para treinamento
		{
		content2 += "\n"+arr[i].getType()+"";		
		}
		System.out.print(content2);
		bw2.write(content2);
		bw2.close();
		
		//---------------------------------------------------------------------------------
		File fileg3 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt");
	       
		// if file doesnt exists, then create it
		if (!fileg3.exists()) {
			fileg3.createNewFile();
		}
		FileWriter fw3 = new FileWriter(fileg3.getAbsoluteFile());
		BufferedWriter bw3 = new BufferedWriter(fw3);
		String content3 = new String();
		
		// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
		content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@attribute absdiff real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data";
		//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
		for (int i=1;i<code;i++)  //numero de arquivos salvos para treinamento
		{
		content3 += "\n"+arr[i].getNumbins()+","+arr[i].getPct2pk()+","+arr[i].getBimodalap()+","+arr[i].getType()+"";		
		//content3 += "\n"+arr[i].getNumbins()+","+arr[i].getPct2pk()+","+arr[i].getBimodalap()+"";
		}
		System.out.print(content3);
		bw3.write(content3);
		bw3.close();
       
		
		//---------------------------------------------------------------------------------
		File fileg4 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesc.txt");
	       
		// if file doesnt exists, then create it
		if (!fileg4.exists()) {
			fileg4.createNewFile();
		}
		FileWriter fw4 = new FileWriter(fileg4.getAbsoluteFile());
		BufferedWriter bw4 = new BufferedWriter(fw4);
		String content4 = new String();
		
		// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
		content4 = "Descritores de Fourier - Golden Images (em ordem de aquisição de imagens)";
		//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
		for (int i=1;i<code;i++)  //numero de arquivos salvos para treinamento
		{
		content4 +="\n"+arr[i].getDescri()[0]+","+arr[i].getDescri()[1]+","+arr[i].getDescri()[2]+","+arr[i].getDescri()[3]+","+arr[i].getDescri()[4]+","+arr[i].getDescri()[5]+","+arr[i].getDescri()[6]+","+arr[i].getDescri()[7]+","+arr[i].getDescri()[8]+","+arr[i].getDescri()[9]+","+arr[i].getDescri()[10]+","+arr[i].getDescri()[11]+","+arr[i].getDescri()[12]+","+arr[i].getDescri()[13]+","+arr[i].getDescri()[14]+","+arr[i].getDescri()[15]+","+arr[i].getDescri()[16]+","+arr[i].getDescri()[17]+","+arr[i].getDescri()[18]+","+arr[i].getDescri()[19]+""; 
		}
		System.out.print(content4);
		bw4.write(content4);
		bw4.close();
       
		}// end if filegi exists

		//--------------------------TEST IMAGES
		if (filegi.exists()) {
			//READ FILES;
		      for (int i=0;i<nfdir+200;i++)  //nfdir = numero de arquivos gold
		      {
		    	  for (int k=0;k<nfdir+10;k++)
		    	  {
		    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+".png";
		      File photo=new File (dir);
		    	 if (photo.isFile())
		    	 {
		    		 arrtest[codetest]= new Caractest();
		    		 arrtest[code].setName(dir);
		    		arrtest[code].setCode(code); 
		    		arrtest[code].setName2("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+"a.png");
		    		arrtest[code].setName3("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+"b.png");
		    		code++;
		    	 }//end bracket if
		    	 }//end bracket for k
		    	  
		      }// end bracket for i

		      
		      //CREATE THREADS and 	RUN---------------------------------------------------------------------------------------- 
		       for (int i=1;i<code;i++)
		       {

		    	   MyThreadctest t1t = new MyThreadctest(arrtest[i]);
		    	   MyThreadftest t2t = new MyThreadftest(arrtest[i]);
		       
		     
		        	
		//CRIAR UM FOR PAR A LER TODOS, VARIA O INDICE DO ARR[X] PARA DOIS T       
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
		
		       }     
		       
				
				//---------------------------------------------------------------------------------
				File filet = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/infotest.txt");
			       
				// if file doesnt exists, then create it
				if (!filet.exists()) {
					filet.createNewFile();
				}
				FileWriter fw3 = new FileWriter(filet.getAbsoluteFile());
				BufferedWriter bw3 = new BufferedWriter(fw3);
				String content3 = new String();
				
				// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
				content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@attribute absdiff real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data";
				//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
				for (int i=1;i<code;i++)  //numero de arquivos salvos para treinamento
				{
				content3 += "\n"+arrtest[i].getNumbins()+","+arrtest[i].getPct2pk()+","+arrtest[i].getBimodalap()+","+arrtest[i].getType()+"";		
				//content3 += "\n"+arr[i].getNumbins()+","+arr[i].getPct2pk()+","+arr[i].getBimodalap()+"";
				}
				System.out.print(content3);
				bw3.write(content3);
				bw3.close();
				
				//---------------------------------------------------------------------------------
				File filet2 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesctest.txt");
			       
				// if file doesnt exists, then create it
				if (!filet2.exists()) {
					filet2.createNewFile();
				}
				FileWriter fw2 = new FileWriter(filet2.getAbsoluteFile());
				BufferedWriter bw2 = new BufferedWriter(fw2);
				String content2 = new String();
				
				// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
				content2 = "Descritores de Fourier para teste";
				//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
				for (int i=1;i<code;i++)  //numero de arquivos salvos para treinamento
				{
				content2 += "\n"+arrtest[i].getDescri()[0]+","+arrtest[i].getDescri()[1]+","+arrtest[i].getDescri()[2]+","+arrtest[i].getDescri()[3]+","+arrtest[i].getDescri()[4]+","+arrtest[i].getDescri()[5]+","+arrtest[i].getDescri()[6]+","+arrtest[i].getDescri()[7]+","+arrtest[i].getDescri()[8]+","+arrtest[i].getDescri()[9]+","+arrtest[i].getDescri()[10]+","+arrtest[i].getDescri()[11]+","+arrtest[i].getDescri()[12]+","+arrtest[i].getDescri()[13]+","+arrtest[i].getDescri()[14]+","+arrtest[i].getDescri()[15]+","+arrtest[i].getDescri()[16]+","+arrtest[i].getDescri()[17]+","+arrtest[i].getDescri()[18]+","+arrtest[i].getDescri()[19]+"";		
				}
				System.out.print(content2);
				bw2.write(content2);
				bw2.close();
		}
		
		
		System.out.printf("FIM");
		
	}

	
	
	
}

	