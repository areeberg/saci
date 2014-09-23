package br.com.pontov.frame;

import java.util.List;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.atul.JavaOpenCV.Imshow;

public class Surf {
	public static void main(String[] args) 
	{
		System.loadLibrary("opencv_java249");
		Mat goldimgc = Highgui.imread("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img1Defect0a.png");
		Mat gray = new Mat(goldimgc.size(),CvType.CV_8UC3);
		Imgproc.cvtColor(goldimgc, gray, Imgproc.COLOR_RGB2GRAY);
		
		MatOfKeyPoint keypoints = new MatOfKeyPoint();
		Mat descriptors = new Mat();

		DescriptorExtractor descriptorExtractor=DescriptorExtractor.create(2);;
		DescriptorMatcher descriptorMatcher=DescriptorMatcher.create(6);
		
		// Create a SIFT keypoint detector.
		
		FeatureDetector detector=FeatureDetector.create(4);;
		detector.detect(gray, keypoints);
		
		Features2d.drawKeypoints(gray, keypoints, gray);
		Imshow ima1 = new Imshow("Drawing");
	     ima1.Window.setResizable(true);
	      ima1.showImage(gray);
	      
		// Compute feature description.
		descriptorExtractor.compute(gray, keypoints, descriptors);
		
		
		List<Mat> descriptorsList;
		 descriptorsList = descriptorMatcher.getTrainDescriptors();
	    descriptorsList.add(descriptors);
	    descriptorMatcher.add(descriptorsList);
		
	    
	

	   
	
	}
}
