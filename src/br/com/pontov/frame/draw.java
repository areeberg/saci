package br.com.pontov.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.atul.JavaOpenCV.Imshow;


public class draw {
	
	public static void main(String[] args) throws Exception {
		System.loadLibrary("opencv_java249");
		
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat goldimagecropped,goldimagecorner;
        goldimagecropped = Highgui.imread("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/cuttedgoodcomponent.png", Imgproc.COLOR_BGR2GRAY);  //entrada da classe
        Mat goldimgc = Highgui.imread("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/cuttedgoodcomponent.png");
        
        //----------------WATERSHED

        Mat gray = new Mat(goldimgc.size(),CvType.CV_8UC3);
    	

    	Imgproc.cvtColor(goldimgc, gray, Imgproc.COLOR_RGB2GRAY);

   
    	Imgproc.threshold(gray, gray, 100, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
    	
 
         
          //Noise removal
          Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3,3));  //19,19
          Mat ret = new Mat(goldimgc.size(),CvType.CV_8U);
          Imgproc.morphologyEx(gray, ret, Imgproc.MORPH_OPEN, kernel);
          
     
          
          //Sure background area
          Mat sure_bg = new Mat(goldimgc.size(),CvType.CV_8U);
          Imgproc.dilate(ret,sure_bg,new Mat(),new Point(-1,-1),3);
          Imgproc.threshold(sure_bg,sure_bg,1, 128,Imgproc.THRESH_BINARY_INV);
          
          Mat sure_fg = new Mat(goldimgc.size(),CvType.CV_8U);
          Imgproc.erode(gray,sure_fg,new Mat(),new Point(-1,-1),2);
    

        Mat markers = new Mat(goldimgc.size(),CvType.CV_8U, new Scalar(0));
        Core.add(sure_fg, sure_bg, markers);
        
   
        markers.convertTo(markers, CvType.CV_32SC1);
        
        Imgproc.watershed(goldimgc, markers);
        Core.convertScaleAbs(markers, markers);


/*	        Imshow dra4 = new Imshow("Drawing4");
          dra4.Window.setResizable(true);
           dra4.showImage(markers);
      */  
        //---------------------------------
        
        
        
        
        goldimagecorner=markers; //goldimagecropped
        
       // Imgproc.GaussianBlur(markers, markers, new Size(5,5), 2,2); //goldimagecropped
        
        int thresh =1; 
        double largest_contour=0;
        int largest_contour_index=0;
        
        double largest_contour2=0;
        int largest_contour_index2=0;
        
        for (int  i=0;i<250;i++)
        {
        Imgproc.Canny(markers, markers, i,255); //goldimagecropped
        
        
        Imgproc.findContours(markers, contours, new Mat(), Imgproc.RETR_EXTERNAL+Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
        //RETR_LIST
     
        }   
        for( int x = 0; x<contours.size(); x++ ) // iterate through each contour.
        {
        
		double a=Imgproc.contourArea(contours.get(x));   //  Find the area of contour
		
          
            if(a>largest_contour){
                largest_contour=a;						//SAIDA: MAIOR CONTORNO
                largest_contour_index=x;                //Store the index of largest contour 
            }
        }
        for( int x = 0; x<contours.size(); x++ ) // iterate through each contour.
        {
        
 		double a=Imgproc.contourArea(contours.get(x));   //  Find the area of contour
 	
          
            if(a>largest_contour2 & a<largest_contour){
                largest_contour2=a;						//SAIDA: SEGUNDO MAIOR CONTORNO
                largest_contour_index2=x;                //Store the index of largest contour 
            }
            
        }
        
       
        boolean key=false;
        if ((largest_contour - largest_contour2) < 0.5*largest_contour)
        {
     	   key=true;
        }
    
//      Imshow ima3 = new Imshow("Contour");
//      ima3.Window.setResizable(true);
//       ima3.showImage(drawing);

        
        //----------------FOURIER DESCRIPTORS
        Point[] arri = contours.get(largest_contour_index).toArray();
        Mat pointsX = new Mat(new Size(1,20), CvType.CV_32FC1);
        Mat pointsY = new Mat(new Size(1,20), CvType.CV_32FC1);    
        int delta = arri.length / 20;             // 20 points from contour
        
        for(int i = 0, j = 0; i < arri.length && j < 20; i += delta, ++j) {
        	   pointsX.put(j, 0, arri[i].x); 
        	   pointsY.put(j, 0, arri[i].y); 
        	}   
      
        List<Mat> planes = new ArrayList<Mat>();
        planes.add(pointsX);
        planes.add(pointsY);
        Mat complexI = new Mat();
        Core.merge(planes, complexI);                   
        Core.dft(complexI, complexI);
        
     // scale invariant Fi = Fi / |F1|
        double Re = complexI.get(1,0)[0];
        double Im = complexI.get(1,0)[1];

        double magF1=Math.sqrt(Re*Re + Im*Im);
        double [][] compli = new double [20][2];
        
        for(int i = 2; i < complexI.rows(); ++i) {
           double[] newVal = new double[2];
           newVal[0] = complexI.get(i, 0)[0] / magF1;
           newVal[1] = complexI.get(i, 0)[1] / magF1;
           
           compli[i][0]=newVal[0];
           compli[i][1]=newVal[1];
           
           complexI.put(i, 0, newVal);
           
        }
        // rotation invariant |Fi|
        Core.split(complexI, planes);            // planes[0] = Re(DFT(I), planes[1] = Im(DFT(I))
        Mat dftMag = new Mat();
        Core.magnitude(planes.get(0), planes.get(1), dftMag);
       double [] magd = new double[dftMag.rows()];
       
        for (int i=0 ; i< dftMag.rows();i++)
        {
        	double newVal = 0.0;
        	newVal =  dftMag.get(i, 0)[0]; 
        	magd[i]= newVal;
        }

    
    //--------------VETORES E MOMENTO 1
        
       
       
        Vector<Moments> mu = new Vector<Moments>(contours.size());
        for(int i=0; i<contours.size(); i++)
        {
         mu.add(Imgproc.moments( (contours.get(i)), false ));
        }
        
        
        Vector<Point> mc = new Vector<Point>( contours.size() ); // valor de centroid
        for( int i = 0; i < contours.size(); i++ )
        {
         mc.add(new Point( (mu.get(i)).get_m10()/mu.get(i).get_m00(), mu.get(i).get_m01()/mu.get(i).get_m00() ));
        }
        
        //Mat drawing = Mat.zeros(markers.size(), largest_contour_index);
        Mat drawing = Mat.zeros(goldimgc.size(), CvType.CV_8UC3);
      
        
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Rect rect=new Rect();
        MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(largest_contour_index).toArray() ); RotatedRect rectrot=null;
        rectrot = Imgproc.minAreaRect(contour2f);
        
        Rect box = rectrot.boundingRect();
     
        
        //Desenhar o retangulo
    Core.rectangle(drawing, box.tl(), box.br(), new Scalar(0,0,255),1);
      
        float W1,L1,elo1;
        W1 = box.width;
        L1 = box.height;
        elo1=1-W1/L1;  //SAIDA: elongation


      //ELLIPSE FIT----------------------------------------------------------------------------------------           
        RotatedRect elip = Imgproc.fitEllipse(contour2f);
        
        

        //Desenhar a elipse
      // Core.ellipse(drawing, elip, new Scalar(255,0,0));
       Core.ellipse(drawing, elip, new Scalar(255,255,0), 1);
       Highgui.imwrite("/Users/alexandrermello/Documents/artigo mestrado visao/wesaac-latex/figuras/elip.png", drawing);
       
       Mat imgf = new Mat(goldimgc.size(),CvType.CV_8U, new Scalar(0));

     // Imgproc.drawContours(imgf, contours, largest_contour_index, new Scalar(200,200,0));
      
      
      Imshow ima3 = new Imshow("Contour");
      ima3.Window.setResizable(true);
      ima3.showImage(imgf);
 
		
        
		
	}

}
