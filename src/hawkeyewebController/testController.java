package hawkeyewebController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import Dao.Mysqltest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Data;
import model.imageInfo;
import model.labelsInfo;
import net.sf.json.JSONArray;

@Controller
public class testController {
	List<Data> ds = new ArrayList<Data>();
	public static String tempUrl = null;
	public static int count = 0;
	// public static int labelId = 1;
	// public static int imageId = 0;
	/*
	 * public testController(){ ds.add(new Data("/labelDemo/resource/labeling.jpg",
	 * "50px", "109px", "232px", "191px", "car2", "color2")); ds.add(new
	 * Data("/labelDemo/resource/test.jpg", "70px", "10px", "22px", "19px", "car2",
	 * "color2")); }
	 */
	
	@RequestMapping("/submit.model")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String imagename = request.getParameter("imagename");
		String X = request.getParameter("X");
		String Y = request.getParameter("Y");
		String Width = request.getParameter("Width");
		String Height = request.getParameter("Height");
		String Cartype = request.getParameter("Cartype");
		String Colr = request.getParameter("Colr");
		String imagename1 = imagename.substring(1, imagename.length() - 1);
		// int labelId = 1;
		// int imageId = 0;
		System.out.println("imagename: " + imagename1 + " X:" + X + " Y: " + Y + " Width:" + Width + " Height:" + Height
				+ " Cartype: " + Cartype + " Colr:" + Colr);
		labelsInfo labels = new labelsInfo();
		//imageInfo image = new imageInfo();
		// if ((this.tempImageName.equals(imagename1)==false) && (image.getBeLabeled()
		// != 1)) {
		// imageId++;
		// image.setId(imageId);
		//image.setBeLabeled(1);
		//image.setUrl(imagename1);
		Mysqltest.connectToDatabase();
		// Mysqltest.getImageId(imagename1);
		//Mysqltest.updateImageData(image);
		// Mysqltest.insertImageData(image);
		// }
		/*
		 * image.setId(imageId); image.setBeLabeled(1);
		 * image.setUrl("/labelDemo/WebContent/resource/" + imagename + ".jpg");
		 */
		// labels.setLabel_id(labelId);
		// System.out.println(image.getId());
		labels.setImage_id(Mysqltest.getImageId(imagename1));
		// labelId++;
		labels.setX(X);
		labels.setY(Y);
		labels.setWidth(Width);
		labels.setHeight(Height);
		labels.setCartype(Cartype);
		labels.setColr(Colr);
		// this.tempImageName = imagename1;
		Mysqltest.connectToDatabase();
		Mysqltest.insertLabelData(labels);
		// Mysqltest.insertImageData(image);
	}

	@RequestMapping("/unlabeledImage.model")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		try {
			Mysqltest.connectToDatabase();
			String url = Mysqltest.nextPicture();
			Mysqltest.setBelabeled(url);
			resp.getWriter().write(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteLabel.model")
	protected void doGet4(HttpServletRequest request, HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		try {
			Mysqltest.connectToDatabase();
			int ID = Mysqltest.getImageId(tempUrl);
			Mysqltest.deteleLabel(ID);
			Mysqltest.updateImageDataDelete(ID);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/nextImage.model")
	protected void doGet1(HttpServletRequest request, HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		try {
			// String url ="http://localhost:8080/jsonpstudy/resource/1.jpg";
			Mysqltest.connectToDatabase();
            String url = Mysqltest.doneLabeled(count);
            
			//String url = Mysqltest.nextPicture();
			//tempUrl = url;
			// System.out.print(url);
			resp.getWriter().write(url);
			tempUrl = url;
			System.out.println(tempUrl);
			count++;
			if(count==Mysqltest.NumOfLabeledPics()) {
				count = 0;
			}
			ds.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/browseImage.model")
	protected void doGet3(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		try {
			/*
			 * ds.add(new Data("/labelDemo/resource/test.jpg", "81px", "109px", "232px",
			 * "191px", "car2", "color2")); ds.add(new
			 * Data("/labelDemo/resource/labeling.jpg", "88px", "10px", "22px", "19px",
			 * "car2", "color2")); ds.add(new Data("/labelDemo/resource/test.jpg", "81px",
			 * "109px", "232px", "191px", "car2", "color2"));
			 */
			// Mysqltest.browse();
			Mysqltest.connectToDatabase();
			/*
			 * int num = Mysqltest.NumOfLabeledPics(); for (int j = 1; j <= num; j++) {
			 * List<labelsInfo> imageList = Mysqltest.browse(j); for (int i = 0; i <
			 * imageList.size(); i++) { String url = Mysqltest.getImageUrl(i); ds.add(new
			 * Data(url, imageList.get(i).getX(), imageList.get(i).getY(),
			 * imageList.get(i).getWidth(), imageList.get(i).getHeight(),
			 * imageList.get(i).getCartype(), imageList.get(i).getColr())); } }
			 */
            int ID = Mysqltest.getImageId(tempUrl);
            System.out.println(ID);
			List<labelsInfo> imageList = Mysqltest.browse(ID);
			for (int i = 0; i < imageList.size(); i++) {
				//String url = Mysqltest.getImageUrl(ID);
				// System.out.print(url);
				ds.add(new Data(tempUrl, imageList.get(i).getX(), imageList.get(i).getY(), imageList.get(i).getWidth(),
						imageList.get(i).getHeight(), imageList.get(i).getCartype(), imageList.get(i).getColr()));
			}
			
			JSONArray jsonArray = new JSONArray();
			for (Object object : ds) {
				jsonArray.add(object);
			}
			//resp.getWriter().write(tempUrl);
			resp.getWriter().write(jsonArray.toString());
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
