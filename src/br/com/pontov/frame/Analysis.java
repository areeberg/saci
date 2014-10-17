package br.com.pontov.frame;

public class Analysis {

	
	public void checkcomponent (double answer,double desout){
		 float class1=0;
	       float Capacitor=(float) 0.0;
	       float ResistorSmall=(float) 0.2;
	       float ResistorBig=(float) 0.4;
	       float SchmittTrigger=(float) 0.6;
	       float Transistor=(float) 0.8;
	       float PowerTransistor=(float) 1.0;
	       double finalanswer=0;
	       finalanswer=answer;
	       
	       
	       if ((answer <= (desout+0.05)) & (answer >= (desout-0.05)) )
	       {

	       if (answer <= (Capacitor+0.05))
	    	   System.out.println("Capacitor");finalanswer=answer;
	    	   
	       if ((answer <= (ResistorSmall+0.05)) & (answer >= (ResistorSmall-0.05))  )
	    	   System.out.println("Resistor Small");finalanswer=answer;
	    	 
	       if ((answer <= (ResistorBig+0.05)) & (answer >= (ResistorBig-0.05))  )
	    	   System.out.println("Resistor Big");finalanswer=answer;

	       if ((answer <= (SchmittTrigger+0.05)) & (answer >= (SchmittTrigger-0.05))  )
	    	   System.out.println("Schmitt Trigger");finalanswer=answer;
	       
	       if ((answer <= (Transistor+0.05)) & (answer >= (Transistor-0.05))  )
	    	   System.out.println("Transistor");finalanswer=answer;
	
	       if ( answer >= (PowerTransistor-0.05)  )
	    	   System.out.println("PowerTransistor");finalanswer=answer;
		
	       }//end if desout
	       
	       
	       
	}//end checkcomponent
	
	
	
	
}
