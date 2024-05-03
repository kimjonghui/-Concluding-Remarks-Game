
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
	private static Map<String, ClientHandler> allUsers = new HashMap<>();
	private static Map<String, Map<String, ClientHandler>> allRooms = new HashMap<>();
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public static void main(String[] args) throws IOException {
		try (ServerSocket server = new ServerSocket(9996)) {
			System.out.println("서버를 오픈");
			System.out.println("-----클라이언트 접속 대기-----");
			while (true) {
				Socket socket = server.accept();
				System.out.println("클라이언트번호 " + socket + "접속완료.");

				ClientHandler clientHandler = new ClientHandler(socket);
				Thread clientThread = new Thread(clientHandler);

				clientThread.start();
			}
		}
	}

	public static void addUsers(String id, ClientHandler handler) {
		try {
			lock.writeLock().lock();

			allUsers.put(id, handler);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public static boolean hasUsers(String id) {
		try {
			lock.readLock().lock();

			return allUsers.containsKey(id);
		} finally {
			lock.readLock().unlock();
		}
	}

	public static void removeUsers(String id, ClientHandler handler) {
		try {
			lock.writeLock().lock();

			allUsers.remove(id);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public static void lastChar(String roomTitle, Character lastWord) {
		try {
			lock.readLock().lock();
			for (ClientHandler c : allRooms.get(roomTitle).values()) {
				c.lastChar = lastWord;
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	public static void sendMessageToRoomAll(String roomTitle, String word) { // 작성한 유효한단어를 접속중인 모든 클라이언트에 전송
		try {
			lock.readLock().lock();
			for (ClientHandler user : allRooms.get(roomTitle).values()) {
				user.pw.println(1);
				user.pw.println(word);
				user.pw.flush();
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	public static boolean createRoom(String title) {
		try {
			lock.readLock().lock();
			if (allRooms.containsKey(title)) {
				System.out.println("이미 존재하는 방입니다: " + title);
				return false;
			}
			try {
				lock.readLock().unlock();

				lock.writeLock().lock();
				allRooms.put(title, new HashMap<>());
				System.out.println("새로운 방이 생성되었습니다: " + title);
				return true;
			} finally {
				lock.writeLock().unlock();
				lock.readLock().lock();
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	private static void roomEnter(String title, String id, ClientHandler clientHandler) {
		try {
			lock.readLock().lock();
			try {
				Map<String, ClientHandler> map = allRooms.get(title);
				lock.readLock().unlock();

				lock.writeLock().lock();
				if (map != null) {
					map.put(id, clientHandler);
					System.out.println(map);
				}
			} finally {
				lock.writeLock().unlock();
				lock.readLock().lock();
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	private static void removeRoomUser(String title, String id) {
		try {
			lock.writeLock().lock();
			try {
				Map<String, ClientHandler> map = allRooms.get(title);
				if (map != null) {
					map.remove(id);
					System.out.println("해당 방에 남아 있는 key  : " + map);
				}
			} finally {
				lock.writeLock().unlock(); // 쓰기 잠금 해제
			}
		} finally {
			lock.readLock().lock(); // 다시 읽기 잠금 획득
			lock.readLock().unlock();
		}
	}

	public static void sendMemberCollectionForRoomEntered(String roomTitle) {
		try {
			lock.readLock().lock();
			Map<String, ClientHandler> map = allRooms.get(roomTitle);
			System.out.println(map);

			for (ClientHandler e : map.values()) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					e.pw.println(2); // 숫자 2를 전송
					e.pw.println(key); // 멤버 이름을 전송
					e.pw.flush();
				}
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	public static void sendMemberCollectionForRoomReset(String roomTitle) {
		try {
			lock.readLock().lock();
			Map<String, ClientHandler> map = allRooms.get(roomTitle);
			System.out.println("맵에 유저 확인 " + map);

			for (ClientHandler e : map.values()) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					e.pw.println(3);
					e.pw.println("");
					e.pw.flush();
				}
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	static class ClientHandler implements Runnable {
		private Socket socket;
		private BufferedReader br;
		private PrintWriter pw;
		private String id;
		private String currentRoomTitle;

		public String getId() {
			return id;
		}

		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				pw = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				if (br == null || pw == null) {
					System.out.println("BufferedReader 또는 PrintWriter가 null입니다.");
					return;
				}

				while (true) {
					String command = br.readLine();
					System.out.println("확인:" + command);
					if (command == null || command.equals("Bye Bye")) {
						break;
					}
					if (command.equals("1")) {
						handleLogin();
					} else if (command.equals("2")) {
						handleFindId();
					} else if (command.equals("3")) {
						handleFindPassWord();
					} else if (command.equals("4")) {
						handleJoinMembership();
					} else if (command.equals("5")) {
						handleLogoutAndListRemove();
					} else if (command.equals("6")) {
						handleMyPage();
					} else if (command.equals("7")) {
						handleSingleGameExit();
					} else if (command.equals("8")) {
						handleSinegleGame();
					} else if (command.equals("9")) {
						handleMultiGameExit();
					} else if (command.equals("10")) {
						handleMultiModeUserInsert();
					} else if (command.equals("99")) {
						handleWordInput();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				removeUsers(id, this);
			}
		}

		// 기존 회원의 로그인을 위해 DB를 확인하여 로그인 성공 유무를 클라이언트로 전달
		private void handleLogin() throws IOException, SQLException {
			pw.println("기존 회원의 로그인입니다.");
			pw.flush();
			String id = br.readLine();
			String password = br.readLine();
			System.out.println("중복로그인 체크 확인 .");
			if (!hasUsers(id)) {
				System.out.println("중복 로그인이 아닙니다.");
				System.out.println("클라이언트에서 전송한 ID: " + id);
				System.out.println("클라이언트에서 전송한 Password: " + password);
				String sql = "SELECT * FROM user WHERE id = ? AND password = ?";
				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setString(1, id);
					stmt.setString(2, password);
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {
							pw.println("로그인이 성공적으로 완료되었습니다.");
							pw.flush();
							addUsers(id, this);
							this.id = id;
						} else {
							System.out.println("잘못된 ID 비밀번호입력.");
							pw.println("해당 ID,비밀번호가 존재하지 않습니다.");
							pw.flush();
						}
					}
				}
			} else {
				pw.println("중복된 로그인이거나, 해당 ID,비밀번호가 존재하지 않습니다.");
				pw.flush();
			}
		}

		// 기존회원이 아이디를 잃어버렸을 경우 DB에 email로 id를 클라이언트에 반환 받을수 있게 구현 필요함
		private void handleFindId() throws IOException, SQLException {
			pw.println("아이디를 찾습니다.");
			pw.flush();
			String email = br.readLine();
			String sql = "select * from user where email = ?";
			try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, email);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						String id = rs.getString("id"); // 해당 이메일로 조회한 ID칼럼의 값을 id변수에 담음
						System.out.println("서버에서 클라이언트로 전송하는 ID : " + id);
						pw.println(id);
						pw.flush();
					} else {
						System.out.println("해당 이메일로 조회되는 아이디가 없습니다.");
						pw.println("해당 이메일로 조회되는 아이디가 없습니다.");
						pw.flush();
					}
				}
			}
		}

		// 기존회원이 비밀번호를 잃어버렸을때 ID,Email로 비밀번호를 찾는 매소드
		private void handleFindPassWord() throws IOException, SQLException {
			pw.println("비밀번호를 찾습니다.");
			pw.flush();
			String id = br.readLine();
			String email = br.readLine();
			String sql = "select * from user where id = ? and email = ?";
			try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, id);
				stmt.setString(2, email);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						String passWord = rs.getString("password");
						System.out.println("찾은 비밀 번호 : " + passWord);
						pw.println(passWord);
						pw.flush();
					} else {
						System.out.println("클라이언트에서 보낸 아이디와 이메일로 비밀번호를 찾을수 없습니다.");
						System.out.println("다시 입력하여주세요");
						pw.println("해당 아이디 이메일로 비밀번호를 찾을수 없습니다.");
						pw.flush();
					}
				}
			}
		}

		// 신규회원가입을 하는 메소드
		private void handleJoinMembership() throws IOException {
			pw.println("신규 회원가입 입니다.");
			pw.flush();
			String id = br.readLine();
			String passWord = br.readLine();
			String email = br.readLine();
			System.out.println("확인 : " + id + passWord + email);
			String insertSQL = "INSERT INTO user (id,password,email,single_score,multi_winner_count) VALUES (?, ?, ?, ?,?)";
			try (Connection conn = DBConnection.getConnection();
					PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
				stmt.setString(1, id);
				stmt.setString(2, passWord);
				stmt.setString(3, email);
				stmt.setInt(4, 0);
				stmt.setInt(5, 0);
				stmt.executeUpdate();
				pw.println("회원가입이 완료되었습니다. 로그인창으로 돌아가서 로그인을 진행 해주세요.");
				pw.flush();
			} catch (SQLException e) {
				// PK값에 중복이 일어났을때 클라이언트로 보내줄 문구
				pw.println("해당 ID는 이미있는 ID입니다. 다른 ID를 입력해주세요");
				pw.flush();
			}
		}

		// 메인 화면에서 로그아웃시 클라이언트에서 접속한 계정의 정보를 지운다.
		private void handleLogoutAndListRemove() throws IOException {
			pw.println("로그아웃 입니다.");
			pw.flush();
			String username = br.readLine(); // 클라이언트에서 유저아이디를 문자열로 받아옴
			if (hasUsers(username)) {
				removeUsers(username, this);
				System.out.println("해당 계정 로그아웃 후 현재 클라이언트에 접속된 아이디 확인");
				pw.println("로그아웃 완료");
				pw.flush();
			}
		}

		// 마이페이지에서 DB연결하여 로그인한 계정의 정보를 불러와 GUI에 출력해주기
		private void handleMyPage() throws IOException, SQLException {
			pw.println("DB에서 계정정보 가져오기");
			pw.flush();
			String username = br.readLine();
			System.out.println(username);
			String sql = "select * from user where id = ?";
			if (hasUsers(username)) {
				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setString(1, username);
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {
							String id = rs.getString("ID");
							System.out.println(id);
							int single = rs.getInt("Single_Score");
							System.out.println(single);
							int multi = rs.getInt("Multi_Winner_Count");
							System.out.println(multi);
							pw.println(id);
							pw.println(single);
							pw.println(multi);
							pw.flush();
						}
					}
				}
			}
		}

		// 멀티모드에서 단어 유효성 검토후 단어 및 잘못된 입력 리턴
		Character lastChar = ' '; // 시작단어를 공백으로 설정

		private void handleWordInput() throws IOException, SQLException {
			String word = br.readLine();
			System.out.println(socket + "클라이언트에서 전송한 단어: " + word);
			if ((singleLastChar == word.charAt(0) || singleLastChar == ' ') && word.length() > 1) {
				String sql = "SELECT * FROM word WHERE word = ?";
				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setString(1, word);
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {
							int doubleCheckValue = rs.getInt("double_check");
							if (doubleCheckValue == 0) {
								pw.println(1);
								pw.println("중복된 단어는 사용할 수 없습니다.");
								pw.flush();
							} else if (doubleCheckValue == 1) {
								String updateSQL = "UPDATE word SET double_check = 0 WHERE word = ?";
								try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
									updateStmt.setString(1, word);
									updateStmt.executeUpdate();
								}
								sendMessageToRoomAll(currentRoomTitle, word); // 유효한 단어 입력시 작성한 단어 다시 클라이언트로 전송
								int lastIndex = word.length() - 1;
								lastChar = word.charAt(lastIndex);
								System.out.println("마지막 끝말 확인 : " + lastChar);
								lastChar(currentRoomTitle, lastChar);
							}
						} else {
							WordAPI api = new WordAPI();
							int returnValue = api.getTotalValue(word);
							System.out.println("리턴값 확인 " + returnValue);
							if (returnValue == -1) {
								String insertSQL = "INSERT INTO word (word, double_check) VALUES (?, ?)";
								try (PreparedStatement insert = conn.prepareStatement(insertSQL)) {
									insert.setString(1, word);
									insert.setInt(2, 0);
									insert.executeUpdate();
									sendMessageToRoomAll(currentRoomTitle, word); // 유효한 단어 입력시 작성한 단어 다시 클라이언트로 전송
									int lastIndex = word.length() - 1;
									lastChar = word.charAt(lastIndex);
									System.out.println("마지막 끝말 확인 : " + lastChar);
									lastChar(currentRoomTitle, lastChar);
								}
							} else if (returnValue == 0) {
								pw.println(1);
								pw.println("유효하지 않는 단어입니다. 다시 입력하세요.");
								pw.flush();
							}
						}
					}
				}
			} else if (2 > word.length()) {
				pw.println(1);
				pw.println("한글자 사용 불가능.");
				pw.flush();
			} else {
				System.out.println("끝말매칭 실패");
				pw.println(1);
				pw.println("끝말이 맞지 않습니다 다시 단어를 입력하세요.");
				pw.flush();
			}
		}

		// 싱글모드에서 유저와 CPU 1:1 게임 (단 DB안에 없는 단어일시 사용자가 끝말잇기를 진행해야함)
		Character singleLastChar = ' '; // 싱글 모드 시작단어를 공백으로 설정

		private void handleSinegleGame() throws IOException, SQLException {
			// 끝말로 시작하는 단어를 인덱스 값으로 하나를 클라이언트로 보내기위해서 잠시 담고있는 리스트
			// 클라이언트 전송후 removeAll로 초기화 필수
			List<String> lastCharStartWord = new ArrayList<>();
			String word = br.readLine();
			System.out.println(socket + "클라이언트에서 전송한 단어: " + word);
			if ((singleLastChar == word.charAt(0) || singleLastChar == ' ') && word.length() > 1) {
				System.out.println("끝말 매칭 완료됨.");
				String sql = "SELECT * FROM word WHERE word = ?";
				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setString(1, word);
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {
							int doubleCheckValue = rs.getInt("double_check");
							if (doubleCheckValue == 0) {
								pw.println("중복된 단어는 사용할 수 없습니다.");
								pw.flush();
							} else if (doubleCheckValue == 1) {
								String updateSQL = "UPDATE word SET double_check = 0 WHERE word = ?";
								try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
									updateStmt.setString(1, word);
									updateStmt.executeUpdate();
									int lastIndex = word.length() - 1;
									singleLastChar = word.charAt(lastIndex);
									System.out.println("마지막 끝말 확인 : " + singleLastChar);
								}
								String searchLastWordStartWord = "select * from word where word like ? and double_check = ?";
								try (PreparedStatement searchWord = conn.prepareStatement(searchLastWordStartWord)) {
									searchWord.setString(1, singleLastChar + "%");
									searchWord.setInt(2, 1);
									try (ResultSet rs1 = searchWord.executeQuery()) {
										while (rs1.next()) {
											lastCharStartWord.add(rs1.getString("word"));
										}
									}
								}
								if (!lastCharStartWord.isEmpty()) {
									Random random = new Random();
									String reciveWord = lastCharStartWord.get(random.nextInt(lastCharStartWord.size()));
									pw.println(reciveWord);
									pw.flush();
									System.out.println("클라이언트로 전송하는 단어 : " + reciveWord);
									int lastIndexReciveWord = reciveWord.length() - 1;
									singleLastChar = reciveWord.charAt(lastIndexReciveWord);
									System.out.println("마지막 끝말 확인 : " + singleLastChar);
									lastCharStartWord.clear();
								} else {
									System.out.println(singleLastChar + " 로 시작하는 단어가 없습니다.");
									pw.println("DB에 끝말로 시작하는 단어가 없습니다. 사용자가 끝말을 이어서 작성하여 주세요.");
									pw.flush();
									pw.println(word);
									pw.flush();
								}
							}
						} else {
							WordAPI api = new WordAPI();
							int returnValue = api.getTotalValue(word);
							System.out.println("리턴값 확인 " + returnValue);
							if (returnValue == -1) {
								String insertSQL = "INSERT INTO word (word, double_check) VALUES (?, ?)";
								try (PreparedStatement insert = conn.prepareStatement(insertSQL)) {
									insert.setString(1, word);
									insert.setInt(2, 0);
									insert.executeUpdate();
									int lastIndex = word.length() - 1;
									singleLastChar = word.charAt(lastIndex);
									System.out.println("마지막 끝말 확인 : " + singleLastChar);
									String searchLastWordStartWord = "select * from word where word like ? and double_check = ?";
									try (PreparedStatement searchWord = conn
											.prepareStatement(searchLastWordStartWord)) {
										searchWord.setString(1, singleLastChar + "%");
										searchWord.setInt(2, 1);
										try (ResultSet rs1 = searchWord.executeQuery()) {
											while (rs1.next()) {
												lastCharStartWord.add(rs1.getString("word"));
											}
										}
									}
									if (!lastCharStartWord.isEmpty()) {
										Random random = new Random();
										String reciveWord = lastCharStartWord
												.get(random.nextInt(lastCharStartWord.size()));
										pw.println(reciveWord);
										pw.flush();
										System.out.println("클라이언트로 전송하는 단어 : " + reciveWord);
										int lastIndexReciveWord = reciveWord.length() - 1;
										singleLastChar = reciveWord.charAt(lastIndexReciveWord);
										System.out.println("마지막 끝말 확인 : " + singleLastChar);
										lastCharStartWord.clear();
									} else {
										System.out.println(singleLastChar + " 로 시작하는 단어가 없습니다.");
										pw.println("DB에 끝말로 시작하는 단어가 없습니다. 사용자가 끝말을 이어서 작성하여 주세요.");
										pw.flush();
										pw.println(word);
										pw.flush();
									}
								}
							} else if (returnValue == 0) {
								pw.println("유효하지 않는 단어입니다. 다시 입력하세요.");
								pw.flush();
							}
						}
					}
				}
			} else if (2 > word.length()) {
				pw.println("한글자 사용 불가능.");
				pw.flush();
			} else {
				System.out.println("끝말매칭 실패");
				pw.println("끝말이 맞지 않습니다 다시 단어를 입력하세요.");
				pw.flush();
			}
		}

		// 싱글모드에서 게임종료 버튼 눌렀을시 중복체크값, 끝말 기록되어 있는것 초기화
		private void handleSingleGameExit() throws SQLException {
			String sql = "UPDATE word SET double_check = ?";
			try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, 1);
				stmt.executeUpdate();
				singleLastChar = ' ';
			}
		}

		// 멀티모드에서 게임종료 버튼 눌렀을시 중복체크값, 끝말 기록되어 있는것 초기화
		private void handleMultiGameExit() throws SQLException, IOException {
			String sql = "UPDATE word SET double_check = ?";
			try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, 1);
				stmt.executeUpdate();
				lastChar = ' ';
				// 방에서 유저 id 지우기
				String roomName = br.readLine();
				String userID = br.readLine();
				// 1. 모든 라벨을 ""을 초기화 시키기 서버에서 방에 접속중인 클라이언트에 "" 뿌리기
				sendMemberCollectionForRoomReset(roomName);
				System.out.println("초기화 확인1");
				removeRoomUser(roomName, userID);
				System.out.println("나간 유저 지우기 확인 2");
				// 2.방에 남아있는 유저를 다시 전송
				sendMemberCollectionForRoomEntered(roomName);
				System.out.println("유저 클라이언트로 다시전송 확인 3");
			}
		}

		// 클라이언트에서 접속하면 멀티모드에 유저 추가
		private void handleMultiModeUserInsert() throws IOException {
			// 1. 방이름을 입력받아야함
			// 2. 방 이름이 존재하는지 확인함
			// 2.1. 존재 시 반환 값 생각할 것
			// 3. 미 존재 시 생성
			// 4. ㄴ> 존재 시 반환 값 : 방의 멤버 목록 전송하기
			String roomTitle = br.readLine();
			System.out.println("방제목: " + roomTitle);
			boolean result = createRoom(roomTitle);
			System.out.println(result);
			roomEnter(roomTitle, id, this);
			currentRoomTitle = roomTitle;
			sendMemberCollectionForRoomEntered(roomTitle);
		}
	}
}
