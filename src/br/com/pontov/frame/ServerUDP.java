package br.com.pontov.frame;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerUDP extends Thread {

	ComuInfo  varonto = new ComuInfo();
	public void run()  {
		// cria socket
	
				DatagramSocket serverSocket;
				try {
					serverSocket = new DatagramSocket(9876);
			
				byte[] receiveData = new byte[1024]; // byte de recebimento
				//byte[] sendData  = new byte[1024]; // byte de envio 

				while(true) { 

					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // montando pacote de recebimento 

					//System.out.println ("Waiting for datagram packet...");

					serverSocket.receive(receivePacket); // socket espera o recebimento do pacote

					String sentence = new String(receivePacket.getData()); // armazenamento do conetúdo do pacote da mensagem recebida

					InetAddress IPAddress = receivePacket.getAddress(); // obtendo o endereço IP do pacote da mensagem recebida
					int port = receivePacket.getPort(); // obtendo endereço da porta do pacote da mensagem

				
					//System.out.println ("From: " + IPAddress + ":" + port);
					
					String uhuu = new String("status");
					String incmsg = new String();
					incmsg=sentence;
					

					if (sentence.equals(incmsg)==true)
					{
						System.out.println("MSG incoming");
						
						
					if(varonto.training==false && varonto.systrained==false)
					{
						System.out.println("System NOT trained");
					}		
					if(varonto.training==true && varonto.systrained==false)
					{
						System.out.println("Training system");
					}
					
					if(varonto.systrained==true)
					{
						System.out.println("System trained");
					}
					
					if(varonto.getgold==true)
					{
						System.out.println("Adquirindo imagens gold");
					}
					
					if(varonto.goldok==true)
					{
					System.out.println("Imagens gold adquiridas");
					}
						
					}
					else
					{
						System.out.println("MSG lost");
					}
							
				} 
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
	
	public void sendmsg(String palavra){
		try {
			// nome do servidor
			String serverHostname = new String ("localhost");

			// cria socket
			DatagramSocket clientSocket = new DatagramSocket(); 

			// obtendo endereço IP
			InetAddress IPAddress = InetAddress.getByName(serverHostname); 
			System.out.println ("Attempting to connect to " + IPAddress + " via UDP port 9876");

			byte[] sendData = new byte[1024]; // byte de envio
			byte[] receiveData = new byte[1024]; // byte de recebimento

			String dataOut = palavra;
			
			sendData = dataOut.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); // montando pacote de envio 

			clientSocket.send(sendPacket); // socket envia o pacote da mensagem 
			
			/* ================================================================================ */
			
			clientSocket.close();
			
		} catch (UnknownHostException ex) { 
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}

	}
	
}
