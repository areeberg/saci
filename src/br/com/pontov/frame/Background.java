package br.com.pontov.frame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;


public class Background extends JDialog  {

	private final JPanel contentPanelback = new JPanel();

	//DECLARE GLOBAL VARIABLES
	public static CaracBack[] arrBack= new CaracBack[200];
	int controlcode=1;
	static int nfdir;
	private JTextField txtXx;
	private int backimg=0;

	
	/**
	 * Launch the application.
	 */
	public static void callbackground() {
		try {
			System.loadLibrary("opencv_java249");
			
			nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/Background").listFiles().length;   //quantidade de arquivos gold
			int code=1;
			String dir="";
			 for (int i=0;i<nfdir+200;i++)
		      {
		    	  for (int k=0;k<nfdir+10;k++)
		    	  {
		    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/Background/Img"+i+"Defect"+k+".png";
		      File photo=new File (dir);
		    	 if (photo.isFile())
		    	 {
		    		 arrBack[code]= new CaracBack();
		    		 arrBack[code].setName(dir);
		    		arrBack[code].setCode(code); 
		    		arrBack[code].setName2("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/Background/Img"+i+"Defect"+k+"a.png");
		    		arrBack[code].setName3("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/Background/Img"+i+"Defect"+k+"b.png");
		    		code++;
		    	 }//end bracket if
		    	 }//end bracket for k		    	  
		      }// end bracket for i
			
			 
			//CRIA O DIALOG MODAL DO TIPO DOCUMENTAL
			Background dialog = new Background(Dialog.ModalityType.DOCUMENT_MODAL);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Background(ModalityType documentModal) {
	
		setBounds(100, 100, 400, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanelback.setBorder(new EmptyBorder(5, 5, 5, 5));	
		getContentPane().add(contentPanelback, BorderLayout.CENTER);
		
		//texto de informacao
		String qtd = "";
		qtd.valueOf(nfdir);
	
		backimg=Count.countbackground();
		txtXx = new JTextField();
		txtXx.setText(""+backimg);
		txtXx.setBounds(131, 57, 134, 28);
		contentPanelback.add(txtXx);
		txtXx.setColumns(10);
		contentPanelback.setVisible(true);
			
		//----------------TEXTAREA
		JTextArea textArea1 = new JTextArea();
		textArea1.setBounds(6, 99, 388, 73);
		contentPanelback.add(textArea1);
		
		contentPanelback.setLayout(null);
		{
			JButton okButton = new JButton("Crop");
			okButton.setBounds(6, 22, 75, 29);
			contentPanelback.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new CropBack(arrBack[controlcode]).start();
						textArea1.setText("-> Image cropped successfully");
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Process");
			cancelButton.setBounds(138, 22, 92, 29);
			contentPanelback.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//ACTIONS FROM MYTHREADF
					new HistogramBack(arrBack[controlcode]).start();
					textArea1.setText("-> Features acquired");
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		
		JButton btnFinish = new JButton("Next/Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backimg=Count.countbackground();
				backimg=backimg-controlcode;
				txtXx.setText(""+backimg);
				controlcode++;
				textArea1.setText("-> Next image");
				
				
			}
		});
		btnFinish.setBounds(277, 22, 117, 29);
		contentPanelback.add(btnFinish);
		{
			JButton btnSaveInfo = new JButton("Save Info");
			btnSaveInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//---------------------------------------------------INFO KNN------------------------------
					File fileg3 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/background/info3back.txt");
				       
					// if file doesnt exists, then create it
					if (!fileg3.exists()) {
						try {
							fileg3.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					FileWriter fw3 = null;
					try {
						fw3 = new FileWriter(fileg3.getAbsoluteFile());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BufferedWriter bw3 = new BufferedWriter(fw3);
					String content3 = new String();
					
					// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
					//content3 = "@relation Background Histogram\n@attribute numBins real\n@attribute pct2pk real\n@attribute absdiff real\n@attribute type {Component,Background}\n@data";
					//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
					for (int i=1;i<controlcode;i++)  //numero de arquivos salvos para treinamento
					{
					content3 += arrBack[i].getNumbins()+","+arrBack[i].getPct2pk()+","+arrBack[i].getBimodalap()+",Background"+"\n";		
					//content3 += "\n"+arrBack[i].getNumbins()+","+arrBack[i].getPct2pk()+","+arrBack[i].getBimodalap()+"";
					}
					//System.out.print(content3);
					try {
						bw3.write(content3);
						bw3.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					textArea1.setText("-> Informations saved");
					System.out.println("All BACKGROUND features and images were saved properly");
					}// end if filegi exists
				}
			);
			btnSaveInfo.setBounds(277, 58, 117, 29);
			contentPanelback.add(btnSaveInfo);
		}
		
		{

			JLabel lblImagesRemaining = new JLabel("Images remaining:");
			lblImagesRemaining.setBounds(6, 63, 117, 16);
			contentPanelback.add(lblImagesRemaining);
			
		}

	}
}
