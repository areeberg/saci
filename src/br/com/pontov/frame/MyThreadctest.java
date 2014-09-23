package br.com.pontov.frame;

import java.io.IOException;

public class MyThreadctest extends Thread{
	private Caractest arrtest;
	public MyThreadctest(Caractest arrtest){
	this.arrtest=arrtest;
}
	
	
	public void run()
	{
		System.out.println("Corte a imagem");
		try {
			new Croptest(arrtest).start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
