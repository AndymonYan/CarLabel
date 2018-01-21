package Dao;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import model.imageInfo;

public class ImportDB {
	private static JdbcTemplate temp = new JdbcTemplate();
	
	public static void main(String[] args) throws Exception {
		connectToDatabase();
		//creattable();
		traverseFolder1("C:/Users/yanya/Desktop/新建文件夹");
	}
	
	public static void connectToDatabase() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");

		ds.setUrl("jdbc:mysql://localhost:3306/label_car?useUnicode=true&characterEncoding=UTF-8");
		ds.setUsername("root");
		ds.setPassword("nanda2013");
		temp.setDataSource(ds);
	}
	public static void insertImageData(String name) {
		//int id = image.getId();
		//int beLabeled = image.getBeLabeled();
		//String url = image.getUrl();
		/*
		 * int id = 1; String url = "www.baidu.com"; int beLabeled = 1;
		 */
		String sqlStr = "INSERT INTO `images` (url)" + "VALUES(?)";
		String url = "http://localhost:8080/jsonpstudy/resource/"+name;
		Object[] args1 = { url };
		temp.update(sqlStr, args1);
	}
	
	public static void creattable() {
		String sql = "CREATE TABLE images "
				+ "("
				+ "id INT not null primary key auto_increment,"
				+ " url VARCHAR(500),"
				+ "be_labeled int default 0);";
				
		temp.execute(sql);
	}
	
	public static void traverseFolder1(String path) throws IOException {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getName());
                    
                    list.add(file2);
                    folderNum++;
                } else {
                    System.out.println("文件:" + file2.getName());
                    insertImageData(file2.getName());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getName());
                        list.add(file2);
                        folderNum++;
                    } else {
                        System.out.println("文件:" + file2.getName());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);

    }
	
	
}
