package model;

public class Data {
	private String imagename;
	private String X;
	private String Y;
	private String Width;
	private String Height;
	private String Cartype;
	private String Colr;
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	public String getWidth() {
		return Width;
	}
	public void setWidth(String width) {
		Width = width;
	}
	public String getHeight() {
		return Height;
	}
	public void setHeight(String height) {
		Height = height;
	}
	public String getCartype() {
		return Cartype;
	}
	public void setCartype(String cartype) {
		Cartype = cartype;
	}
	public String getColr() {
		return Colr;
	}
	public void setColr(String colr) {
		Colr = colr;
	}
	public Data(String imagename, String x, String y, String width, String height, String cartype, String colr) {
		super();
		this.imagename = imagename;
		X = x;
		Y = y;
		Width = width;
		Height = height;
		Cartype = cartype;
		Colr = colr;
	}
	
	
}
