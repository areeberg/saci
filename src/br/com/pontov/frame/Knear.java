package br.com.pontov.frame;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.ml.CvKNearest;
import org.opencv.*;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.clusterers.ClusterEvaluation;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;

public class Knear {
	
	public  String texto="";
	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
	
		return inputReader;
	}
	
	public static void mergefiles() throws IOException{
		
		// Files to read
		File file1 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/infoKNN.txt");
		File file2 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/background/info3back.txt");

		// File to write
		File file3 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/KNNFinal.txt");

		// Read the file like string
		String file1Str = FileUtils.readFileToString(file1);
		String file2Str = FileUtils.readFileToString(file2);
		file1Str=file1Str+"\n";
		// Write the file
		FileUtils.write(file3, file1Str);
		FileUtils.write(file3, file2Str, true); // true for append
		
		
	}
		
	public  String KNN(int imagecode) throws Exception{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	
		
		BufferedReader datafile = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/KNNfinal.txt");
		 
		Instances data = new Instances(datafile);
		
		data.setClassIndex(data.numAttributes()-1);  //numAttributes() - 1
		Classifier ibk = new IBk();		
		ibk.buildClassifier(data);

		Evaluation eTest = new Evaluation(data);
	//	eTest.evaluateModel(ibk, isTestingSet);
		
		
		//TEST
		
		String info = "";
		String[] vetor = new String[3];
		double[] datas = new double[3];  //15 = training samples; 10 numero de caracteristicas
		 BufferedReader inputfile =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/TestImages/infotest.txt"));
		 
		 for (int a = 0; a < (5+imagecode); a++)
         { info = inputfile.readLine(); }

		    vetor = info.split(",");     
	        for (int p = 0; p < vetor.length; p++) 
	        { datas[p] =  Double.parseDouble(vetor[p]);}
        
	        
	        double atrib1,atrib2,atrib3;
	        
	        atrib1=(int) datas[0];
	        atrib2=(int) datas[1];
	        atrib3=(int) datas[2];
		
		//---
		
		Instance instance = new Instance(data.numAttributes());
		instance.setDataset(data);
	    instance.setValue(data.attribute(0), atrib1);
	    instance.setValue(data.attribute(1), atrib2);
	    instance.setValue(data.attribute(2), atrib3);
	    //instance.setValue(data.attribute(3), "cap");
	    

	    
		double class1 = ibk.classifyInstance(instance);

		
		double[] distribution = ibk.distributionForInstance(instance);

		
		Random rand = new Random(1);
		 Evaluation eval = new Evaluation(data);
		 eval.crossValidateModel(ibk, data, 3, rand);
		
		//System.out.println(eval.toMatrixString());
		//System.out.println(eval.toClassDetailsString());
		//System.out.print(distribution);
		
		
		String tipoel="";
		
		switch((int) class1)
		{
		case 0:tipoel="Component";break;
		case 1:tipoel="Background";break;
		}
	
		//System.out.println("first: " + tipoel);
		
		texto=eval.toMatrixString()+'\n'+eval.toClassDetailsString()+'\n'+"Type "+tipoel;
		//System.out.println(texto);
		return (tipoel);
	}
	
	
}
