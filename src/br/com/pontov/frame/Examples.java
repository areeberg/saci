package br.com.pontov.frame;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.atul.JavaOpenCV.Imshow;

public class Examples {

	public static void main(String[] args) throws Exception {
		System.loadLibrary("opencv_java249");
		
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat goldimagecropped,goldimagecorner;
        goldimagecropped = Highgui.imread("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/cuttedgoodcomponent.png", Imgproc.COLOR_BGR2GRAY);  //entrada da classe
        Mat goldimgc = Highgui.imread("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/cuttedgoodcomponent.png");
        
        Mat gray = new Mat(goldimgc.size(),CvType.CV_8UC3);
    	

    	Imgproc.cvtColor(goldimgc, gray, Imgproc.COLOR_RGB2GRAY);
    	Imgproc.threshold(gray, gray, 100, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
//       Highgui.imwrite("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/otsu.png", gray);
//       Imshow ima = new Imshow("Otsu Threshold");
//        ima.Window.setResizable(true);
//         ima.showImage(gray);
//         
         
       //Noise removal
         Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3,3));  //19,19
         Mat ret = new Mat(goldimgc.size(),CvType.CV_8U);
         Imgproc.morphologyEx(gray, ret, Imgproc.MORPH_OPEN, kernel);
         
//         Imshow ima2 = new Imshow("Opening");
//         ima2.Window.setResizable(true);
//          ima2.showImage(ret);
//      	Highgui.imwrite("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/opening.png", ret);

         Mat sure_bg = new Mat(goldimgc.size(),CvType.CV_8U);
         Imgproc.dilate(ret,sure_bg,new Mat(),new Point(-1,-1),3);
         Imgproc.threshold(sure_bg,sure_bg,1, 128,Imgproc.THRESH_BINARY_INV);
         //STOP
         Mat sure_fg = new Mat(goldimgc.size(),CvType.CV_8U);
         Imgproc.erode(gray,sure_fg,new Mat(),new Point(-1,-1),2);
    

       Mat markers = new Mat(goldimgc.size(),CvType.CV_8U, new Scalar(0));
       Core.add(sure_fg, sure_bg, markers);
       
//     Imshow ima3 = new Imshow("Erode and dilate");
//     ima3.Window.setResizable(true);
//      ima3.showImage(markers);
//  	Highgui.imwrite("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/erodedilate.png", markers);

  
       markers.convertTo(markers, CvType.CV_32SC1);
       
       Imgproc.watershed(goldimgc, markers);
       Core.convertScaleAbs(markers, markers);
       
 	//Highgui.imwrite("/Users/alexandrermello/Documents/artigo mestrado visao/Artigo1/figuras/watershed.png", markers);

       
//     Imshow ima3 = new Imshow("Opening");
//     ima3.Window.setResizable(true);
//     ima3.showImage(markers);
      
        
	}
	
	
}
