package br.com.pontov.frame;

import java.lang.reflect.Array;
import java.util.Vector;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import weka.core.Instance;

import com.atul.JavaOpenCV.Imshow;


	

public class Histogramtest {
	private Caractest arrtest;
	public Histogramtest(Caractest arrtest){
	this.arrtest=arrtest;
}

	

	public void start()
	{
		Mat goldhist = Highgui.imread(arrtest.getURL3());
        Mat src =  new Mat(goldhist.height(), goldhist.width(), CvType.CV_8UC2);
        Imgproc.cvtColor(goldhist, src, Imgproc.COLOR_RGB2GRAY);
        Vector<Mat> bgr_planes = new Vector<Mat>();                                                                                                                                                                                 
        Core.split(src, bgr_planes);
        MatOfInt histSize = new MatOfInt(256);
        final MatOfFloat histRange = new MatOfFloat(0f, 256f);
        boolean accumulate = false;
        Mat b_hist = new  Mat();
        MatOfInt channels = new MatOfInt(0);

        
        Imgproc.calcHist(bgr_planes, channels,new Mat(), b_hist, histSize, histRange, accumulate);
        
        int hist_w = 512;
        int hist_h = 600;
        long bin_w;
        bin_w = Math.round((double) (hist_w / 256));
        Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC1);
        Core.normalize(b_hist, b_hist, 3, histImage.rows(), Core.NORM_MINMAX);	
        
        
     
     // Extract histogram bean counts values to an array, note CvHistogram stores counts as floats

        for (int i = 1; i < 256; i++) {         


            Core.line(histImage, new Point(bin_w * (i - 1),hist_h- Math.round(b_hist.get( i-1,0)[0])), 
                    new Point(bin_w * (i), hist_h-Math.round(Math.round(b_hist.get(i, 0)[0]))),
                    new  Scalar(255, 0, 0), 2, 8, 0);
                  double[] valor = b_hist.get(i, 0);    
        }
        
        
        Mat Ch = b_hist.clone();
        Ch.convertTo(Ch, CvType.CV_64FC3);
       
        int size = (int) (Ch.total() * Ch.channels());
        double[] temp = new double[size]; 
        
       
        for (int i = 0; i < size; i++)
        {
        	Ch.get(0, i, temp);
           temp[i] = (temp[i]);  // no more casting required.
           
        }
        
        //C.put(0, 0, temp);
        //b_hist.put(i, 0, buff);
        
        //Pct0.5 - percent of the histogram bins with >0.5% of the pixels.
        double numBins = 0.0;
        for (int i=0; i< temp.length; i++)
        {
        	if (temp[i]>0.5)  //tentar com 3
        		numBins++;
        }
        numBins = numBins*100/256;   //256 corresponde ao width do histograma
        arrtest.setNumBins(numBins);
      //  System.out.print(numBins);
        //------------------------------------------------------------------------
        
       // Pct2Pk - percent of the histogram range within the largest 2 peaks.
        double pct2pk = 0.0;
        int maximal = getMaxima(temp, new int[][]{{0,temp.length}});
        
     // navigate left until an inflection point is reached
        int lminima1 = getMinima(temp, new int[] {maximal, 0});
        int rminima1 = getMinima(temp, new int[] {maximal, temp.length});
        for (int gl = lminima1; gl <= rminima1; gl++) {
          pct2pk += temp[gl];
        }
        // find the second peak
        int maxima2 = getMaxima(temp, new int[][] {
            {0, lminima1 - 1}, {rminima1 + 1, temp.length}}); 
        int lminima2 = 0;
        int rminima2 = 0;
        if (maxima2 > maximal) {
          // new maxima is to the right of previous on
          lminima2 = getMinima(temp, new int[] {maxima2, rminima1 + 1});
          rminima2 = getMinima(temp, new int[] {maxima2, temp.length}); 
        } else {
          // new maxima is to the left of previous one
          lminima2 = getMinima(temp, new int[] {maxima2, 0});
          rminima2 = getMinima(temp, new int[] {maxima2, lminima1 - 1});
        }
        for (int gl = lminima2; gl < rminima2; gl++) {
          pct2pk += temp[gl];
        }
        arrtest.setPct2pk(pct2pk);
       // System.out.print(pct2pk);
        //-------------------------------------------------------------------------
  
       // Bimodal - average of various sums of nearest neighbor pixel differences.
        double absdiff = 0.0;
        int diffSteps = 0;
        double bimodalap = 0.0;
        for (int i=1;i<temp.length;i++)
        {
        	if (temp[i-1] != temp[i])
        		absdiff += Math.abs(temp[i]-temp[i-1]);
        	diffSteps++;
        }
        bimodalap = absdiff/diffSteps;
        arrtest.setBimodalap(bimodalap);
        //System.out.print(bimodalap);
        //-------------------------------------------------------------------------
        
        //System.out.print(temp);
       // System.out.print(Arrays.toString(temp));
        
        //converte o histograma (double) em string
       StringBuilder sb = new StringBuilder(Arrays.toString(temp));
       sb.deleteCharAt(0);
       sb.deleteCharAt(sb.length()-1);
       String resultString = sb.toString();
       //System.out.print(resultString.length());
       //System.out.print(resultString);
       
       
        arrtest.setHisto(temp);
       // arr.setType(resultString);
        
        
	}
	
	
	//METODO PEGAR O MAIOR VALOR DO HISTOGRAMA
	private int getMaxima(double[] histogram, int[][] ranges) {
	    int maxima = 0;
	    double maxY = 0.0D;
	    for (int i = 0; i < ranges.length; i++) {
	      for (int gl = ranges[i][0]; gl < ranges[i][1]; gl++) {
	        if (histogram[gl] > maxY) {
	          maxY = histogram[gl];
	          maxima = gl;
	        }
	      }
	    }
	    return maxima;
	  }
	
	private int getMinima(double[] histogram, int[] range) {
	    int start = range[0];
	    int end = range[1];
	    if (start == end) {
	      return start;
	    }
	    boolean forward = start < end;
	    double prevY = histogram[start];
	    double dy = 0.0D;
	    double prevDy = 0.0D;
	    if (forward) {
	      // avoid getting trapped in local minima
	      int minlookahead = start + 10;
	      for (int pos = start + 1; pos < end; pos++) {
	        dy = histogram[pos] - prevY;
	        if (signdiff(dy, prevDy) && pos >= minlookahead) {
	          return pos;
	        }
	        prevY = histogram[pos];
	        prevDy = dy;
	      }
	    } else {
	      // avoid getting trapped in local minima
	      int minlookbehind = start - 10;
	      for (int pos = start - 1; pos >= end; pos--) {
	        dy = histogram[pos] - prevY;
	        if (signdiff(dy, prevDy) && pos <= minlookbehind) {
	          return pos;
	        }
	        prevY = histogram[pos];
	        prevDy = dy;
	      }
	    }
	    return start;
	}
	private boolean signdiff(double dy, double prevDy) {
	    return ((dy < 0.0D && prevDy > 0.0D) ||
	        (dy > 0.0 && prevDy < 0.0D));
	  }
}
