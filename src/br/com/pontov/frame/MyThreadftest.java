package br.com.pontov.frame;

public class MyThreadftest extends Thread{
	private Caractest arrtest;
	public MyThreadftest(Caractest arrtest){
	this.arrtest=arrtest;
}
	
	public void run()
	{
		System.out.println("-> Finding features");
	  new Featurestest(arrtest).findContour();
  	  new Autocroptest(arrtest).start(arrtest.getBox());
      new Histogramtest(arrtest).start();
      System.out.println("-> Features found");
	}
	
}
