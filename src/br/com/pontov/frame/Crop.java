package br.com.pontov.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Crop extends JFrame implements MouseListener, MouseMotionListener {
	private Carac arr;
	public Crop(Carac arr){
	this.arr=arr;
}
	int drag_status=0,c1,c2,c3,c4;
	Mat cropped=null;
	boolean go=false;
	public class ImagePanel extends JPanel {

		  private Image img;

		  public ImagePanel(String img) {
		    this(new ImageIcon(img).getImage());
		  }

		  public ImagePanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    //setLayout(null);
		  }
		  public void paintComponent(Graphics g) {
			    g.drawImage(img, 0, 0, null);
			  }
		  
	}

	public synchronized void start() throws InterruptedException
	{
		
		ImagePanel im=new ImagePanel(arr.getURL()); //Esta variável vai ter que buscar as goldimages
		getContentPane().add(im);
		//add(im);
		//setSize(640,480);
		im.setBackground(Color.black);
		im.setOpaque(true);
		setSize(im.getSize());
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener( this ); 
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//while(go==false)
	//		wait();
		
	}
	public synchronized void draggedScreen()throws Exception
	{
			int w = c1 - c3;
	   		int h = c2 - c4;
	   		arr.setC1(c1);
	   		arr.setC2(c2);
	   		arr.setC3(c3);
	   		arr.setC4(c4);
	   		
	   	    w = Math.abs(w);
	   	    h = Math.abs(h);

		    Mat imgfinal = Highgui.imread(arr.getURL(),Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		    Rect ret = new Rect(c1,c2-30,w,h+15);
		   
		    cropped = new Mat(imgfinal,ret); 
		    Highgui.imwrite(arr.getURL2(), cropped);
		    notify();
		 
		    System.out.println("Cropped image saved successfully.");
		    System.out.print(c1);
		    System.out.print(c2);
		    dispose();
		    
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		repaint();
		drag_status=1;
        c3=arg0.getX();
	    c4=arg0.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		c2=arg0.getY();
		c1=arg0.getX();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		if(drag_status==1)
		{
		c3=arg0.getX();
		c4=arg0.getY();
		go=true;
		try
		{
		draggedScreen();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		int w = c1 - c3;
		int h = c2 - c4;
	    w=Math.abs(w);
	    h=Math.abs(h);
		//w = w * -1;
		//h = h * -1;
		//if(w<0)
		//	w = w * -1;
	    g.drawRect(c1, c2, w,h);
	  
	    
	}

}
