package br.com.pontov.frame;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

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
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	
		
		
		BufferedReader datafile = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt");
		 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
	
		 //-------------------------------------------------------------------------------------------------------------------------
		
		
		
		
		//do not use first and second
		Instance first = data.instance(1);
		Instance second = data.instance(2);
		Instance third = data.instance(3);
		
		//data.delete(1);
		//data.delete(2);
 
		Classifier ibk = new IBk();		
		ibk.buildClassifier(data);

		Evaluation eTest = new Evaluation(data);
	//	eTest.evaluateModel(ibk, isTestingSet);
		
		
		//TEST
		
		String info = "";
		String[] vetor = new String[3];
		double[] datas = new double[3];  //15 = training samples; 10 numero de caracteristicas
		 BufferedReader inputfile =  new BufferedReader(new FileReader("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/infotest.txt"));
		 for (int a = 0; a <= 6; a++)
         { info = inputfile.readLine(); }

		    vetor = info.split(",");     
	        for (int p = 0; p < vetor.length-1; p++) 
	        { datas[p] =  Double.parseDouble(vetor[p]);}
        
	        
	        int atrib1,atrib2,atrib3;
	        
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
		System.out.println(eval.toMatrixString());
		System.out.println(eval.toClassDetailsString());
		//System.out.print(distribution);
		
		
		String tipoel="";
		
		switch((int) class1)
		{
		case 0:tipoel="Capacitor";break;
		case 1:tipoel="ResistorSmall";break;
		case 2:tipoel="ResistorBig";break;
		case 3:tipoel="Schmitt trigger";break;	
		case 4:tipoel="Transistor";break;
		case 5:tipoel="PowerTransistor";break;
		}
		
		
		
		System.out.println("first: " + tipoel);
		
	}
}
