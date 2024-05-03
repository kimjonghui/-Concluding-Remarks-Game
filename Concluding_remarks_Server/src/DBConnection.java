import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	// JDBC URL은 DBMS마다 형식이 다릅니다.
	// MySQL의 경우: "jdbc:mysql://호스트명:포트번호/데이터베이스명"
	private static final String URL = "jdbc:mysql://192.168.0.99:3306/concluding_remarks";
	private static final String USERNAME = "Team4_DB";
	private static final String PASSWORD = "root";

	// 데이터베이스 연결을 설정하는 메소드
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("MySQL JDBC Driver not found.");
		}
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	// 데이터베이스 연결을 닫는 메소드
	public static void Connectionclose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// PreparedStatement를 닫는 메소드
	public static void PreparedStatementclose(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ResultSet를 닫는 메소드
	public static void ResultSetclose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
