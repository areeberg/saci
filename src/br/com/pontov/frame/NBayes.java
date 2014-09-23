package br.com.pontov.frame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.opencv.core.Core;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;


public class NBayes {
	private Carac arr;
	public NBayes(Carac arr){
	this.arr=arr;
	}
	private Caractest arrtest;
	public NBayes(Caractest arrtest){
	this.arrtest=arrtest;
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
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		

		BufferedReader datafile = readDataFile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt");
	
		
		//------TRAIN
		
		Classifier naiveb = new NaiveBayes();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		naiveb.buildClassifier(data);
		
		//------TEST
		
		
	        
	
				Instance instance = new Instance(data.numAttributes());
				instance.setDataset(data);
			    instance.setValue(data.attribute(0), 100);
			    instance.setValue(data.attribute(1), 1000);
			    instance.setValue(data.attribute(2), 9);
			    
			    
			    double[] distribution = naiveb.distributionForInstance(instance);
			   double classe = naiveb.classifyInstance(instance);
			    System.out.print(classe);
	}
}
	
