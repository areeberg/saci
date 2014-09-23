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

		 Instance instance = new Instance(data.numAttributes());
			instance.setDataset(data);
		    instance.setValue(data.attribute(0), 100.0D);
		    instance.setValue(data.attribute(1),7000.0D);
		    instance.setValue(data.attribute(2), 27.0D);
		    
		    
		    Instance instance2 = new Instance(data.numAttributes());
			instance2.setDataset(data);
		    instance2.setValue(data.attribute(0), 100.0D);
		    instance2.setValue(data.attribute(1), 8000.0D);
		    instance2.setValue(data.attribute(2), 16.0D);
		   
		    
			
		    Instance instance3 = new Instance(data.numAttributes());
			instance3.setDataset(data);
		    instance3.setValue(data.attribute(0), 100.0D);
		    instance3.setValue(data.attribute(1), 1500.0D);
		    instance3.setValue(data.attribute(2), 8.0D);
		    
		    
		    ClassificationViaClustering cvc = new ClassificationViaClustering();
		    cvc.setClusterer(clusterer);
		    cvc.buildClassifier(data);
		    double class1 = cvc.classifyInstance(instance);
			double class2 = cvc.classifyInstance(instance2);
			double class3 = cvc.classifyInstance(instance3);
		
		 System.out.println("first: " + class1 + "\nsecond: " + class2 + "\nthird: "+ class3);
		 
		 System.out.println(eval.clusterResultsToString());
		
		
	}
}
