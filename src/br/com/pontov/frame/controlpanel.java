package br.com.pontov.frame;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.swing.JScrollBar;


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
	private PrintStream standardOut;
	private PrintStream standardErr;
	private JTextField txtXx;
	private JTextField txtXx_1;
	
	/**
	 * Launch the application.
	 */
	
	
	//------------------------System.out to textArea----------------------------------------
	public class CustomOutputStream extends OutputStream {
	    private JTextArea textArea;
	     
	    public CustomOutputStream(JTextArea textArea) {
	        this.textArea = textArea;
	    }
	     
	    @Override
	    public void write(int b) throws IOException {
	        // redirects data to the text area
	        textArea.append(String.valueOf((char)b));
	        // scrolls the text area to the end of data
	        textArea.setCaretPosition(textArea.getDocument().getLength());
	    }
	}
	//--------------------------------------------------------------------------------------
	
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
	 * @throws IOException 
	 */
	public controlpanel() throws IOException {
		
		//Cria arquivo
		File testreport = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/AgentInfo/testreport.txt");
		PrintWriter arqdesaida = null;
		arqdesaida = new PrintWriter(new FileWriter(testreport, true));
		String header="";
		header="@Image number\n@Type expected\n@Type\n@Angle expected\n@Angle\n@Distance between centroids\n@Correct Type \n@Correct Angle \n@Correct Distance";
		arqdesaida.write(header);
		arqdesaida.close();
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;
		nfdir=nfdir-1;
		
		
	
		//---------------------------------------SCROLLABLE TEXT AREA-------------------------------------------------
				JTextArea textArea = new JTextArea();
				textArea.setEditable(false);
				textArea.setBounds(6, 320, 552, 152);
				PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
				// keeps reference of standard output stream
		        standardOut = System.out;
		        standardErr = System.err;
		        // re-assigns standard output stream and error output stream
		        System.setOut(printStream);
		        System.setErr(printStream);
		       
				
				JScrollPane scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scroll.setBounds(6, 320, 552, 152);
				
				contentPane.add(scroll);
				contentPane.setVisible(true);
				
				System.out.println("Start ->");
	     //----------------------------------------MEDIDORES-------------------------------------
			int numimggold=0;
			numimggold=Count.countimagesfromfile("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info2.txt");
			
			txtXx = new JTextField();
			txtXx.setText(""+numimggold);
			txtXx.setBounds(560, 5, 134, 28);
			contentPane.add(txtXx);
			txtXx.setColumns(10);
			

			
			txtXx_1 = new JTextField();
			txtXx_1.setText(""+numimggold);
			txtXx_1.setBounds(560, 223, 134, 28);
			contentPane.add(txtXx_1);
			txtXx_1.setColumns(10);
		//---------------------------------------------GET GOLDEN IMAGES-----------------------------------------------------
		JButton btnGetGoldenImages = new JButton("Get Golden Images");
		btnGetGoldenImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//goldenimages.start();
				gold.callgold();
			}
		});
		btnGetGoldenImages.setBounds(8, 5, 177, 69);
		contentPane.add(btnGetGoldenImages);
		
		//---------------------------------------------GET TEST IMAGE-----------------------------------------------------
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
					
					int numtest=0;
					numtest=Count.counttestimages();
					numtest=numtest-click+1;
					txtXx_1.setText(""+numtest);
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
		btnGetTestImage.setBounds(8, 204, 177, 69);
		contentPane.add(btnGetTestImage);
		
		//---------------------------------------------CREATE NN INPUT-----------------------------------------------------
		JButton btnCreateNnInput = new JButton("Create NN input");
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
					//NNO.readFiletest(1,"/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/trainingNNO2.txt",nncontrol); 
					Knear.mergefiles();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				String nnos= ">_NN Created";
				//txtInfo.setText(nnos);
				
				//nnos="";
				
			}
		});
		btnCreateNnInput.setBounds(182, 5, 196, 29);
		contentPane.add(btnCreateNnInput);
		
		//---------------------------------------------TRAIN NN-----------------------------------------------------
		JButton btnTrainNn = new JButton("Train NN");
		btnTrainNn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//train NN
				try {
					new NNeuroph().train();
					//nh.train();
			
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnTrainNn.setBounds(226, 45, 117, 29);
		contentPane.add(btnTrainNn);
		
		//---------------------------------------------NN CLASSIFY / ANALYSIS-----------------------------------------------------
		JButton btnNnClassify = new JButton("NN classify");
		btnNnClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					new NNeuroph().classify(nncontrol);
					//nh.classify(nncontrol);
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNnClassify.setBounds(226, 224, 117, 29);
		contentPane.add(btnNnClassify);

		
		//---------------------------------------------BACKGROUND-----------------------------------------------------
		JButton btnGetBackgroundSamples = new JButton("Get Background Samples");
		btnGetBackgroundSamples.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//programar aqui para adquirir background samples
				Background.callbackground();
			}
		});
		btnGetBackgroundSamples.setBounds(8, 77, 177, 84);
		contentPane.add(btnGetBackgroundSamples);
		
		JLabel lblNumberOfGold = new JLabel("Number of gold images:");
		lblNumberOfGold.setBounds(403, 10, 167, 16);
		contentPane.add(lblNumberOfGold);
		
		JLabel lblTestImagesRemaining = new JLabel("Test images remaining:");
		lblTestImagesRemaining.setBounds(403, 229, 167, 16);
		contentPane.add(lblTestImagesRemaining);
		
		
		
	
		
		
	
	
		
	}
}
