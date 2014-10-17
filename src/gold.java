import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.pontov.frame.Autocrop;
import br.com.pontov.frame.Carac;
import br.com.pontov.frame.Crop;
import br.com.pontov.frame.Features;
import br.com.pontov.frame.Histogram;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;


public class gold extends JDialog {
	

	private final JPanel contentPanel = new JPanel();

	//DECLARE GLOBAL VARIABLES
	public static Carac[] arr= new Carac[200];
	int controlcode=1;
	static int nfdir;
	/**
	 * Launch the application.
	 */
	public static void callgold() {
		try {
			System.loadLibrary("opencv_java249");
			
			nfdir = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages").listFiles().length;   //quantidade de arquivos gold
			int code=1;
			String dir="";
			 for (int i=0;i<nfdir+200;i++)
		      {
		    	  for (int k=0;k<nfdir+10;k++)
		    	  {
		    		  dir = "/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+".png";
		      File photo=new File (dir);
		    	 if (photo.isFile())
		    	 {
		    		 arr[code]= new Carac();
		    		 arr[code].setName(dir);
		    		arr[code].setCode(code); 
		    		arr[code].setName2("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"a.png");
		    		arr[code].setName3("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/GoldenImages/Img"+i+"Defect"+k+"b.png");
		    		code++;
		    	 }//end bracket if
		    	 }//end bracket for k		    	  
		      }// end bracket for i
			
			 
			//CRIA O DIALOG MODAL DO TIPO DOCUMENTAL
			gold dialog = new gold(Dialog.ModalityType.DOCUMENT_MODAL);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public gold(ModalityType documentModal) {
	
		setBounds(100, 100, 400, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));	
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//texto de informacao
		String qtd = "";
		qtd.valueOf(nfdir);
		JTextPane txtpnP = new JTextPane();
		txtpnP.setText(qtd);
		txtpnP.setBounds(256, 35, 138, 42);
		contentPanel.add(txtpnP);
		
	//	if(controlcode<nfdir)
		//TEM QUE SER O WHILE, PARA SE CRIAR OS BOTOES
		
			
		
		contentPanel.setLayout(null);
		{
			JButton okButton = new JButton("Crop");
			okButton.setBounds(27, 22, 75, 29);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new Crop(arr[controlcode]).start();
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
			cancelButton.setBounds(136, 22, 92, 29);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//ACTIONS FROM MYTHREADF
					 new Features(arr[controlcode]).findContour();
				  	  new Autocrop(arr[controlcode]).start(arr[controlcode].getBox());
				      new Histogram(arr[controlcode]).start();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		
		JButton btnFinish = new JButton("Next/Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlcode++;
				System.out.println("Next Image");
				
			}
		});
		btnFinish.setBounds(6, 63, 117, 29);
		contentPanel.add(btnFinish);
		{
			JButton btnSaveInfo = new JButton("Save Info");
			btnSaveInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File fileg = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info.txt");
				       
					// if file doesnt exists, then create it
					if (!fileg.exists()) {
						try {
							fileg.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					FileWriter fw = null;
					try {
						fw = new FileWriter(fileg.getAbsoluteFile());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BufferedWriter bw = new BufferedWriter(fw);
					String content = new String();

					content = "@relation info\n@attribute code numeric\n@attribute contour numeric\n@attribute elongation numeric\n@attribute angle numeric\n@attribute c1 numeric\n@attribute c2 numeric\n@attribute c3 numeric\n@attribute c4 numeric\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data";

					for (int i=1;i<controlcode;i++)  //numero de arquivos salvos para treinamento
					{
					content += "\n"+arr[i].getCode()+","+arr[i].getContour()+","+arr[i].getElongation()+","+arr[i].getAngle()+","+arr[i].getC1()+","+arr[i].getC2()+","+arr[i].getC3()+","+arr[i].getC4()+","+arr[i].getType()+"";		
					}
					System.out.print(content);
					try {
						bw.write(content);
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//---------------------------------------------------------------------------------
					
					File fileg2 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info2.txt");
				       
					// if file doesnt exists, then create it
					if (!fileg2.exists()) {
						try {
							fileg2.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					FileWriter fw2 = null;
					try {
						fw2 = new FileWriter(fileg2.getAbsoluteFile());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BufferedWriter bw2 = new BufferedWriter(fw2);
					String content2 = new String();
					content2 = "@relation info2\n@attribute histograma numeric\n\n@data";
					for (int i=1;i<controlcode;i++)  //numero de arquivos salvos para treinamento
					{
					content2 += "\n"+arr[i].getAngle()+","+arr[i].getType()+"";		 //Angulo, tipo, centroid, 
					}
					System.out.print(content2);
					try {
						bw2.write(content2);
						bw2.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//---------------------------------------------------------------------------------
					File fileg3 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/info3.txt");
				       
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
					content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@attribute absdiff real\n@attribute type {Capacitor,ResistorSmall,ResistorBig,Schmitt trigger,Transistor,PowerTransistor}\n@data";
					//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
					for (int i=1;i<controlcode;i++)  //numero de arquivos salvos para treinamento
					{
					content3 += "\n"+arr[i].getNumbins()+","+arr[i].getPct2pk()+","+arr[i].getBimodalap()+","+arr[i].getType()+"";		
					//content3 += "\n"+arr[i].getNumbins()+","+arr[i].getPct2pk()+","+arr[i].getBimodalap()+"";
					}
					System.out.print(content3);
					try {
						bw3.write(content3);
						bw3.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//---------------------------------------------------------------------------------
					File fileg4 = new File("/Users/alexandrermello/Documents/GoldenImages/PCB_ID15V0/InspectionImages/fourierdesc.txt");
				       
					// if file doesnt exists, then create it
					if (!fileg4.exists()) {
						try {
							fileg4.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					FileWriter fw4 = null;
					try {
						fw4 = new FileWriter(fileg4.getAbsoluteFile());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BufferedWriter bw4 = new BufferedWriter(fw4);
					String content4 = new String();
					
					// "Capacitor", "ResistorSmall","ResistorBig", "Schmitt trigger","Transistor","PowerTransistor"
					content4 = "Descritores de Fourier - Golden Images (em ordem de aquisição de imagens)";
					//content3 = "@relation info3\n@attribute numBins real\n@attribute pct2pk real\n@data";
					for (int i=1;i<controlcode;i++)  //numero de arquivos salvos para treinamento
					{
					content4 +="\n"+arr[i].getDescri()[0]+","+arr[i].getDescri()[1]+","+arr[i].getDescri()[2]+","+arr[i].getDescri()[3]+","+arr[i].getDescri()[4]+","+arr[i].getDescri()[5]+","+arr[i].getDescri()[6]+","+arr[i].getDescri()[7]+","+arr[i].getDescri()[8]+","+arr[i].getDescri()[9]+","+arr[i].getDescri()[10]+","+arr[i].getDescri()[11]+","+arr[i].getDescri()[12]+","+arr[i].getDescri()[13]+","+arr[i].getDescri()[14]+","+arr[i].getDescri()[15]+","+arr[i].getDescri()[16]+","+arr[i].getDescri()[17]+","+arr[i].getDescri()[18]+","+arr[i].getDescri()[19]+""; 
					}
					System.out.print(content4);
					try {
						bw4.write(content4);
						bw4.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}// end if filegi exists
				}
			);
			btnSaveInfo.setBounds(127, 63, 117, 29);
			contentPanel.add(btnSaveInfo);
		}
		
		

	}
}
