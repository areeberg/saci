package br.com.pontov.frame;

public class MyThreadftest extends Thread{
	private Caractest arrtest;
	public MyThreadftest(Caractest arrtest){
	this.arrtest=arrtest;
}
	
	public void run()
	{
		System.out.println("Achando as caracteristicas");
	  new Featurestest(arrtest).findContour();
  	  new Autocroptest(arrtest).start(arrtest.getBox());
      new Histogramtest(arrtest).start();
	}
	
}
