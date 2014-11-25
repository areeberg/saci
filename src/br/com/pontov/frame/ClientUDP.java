package br.com.pontov.frame;

import java.io.*; 
import java.net.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class ClientUDP {
	
	
	public static void send(int control, double[] check){

		try {
			// nome do servidor
			String serverHostname = new String ("localhost");

//			if (args.length > 0) {
//				serverHostname = args[0];
//			}

			// cria socket
			DatagramSocket clientSocket = new DatagramSocket(); 

			// obtendo endereço IP
			InetAddress IPAddress = InetAddress.getByName(serverHostname); 
			System.out.println ("Attempting to connect to " + IPAddress + " via UDP port 9876");

			/* ================================================================================ */
			// leitura do teclado
			//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			
			DecimalFormat df = new DecimalFormat("0.0");
			DecimalFormatSymbols dfs=new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
	
			
			byte[] sendData = new byte[1024]; // byte de envio
			byte[] receiveData = new byte[1024]; // byte de recebimento

			//System.out.print("Enter Message: "); // mensagem para o usuário
			//String sentence = inFromUser.readLine(); // leitura da mensagem do usuário
			//sendData = sentence.getBytes(); // armazena a mensagem do usuário no byte de envio
			double controle=control;
			
			//System.out.print("Enviando messagem com X = (" + df.format(x) + ") e Y = (" + df.format(y) + ")");
			String dataOut = "MSG;" + String.valueOf(df.format(controle)) + ";"+ String.valueOf(df.format(check[0])) + ";" + String.valueOf(df.format(check[1])) + ";" + String.valueOf(df.format(check[2])) + ";" + String.valueOf(df.format(check[3]))+ ";#";
			//String dataOut = "MSG;" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";#";
			sendData = dataOut.getBytes();

			//System.out.println ("Sending data to " + sendData.length + " bytes to server.");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); // montando pacote de envio 

			clientSocket.send(sendPacket); // socket envia o pacote da mensagem 

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // montando pacote de recebimento

			System.out.println ("Waiting for return packet");
			clientSocket.setSoTimeout(10000); // timeout com o tempo de espera por uma resposta

			try {
				clientSocket.receive(receivePacket); // socket espera o recebimento do pacote
				String dataIn = new String(receivePacket.getData()); // armazenamento do conetúdo do pacote da mensagem recebida (msg modificada)

				InetAddress returnIPAddress = receivePacket.getAddress(); // obtendo o endereço IP do pacote da mensagem recebida

				int port = receivePacket.getPort(); // obtendo endereço da porta do pacote da mensagem
				
				

				System.out.println ("From server at: " + returnIPAddress + ":" + port);
				System.out.println("Message: " + dataIn); 

			} catch (SocketTimeoutException ste) {
				System.out.println ("Timeout Occurred: Packet assumed lost");
			}
			/* ================================================================================ */
			
			clientSocket.close();
			
		} catch (UnknownHostException ex) { 
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
		
	public static void finish(){

		try {
			// nome do servidor
			String serverHostname = new String ("localhost");

			DatagramSocket clientSocket = new DatagramSocket(); 

			// obtendo endereço IP
			InetAddress IPAddress = InetAddress.getByName(serverHostname); 
			System.out.println ("Attempting to connect to " + IPAddress + " via UDP port 9876");

	
			byte[] sendData = new byte[1024]; // byte de envio
			byte[] receiveData = new byte[1024]; // byte de recebimento

	
			String dataOut = "MSG;#";
			sendData = dataOut.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); // montando pacote de envio 

			clientSocket.send(sendPacket); // socket envia o pacote da mensagem 

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // montando pacote de recebimento

			System.out.println ("Waiting for return packet");
			clientSocket.setSoTimeout(10000); // timeout com o tempo de espera por uma resposta

			try {
				clientSocket.receive(receivePacket); // socket espera o recebimento do pacote
				String dataIn = new String(receivePacket.getData()); // armazenamento do conetúdo do pacote da mensagem recebida (msg modificada)

				InetAddress returnIPAddress = receivePacket.getAddress(); // obtendo o endereço IP do pacote da mensagem recebida

				int port = receivePacket.getPort(); // obtendo endereço da porta do pacote da mensagem
				
				

				System.out.println ("From server at: " + returnIPAddress + ":" + port);
				System.out.println("Message: " + dataIn); 

			} catch (SocketTimeoutException ste) {
				System.out.println ("Timeout Occurred: Packet assumed lost");
			}
			/* ================================================================================ */
			
			clientSocket.close();
			
		} catch (UnknownHostException ex) { 
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	

	public static void board(){

		try {
			// nome do servidor
			String serverHostname = new String ("localhost");

			DatagramSocket clientSocket = new DatagramSocket(); 

			// obtendo endereço IP
			InetAddress IPAddress = InetAddress.getByName(serverHostname); 
			System.out.println ("Attempting to connect to " + IPAddress + " via UDP port 9876");

	
			byte[] sendData = new byte[1024]; // byte de envio
			byte[] receiveData = new byte[1024]; // byte de recebimento
			double controle=1.0;

			DecimalFormat df = new DecimalFormat("0.0");
			
//			String dataOut = "MSG;"+ String.valueOf(df.format(controle))+";#";
			String dataOut = "MSG;1.0;#";
			sendData = dataOut.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); // montando pacote de envio 

			clientSocket.send(sendPacket); // socket envia o pacote da mensagem 

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // montando pacote de recebimento

			System.out.println ("Waiting for return packet");
			clientSocket.setSoTimeout(10000); // timeout com o tempo de espera por uma resposta

			try {
				clientSocket.receive(receivePacket); // socket espera o recebimento do pacote
				String dataIn = new String(receivePacket.getData()); // armazenamento do conetúdo do pacote da mensagem recebida (msg modificada)

				InetAddress returnIPAddress = receivePacket.getAddress(); // obtendo o endereço IP do pacote da mensagem recebida

				int port = receivePacket.getPort(); // obtendo endereço da porta do pacote da mensagem
				
				

				System.out.println ("From server at: " + returnIPAddress + ":" + port);
				System.out.println("Message: " + dataIn); 

			} catch (SocketTimeoutException ste) {
				System.out.println ("Timeout Occurred: Packet assumed lost");
			}
			/* ================================================================================ */
			
			clientSocket.close();
			
		} catch (UnknownHostException ex) { 
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	
	
	public static void main(String args[]) throws Exception {
	
		try {
			// nome do servidor
			String serverHostname = new String ("localhost");

			if (args.length > 0) {
				serverHostname = args[0];
			}

			// cria socket
			DatagramSocket clientSocket = new DatagramSocket(); 

			// obtendo endereço IP
			InetAddress IPAddress = InetAddress.getByName(serverHostname); 
			System.out.println ("Attempting to connect to " + IPAddress + " via UDP port 9876");

			/* ================================================================================ */
			// leitura do teclado
			//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			
			DecimalFormat df = new DecimalFormat("0.000");
			
			double x = Math.random()*10;
			double y = Math.random()*10;
			double z = Math.random()*10;

			byte[] sendData = new byte[1024]; // byte de envio
			byte[] receiveData = new byte[1024]; // byte de recebimento

			//System.out.print("Enter Message: "); // mensagem para o usuário
			//String sentence = inFromUser.readLine(); // leitura da mensagem do usuário
			//sendData = sentence.getBytes(); // armazena a mensagem do usuário no byte de envio
			
			//System.out.print("Enviando messagem com X = (" + df.format(x) + ") e Y = (" + df.format(y) + ")");
			String dataOut = "MSG;" + String.valueOf(df.format(x)) + ";" + String.valueOf(df.format(y)) + ";" + String.valueOf(df.format(z)) + ";#";
			//String dataOut = "MSG;" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";" + String.valueOf(df.format(1)) + ";#";
			sendData = dataOut.getBytes();

			//System.out.println ("Sending data to " + sendData.length + " bytes to server.");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); // montando pacote de envio 

			clientSocket.send(sendPacket); // socket envia o pacote da mensagem 

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // montando pacote de recebimento

			System.out.println ("Waiting for return packet");
			clientSocket.setSoTimeout(10000); // timeout com o tempo de espera por uma resposta

			try {
				clientSocket.receive(receivePacket); // socket espera o recebimento do pacote
				String dataIn = new String(receivePacket.getData()); // armazenamento do conetúdo do pacote da mensagem recebida (msg modificada)

				InetAddress returnIPAddress = receivePacket.getAddress(); // obtendo o endereço IP do pacote da mensagem recebida

				int port = receivePacket.getPort(); // obtendo endereço da porta do pacote da mensagem
				
				

				System.out.println ("From server at: " + returnIPAddress + ":" + port);
				System.out.println("Message: " + dataIn); 

			} catch (SocketTimeoutException ste) {
				System.out.println ("Timeout Occurred: Packet assumed lost");
			}
			/* ================================================================================ */
			
			clientSocket.close();
			
		} catch (UnknownHostException ex) { 
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
