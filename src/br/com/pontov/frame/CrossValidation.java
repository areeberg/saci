package br.com.pontov.frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Random;

import org.neuroph.core.data.DataSet;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Utils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
/**
 * Performs multiple runs of cross-validation.
 *
 * Command-line parameters:
 * <ul>
 *    <li>-t filename - the dataset to use</li>
 *    <li>-x int - the number of folds to use</li>
 *    <li>-r int - the number of runs to perform</li>
 *    <li>-c int - the class index, "first" and "last" are accepted as well;
 *    "last" is used by default</li>
 *    <li>-W classifier - classname and options, enclosed by double quotes; 
 *    the classifier to cross-validate</li>
 * </ul>
 *
 * Example command-line:
 * <pre>
 * java CrossValidationMultipleRuns -t labor.arff -c last -x 10 -r 10 -W "weka.classifiers.trees.J48 -C 0.25"
 * </pre>
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */

public class CrossValidation {
	final static int training_samples=16;
	final static int test_samples=1;
	final static int attrib = 20;
	
	
	public static double[] findbigandsmall() throws IOException{
		double[][] datas = new double[training_samples][attrib+1];
		datas = NNeuroph.read_dataset("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt", datas, training_samples);
		double big=0;
		double small=10;
		
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
		
		double []bas=new double[2];
		bas[0]=big;
		bas[1]=small;
		return bas;
		
	}
	
	public static void generatedata(double big, double small) throws IOException{
		
		File file5 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross5.arff");
		File file10 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross10.arff");
		File file15 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross15.arff");
		File file20 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross20.arff");
		
		PrintWriter outputfilecross5 = null;
		PrintWriter outputfilecross10 = null;
		PrintWriter outputfilecross15 = null;
		PrintWriter outputfilecross20 = null;
		
		outputfilecross5 = new PrintWriter(new FileWriter(file5, true));
		outputfilecross10 = new PrintWriter(new FileWriter(file10, true));
		outputfilecross15 = new PrintWriter(new FileWriter(file15, true));
		outputfilecross20 = new PrintWriter(new FileWriter(file20, true));

		
		String dir;
		 int nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
		 String info = "";
		 String tipo = "";
		 int endLine=5;
		 int numlinha=0;
		 String lin="";
		 boolean head=false;
		 
		 BufferedReader inputfile =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt"));
		 LineNumberReader reader  = new LineNumberReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt"));
	
		 BufferedReader inputfiledesc =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesc.txt"));
		 LineNumberReader readerdesc  = new LineNumberReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesc.txt"));
	
		 
		 //-------------WRITE DE HEADER 
		 if(head==false)
			{
				String headercross5="";
				String headercross10="";
				String headercross15="";
				String headercross20="";
				
				headercross5="@relation CrossValidation5\n@attribute FD1 real\n@attribute FD2 real\n@attribute FD3 real\n@attribute FD4 real\n@attribute FD5 real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data\n";
				headercross10="@relation CrossValidation5\n@attribute FD1 real\n@attribute FD2 real\n@attribute FD3 real\n@attribute FD4 real\n@attribute FD5 real\n@attribute FD6 real\n@attribute FD7 real\n@attribute FD8 real\n@attribute FD9 real\n@attribute FD10 real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data\n";
				headercross15="@relation CrossValidation5\n@attribute FD1 real\n@attribute FD2 real\n@attribute FD3 real\n@attribute FD4 real\n@attribute FD5 real\n@attribute FD6 real\n@attribute FD7 real\n@attribute FD8 real\n@attribute FD9 real\n@attribute FD10 real\n@attribute FD11 real\n@attribute FD12 real\n@attribute FD13 real\n@attribute FD14 real\n@attribute FD15 real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data\n";
				headercross20="@relation CrossValidation5\n@attribute FD1 real\n@attribute FD2 real\n@attribute FD3 real\n@attribute FD4 real\n@attribute FD5 real\n@attribute FD6 real\n@attribute FD7 real\n@attribute FD8 real\n@attribute FD9 real\n@attribute FD10 real\n@attribute FD11 real\n@attribute FD12 real\n@attribute FD13 real\n@attribute FD14 real\n@attribute FD15 real\n@attribute FD16 real\n@attribute FD17 real\n@attribute FD18 real\n@attribute FD19 real\n@attribute FD20 real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data\n";
				
				
				
			
				outputfilecross5.write(headercross5);
				outputfilecross10.write(headercross10);
				outputfilecross15.write(headercross15);
				outputfilecross20.write(headercross20);

				head=true;
			}
		 //-------------READ THE FOURIER DESCRIPTORS
		 
		 String lindesc="";
		 int numlinhadesc=0;
		 int endlinedesc=1;
		 String infdesc="";
		 double[] datas = new double[21];
		 double []datas5=new double[21];
		 double []datas10=new double[21];
		 double []datas15=new double[21];
		 
		 
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
		  
		        for (int p = 0; p < vetor.length; p++) 
		        { datas5[p] =  Double.parseDouble(vetor[p]);} 
		        
		        for (int p = 0; p < vetor.length; p++) 
		        { datas10[p] =  Double.parseDouble(vetor[p]);} 
		        
		        for (int p = 0; p < vetor.length; p++) 
		        { datas15[p] =  Double.parseDouble(vetor[p]);} 
		        
		        
		        //--------------------FIND BIG AND SMALL VALUES

		        
		        for(int d=0;d<20;d++)
		        {datas[d]=(datas[d]-small)/(big-small);
		        	outputfilecross20.write(datas[d]+",");}
		        
		        for(int d=0;d<5;d++)
	        	{datas5[d]=(datas5[d]-small)/(big-small);
		        	outputfilecross5.write(datas5[d]+",");}
		        
		        for(int d=0;d<10;d++)
	        	{datas10[d]=(datas10[d]-small)/(big-small);
		        	outputfilecross10.write(datas10[d]+",");}
		        
		        for(int d=0;d<15;d++)
	        	{datas15[d]=(datas15[d]-small)/(big-small);
		        	outputfilecross15.write(datas15[d]+",");}
		        
		        
		//writing the label to file
        //{ "0 Capacitor", "1 ResistorSmall","2 ResistorBig", "3 Schmitt trigger","4 Transistor","5 PowerTransistor" }

          if (endLine < (numlinha-1))
          {

              for (int i = endLine; i == endLine; i++) {
          
                  info = inputfile.readLine();
                
                  //search in this line for the correct word (type)
                  
                  if (info.contains("Capacitor")){tipo = "Capacitor";}
                  if (info.contains("ResistorSmall")){tipo = "ResistorSmall";}
                  if (info.contains("ResistorBig")){tipo = "ResistorBig";}
                  if (info.contains("Schmitt trigger")){tipo = "Schmitt trigger";}
                  if (info.contains("Transistor")){tipo = "Transistor";}
                  if (info.contains("PowerTransistor")){tipo = "PowerTransistor";}
                  
                  //System.out.println(tipo);
                  
              }

          
              outputfilecross5.write(tipo+"\n");
              outputfilecross10.write(tipo+"\n");
              outputfilecross15.write(tipo+"\n");
              outputfilecross20.write(tipo+"\n");

	 }
          endLine++;  
          
          //System.out.print(datas);
          //System.out.println(tipo);
		 } //end for linhas
          
		 outputfilecross5.close();
		 outputfilecross10.close();
		 outputfilecross15.close();
		 outputfilecross20.close();
		  reader.close();
		  readerdesc.close();
	}
		 
		    
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
	
		return inputReader;
	}
	
	
	public static void main(String[] args) throws Exception {
	    // loads data and set class index
		double [] bigandsmall=new double[2];
		bigandsmall=findbigandsmall();
		generatedata(bigandsmall[0],bigandsmall[1]);
//		
//		File file5 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross5.txt");
//		File file10 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross10.txt");
//		File file15 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross15.txt");
//		File file20 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross20.txt");
//		
//		BufferedReader datafile5 = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross5.txt");
//		BufferedReader datafile10 = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross10.txt");
//		BufferedReader datafile15 = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross15.txt");
//		BufferedReader datafile20 = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/CrossValidation/filecross20.txt");
//		
//		
//		
//	    Instances data5 = new Instances(datafile5);
//	    Instances data10 = new Instances(datafile10);
//	    Instances data15 = new Instances(datafile15);
//	    Instances data20 = new Instances(datafile20);
//	    
////	    String clsIndex = Utils.getOption("c", args);
////	    if (clsIndex.length() == 0)
////	    	clsIndex = "last";
////	    if (clsIndex.equals("first"))
////	    	data5.setClassIndex(0);
////	    else if (clsIndex.equals("last"))
////	    	data5.setClassIndex(data5.numAttributes() - 1);
////	    else
////	    	data5.setClassIndex(Integer.parseInt(clsIndex) - 1);
//
//	    data5.setClassIndex(data5.numAttributes() - 1);
//	    
//	    // classifier
////	    String[] tmpOptions;
////	    String classname;
////	    tmpOptions     = Utils.splitOptions(Utils.getOption("W", args));
////	    classname      = tmpOptions[0];
////	    tmpOptions[0]  = "";
//	    Classifier cls = new IBk();  //IBK = k nearest neighbor
//
//	    // other options
//	    int runs  = 10; //number of runs
//	    int folds = 4; //number of folds, best if its divisible by the total number of files
//
//	    // perform cross-validation
//	    for (int i = 0; i < runs; i++) {
//	      // randomize data
//	      int seed = i + 1;
//	      Random rand = new Random(seed);
//	      Instances randData = new Instances(data5);
//	      randData.randomize(rand);
//	      if (randData.classAttribute().isNominal())
//	        randData.stratify(folds);
//
//	      Evaluation eval = new Evaluation(randData);
//	      for (int n = 0; n < folds; n++) {
//	        Instances train = randData.trainCV(folds, n);
//	        Instances test = randData.testCV(folds, n);
//	        // the above code is used by the StratifiedRemoveFolds filter, the
//	        // code below by the Explorer/Experimenter:
//	        // Instances train = randData.trainCV(folds, n, rand);
//
//	        // build and evaluate classifier
//	        Classifier clsCopy = Classifier.makeCopy(cls);
//	        clsCopy.buildClassifier(train);
//	        eval.evaluateModel(clsCopy, test);
//	      }
//
//	      // output evaluation
//	      System.out.println();
//	      System.out.println("=== Setup run " + (i+1) + " ===");
//	      System.out.println("Classifier: " + cls.getClass().getName() + " " + Utils.joinOptions(cls.getOptions()));
//	      System.out.println("Dataset: " + data5.relationName());
//	      System.out.println("Folds: " + folds);
//	      System.out.println("Seed: " + seed);
//	      System.out.println();
//	      System.out.println(eval.toSummaryString("=== " + folds + "-fold Cross-validation run " + (i+1) + "===", false));
//	    }
		
		
	  }

}
