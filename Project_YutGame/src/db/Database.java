package db;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Database {
	
	DefaultListModel<String> gamelistdata = new DefaultListModel<String>();
	
//	Game List를 보여주기 위한 함수
	public DefaultListModel gameShow() {
		gamelistdata.removeAllElements();
		Connection conn;
		Statement stmt = null;
		try {
			String url = "jdbc:mysql://localhost:3306/Opensource";
			String user = "root";
			String pw = "2017018023";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
//			user_game 테이블에 있는 모든 값을 선택
			ResultSet srs = stmt.executeQuery("select * from user_game");
			
			while (srs.next()) {
//				GameStatus가 false인 값만 gamelistdata에 입력
				if (!srs.getBoolean("GameStatus")) {
					gamelistdata.addElement(srs.getInt("RoomNum")
							+ "\t|\t" + srs.getString("HostId")
							+ "\t|\t" + srs.getInt("Totalpop")
							+ "\t|\t" + srs.getBoolean("GameStatus"));
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver load error");
		} catch (SQLException e) {
			System.out.println("SQL error");
		}
		
		return gamelistdata;
		
	}

//	Create Room 버튼 클릭 시 작동
	public void createGame (String userId) {
		Connection conn;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			String url = "jdbc:mysql://localhost:3306/Opensource";
			String user = "root";
			String pw = "2017018023";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
//			RoomNum의 마지막 값을 알려주는 쿼리문
			ResultSet srs = stmt.executeQuery("select last_value(RoomNum) over() as rn_last from game");
			srs.next();
			
//			RoomNum 마지막 값 + 1
			Integer rn_num = srs.getInt("rn_last") + 1;
			String rn = String.valueOf(rn_num);
			
//				game table에 새로운 data 입력
			pstmt = conn.prepareStatement("insert into game (RoomNum, HostPlayer) values (?, ?)");
			pstmt.setString(1, rn);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
//				user_game table에 새로운 data 입력
			pstmt = conn.prepareStatement("insert into user_game (RoomNum, HostId, Totalpop, GameStatus) values (?, ?, 1, 0)");
			pstmt.setString(1, rn);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			
//			gamelistdata 최신화
			gameShow();
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver load error");
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
	}

//	Game 창을 종료 시
	public void deleteGame (String userId) {
		Connection conn;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			String url = "jdbc:mysql://localhost:3306/Opensource";
			String user = "root";
			String pw = "2017018023";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
//			RoomNum의 마지막 값을 알려주는 쿼리문
			ResultSet srs = stmt.executeQuery("select last_value(RoomNum) over() as rn_last from game");
			srs.next();
			
//			RoomNum 마지막 값 + 1
			Integer rn_num = srs.getInt("rn_last");
			String rn = String.valueOf(rn_num);
			
//			user_game table에 새로운 data 입력
			pstmt = conn.prepareStatement("delete from user_game where RoomNum = (?)");
			pstmt.setString(1, rn);
			pstmt.executeUpdate();
//			game table에 새로운 data 입력
			pstmt = conn.prepareStatement("delete from game where RoomNum = (?)");
			pstmt.setString(1, rn);
			pstmt.executeUpdate();

//			gamelistdata 최신화
			gameShow();
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver load error");
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
	}
	

//Create Room 버튼 클릭 시 작동
public void createGame() {
	Connection conn;
	Statement stmt = null;
	try {
		String url = "jdbc:mysql://localhost/?characterEncoding=UTF-8&serverTimezone=UTC";
		String user = "root";
		String pw = "2018004027";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		conn = DriverManager.getConnection(url, user, pw);
		stmt = conn.createStatement();
//		RoomNum의 마지막 값을 알려주는 쿼리문
		ResultSet srs = stmt.executeQuery("select last_value(RoomNum) over() as rn_last from game");

		srs.next();
//		RoomNum 마지막 값 + 1
		Integer rn_last = srs.getInt("rn_last") + 1;
		
		System.out.println(rn_last);
		
		
		
	} catch (ClassNotFoundException e) {
		System.out.println("JDBC Driver load error");
	} catch (SQLException e) {
		System.out.println("SQL error2");
	}
}

public void createUser(String _i, String _p) {
	Connection conn;
	Statement stmt = null;
	
	try {
		String url = "jdbc:mysql://localhost/sample";
		String user = "root";
		String pw = "2018004027";
		

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
			System.out.println("MySQL 서버 연동 성공");
		} catch(Exception e) {
			System.out.println("MySQL 서버 연동 실패 > " + e.toString());
			}
}


static public boolean logincheck(String _i, String _p) {
	boolean flag = false;
	
	String id = _i;
	String pw = _p;
	Connection conn;
	Statement stmt = null;
	Statement stmt2 = null;
	
	try {
		String url = "jdbc:mysql://localhost/sample";
		String user = "root";
		String password = "2018004027";
		

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			
			System.out.println("MySQL 서버 연동 성공");
		} catch(Exception e) {
			System.out.println("MySQL 서버 연동 실패 > " + e.toString());
			}
	
	
	try {
		String checkingStr = "SELECT password FROM user WHERE Username='" + id + "'";
		ResultSet result = stmt.executeQuery(checkingStr);
		
		int count = 0;
		while(result.next()) {
			if(pw.equals(result.getString("password"))) {
				String status = "UPDATE user SET LoginStatus='0' WHERE Username= '" + id +"'";
				
				flag = true;
				System.out.println("로그인 성공");
				stmt2.executeUpdate(status);
			}
			
			else {
				flag = false;
				System.out.println("로그인 실패");
			}
			count++;
		}
	} catch(Exception e) {
		flag = false;
		System.out.println("로그인 실패 > " + e.toString());
	}
	
	return flag;
}


static public boolean joinCheck(String _i, String _p) {
boolean flag = false;
String id = _i;
String pw = _p;
Connection conn;
Statement stmt = null;

try {
	String url = "jdbc:mysql://localhost/sample";
	String user = "root";
	String password = "2018004027";
	

		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		stmt = conn.createStatement();
		
		System.out.println("MySQL 서버 연동 성공");
	} catch(Exception e) {
		System.out.println("MySQL 서버 연동 실패 > " + e.toString());
		}
	
try {
	String insertStr = "INSERT INTO user VALUES('" + id + "', '" + pw + "', '" + 0 + "')";
	stmt.executeUpdate(insertStr);
		
	flag = true;
	System.out.println("회원가입 성공");
} catch(Exception e) {
	flag = false;
	System.out.println("회원가입 실패 > " + e.toString());
}
	
return flag;
}
}
