import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.com.pontov.frame.Carac;
import br.com.pontov.frame.Caractest;
import br.com.pontov.frame.MyThreadc;
import br.com.pontov.frame.MyThreadf;


public class goldenimages {

	
	  private static final int CV_LOAD_IMAGE_GRAYSCALE = 0;
		private static final boolean DEBUG_GRAPHICS_LOADED = false;
		private static final Object[] MatOfPoint2f = null;
		public static boolean control=false;
		public static Carac[] arr= new Carac[200];
		public static Caractest[] arrtest = new Caractest[200];
		
		public static void start () throws IOException{
			System.loadLibrary("opencv_java249");
			int code=1;
	        int codetest=1;
	        String dir;
	        int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
	          

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
	       
		

		}
	
}
