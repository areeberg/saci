import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import br.com.pontov.frame.Crop;
import br.com.pontov.frame.Knear;
import br.com.pontov.frame.NNO;
import br.com.pontov.frame.NNeuroph;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import java.awt.Color;


public class controlpanel extends JFrame {

	private JPanel contentPane;
	private String status=">_";
	public static String uplog="Log Info";
	private int cont=1;
	private int click=1;
	private int knncontrol=1;
	private int nncontrol=0;
	private int  nfdir;
	private testimages ti= new testimages();
	private Knear kn = new Knear();
	private NNeuroph nh = new NNeuroph();
	private static Dialog gd;
	

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) throws IOException {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					controlpanel frame = new controlpanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	


	/**
	 * Create the frame.
	 */
	public controlpanel() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
		nfdir=nfdir-1;
	
		
		
		
		JButton btnGetGoldenImages = new JButton("Get Golden Images");
		btnGetGoldenImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//goldenimages.start();
				gold.callgold();
			}
		});
		btnGetGoldenImages.setBounds(5, 5, 162, 69);
		contentPane.add(btnGetGoldenImages);
		
		JButton btnGetTestImage = new JButton("Get Test Image");
		btnGetTestImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		
					
					cont=ti.start();
					
					if(click<=cont)
					{
					ti.init(click);
					
					click++;
					knncontrol++;
					nncontrol++;
					}
					else
					{
						System.out.println("No more test images!");
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGetTestImage.setBounds(5, 143, 162, 69);
		contentPane.add(btnGetTestImage);
		
		JButton btnCreateNnInput = new JButton("Create/Update NN input");
		btnCreateNnInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.loadLibrary("opencv_java249");
				Mat train_data;
				Mat train_labels;
				Mat images = new Mat();
				
				String dir;
				
				 
			    for (int i=0;i<nfdir+200;i++)
			      {
			    	  for (int k=0;k<nfdir;k++)
			    	  {
				
				dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"b.png";
				
				 File photo=new File (dir);
		    	 if (photo.isFile())
		    	 {
		    			Mat target = Highgui.imread(dir,CvType.CV_8UC1);
		    		    images.push_back(target);
		    		    
		    	 }
			    	  }
			      }
			    //System.out.print(images.size());

			    try {
					NNO.readFile(15,"/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO.txt");
					NNO.readFiletest(1,"/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO2.txt",nncontrol); 
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				String nnos= ">_NN Created";
				//txtInfo.setText(nnos);
				
				//nnos="";
				
			}
		});
		btnCreateNnInput.setBounds(232, 73, 196, 29);
		contentPane.add(btnCreateNnInput);
		
		JButton btnKnnTrain = new JButton("KNN Analysis");
		btnKnnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//KNN TRAIN
				try {
				String ans="";
					ans=kn.KNN(knncontrol-1);
			
					//escrever no log a resposta
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnKnnTrain.setBounds(481, 5, 117, 29);
		contentPane.add(btnKnnTrain);
		
		JButton btnTrainNn = new JButton("Train NN");
		btnTrainNn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//train NN
				try {
					
					nh.train();
			
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnTrainNn.setBounds(274, 102, 117, 29);
		contentPane.add(btnTrainNn);
		
		JButton btnNnClassify = new JButton("NN classify");
		btnNnClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					nh.classify(nncontrol);
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNnClassify.setBounds(481, 58, 117, 29);
		contentPane.add(btnNnClassify);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 320, 552, 152);
		contentPane.add(textArea);
		
	
	
		
	}
}
