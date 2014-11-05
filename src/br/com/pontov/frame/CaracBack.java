package br.com.pontov.frame;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class CaracBack {
	private String URL;
	private String URL2;
	private String URL3;
	private int code;
	private double contour;
	private float elongation;
	private float angle;
	private Rect box;
	private double[] histo;
	private String type;
	private double numBins;
	private double pct2pk;
	private double bimodalap;
	private int c1;
	private int c2;
	private int c3;
	private int c4;
	private double[] descri;
	private Point centro;
	private float centrox;
	private float centroy;
	
	
	public CaracBack (String URL,String URL2,String URL3, int code,double contour,float elongation,float angle,Rect box,double[] histo,String type,double numBins, double pct2pk, double bimodalap, int c1, int c2, int c3, int c4,double[] descri,Point centro,float centrox, float centroy)
	{
		this.URL = URL;
		this.URL2 = URL2;
		this.URL3 = URL3;
	    this.code = code;
	    this.contour = contour;
	    this.elongation=elongation;
	    this.angle=angle;
	    this.box=box;
	    this.histo=histo;
	    this.type=type;
	    this.numBins=numBins;
	    this.pct2pk=pct2pk;
	    this.bimodalap=bimodalap;
	    this.c1=c1;
	    this.c2=c2;
	    this.c3=c3;
	    this.c4=c4;
	    this.descri=descri;
	    this.centro=centro;
	    this.centrox=centrox;
	    this.centroy=centroy;
	}
	public CaracBack()
	{}
	
	//--------GETTER
	public String getURL(){
		return URL;
	} 
	public String getURL2(){
		return URL2;
	}
	public String getURL3(){
		return URL3;
	}
	public int getCode(){
		return code;
	}
	public double getContour(){
		return contour;
	}
	public float getElongation(){
		return elongation;
	}
	public float getAngle(){
		return angle;
	}
	public Rect getBox(){
		return box;
	}
	public double[] getHisto(){
		return histo;
	}
	public String getType(){
		return type;
	}
	public double getNumbins(){
		return numBins;
	}
	public double getPct2pk(){
		return pct2pk;
	}
	public double getBimodalap(){
		return bimodalap;
	}
	public int getC1(){
		return c1;
	}
	public int getC2(){
		return c2;
	}
	public int getC3(){
		return c3;
	}
	public int getC4(){
		return c4;
	}
	public double[]getDescri(){
		return descri;
	}
	public Point getCentro(){
		return centro;
	}
	public float getCentrox(){
		return centrox;
	}
	public float getCentroy(){
		return centroy;
	}
	
	//--------SETTER
	public void setName(String URL){
		this.URL=URL;
	}
	public void setName2(String URL2){
		this.URL2=URL2;
	}
	public void setName3(String URL3){
		this.URL3=URL3;
	}
	public void setCode(int code){
		this.code=code;
	}
	public void setContour(double contour){
		this.contour=contour;
	}
	public void setElongation(float elongation){
		this.elongation=elongation;
	}
	public void setAngle(float angle){
		this.angle=angle;
	}
	public void setBox(Rect box){
		this.box=box;
	}
	public void setHisto(double[] temp){
		this.histo=temp;
	}
	public void setType(String type){
		this.type=type;
	}
	public void setNumBins(double numBins){
		this.numBins=numBins;
	}
	public void setPct2pk(double pct2pk){
		this.pct2pk=pct2pk;
	}
	public void setBimodalap(double bimodalap){
		this.bimodalap=bimodalap;
	}
	public void setC1(int c1){
		this.c1=c1;
	}
	public void setC2(int c2){
		this.c2=c2;
	}
	public void setC3(int c3){
		this.c3=c3;
	}
	public void setC4(int c4){
		this.c4=c4;
	}
	public void setDescri(double[] descri){
		this.descri=descri;
	}
	public void setCentro(Point centro){
		this.centro=centro;
	}
	public void setCentrox(float centrox){
		this.centrox=centrox;
	}
	public void setCentroy(float centroy){
		this.centroy=centroy;
	}
}
