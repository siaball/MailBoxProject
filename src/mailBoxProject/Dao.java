package mailBoxProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Dao {
	public int idStart;
	public int idEnd;
	
	//資料庫連線
	private Connection conn;
	
	public void createConnection() throws SQLException {
		String urlStr = "jdbc:sqlserver://localhost:1433;databaseName=mailbox;"
				+ "user=sa;password=P@ssw0rd!;trustServerCertificate=true";
		this.conn = DriverManager.getConnection(urlStr);
		
		boolean status = !conn.isClosed();
		if(status) {
			System.out.println("開啟連線成功");
		}
	}
	
	public void closeConnection() throws SQLException {
		if(conn != null) {
			conn.close();
			System.out.println("成功關閉連線");
		}
	}

	
	//匯入單筆資料
	public void insertDataBy1(Vector<String> data) throws SQLException, IOException {
		this.createConnection();
		String sqlInsert = "insert into mailBoxTable values(?,?,?,?,?,?,?,?,?,?,?,?)";
		int times = 0;
		PreparedStatement preStateInsert = conn.prepareStatement(sqlInsert);
				preStateInsert.setString(1, (String)data.get(0));
				preStateInsert.setString(2, (String)data.get(1));
				preStateInsert.setString(3, data.get(2));
				preStateInsert.setString(4, (String)data.get(3));
				preStateInsert.setString(5, data.get(4));
				preStateInsert.setString(6, data.get(5));
				preStateInsert.setString(7, data.get(6));
				preStateInsert.setString(8, data.get(7));
				preStateInsert.setString(9, data.get(8));
				preStateInsert.setString(10, data.get(9));
				preStateInsert.setString(11, (String)data.get(10));
				preStateInsert.setString(12, (String)data.get(11));
				preStateInsert.executeUpdate();
				times++;
			preStateInsert.close();
			System.out.println("完成，共新增" + times + "筆資料");
			idEnd = idStart + times;
	}
	
	//匯入CSV資料
	public int insertDataByCSV(File file) throws SQLException, IOException {
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, "utf8");
			BufferedReader br = new BufferedReader(isr);
				) {
			this.createConnection();
			String sqlInsert = "insert into mailBoxTable values(?,?,?,?,?,?,?,?,?,?,?,?)";
			String sqlGetLastId = "select top 1 id from mailBoxTable order by id desc";
			PreparedStatement preStateId = conn.prepareStatement(sqlGetLastId);
			ResultSet rsId = preStateId.executeQuery();
			int id = 0;
			if(rsId.next()) {
				id = rsId.getInt(1);				
			}
			idStart = id;
			int times = 0;
			rsId.close();
			preStateId.close();
			PreparedStatement preStateInsert = conn.prepareStatement(sqlInsert);
			br.readLine();
			String line = null;
			while((line = br.readLine()) != null) {
				id++;
				String[] splitline = line.split(",");
				Member m = new Member();
				m.setId(id);
				m.setClassNumber(Integer.parseInt(splitline[0]));
				m.setClassName(splitline[1]);
				m.setClassOfMailbox(Integer.parseInt(splitline[2]));
				m.setMailboxName(splitline[3]);
				m.setAddress(splitline[4]+splitline[5]+splitline[6]+splitline[7]);
				m.setDescripAddress(splitline[8]);
				m.setMechanism(splitline[9]);
				m.setPhoneNumber(splitline[10]);
				m.setRemark(splitline[11]);
				m.setLongitude(Float.parseFloat(splitline[12]));
				m.setLatitude(Float.parseFloat(splitline[13]));
				preStateInsert.setInt(1, m.getId());
				preStateInsert.setInt(2, m.getClassNumber());
				preStateInsert.setString(3, m.getClassName());
				preStateInsert.setInt(4, m.getClassOfMailbox());
				preStateInsert.setString(5, m.getMailboxName());
				preStateInsert.setString(6, m.getAddress());
				preStateInsert.setString(7, m.getDescripAddress());
				preStateInsert.setString(8, m.getMechanism());
				preStateInsert.setString(9, m.getPhoneNumber());
				preStateInsert.setString(10, m.getRemark());
				preStateInsert.setFloat(11, m.getLongitude());
				preStateInsert.setFloat(12, m.getLatitude());
				preStateInsert.executeUpdate();
				times++;
			}
			preStateInsert.close();
			System.out.println("完成，共新增" + times + "筆資料");
			idEnd = idStart + times;
			return times;
		}finally {
			this.closeConnection();			
		}
		
	}
	
	//匯入JSON資料
	public int insertDataByJson(File file) throws SQLException, IOException {
		int times = 0;
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis,"utf8");
				BufferedReader br = new BufferedReader(isr);
				){
			this.createConnection();
			String sql = "insert into mailBoxTable values(?,?,?,?,?,?,?,?,?,?,?,?)";
			String sqlGetLastId = "select top 1 id from mailBoxTable order by id desc";
			PreparedStatement preStateId = conn.prepareStatement(sqlGetLastId);
			ResultSet rsId = preStateId.executeQuery();
			int id = 0;
			if(rsId.next()) {
				id = rsId.getInt(1);				
			}
			idStart = id;
			rsId.close();
			preStateId.close();
			PreparedStatement preState = conn.prepareStatement(sql);
			JsonElement parseReader = JsonParser.parseReader(br);
			JsonArray JArray = parseReader.getAsJsonArray();
			for(JsonElement jelement:JArray) {
				id++;
				JsonObject jObject = jelement.getAsJsonObject();
				int classNumber = jObject.get("類別代號").getAsInt();
				String className = jObject.get("類別").getAsString();
				int classOfMailBox = jObject.get("信筒樣式代號").getAsInt();
				String mailBoxName = jObject.get("信筒樣式").getAsString();
				String address1 = jObject.get("縣市").getAsString();
				String address2 = jObject.get("鄉鎮市區").getAsString();
				String address3 = jObject.get("村里").getAsString();
				String address4 = jObject.get("路名").getAsString();
				String descripAddress = jObject.get("地址描述").getAsString();
				String mechanism = jObject.get("服務單位").getAsString();
				String phoneNumber = jObject.get("聯絡電話").getAsString();
				String remark = jObject.get("備註").getAsString();
				float longitude = jObject.get("x座標").getAsFloat();
				float latitude = jObject.get("y座標").getAsFloat();
				times++;
				Member m = new Member(classNumber, className, classOfMailBox, mailBoxName,
						address1+address2+address3+address4, descripAddress, mechanism,
						phoneNumber, remark, longitude, latitude);
				m.setId(id);
				preState.setInt(1, m.getId());
				preState.setInt(2, m.getClassNumber());
				preState.setString(3, m.getClassName());
				preState.setInt(4, m.getClassOfMailbox());
				preState.setString(5, m.getMailboxName());
				preState.setString(6, m.getAddress());
				preState.setString(7, m.getDescripAddress());
				preState.setString(8, m.getMechanism());
				preState.setString(9, m.getPhoneNumber());
				preState.setString(10, m.getRemark());
				preState.setFloat(11, m.getLongitude());
				preState.setFloat(12, m.getLatitude());
				preState.executeUpdate();
			}
			preState.close();
			System.out.println("完成，共新增" + times + "筆資料");
			idEnd = idStart + times;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeConnection();
		}
		return times;
	}
	
	//匯入XML資料
	public int insertDataByXML(File file) throws  SAXException, IOException, SQLException {
		int times = 0;
		try(FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				) {
			SAXReader reader = new SAXReader();
			Document document= reader.read(br);
			
			List<Node> nodes = document.selectNodes("table/row");
			this.createConnection();
			String sqlInsert = "insert into mailBoxTable values(?,?,?,?,?,?,?,?,?,?,?,?)";
			String sqlGetLastId = "select top 1 id from mailBoxTable order by id desc";
			PreparedStatement preStateId = conn.prepareStatement(sqlGetLastId);
			ResultSet rsId = preStateId.executeQuery();
			int id = 0;
			if(rsId.next()) {
				id = rsId.getInt(1);				
			}
			idStart = id;
			rsId.close();
			preStateId.close();
			PreparedStatement preStateInsert = conn.prepareStatement(sqlInsert);
			for(Node node : nodes) {
				id++;
				int classNumber = Integer.parseInt(node.selectSingleNode("Col1").getText());
				String className = node.selectSingleNode("Col2").getText();
				int classofMailBox = Integer.parseInt(node.selectSingleNode("Col3").getText());
				String mailboxName = node.selectSingleNode("Col4").getText();
				String address1 = node.selectSingleNode("Col5").getText();
				String address2 = node.selectSingleNode("Col6").getText();
				String address3 = node.selectSingleNode("Col7").getText();
				String address4 = node.selectSingleNode("Col8").getText();
				String desripAddress = node.selectSingleNode("Col9").getText();
				String mechanism = node.selectSingleNode("Col10").getText();
				String phoneNumber = node.selectSingleNode("Col11").getText();
				String remark = node.selectSingleNode("Col12").getText();
				float longitude =  Float.valueOf(node.selectSingleNode("Col13").getText());
				float latitude = Float.valueOf(node.selectSingleNode("Col14").getText());
				Member m = new Member(classNumber, className, classofMailBox, mailboxName, address1+address2+address3+address4, desripAddress, mechanism, phoneNumber, remark, longitude, latitude);
				times++;
				m.setId(id);
				preStateInsert.setInt(1, m.getId());
				preStateInsert.setInt(2, m.getClassNumber());
				preStateInsert.setString(3, m.getClassName());
				preStateInsert.setInt(4, m.getClassOfMailbox());
				preStateInsert.setString(5, m.getMailboxName());
				preStateInsert.setString(6, m.getAddress());
				preStateInsert.setString(7, m.getDescripAddress());
				preStateInsert.setString(8, m.getMechanism());
				preStateInsert.setString(9, m.getPhoneNumber());
				preStateInsert.setString(10, m.getRemark());
				preStateInsert.setFloat(11, m.getLongitude());
				preStateInsert.setFloat(12, m.getLatitude());
				preStateInsert.executeUpdate();
			}
			preStateInsert.close();
			System.out.println("完成，共新增" + times + "筆資料");
			idEnd = idStart + times;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			this.closeConnection();
		}
		return times;
	}
	
	
	//透過ID刪除資料
	public void deleteById(int id) throws SQLException {
		try {
			String sql = "delete from mailBoxTable where Id = ?" ;
			this.createConnection();
			PreparedStatement preState = conn.prepareStatement(sql);
			preState.setInt(1, id);
			int row = preState.executeUpdate();
			preState.close();
			System.out.println("成功，刪除" + row +"筆資料");			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			this.closeConnection();
		}
	}	
	
	
	//依照關鍵字查找資料
	public Vector<Vector<String>> searchByKey(String selectType,String keyword) throws SQLException{
		Vector<Vector<String>> vecBig = new Vector<Vector<String>>();
		try {
			String sql = "select * from mailBoxTable ";
			if(selectType != null) {
				sql += " where " + selectType + " like " +keyword;
			}
			this.createConnection();
			PreparedStatement preState = conn.prepareStatement(sql);
			ResultSet rs = preState.executeQuery();
			
			while(rs.next()) {
						Vector<String> vecSmall = new Vector<String>();
						vecSmall.add(rs.getString(1));
						vecSmall.add(rs.getString(2));
						vecSmall.add(rs.getString(3));
						vecSmall.add(rs.getString(4));
						vecSmall.add(rs.getString(5));
						vecSmall.add(rs.getString(6));
						vecSmall.add(rs.getString(7));
						vecSmall.add(rs.getString(8));
						vecSmall.add(rs.getString(9));
						vecSmall.add(rs.getString(10));
						vecSmall.add(rs.getString(11));
						vecSmall.add(rs.getString(12));
						vecBig.add(vecSmall);
			}
			rs.close();
			preState.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeConnection();
		}
		return vecBig;
	}
	
	//依照ID更改資料
	public void updateDataById(String selectType,String newValue,String idValue) throws SQLException {
		switch (selectType) {
		case "類別編號":
			selectType = "classNumber";
			break;
		case "類別":
			selectType = "className";
			break;
		case "信筒樣式代號":
			selectType = "classOfMailbox";
			break;
		case "信筒樣式":
			selectType = "mailboxName";
			break;
		case "地址":
			selectType = "address";
			break;
		case "地址描述":
			selectType = "descripAddress";
			break;
		case "服務單位":
			selectType = "mechanism";
			break;
		case "聯絡電話":
			selectType = "phoneNumber";
			break;
		case "備註":
			selectType = "remark";
			break;
		case "X座標":
			selectType = "longitude";
			break;
		case "Y座標":
			selectType = "latitude";
			break;
		default:
			selectType = "?";
			break;
		}
		String sql = "update mailBoxTable set ";
		sql += selectType + " = '"+newValue+"' where id = "+idValue;
		System.out.println(sql);
		this.createConnection();
		try{
		PreparedStatement preState = conn.prepareStatement(sql);

		int row = preState.executeUpdate();
		
		System.out.println("修改了" + row + "筆資料");
		preState.close();
		}finally {
			this.closeConnection();			
		}
	}
	
	//匯出CSV檔，把加入的ID刪除
	public void exportDataCSV(String filepath,String selectType,String keyword) throws SQLException, IOException {
		String sqlOutput = "select * from mailBoxTable " ;
		this.createConnection();
		if(keyword != null ) {
		sqlOutput += " where " + selectType + " like " + keyword;
		}
		System.out.println(sqlOutput);
		PreparedStatement preStateOutput = conn.prepareStatement(sqlOutput);
		File file = new File(filepath);
		try(FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF8");
				){
			String header = new String("類別代號"+","+"類別"+","+"信筒樣式代號"+","+"信筒樣式"+","+"地址"+","+"地址描述"+","+"服務單位"+","
				+"聯絡電話"+","+"備註"+","+"x座標"+","+"y座標"+"\n");
			System.out.println(header);
			osw.write(header);
			osw.flush();
				ResultSet rs = preStateOutput.executeQuery();
				while(rs.next()) {
					String result = rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+","+rs.getString(5)+","
							+rs.getString(6)+","+rs.getString(7)+","+rs.getString(8)+","+rs.getString(9)+","+rs.getString(10)+","
							+rs.getString(11)+","+rs.getString(12)+"\n";
					osw.write(result);
					osw.flush();
				}
			preStateOutput.close();
			this.closeConnection();
		}
	}
	
	//匯出json檔案
	public void exportDataJson(String filepath , String selectType , String keyword) throws SQLException, IOException {
		String sqlOutput = "select * from mailBoxTable " ;
		if(keyword != null ) {
			sqlOutput += " where " + selectType + " like " + keyword +" for json path";
			}else {
				sqlOutput += " for json path";
			}
		this.createConnection();
		PreparedStatement preState = conn.prepareStatement(sqlOutput);
		File file = new File(filepath);
		try(FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF8");
				BufferedWriter bw = new BufferedWriter(osw);
				){
				ResultSet rs = preState.executeQuery();
				while(rs.next()) {
					String result = rs.getString(1);
					bw.write(result);
					bw.flush();
				}
			preState.close();
			rs.close();
		}finally {
			this.closeConnection();			
		}
	}
	
	
}
