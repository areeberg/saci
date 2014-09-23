package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

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
	final static int attrib = 900;
	final static int test_sample = 1;
	final static int classes = 6;
	//--------------------------------


	static byte[][] read_dataset(String filename, byte[][] datas,  int total_samples) throws IOException
	{

	    //open the file
	    BufferedReader inputfile =  new BufferedReader(new FileReader(filename));
	   
	    String info = "";
	 
	    datas = new byte[training_samples][attrib+1];  //+1 pois contem a classe
	    String[] vetor = new String[attrib+1];
	    for (int a = 0; a < training_samples; a++)
        { info = inputfile.readLine();
        
        //System.out.print(datas);
        
        vetor = info.split(",");
      
        
        for (int p = 0; p < vetor.length; p++) 
        { datas[a][p] = (byte) Double.parseDouble(vetor[p]);}

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
	
	public static void main(String[] args) throws Exception {
		System.loadLibrary("opencv_java249");
		Mat training_set = new Mat(training_samples, attrib, CvType.CV_32F);
		Mat training_set_classifications = new Mat(training_samples, classes, CvType.CV_32F); // esta com 15*1
		byte[][] datas = new byte[training_samples][attrib+1];
	
		datas = read_dataset("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt", datas, training_samples);
		//System.out.print(datas);   		//DATAS CONTEM TODAS AS INFO DAS IMAGENS

		for (int r=0;r<training_samples;r++)
		{
			byte[] aux = new byte[attrib];
		for (int c=0;c < attrib;c++)
		{
			//Copia as informacoes
			aux[c]=datas[r][c];
		}
		for (int ca = 0;ca<attrib;ca++)
		{training_set.put(r, ca,aux[ca]);}
		
		}
		//System.out.print(training_set);   //garantir que o training_set esta correto
		
		
		byte[] aux2 = new byte[training_samples];
		for (int r=0;r<training_samples;r++)
		{
			aux2[r]=datas[r][900];	
		}
		
		for (int r=0;r<training_samples;r++)
		{
		training_set_classifications.put(r, 0, aux2[r]);
		}
		//System.out.print(training_set_classifications);
		
		//ESTRUTURA DA REDE NEURAL (3 CAMADAS)
		// - one input node per attribute in a sample so 900 input nodes
        // - 16 hidden nodes
        // - 10 output node, one for each class.
	
		Mat layers = new Mat(3,1,CvType.CV_32S);
		layers.put(0, 0, attrib);
		layers.put(1, 0, 16);
		layers.put(2, 0, classes);
		
		CvANN_MLP network = new CvANN_MLP (layers, CvANN_MLP.SIGMOID_SYM,0.6,1);
		
		CvANN_MLP_TrainParams params = new CvANN_MLP_TrainParams();
		params.set_train_method(CvANN_MLP_TrainParams.BACKPROP);
		params.set_bp_dw_scale(0.01);
		params.set_bp_moment_scale(0.05);
		params.set_term_crit(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS,1000,0.001));
		
		
		float iterations =network.train(training_set, training_set_classifications, new Mat(), new Mat(), params, CvANN_MLP_TrainParams.BACKPROP);; 
				network.train(training_set, training_set_classifications, new Mat(), new Mat(), params, CvANN_MLP_TrainParams.BACKPROP);
				
		//sainda = training_set_classification
		System.out.println("Training iterations:" + iterations);  
		
		network.save("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainedNNO.xml");
		
		
		//---------------------------TESTE
		Mat test_sample = new Mat();
		int correct_class = 0;
    	int wrong_class = 0;
    	int classification_matrix[][]= new int [classes][classes];
    	
    	Mat test_set = new Mat(test_samples, attrib, CvType.CV_32F);
		Mat test_set_classifications = new Mat(test_samples, classes, CvType.CV_32F); // esta com 15*1
    	byte[][] datatest = new byte[1][attrib+1];
    	datatest = read_dataset("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt", datatest, 1);
    	
    	for (int r=0;r<test_samples;r++)
		{
			byte[] aux = new byte[attrib];
		for (int c=0;c < attrib;c++)
		{
			//Copia as informacoes
			aux[c]=datatest[r][c];
		}
		for (int ca = 0;ca<attrib;ca++)
		{test_set.put(r, ca,aux[ca]);}
		
		}
		//System.out.print(test_set);   //garantir que o training_set esta correto
		
		
		byte[] aux2t = new byte[test_samples];
		for (int r=0;r<test_samples;r++)
		{
			aux2t[r]=datatest[r][900];	
		}
		
		for (int r=0;r<test_samples;r++)
		{
		test_set_classifications.put(r, 0, aux2t[r]);
		}
		//System.out.print(test_set_classifications);
    	
		
		Mat classificationResult= new Mat(1, classes, CvType.CV_32F);
		Mat classificres=new Mat();
		
		
		//PREVISAO
		network.predict(test_set, classificationResult);
		
		//System.out.print(classificationResult.toString());
		
		
		//ANALISE DE RESULTADO
		int maxIndex = 0;
		double[] valuea = new double[0];
		double value=0.0;
		float [] mxv = new float[classificationResult.width()];
		classificationResult.get(0, 0, mxv);
		double maxValue = mxv[0];
		System.out.print("classification_Result (" + maxValue + ") ");
		
		
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
            System.out.print(classificationResult.get(0,maxIndex) + " ");
            	
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
