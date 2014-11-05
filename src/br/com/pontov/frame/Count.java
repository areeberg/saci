package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Count {

	static int countimagesfromfile(String filename) throws IOException{
		
	    BufferedReader inputfile =  new BufferedReader(new FileReader(filename));
		 LineNumberReader reader  = new LineNumberReader(new FileReader(filename));

		 String lin="";
		 int numlinha=0;
		 while((lin=reader.readLine())!=null){}
		 numlinha=reader.getLineNumber()-1; //-1 devido ao cabecalho
		
		 
		 return numlinha;
	}
	
	static int counttestimages(){
		int nfdir;
		nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages").listFiles().length;   //quantidade de arquivos gold
		int code=0;
		String dir="";
		 for (int i=0;i<nfdir+200;i++)
	      {
	    	  for (int k=0;k<nfdir+10;k++)
	    	  {
	    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/TestImages/Img"+i+"Defect"+k+".png";
	    		  File photo=new File (dir);
			    	 if (photo.isFile())
			    	 {
			    		code++; 
			    	 }
	    	  }
	      }
		 
		
		return code;
		
	}
	
	static int countgoldimages(){
		int nfdir;
		nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;   //quantidade de arquivos gold
		int code=0;
		String dir="";
		 for (int i=0;i<nfdir+200;i++)
	      {
	    	  for (int k=0;k<nfdir+10;k++)
	    	  {
	    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+".png";
	    		  File photo=new File (dir);
			    	 if (photo.isFile())
			    	 {
			    		code++; 
			    	 }
	    	  }
	      }
		
		return code;
		
	}
	
	
	static int countbackground(){
		int nfdir;
		nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/Background").listFiles().length;   //quantidade de arquivos gold
		int code=0;
		String dir="";
		 for (int i=0;i<nfdir+200;i++)
	      {
	    	  for (int k=0;k<nfdir+10;k++)
	    	  {
	    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/Background/Img"+i+"Defect"+k+".png";
	    		  File photo=new File (dir);
			    	 if (photo.isFile())
			    	 {
			    		code++; 
			    	 }
	    	  }
	      }
		
		return code;
		
	}
}
