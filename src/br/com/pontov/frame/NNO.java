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

	public static void readFile(int samplesPerClass,String outputfile ) throws IOException
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
	
		 BufferedReader inputfiledesc =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesc.txt"));
		 LineNumberReader readerdesc  = new LineNumberReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesc.txt"));
	
		 //-------------READ THE FOURIER DESCRIPTORS
		 
		 String lindesc="";
		 int numlinhadesc=0;
		 int endlinedesc=1;
		 String infdesc="";
		 double[] datas = new double[21];
		 
		 while((lindesc=readerdesc.readLine())!=null){}
		 numlinhadesc=readerdesc.getLineNumber();
		 while((lin=reader.readLine())!=null){}
		 numlinha=reader.getLineNumber();

		 
		 for (int a = 0; a <= 0; a++)
         { infdesc = inputfiledesc.readLine(); }
		 for (int a = 0; a <= endLine; a++)
         { info = inputfile.readLine(); }
		 
		 
		 for (int a=0;a<numlinhadesc-1;a++)
		 {
			 infdesc = inputfiledesc.readLine();
			 String[] vetor = new String[20];
			 vetor = infdesc.split(",");     
		        for (int p = 0; p < vetor.length; p++) 
		        { datas[p] =  Double.parseDouble(vetor[p]);} 
		  
		      //  System.out.print(datas);
		        
		        for(int d=0;d<20;d++){
	            	//escrever tambem o angulo e tamanho do contorno
	            	outputfile2.write(datas[d]+",");
	            }
		        
		//writing the label to file
         //{ "0 Capacitor", "1 ResistorSmall","2 ResistorBig", "3 Schmitt trigger","4 Transistor","5 PowerTransistor" }

           if (endLine < (numlinha-1))
           {

               for (int i = endLine; i == endLine; i++) {
           
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

           
           outputfile2.write(tipo+"\n");
           
	 }
           endLine++;  
           
           System.out.print(datas);
           System.out.println(tipo);
		 } //end for linhas
           
		 outputfile2.close();
		  reader.close();
		  readerdesc.close();
	}
		 
		    

	
	//------------------READ THE TEST IMAGE
	public static void readFiletest(int samplesPerClass,String outputfile ) throws IOException
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
	
		 BufferedReader inputfiledesc =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesctest.txt"));
		 LineNumberReader readerdesc  = new LineNumberReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesctest.txt"));
	
		 //-------------READ THE FOURIER DESCRIPTORS
		 
		 String lindesc="";
		 int numlinhadesc=0;
		 int endlinedesc=1;
		 String infdesc="";
		 double[] datas = new double[21];
		 
		 while((lindesc=readerdesc.readLine())!=null){}
		 numlinhadesc=readerdesc.getLineNumber();
		 while((lin=reader.readLine())!=null){}
		 numlinha=reader.getLineNumber();

		 
		 for (int a = 0; a <= 0; a++)
        { infdesc = inputfiledesc.readLine(); }
		 for (int a = 0; a <= endLine; a++)
        { info = inputfile.readLine(); }
		 
		 
		 for (int a=0;a<numlinhadesc-1;a++)
		 {
			 infdesc = inputfiledesc.readLine();
			 String[] vetor = new String[20];
			 vetor = infdesc.split(",");     
		        for (int p = 0; p < vetor.length; p++) 
		        { datas[p] =  Double.parseDouble(vetor[p]);} 
		  
		      //  System.out.print(datas);
		        
		        for(int d=0;d<20;d++){
	            	//escrever tambem o angulo e tamanho do contorno
	            	outputfile2.write(datas[d]+",");
	            }
		        
		//writing the label to file
        //{ "0 Capacitor", "1 ResistorSmall","2 ResistorBig", "3 Schmitt trigger","4 Transistor","5 PowerTransistor" }

          if (endLine < (numlinha-1))
          {

              for (int i = endLine; i == endLine; i++) {
          
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

          
          outputfile2.write(tipo+"\n");
          
	 }
          endLine++;  
          
          System.out.print(datas);
          System.out.println(tipo);
		 } //end for linhas
          
		 outputfile2.close();
		  reader.close();
		  readerdesc.close();
		
	}
	
	
}

