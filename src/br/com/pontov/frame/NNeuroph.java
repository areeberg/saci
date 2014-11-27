package br.com.pontov.frame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import org.neuroph.contrib.samples.stockmarket.TrainingData;
import org.neuroph.core.*;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;

import weka.gui.beans.TrainingSetEvent;


public class NNeuroph extends SigmoidDeltaRule {

	
	//--------------------------------
	final static int training_samples=5;
	final static int test_samples=1;
	final static int attrib = 20;
	final static int classes = 6;
	final static int input = training_samples*attrib;
	final static MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID ,20, 30, 1);  //20 entradas, 30 intermediarias e 6 saidas
	static double big=0;
	static double small=10;
	public  String texto="";
	final static double [] tclass=new double[training_samples];
	double[] check = new double[4];
	DataSet dsTraining = new DataSet(20, 1);
	
	
	//--------------------------------   /**



	//--------------------------------
	
	static double[][] read_dataset(String filename, double[][] datas,  int total_samples) throws IOException
	{

	    //open the file
	    BufferedReader inputfile =  new BufferedReader(new FileReader(filename));
	   
	    String info = "";
	 
	    datas = new double[training_samples][attrib+1];  //+1 pois contem a classe
	    String[] vetor = new String[attrib+1];
	    for (int a = 0; a < training_samples; a++)
        { info = inputfile.readLine();
        
        //System.out.print(datas);
        
        vetor = info.split(",");
      
  
        for (int p = 0; p < vetor.length; p++) 
        {datas[a][p] = (double) Double.parseDouble(vetor[p]);}

        }

	 try {
		inputfile.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// System.out.print(datas);   		//DATAS CONTEM TODAS AS INFO DAS IMAGENS
	 return datas;
	}
	//--------------------------------------------------------------------------------------------------------------
	static double[][] read_datasettest(String filename, double[][] datatest,  int control) throws IOException
	{

	    //open the file
	    BufferedReader inputfile =  new BufferedReader(new FileReader(filename));
	   
	    String info = "";
	 
	    datatest = new double[30][attrib+1];  // 1 sample + num de atributos (20)
	    String[] vetor = new String[attrib+1];
	    
	    for (int a = 0; a < control; a++)  
        { info = inputfile.readLine();}
        
        vetor = info.split(",");

        for (int p = 0; p < vetor.length; p++) 
        { datatest[0][p] = (double) Double.parseDouble(vetor[p]);}
        
	    
	    
	 try {
		inputfile.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// System.out.print(datas);   		//DATAS CONTEM TODAS AS INFO DAS IMAGENS
	 return datatest;
	}
	
	
	public void train() throws IOException{
		System.loadLibrary("opencv_java249");
		
		
		double[][] datas = new double[training_samples][attrib+1];
		datas = read_dataset("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt", datas, training_samples);
		
				//Cria o dataset de input
		String label="goldenimages";
		String[] colunas = new String[20];
		colunas[0]="0";colunas[1]="1";colunas[2]="2";colunas[3]="3";colunas[4]="4";colunas[5]="5";colunas[6]="6";colunas[7]="7";colunas[8]="8";colunas[9]="9";
		colunas[10]="10";colunas[11]="11";colunas[12]="12";colunas[13]="13";colunas[14]="14";colunas[15]="15";colunas[16]="16";colunas[17]="17";colunas[18]="18";colunas[19]="19";
		//DataSet dsTraining = new DataSet(20, 1);
		dsTraining.setLabel(label);
		dsTraining.setColumnNames(colunas);
		
		double[][] trainingattrib = new double[training_samples][attrib];
		double[] trainingclass = new double[training_samples];
		double[] auxtraiat=new double[attrib];
		double[][] auxtraicla = new double[15][6];
		double desiredout=0.0;
		int class1=0;
		
		
		//find biggest and smaller value, adicionar margem de seguranca no biggest value
		

		double aux=0;
		double aux2=110;
		for (int r=0;r<training_samples;r++)
		{	
		for (int c=0;c < attrib;c++)
		{
			aux = datas[r][c];
			aux2=datas[r][c];
			if (aux>big)	big=aux;
			
			if (aux2<small) small=aux2;
			
		}}
		
		big=big+2000;
		
		//System.out.print(big);
		//System.out.print(small);
		//System.out.println();
		
		
		for (int r=0;r<training_samples;r++)
		{	
		for (int c=0;c < attrib;c++)
		{
			//Copia as informacoes
			trainingattrib[r][c]=datas[r][c];
			trainingattrib[r][c]=(trainingattrib[r][c]-small)/(big-small);
			auxtraiat[c]=trainingattrib[r][c];
		}
		trainingclass[r]= datas[r][20];	//valor desejado de saida (0-5)
		class1 = (int)trainingclass[r];
		
		
		switch((int) class1)
		{
		case 0:desiredout=0.0;break;
		case 1:desiredout=0.2;break;
		case 2:desiredout=0.4;break;
		case 3:desiredout=0.6;break;	
		case 4:desiredout=0.8;break;
		case 5:desiredout=1;break;
		}
		 //auxtraicla[r]= desiredout;
		
		tclass[r]=desiredout;
		dsTraining.addRow(new DataSetRow(trainingattrib[r],new double[]{desiredout}));
		
		}//end 1for
		 // create multi layer perceptron
        //System.out.println("Creating neural network");
       // MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID ,20, 30, 1);  //20 entradas, 10 intermediarias e 6 saidas
       texto="Creating neural network";

        
        // set learning parametars
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.4);
        learningRule.setMomentum(0.9);
        learningRule.setMaxIterations(50000);
        learningRule.setMaxError(0.00001);//0.000001
       
        
        // learn the training set
       // System.out.println("Training neural network...");
        texto='\n'+"Training neural Network";
        //neuralNet.learn(dsTraining);   //trainingSet
        neuralNet.learnInNewThread(dsTraining); 

	}
	
	
	public void save() {
		System.loadLibrary("opencv_java249");
        dsTraining.save("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trained.nnet");

	}
	
	public void load() {
		System.loadLibrary("opencv_java249");
        dsTraining.load("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trained.nnet");

	}
	
	public void classify(int control) throws IOException{
		System.loadLibrary("opencv_java249");
		  double[][] datatest = new double[1][attrib+1];
	    	datatest = read_datasettest("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/TestImages/trainingNNO2.txt", datatest, control);
	    	//Cria o dataset de test
	    			String labeltest="goldenimages";
	    			String[] colunast = new String[20];
	    			colunast[0]="0";colunast[1]="1";colunast[2]="2";colunast[3]="3";colunast[4]="4";colunast[5]="5";colunast[6]="6";colunast[7]="7";colunast[8]="8";colunast[9]="9";
	    			colunast[10]="10";colunast[11]="11";colunast[12]="12";colunast[13]="13";colunast[14]="14";colunast[15]="15";colunast[16]="16";colunast[17]="17";colunast[18]="18";colunast[19]="19";
	    			DataSet dsTest = new DataSet(20, 1);
	    			dsTest.setLabel(labeltest);
	    			dsTest.setColumnNames(colunast);
	    			
	    			double[][] trainingattribt = new double[training_samples][attrib];
	    			double[] trainingclasst = new double[training_samples];
	    			double[] auxtraiatt=new double[attrib];
	    			double[] auxtraiclat = new double[1];
	    			double desout=0.0;
	    			
	    			double des=datatest[0][20]; //tem que ser o valor desejado
	    	    	
	    	
	    			for (int r=0;r<test_samples;r++)
	    			{	
	    			for (int c=0;c < attrib;c++)
	    			{
	    				//Copia as informacoes
	    				trainingattribt[r][c]=datatest[r][c];
	    				trainingattribt[r][c]=(trainingattribt[r][c]-small)/(big-small);  
	    				auxtraiatt[c]=trainingattribt[r][c];
	    			}
	    			trainingclasst[r]= datatest[r][20];	
	    			auxtraiclat[0]=trainingclasst[r];	
	    			
	    			dsTest.addRow(new DataSetRow(trainingattribt[r],new double[] {0.4}));
	    			desout=tclass[control-1];
	    					dsTest.addRow(new DataSetRow(trainingattribt[r],new double[] {desout}));		
	    					
	    			}//end 1for

	    			
	    
	        // test perceptron
	        //System.out.println("Testing neural network");
	        
	       double answer=0; 
	       double desiredanswer=0;
	       
	      // desiredanswer=dsTest
	        answer = testClassification(neuralNet, dsTest);
	        	
	        String finalans = new Analysis().componenttype(answer);
	        		//new Analysis().checkcomponent(control,answer, desout);
	      float angle =  new Analysis().checkrotation(control);
	       double[] distance = new Analysis().checkshift(control);
	        
	        String compo="";
			try {
				compo=new Knear().KNN(control);  //control -1
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        check = new Analysis().checkfullstatus(control, angle, distance, answer, desout,compo);
	        
	        System.out.println("MSG;"+control+";"+check[0]+";"+check[1]+";"+check[2]+";"+check[3]+"#");
	        ClientUDP.board();
			 ClientUDP.send(control,check);
			 
			 if (control==5)
			 {
				 ClientUDP.finish();
			 }
			 
	        new Analysis().report(control, desout, finalans, angle, distance, check);

	 
	}
	
	public double testClassification(NeuralNetwork nnet, DataSet dset) {

        for (DataSetRow testElement : dset.getRows()) {

            nnet.setInput(testElement.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            
           // System.out.print("Input: " + Arrays.toString(testElement.getInput()));
            System.out.println("Time stamp Final:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
           
           double answer=networkOutput[0];
          
           return answer;
        }
		return big;
		
    }
	
	
	
}
