package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;

public class Analysis {

	
	
	public String checkcomponent (int code,double answer,double desout){
	       String finalans="";
	    		  finalans=componenttype(answer);
	       return finalans;      
	}//end checkcomponent
	
public String componenttype(double answer){
	
	  float Capacitor=(float) 0.0;
      float ResistorSmall=(float) 0.2;
      float ResistorBig=(float) 0.4;
      float SchmittTrigger=(float) 0.6;
      float Transistor=(float) 0.8;
      float PowerTransistor=(float) 1.0;
      String resp="";
	
	  if (answer <= (Capacitor+0.05))
	  {  System.out.println("-> Type: Capacitor"); resp="Capacitor";}
   	   
      if ((answer <= (ResistorSmall+0.05)) & (answer >= (ResistorSmall-0.05))  )
   	   {System.out.println("-> Type: Resistor Small");resp="Resistor Small";}
   	 
      if ((answer <= (ResistorBig+0.05)) & (answer >= (ResistorBig-0.05))  )
      { System.out.println("-> Type: Resistor Big");resp="Resistor Big";}

      if ((answer <= (SchmittTrigger+0.05)) & (answer >= (SchmittTrigger-0.05))  )
      {   System.out.println("-> Type: Schmitt Trigger");resp="Schmitt Trigger";}
      
      if ((answer <= (Transistor+0.05)) & (answer >= (Transistor-0.05))  )
      {  System.out.println("-> Type: Transistor");resp="Transistor";}

      if ( answer >= (PowerTransistor-0.05)  )
      {  System.out.println("-> Type: PowerTransistor");resp="PowerTransistor";}
      
      
      return resp;
	
	}
	
	public double[] getinfo(String url,int code) throws IOException{
		//read the angle from file info2
				BufferedReader inputangle =  new BufferedReader(new FileReader(url));
				 LineNumberReader readangle  = new LineNumberReader(new FileReader(url));
				 
				 String anglestring="";
				 double[] datas = new double[3];
				 anglestring = inputangle.readLine();
				 for (int a = 0; a < code; a++)
				 {
					 anglestring = inputangle.readLine();
					 
					 String[] vetor = new String[3];
					 vetor = anglestring.split(",");     
				        for (int p = 0; p < (vetor.length-1); p++)   //-1 devido ao ultimo ser uma string (tipo)
				        { datas[p] =  Double.parseDouble(vetor[p]);} 
				 }
				 return datas;
			
	}
	
	
	public float checkrotation(int code) throws IOException{
		
		
		 double[] infofromtest=new double[3];
		
		 infofromtest=getinfo("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/TestImages/fileinfo.txt",code);
	
		float angleteste=(float) infofromtest[0];

		return angleteste;
	}
	
	public double checkshift(int code) throws IOException{
		 double[] infofromgold=new double[3];
		 double[] infofromtest=new double[3];
		 infofromgold=getinfo("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info2.txt",code);
		 infofromtest=getinfo("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/TestImages/fileinfo.txt",code);
		
		double distance=0;
		distance= Math.sqrt(Math.pow((infofromgold[1]-infofromtest[1]), 2)+Math.pow((infofromgold[2]-infofromtest[2]), 2));
		
		if (distance > 150)
			System.out.println("-> Image shifted: "+ distance);
		else
			System.out.println("-> No shifiting");
		
		System.out.println("-> Distance between centers: "+distance);
		return distance;
	}
	
	public double[] checkfullstatus(int code,float angleteste,double distance,double answer, double desout, String compo) throws IOException{
		double[] check= new double[4];
		
		double[] infofromgold=new double[2];
		
		 infofromgold=getinfo("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info2.txt",code);
		 float angle = (float)infofromgold[0];
		 
		 
		 if(compo=="Component")
		 {
			 check[0]=1.0;
			 if ((answer >= (desout-0.05)) & (answer <= (desout+0.05)))
				 check[1]=1.0;
			 else
				 check[1]=0.0;

			 if ((angleteste>=(angle-10)) & angleteste<=(angle+10))
				 check[2]=1.0;
			 else
				 check[2]=0.0;

			 if (distance<200)
				 check[3]=1.0;
			 else
				 check[3]=0.0;

		 }
		 else
		 {
			 check[0]=0.0;
			 check[1]=0.0;
			 check[2]=0.0;
			 check[3]=0.0;
		 }
		
		  
		 return check;
			
		
	}
	
	public void report(int code,double desout,String finalanswer, float angletest,double distance,double[] check) throws IOException{
		boolean head=false;
		File testreport = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/AgentInfo/testreport.txt");
		PrintWriter arqdesaida = null;
		arqdesaida = new PrintWriter(new FileWriter(testreport, true));
		
		String des = componenttype(desout);
		double[] infofromgold=new double[3];
		infofromgold=getinfo("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info2.txt",code);
		float angle = (float)infofromgold[0];

		String body="";	
		body="\n"+code+","+des+","+finalanswer+","+angle+","+angletest+","+distance+","+check[0]+","+check[1]+","+check[2]+"";
		
		
		arqdesaida.write(body);
		arqdesaida.close();	

		
	}
	
	
	
}
