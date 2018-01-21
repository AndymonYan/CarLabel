package Dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.SAXException;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import bsh.This;
import model.imageInfo;
import model.labelsInfo;

//import Domain.model;

public class Mysqltest {

	private static JdbcTemplate temp = new JdbcTemplate();
	//private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static int temp2;
	public static String URL;
	public static String uesrName;
	public static String passWord;
	public static int ID = 1;

	public static void main(String[] args) throws Exception {

		connectToDatabase();
        //List<String> url = getImageUrl(1);
        //System.out.println(url.get(0));
		//String x= nextPicture();
		//System.out.print(x);
		System.out.print(NumOfLabeledPics());
	}
    	
	public static void connectToDatabase() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");

		ds.setUrl("jdbc:mysql://localhost:3306/labelme_db?useUnicode=true&characterEncoding=UTF-8");
		ds.setUsername("root");
		ds.setPassword("nanda2013");
		temp.setDataSource(ds);
	}

	public static void creattable() {
		String sql = "CREATE TABLE user_in (ID INT(60) primary key, EmailUrl VARCHAR(100),"
				+ " name VARCHAR(60), password VARCHAR(100), Company VARCHAR(20));";
		temp.execute(sql);
	}

    public static void deteleLabel(int ID) {
    	String sqlStr = " DELETE FROM labels WHERE image_id=?";
    	Object[] args1 = {ID};
		temp.update(sqlStr,args1);	
    }
	
	
	public static int getImageId(String url) {
		String sqlStr = "SELECT id,url,be_labeled FROM images WHERE url=?";
		RowMapper<imageInfo> rowMapper = new BeanPropertyRowMapper<>(imageInfo.class);
		imageInfo image = temp.queryForObject(sqlStr, rowMapper, url);
		int ImageId = image.getId();
		return ImageId;
	}
	public static int NumOfLabeledPics() {
		String sqlStr = "SELECT id,url,be_labeled FROM images WHERE be_labeled=1";
		RowMapper<imageInfo> rowMapper = new BeanPropertyRowMapper<>(imageInfo.class);
		List<imageInfo> imageList2 = temp.query(sqlStr, rowMapper);
		return imageList2.size();
	}
	public static String getImageUrl(int i) {
		String sqlStr = "SELECT id,url,be_labeled FROM images WHERE id=?";
		RowMapper<imageInfo> rowMapper = new BeanPropertyRowMapper<>(imageInfo.class);
		//List<imageInfo> imageList2 = temp.query(sqlStr, rowMapper,i);
		//java.util.ListIterator<imageInfo> lit2 = imageList2.listIterator();
		imageInfo image = temp.queryForObject(sqlStr, rowMapper, i);
		String ImageUrl = image.getUrl();
	    return ImageUrl;

	}
    public static String doneLabeled(int num){
    	String sqlStr = "SELECT id,url,be_labeled FROM images WHERE be_labeled=1";
    	RowMapper<imageInfo> rowMapper = new BeanPropertyRowMapper<>(imageInfo.class);
    	List<imageInfo> imageList2 = temp.query(sqlStr, rowMapper);
    	List<String> urlList = new ArrayList<>();
        for(int i=0;i<imageList2.size();i++) {
        	urlList.add(imageList2.get(i).getUrl());
        }
    	return urlList.get(num);
    }
    
    
    
    
    
    
	public static String nextPicture() {
		String sqlStr = "SELECT id,url,be_labeled FROM images WHERE be_labeled<1";
		RowMapper<imageInfo> rowMapper = new BeanPropertyRowMapper<>(imageInfo.class);
		List<imageInfo> imageList1 = temp.query(sqlStr, rowMapper);
		//java.util.ListIterator<imageInfo> lit1 = imageList1.listIterator();
		return imageList1.get(0).getUrl();
	}
//获得对应label_id的labelsinfo信息
	public static List<labelsInfo> browse(int i) {
		// int image_id = 1;
		String sqlStr = "SELECT image_id,x,y,width,height,cartype,colr FROM labels WHERE image_id=?";
		RowMapper<labelsInfo> rowMapper = new BeanPropertyRowMapper<>(labelsInfo.class);
		List<labelsInfo> imageList = temp.query(sqlStr, rowMapper,i);
		//java.util.ListIterator<labelsInfo> lit = imageList.listIterator();
		return imageList;
		
		 //while(lit.hasNext()) { System.out.println(lit.next().getX()); }
		 
	}
    public static void setBelabeled(String url) {
    	String sqlStr = "UPDATE images SET be_labeled=1 WHERE url=?";
    	Object[] args1 = {url};
    	temp.update(sqlStr,args1);
    	
    }
	public static void updateImageData(imageInfo image) {
		int beLabeled = image.getBeLabeled();
		String url = image.getUrl();
		String sqlStr = "UPDATE images SET be_labeled=? WHERE url=?";
		Object[] args1 = {beLabeled,url};
		temp.update(sqlStr,args1);
		//return 0;
	}
	public static void updateImageDataDelete(int ID) {
		String sqlStr = "UPDATE images SET be_labeled=0 WHERE id=?";
		Object[] args = {ID};
		temp.update(sqlStr,args);
	}
/*	public static int updateImageData(int i) {
		int beLabeled = i;
		String sqlStr = "UPDATE images SET be_labeled=? WHERE";
		Object[] args1 = {beLabeled};
		temp.update(sqlStr,args1);
		return 0;
	}*/
	
	
	public static void insertImageData(imageInfo image) {
		int id = image.getId();
		int beLabeled = image.getBeLabeled();
		String url = image.getUrl();
		/*
		 * int id = 1; String url = "www.baidu.com"; int beLabeled = 1;
		 */
		String sqlStr = "INSERT INTO `images` (id,url,be_labeled)" + "VALUES(?,?,?)";
		Object[] args1 = { id, url, beLabeled };
		temp.update(sqlStr, args1);
	}

	
	public static void insertLabelData(labelsInfo labels) {
		/*
		 * for(Iterator<labelsInfo> iter = labels.iterator();iter.hasNext();) {
		 * System.out.println(1); }
		 */
		/*
		 * labelsInfo label = new labelsInfo(); label.setLabel_id(5);
		 * label.setImage_id(1); label.setX(x);
		 */
		//int labelId = labels.getLabel_id();
		int imageId = labels.getImage_id();
		String x = labels.getX();
		String y = labels.getY();
		String width = labels.getWidth();
		String height = labels.getHeight();
		String carType = labels.getCartype();
		String colr = labels.getColr();

		/*
		 * int labelId = 8; int imageId = 2; String x = "1"; String y = "1"; String
		 * width = "1"; String height = "1"; String carType = "1"; String colr = "1";
		 */
		String sql = "INSERT INTO `labels` (image_id,x,y,width,height,cartype,colr)"
				+ "VALUES(?,?,?,?,?,?,?)";
		Object[] args = { imageId, x, y, width, height, carType, colr };
		// Object args = new Object[] {x,y,width,height,carType,colr};
		// sql = String.format(sql, args);
		temp.update(sql, args);

	}
}