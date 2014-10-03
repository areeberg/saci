package br.com.pontov.frame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
	final static int training_samples = 15;
	final static int test_samples=1;
	final static int attrib = 20;
	final static int classes = 6;
	final static int input = training_samples*attrib;

	
	
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
	static double[][] read_datasettest(String filename, double[][] datatest,  int total_samples) throws IOException
	{

	    //open the file
	    BufferedReader inputfile =  new BufferedReader(new FileReader(filename));
	   
	    String info = "";
	 
	    datatest = new double[1][attrib+1];  // 1 sample + num de atributos (20)
	    String[] vetor = new String[attrib+1];
	    
	    for (int a = 0; a < 1; a++)  //1 = numero de imagens a serem testadas
        { info = inputfile.readLine();
        
        //System.out.print(datas);
        
        vetor = info.split(",");

        for (int p = 0; p < vetor.length; p++) 
        { datatest[a][p] = (double) Double.parseDouble(vetor[p]);}
        }
	    
	    
	 try {
		inputfile.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// System.out.print(datas);   		//DATAS CONTEM TODAS AS INFO DAS IMAGENS
	 return datatest;
	}
	
	
	//--------------------------------------MAIN--------------
	public static void main(String[] args) throws Exception {
		System.loadLibrary("opencv_java249");

		double[][] datas = new double[training_samples][attrib+1];
		datas = read_dataset("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt", datas, training_samples);
		
				//Cria o dataset de input
		String label="goldenimages";
		String[] colunas = new String[20];
		colunas[0]="0";colunas[1]="1";colunas[2]="2";colunas[3]="3";colunas[4]="4";colunas[5]="5";colunas[6]="6";colunas[7]="7";colunas[8]="8";colunas[9]="9";
		colunas[10]="10";colunas[11]="11";colunas[12]="12";colunas[13]="13";colunas[14]="14";colunas[15]="15";colunas[16]="16";colunas[17]="17";colunas[18]="18";colunas[19]="19";
		DataSet dsTraining = new DataSet(20, 1);
		dsTraining.setLabel(label);
		dsTraining.setColumnNames(colunas);
		
		double[][] trainingattrib = new double[training_samples][attrib];
		double[] trainingclass = new double[training_samples];
		double[] auxtraiat=new double[attrib];
		double[][] auxtraicla = new double[15][6];
		double desiredout=0.0;
		int class1=0;
		
		
		for (int r=0;r<training_samples;r++)
		{	
		for (int c=0;c < attrib;c++)
		{
			//Copia as informacoes
			trainingattrib[r][c]=datas[r][c];
			trainingattrib[r][c]=trainingattrib[r][c]/10000;
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

		
		dsTraining.addRow(new DataSetRow(trainingattrib[r],new double[]{desiredout}));
		
		}//end 1for

		System.out.print(dsTraining);
		

		/*
		String trainingSetFileName = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt";
        int inputsCount = 20;
        int outputsCount = 6;

        System.out.println("Running Sample");
        System.out.println("Using training set " + trainingSetFileName);
        
        
     // create training set
        DataSet trainingSet = null;
        try {
            trainingSet = TrainingSetImport.importFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error reading file or bad number format!");
        }
        
		//System.out.print(trainingSet);
        */
        
		 // create multi layer perceptron
        System.out.println("Creating neural network");
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID ,20, 30, 1);  //20 entradas, 10 intermediarias e 6 saidas
       
  /*      
        SupervisedLearning learningRule = (SupervisedLearning)neuralNet.getLearningRule();
        learningRule.setMaxError(0.001);
        learningRule.setMaxIterations(10000); // make sure we can end.
        learningRule.addObserver(this); // user observer to tell when individual networks are done and launch new networks.
        this._mlpVector.add(neuralNet);
        neuralNet.learnInNewThread(trainingSet);
   */
        
        // set learning parametars
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.7);
        learningRule.setMomentum(0.7);
        learningRule.setMaxIterations(3000000);
        learningRule.setMaxError(0.00001);
       
        
        // learn the training set
        System.out.println("Training neural network...");
        neuralNet.learn(dsTraining);   //trainingSet
        System.out.println("Done!");
        
        
        double[][] datatest = new double[1][attrib+1];
    	datatest = read_datasettest("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO2.txt", datatest, 1);
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
    			

    			
    			for (int r=0;r<test_samples;r++)
    			{	
    			for (int c=0;c < attrib;c++)
    			{
    				//Copia as informacoes
    				trainingattribt[r][c]=datatest[r][c];
    				trainingattribt[r][c]=trainingattribt[r][c]/10000;  //normalizar melhor que isso!!!
    				auxtraiatt[c]=trainingattribt[r][c];
    			}
    			trainingclasst[r]= datatest[r][20];	
    			auxtraiclat[0]=trainingclasst[r];
    			
    			dsTest.addRow(new DataSetRow(trainingattribt[r],new double[] {0.4}));
    			
    			
    			}//end 1for

    			System.out.print(dsTest);
    			System.out.println();
    
        // test perceptron
        System.out.println("Testing neural network");
        testClassification(neuralNet, dsTest);
                
	}
	
	public static void testClassification(NeuralNetwork nnet, DataSet dset) {

        for (DataSetRow testElement : dset.getRows()) {

            nnet.setInput(testElement.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            System.out.print("Input: " + Arrays.toString(testElement.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
            System.out.println("Time stamp Final:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
        }

    }
	
}
