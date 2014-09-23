package br.com.pontov.frame;

public class MyThreadf extends Thread{
	private Carac arr;
	public MyThreadf(Carac arr){
	this.arr=arr;
}
	
	public void run()
	{
		System.out.println("Achando as caracteristicas");
	  new Features(arr).findContour();
  	  new Autocrop(arr).start(arr.getBox());
      new Histogram(arr).start();
	}
	
}
