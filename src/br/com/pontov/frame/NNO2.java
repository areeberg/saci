package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.CvANN_MLP;
import org.opencv.ml.CvANN_MLP_TrainParams;

import com.atul.JavaOpenCV.Imshow;

public class NNO2{
	private static Carac arr;
	public NNO2(Carac arr){
	this.arr=arr;
	}
	//--------------------------------
	final static int training_samples = 15;
	final static int test_samples=1;
	final static int attrib = 20;
	final static int classes = 6;
	final static int input = training_samples*attrib;
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
	
	
	
	//-----------------------------------MAIN---------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception {
		System.loadLibrary("opencv_java249");
		Mat training_set = new Mat(training_samples, attrib, CvType.CV_32FC1);
		Mat training_set_classifications = new Mat(training_samples, classes, CvType.CV_32FC1); // esta com 15*1
		Mat sampleWts =  new Mat(training_samples,1,CvType.CV_32FC1);
		
		double[][] datas = new double[training_samples][attrib+1];

		datas = read_dataset("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt", datas, training_samples);
		
		for (int r=0;r<training_samples;r++)
		{
			double[] aux = new double[attrib];
			
		for (int c=0;c < attrib;c++)
		{
			//Copia as informacoes
			aux[c]=datas[r][c];
		}
		
	System.out.print(aux);
		training_set.put(r, 0,aux);
		
		//for (int ca = 0;ca<attrib;ca++)
		//{training_set.put(r, ca,aux[ca]);}
		
		}
		
		
	
		System.out.print(training_set.size());   //garantir que o training_set esta correto
		
		
		double[] aux2 = new double[training_samples];
		for (int r=0;r<training_samples;r++)
		{
			aux2[r]= datas[r][20];	
		}
		//training_set_classifications.put(0, 0, aux2);
		
		for (int r=0;r<training_samples;r++)
		{training_set_classifications.put(r, 0, aux2);}
		System.out.print(training_set_classifications.size());  //15x6
		
		
		//ESTRUTURA DA REDE NEURAL (3 CAMADAS)
		// - one input node per attribute in a sample so 20*samples input nodes
        // - 16 hidden nodes
        // - 6 output node, one for each class.
	
		Mat layers = new Mat(3,1,CvType.CV_32SC1);
		layers.put(0, 0, training_set.cols());
		layers.put(1, 0, 16);
		layers.put(2, 0, classes);
	
		
		for (int k=0;k<=training_samples;k++)
		{
		sampleWts.put(k, 0,1);
		}
		
		CvANN_MLP network = new CvANN_MLP (layers, CvANN_MLP.SIGMOID_SYM,1,0);
		//CvANN_MLP network = new CvANN_MLP (layers, CvANN_MLP.SIGMOID_SYM,0,0);
		network.create(layers);
		
		CvANN_MLP_TrainParams params = new CvANN_MLP_TrainParams();
		params.set_train_method(CvANN_MLP_TrainParams.BACKPROP);
		params.set_bp_dw_scale(0.05f);
		params.set_bp_moment_scale(0.01f);
		params.set_term_crit(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS,200000,0.00000000001));
		
		
	
		
		float iterations = network.train(training_set, training_set_classifications, sampleWts, new Mat(), params, CvANN_MLP_TrainParams.BACKPROP);
				//network.train(training_set, training_set_classifications,sampleWts, new Mat(), params, CvANN_MLP_TrainParams.BACKPROP);

		//sainda = training_set_classification
		System.out.println("Training iterations:" + iterations);  
		
		network.save("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainedNNO.xml");
		
		
		//---------------------------TESTE------------------------------------------------------------------------------------------------
		Mat test_sample = new Mat();
		int correct_class = 0;
    	int wrong_class = 0;
    	int classification_matrix[][]= new int [classes][classes];
    	
    	Mat test_set = new Mat(test_samples, attrib, CvType.CV_32F);
		Mat test_set_classifications = new Mat(test_samples, classes, CvType.CV_32F); // esta com 15*1
		//The weight of each training data sample. We'll later set all to equal weights.

		
    	double[][] datatest = new double[1][attrib+1];
    	datatest = read_datasettest("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO2.txt", datatest, 1);
    	
    	//System.out.print(datatest);
   //System.out.println();
   
    	for (int r=0;r<test_samples;r++)
		{
			double[] aux = new double[attrib];
		for (int c=0;c < attrib;c++)
		{
			//Copia as informacoes
			aux[c]= datatest[r][c];
		}
		//System.out.print(aux);
		test_set.put(0, 0, aux); 
		
		/*
		for (int ca = 0;ca<attrib;ca++)
		{test_set.put(r, ca,aux[ca]);}	
		*/
		}
		//System.out.print(test_set);
		
		
		double[] aux2t = new double[test_samples];
		for (int r=0;r<test_samples;r++)
		{
			aux2t[r]= datatest[r][20];	
		}
		test_set_classifications.put(0, 0, aux2t);
		
		/*
		for (int r=0;r<test_samples;r++)
		{
		test_set_classifications.put(r, 0, aux2t[r]);
		}
		//System.out.print(test_set_classifications);
    	*/
		
		Mat classificationResult = new Mat(1, classes, CvType.CV_32FC1);
		
		
		//PREVISAO
		network.predict(test_set, classificationResult);
		

		//ANALISE DE RESULTADO
		int maxIndex = 0;
		double[] valuea = new double[0];
		double value=0.0;
		float [] mxv = new float[classificationResult.width()];
		
		classificationResult.get(0, 0, mxv);	
		//classificationResult.get(0, 0, mxv2);
		System.out.println(""+mxv[0]+" "+mxv[1]+" "+mxv[2]+" "+mxv[3]+" "+mxv[4]+" "+mxv[5]+"");
		System.out.println();
		
		double maxValue = mxv[0];
		//System.out.print("classification_Result (" + (int)maxValue + ") ");
		System.out.print("classification_Result (" + mxv[0] + ") ");
		
		MinMaxLocResult mmr = Core.minMaxLoc(classificationResult);
		double mmrr=1;
		mmrr=mmr.maxVal;
		
		System.out.print("MAXclassification_Result (" + mmrr + ") ");
		
		for(int index=0;index<classes;index++)
        {   

			valuea=classificationResult.get(0, index);
			value=valuea[0];
			//System.out.print(value);
            if(value>maxValue)
            {   
            	maxValue = value;
                maxIndex=index;
            }
            //System.out.print(classificationResult.get(0,maxIndex) + " ");
            	
	}//end for index
		
		
		double[] test_cla = test_set_classifications.get(0, maxIndex);
		double tcla=test_cla[0];
		
		if (tcla != 1.0f)
		{
			wrong_class++;
			
			 //find the actual label 'class_index'
            for(int class_index=0;class_index<classes;class_index++)
            {
            	if (tcla != 1.0f)
                {
                    classification_matrix[class_index][maxIndex]++;// A class_index sample was wrongly classified as maxindex.
                    break;
                }
            }
			
			
		} //end if tcla
		else
		{
			correct_class++;
            classification_matrix[maxIndex][maxIndex]++;
		}
	
		System.out.println("\nResults on the testing dataset\n" + 
    	        "\tCorrect classification: " + correct_class +" ("+ (double) correct_class*100/1.0 + ")\n" +
    	        "\tWrong classifications: " + wrong_class + " (" + (double) wrong_class*100/1.0 + ")\n");
    	
    	for (int i = 0; i < classes; i++)
        {
            System.out.print( i + "\t");
        }
    	System.out.println();
    	
    	for(int row=0;row<classes;row++)
        {
    		System.out.print(row+"  ");
            for(int col=0;col<classes;col++)
            {
            	System.out.print(classification_matrix[row][col] + "\t");
            }
            System.out.println();
        }
		
		
		
		
	}//end main
	
	
}
