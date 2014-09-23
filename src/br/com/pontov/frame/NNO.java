package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.atul.JavaOpenCV.Imshow;

public class NNO {
	private static Carac arr;
	public NNO(Carac arr){
	this.arr=arr;
	
	}

	static void readFile(int samplesPerClass,String outputfile ) throws IOException
	{
		File file = new File(outputfile);
		PrintWriter outputfile2 = null;

		try {
			outputfile2 = new PrintWriter(new FileWriter(file, true));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    //fstream file(outputfile,ios::out);
	    
		String dir;
		 int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
		 String info = "";
		 int tipo = 0;
		 int endLine=5;
		 int numlinha=0;
		 String lin="";
		 
		 BufferedReader inputfile =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt"));
		 LineNumberReader reader  = new LineNumberReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt"));
		 
		 while((lin=reader.readLine())!=null){}
		 numlinha=reader.getLineNumber();
		 
		 for (int a = 0; a <= endLine; a++)
         { info = inputfile.readLine(); }
		 
	    for (int i=0;i<nfdir+200;i++)
	      {
	    	  for (int k=0;k<nfdir;k++)
	    	  {
		
		dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"b.png";
		
		 File photo=new File (dir);
    	 if (photo.isFile())
    	 {
    			Mat img = Highgui.imread(dir);
	            //declaring array to hold the pixel values in the memory before it written into file
	            int pixelarray[]= new int [900];
	            //-----------------
	            int size = (int) img.total();
	    	    byte[] destinationBuffer = new byte[size];
	    	    //Imgproc.threshold(img, img, 250, 255, Imgproc.THRESH_BINARY );  //250     
	    	    img.get(0, 0, destinationBuffer);
	    	    for(int y=0;y<size;y++)
		        {
		        	pixelarray[y] = (destinationBuffer[y] > 90) ? 1 : 0;
		        }
	            //-----------------

	            //writing pixel data to file
	            
	            for(int d=0;d<900;d++){
	            	//escrever tambem o angulo e tamanho do contorno
	            	outputfile2.write(pixelarray[d]+",");
	            }
	            
	            //writing the label to file
	          //{ "0 Capacitor", "1 ResistorSmall","2 ResistorBig", "3 Schmitt trigger","4 Transistor","5 PowerTransistor" }

	            if (endLine < (numlinha-1))
	            {

	                for (int a = endLine; a == endLine; a++) {
	            
	                    info = inputfile.readLine();
	                  
	                    //search in this line for the correct word (type)
	                    
	                    if (info.contains("Capacitor")){tipo = 0;}
	                    if (info.contains("ResistorSmall")){tipo = 1;}
	                    if (info.contains("ResistorBig")){tipo = 2;}
	                    if (info.contains("Schmitt trigger")){tipo = 3;}
	                    if (info.contains("Transistor")){tipo = 4;}
	                    if (info.contains("PowerTransistor")){tipo = 5;}
	                    
	                    System.out.println(tipo);
	                    
	                }

	            //if (arr.getType() == "Capacitor")lkjk
	            outputfile2.write(tipo+"\n");
	            endLine++;  
    	 }
	            
    	 }//end if
    	 
	    	  }
	    	  
	      }

	    outputfile2.close();
	    reader.close();
		
	}
	
	
	
	//------------------READ THE TEST IMAGE
	static void readFiletest(int samplesPerClass,String outputfile ) throws IOException
	{
		File file = new File(outputfile);
		PrintWriter outputfile2 = null;

		try {
			outputfile2 = new PrintWriter(new FileWriter(file, true));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    //fstream file(outputfile,ios::out);
	    
		String dir;
		 int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
		 String info = "";
		 int tipo = 0;
		 int endLine=5;
		 int numlinha=0;
		 String lin="";
		 
		 BufferedReader inputfile =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/infotest.txt"));
		 LineNumberReader reader  = new LineNumberReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/infotest.txt"));
		 
		 while((lin=reader.readLine())!=null){}
		 numlinha=reader.getLineNumber();
		 
		 for (int a = 0; a <= endLine; a++)
         { info = inputfile.readLine(); }
		 
	    for (int i=0;i<nfdir+200;i++)
	      {
	    	  for (int k=0;k<nfdir;k++)
	    	  {
		
		dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+"b.png";
		
		 File photo=new File (dir);
    	 if (photo.isFile())
    	 {
    			Mat img = Highgui.imread(dir);
	            //declaring array to hold the pixel values in the memory before it written into file
	            int pixelarray[]= new int [900];
	            //-----------------
	            int size = (int) img.total();
	    	    byte[] destinationBuffer = new byte[size];
	    	    //Imgproc.threshold(img, img, 250, 255, Imgproc.THRESH_BINARY );  //250     
	    	    img.get(0, 0, destinationBuffer);
	    	    for(int y=0;y<size;y++)
		        {
		        	pixelarray[y] = (destinationBuffer[y] > 90) ? 1 : 0;
		        }
	            //-----------------

	            //writing pixel data to file
	            
	            for(int d=0;d<900;d++){
	            	//escrever tambem o angulo e tamanho do contorno
	            	outputfile2.write(pixelarray[d]+",");
	            }
	            
	            //writing the label to file
	          //{ "1 Capacitor", "2 ResistorSmall","3 ResistorBig", "4 Schmitt trigger","5 Transistor","6 PowerTransistor" }

	            if (endLine < (numlinha-1))
	            {

	                for (int a = endLine; a == endLine; a++) {
	            
	                    info = inputfile.readLine();
	                  
	                    //search in this line for the correct word (type)
	                    
	                    if (info.contains("Capacitor")){tipo = 0;}
	                    if (info.contains("ResistorSmall")){tipo = 1;}
	                    if (info.contains("ResistorBig")){tipo = 2;}
	                    if (info.contains("Schmitt trigger")){tipo = 3;}
	                    if (info.contains("Transistor")){tipo = 4;}
	                    if (info.contains("PowerTransistor")){tipo = 5;}
	                    
	                    System.out.println(tipo);
	                    
	                }

	            //if (arr.getType() == "Capacitor")lkjk
	            outputfile2.write(tipo+"\n");
	            endLine++;  
    	 }
	            
    	 }//end if
    	 
	    	  }
	    	  
	      }

	    outputfile2.close();
	    reader.close();
		
	}
	//-----------------------------------------------------------
	public static void main(String[] args) throws Exception {
		System.loadLibrary("opencv_java249");
		Mat train_data;
		Mat train_labels;
		Mat images = new Mat();
		
		String dir;
		 int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
		 
	    for (int i=0;i<nfdir+200;i++)
	      {
	    	  for (int k=0;k<nfdir;k++)
	    	  {
		
		dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"b.png";
		
		 File photo=new File (dir);
    	 if (photo.isFile())
    	 {
    			Mat target = Highgui.imread(dir,CvType.CV_8UC1);
    		    images.push_back(target);
    		    
    	 }
	    	  }
	      }
	    //System.out.print(images.size());

	    readFile(15,"/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt"); // 15 = numeros de arquivos na pasta que sao gold e validos
	    readFiletest(15,"/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO2.txt"); // 15 = numeros de arquivos na pasta que sao gold e validos
		
	}
	
	

	
	
}

