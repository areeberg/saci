package br.com.pontov.frame;

public class MyThreadc extends Thread{
	private Carac arr;
	public MyThreadc(Carac arr){
	this.arr=arr;
}
	
	
	public void run()
	{
		System.out.println("Corte a imagem");
		try {
			new Crop(arr).start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
