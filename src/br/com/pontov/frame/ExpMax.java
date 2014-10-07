package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.opencv.core.Core;

import weka.core.*;
import weka.clusterers.*;
import weka.classifiers.*;
import weka.classifiers.meta.ClassificationViaClustering;

public class ExpMax {

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
		
		
		 String[] options = new String[2];
		 options[0] = "-I";                 // max. iterations
		 options[1] = "100";
		 EM clusterer = new EM();   // new instance of clusterer
		 clusterer.setOptions(options);     // set the options
		 clusterer.setNumClusters(6);
		 clusterer.buildClusterer(data);    // build the clusterer
		
		 data.setClassIndex(3);
		 
		 ClusterEvaluation eval = new ClusterEvaluation();
		 eval.setClusterer(clusterer);                                   // the cluster to evaluate
		 eval.evaluateClusterer(data);                                // data to evaluate the clusterer on
		 
		 
		 
		 //----------TEST

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
		    
		    
		    ClassificationViaClustering cvc = new ClassificationViaClustering();
		    cvc.setClusterer(clusterer);
		    cvc.buildClassifier(data);
		    double class1 = cvc.classifyInstance(instance);

		
		 System.out.println("first: " + class1 );
		 
		 System.out.println(eval.clusterResultsToString());
		
		
	}
}
