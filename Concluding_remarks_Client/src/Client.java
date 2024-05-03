import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Client {
	public static List<String> list = new ArrayList<>();
	public static List<String> roomTitle = new ArrayList<>();
	FindID findId = new FindID(); // 아이디 찾기 창
	FindPassWord findpassword = new FindPassWord(); // 비밀번호 찾기 창
	GameRule gameRule = new GameRule(); // 게임 설명 창
	JoinMembership joinMembership = new JoinMembership();
	Login login = new Login(); // 로그인 창
	MainView mainView = new MainView(); // 로그인 이후 진입하는 메인창
	ModeChoice modeChoice = new ModeChoice(); // 싱글, 멀티 모드 선택창
	static MultiMode multiMode = new MultiMode(); // 멀티 게임 창
	MyPage myPage = new MyPage();// 마이페이지 창
	SingleMode singleMode = new SingleMode(this); // 싱글모드 창
	private Thread wordTx;
	private Thread wordRx;
	private Socket socket;
	public Timer timer;
	int singleScore = 0;
	int WinnerCount = 0;
	private PrintWriter pw;

	public static void main(String[] args) {
		new Client().start();
	}

	public void timerFinished() {
		pw.println(7);
		pw.flush();
		pw.println(list.get(0));
		pw.println(singleScore);
		pw.flush();
		singleScore = 0;
		// 여기서 게임 종료시 모드 선택 창으로 전환하는 액션 리스너를 호출할 수 있습니다.
		// exitSingleMode.actionPerformed(e);
	}

	// -----------------------------------------김종희----------------------------------------------
	public void multiTimerFinished() {
		pw.println(12);
		pw.flush();
		pw.println(list.get(0));
		pw.println(WinnerCount);
		pw.flush();
		WinnerCount = 1;
	}

	// -----------------------------------------김종희----------------------------------------------
	public synchronized void start() {
		socket = null;

		try {
			socket = new Socket("192.168.0.99", 9996);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());

			System.out.println("서버에 연결됨.");
			System.out.println("서버에 성공적으로 연결되었습니다.");

//------------------------------------------------------------------------------------------------------------
			// 로그인 버튼 엑션리스너
			login.setLoginListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						pw.println(1);
						pw.flush();
						String returnString = br.readLine();
						if (returnString.equals("기존 회원의 로그인입니다.")) {
							pw.println(login.getID());
							pw.println(login.getPassword());
							pw.flush();
							System.out.println("서버에서 로그인 처리 확인:" + returnString);
							String returnString2 = br.readLine();
							if (returnString2.equals("로그인이 성공적으로 완료되었습니다.")) {
								list.add(login.getID());
								System.out.println(list.toString());
								mainView.setVisible(true);
								login.setVisible(false);
							} else if (returnString2.equals("해당 ID,비밀번호가 존재하지 않습니다.")) {
								System.out.println("ID, 비밀번호를 다시 입력하여 주세요.");
							} else {
								System.out.println(returnString2);
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			// 회원가입 버튼 액션 리스너, 회원가입 버튼을 눌렀을때 로그인창을 끄고 회원가입 창으로 전환
			login.setJoinMembershipListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					login.setVisible(false);
					joinMembership.setVisible(true);
				}
			});
			// 아이디 찾기 버튼 눌렀을시 , 로그인창에서 아이디 찾기 창으로 변경
			login.setFindIDListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					login.setVisible(false);
					findId.setVisible(true);
				}
			});
			// 비밀번호 찾기 버튼 눌렀을시 로그인 창에서 비밀번호 찾기 창으로 변경
			login.setFindPassWordListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					login.setVisible(false);
					findpassword.setVisible(true);
				}
			});
			login.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//---------------------------------------------------------------------------------------------------
			// 아이디 찾기 창에서 잃어버린 아이디 찾기 (DB연결하여 ID를 찾을수 있음)
			findId.setFindIDListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(2);
					pw.flush();
					// e-mail로 ID를 찾을수 있다
					try {
						if (br.readLine().equals("아이디를 찾습니다.")) {
							pw.println(findId.getFindID_emil());
							pw.flush();
							String idAlarm = br.readLine();
							System.out.println("해당 이메일로 찾은 아이디 : " + idAlarm);
							JOptionPane.showMessageDialog(findpassword, "해당 이메일로 찾은 아이디 : " + idAlarm + " 입니다.");
							findId.setVisible(false);
							login.setVisible(true);
						} else {
							String reServer = br.readLine();
							System.out.println(reServer);
							JOptionPane.showMessageDialog(findId, reServer);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			// 아이디 찾기 창에서 뒤로가기 버튼 눌렀을때 다시 로그인창으로 이동
			findId.setFindID_BackListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					findId.setVisible(false);
					login.setVisible(true);
				}
			});
			findId.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
// -----------------------------------------------------------------------------------------------------			
			// 비밀번호 찾기 창에서 아이디,이메일을 입력하여 비밀번호 찾기(DB에 연결하여 비밀번호 찾음)
			findpassword.setFindPassWordListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(3);
					pw.flush();
					try {
						if (br.readLine().equals("비밀번호를 찾습니다.")) {
							pw.println(findpassword.getFindPassWord_ID()); // 아아디
							pw.println(findpassword.getFindPassWord_Email()); // 이메일
							pw.flush();
							String passwordAlarm = br.readLine();
							System.out.println("찾은 비밀번호는 " + passwordAlarm + " 입니다.");
							// 다이얼로그 창에 찾은 비밀번호 표시 필요함
							JOptionPane.showMessageDialog(findpassword, "찾은 비밀번호는 " + passwordAlarm + " 입니다.");
							findpassword.setVisible(false);
							login.setVisible(true);
						} else {
							String reServer = br.readLine();
							System.out.println(reServer);
							JOptionPane.showMessageDialog(findpassword, reServer);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			// 뒤로가기 버튼 누르면 비밀번호 찾기 창이 꺼지고 로그인창으로 돌아가기
			findpassword.setFindPassWord_BackListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					findpassword.setVisible(false);
					login.setVisible(true);
				}
			});
			findpassword.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//-----------------------------------------------------------------------------------------------------------------
			// 회원가입 창에서 회원가입하기 버튼을 누르면 ID,PassWord,email을 서버로 전송하여 서버에서 DB에 추가
			joinMembership.setJoinMembershipListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(4);
					pw.flush();
					try {
						if (br.readLine().equals("신규 회원가입 입니다.")) {
							if (joinMembership.getJoinMembership_PassWord()
									.equals(joinMembership.getJoinMembership_PassWord_DoubleCheck())) {
								pw.println(joinMembership.getJoinMembership_ID()); // 아이디 서버로 전송
								pw.println(joinMembership.getJoinMembership_PassWord()); // 비밀번호 서버로 전송
								pw.println(joinMembership.getJoinMembership_Email()); // 이메일을 서버로 전송
								pw.flush();

								System.out.println(br.readLine());// 이문구가 뜨는 다이얼로그 만들기
								JOptionPane.showMessageDialog(joinMembership, "회원 가입이 완료 되었습니다.");
								joinMembership.setVisible(false);
								login.setVisible(true);
							} else {
								// 다이얼로그로 비밀번호와 비밀번호 확인에 입력한 값이 동일하지 않다는 다이얼로그 출력하기
								System.out.println("비밀번호, 비밀번호 확인이 다릅니다.");
								JOptionPane.showMessageDialog(joinMembership, "비밀번호, 비밀번호 확인이 다릅니다.");
							}
						} else {
							// DB에 ID가 PK값이 때문에 이미 있는 ID라는 값 받기 "이미 있는 아이디 입니다" 다이얼로그 출력
							String reciveServer = br.readLine();
							System.out.println(reciveServer);
							JOptionPane.showMessageDialog(joinMembership, reciveServer);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			// 회원가입창에서 뒤로가기 버튼누르면 회원가입창에서 로그인창으로 전환
			joinMembership.setJoinMembership_back_LoginListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					joinMembership.setVisible(false);
					login.setVisible(true);
				}
			});
			joinMembership.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//---------------------------------------------------------------------------------------------------------------------------
			// 메인화면에서 모드 선택 창으로 변경
			mainView.setSelectMode(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainView.setVisible(false);
					modeChoice.setVisible(true);
				}
			});
			// 메인화면에서 마이페이지로 변경
			mainView.setMypage(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainView.setVisible(false);
					myPage.setVisible(true);
					pw.println(6);
					pw.flush();
					try {
						String command = br.readLine();
						System.out.println(command);
						String username = list.get(0);
						if (command.equals("DB에서 계정정보 가져오기")) {
							pw.println(username);
							pw.flush();
							String userID = br.readLine();
							String singleScore = br.readLine();
							String multiWinnerCount = br.readLine();
							myPage.setMyPageID(userID);
							myPage.setMyPageSingleScore(singleScore);
							myPage.setMultiWinnerCount(multiWinnerCount);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			// 메인화면에서 게임 설명창으로 변경
			mainView.setGameExplanation(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainView.setVisible(false);
					gameRule.setVisible(true);
				}
			});
			// 로그아웃 버튼 눌러서 메인화면에서 다시 로그인 창으로 이동
			mainView.setMainLogout(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 리스트 비우고 서버에도 해당 계정과 이름과 인덱스번호 삭제 해야함
					String username = list.get(0); // 클라이언트에서 로그인한 아이디를 저장한 리스트 불러오기
					System.out.println("로그아웃 할려는 ID 확인" + username);
					pw.println(5);
					pw.flush();
					try {
						String command = br.readLine();
						System.out.println("로그아웃 커맨드 확인 " + command);
						if (command.equals("로그아웃 입니다.")) {
							pw.println(username);
							pw.flush();
							String command2 = br.readLine();
							System.out.println("로그아웃 커맨드2 확인 " + command2);
							if (command2.equals("로그아웃 완료")) {
								list.remove(username); // 서버에서 지우고나서 클라이언트에 접속한 아이디 리스트에서 삭제
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					mainView.setVisible(false);
					login.setVisible(true);
				}
			});
			mainView.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
// ---------------------------------------------------------------------------------------------
			// 게임 설명창에서 메인화면 으로 변경
			gameRule.setGameRuleBackListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameRule.setVisible(false);
					mainView.setVisible(true);
				}
			});
			gameRule.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//-------------------------------------------------------------------------------------------------
			// 게임 선택 모드에서 싱글 화면으로 전환
			modeChoice.setSingleMode(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modeChoice.setVisible(false);
					singleMode.setVisible(true);
				}
			});
			// 게임 선택 모드에서 멀티 화면으로 전환
			modeChoice.setMultiMode(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String roomName = JOptionPane.showInputDialog(null, "방 이름을 입력하세요:");
					if (roomName != null) {
						roomTitle.add(roomName); // 입력한 방제목을 리스트에 저장
						System.out.println(roomName);
						modeChoice.setVisible(false);
						multiMode.setVisible(true);
						wordTx = new Thread(new WordTx(pw));
						wordRx = new Thread(new WordRx(br));
						wordTx.start();
						System.out.println("스래드 시작 1");
						wordRx.start();
						System.out.println("스래드 시작 2");
						pw.println(10);
						pw.flush();
						pw.println(roomName);
						pw.flush();
					}
				}
			});
			// 게임 선택 모드에서 메인화면으로 전환
			modeChoice.setSelect_Back(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modeChoice.setVisible(false);
					mainView.setVisible(true);
				}
			});
			modeChoice.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//-----------------------------------------------------------------------------------------------------
			// 뒤로가기 버튼 클릭시 마이페이지에서 메인화면으로 전환
			myPage.setExitMypage(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myPage.setVisible(false);
					mainView.setVisible(true);
				}
			});
			myPage.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//------------------------------------------------------------------------------------------------------
			// 게임 종료 시 모드 선택 창으로 전환하는 액션 리스너 설정
			singleMode.setExitSingleMode(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(7);
					pw.flush();
					pw.println(list.get(0));
					pw.println(singleScore);
					pw.flush();
					singleScore = 0;
					singleMode.setVisible(false);
					modeChoice.setVisible(true);
				}
			});

			// DB에 없을시 사용자가 끝말을 다시 입력
			singleMode.setSingleSendWord(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(8);
					pw.flush();
					pw.println(singleMode.getSendWord());// 싱글에서 입력한 단어 게터로 가저오기
					pw.flush();

					try {
						String command = br.readLine();
						if (command.equals("중복된 단어는 사용할 수 없습니다.")) {
							// System.out.println("중복된 단어는 사용이 불가능합니다.");
							singleMode.setSingleWarning_text("중복된 단어는 사용이 불가능합니다.");
						} else if (command.equals("유효하지 않는 단어입니다. 다시 입력하세요.")) {
							// System.out.println("유효하지 않는 단어입니다. 다시 입력하세요.");
							singleMode.setSingleWarning_text("유효하지 않는 단어입니다. 다시 입력하세요.");
						} else if (command.equals("한글자 사용 불가능.")) {
							// System.out.println("한글자 사용 불가능.");
							singleMode.setSingleWarning_text("한글자 사용 불가능.");
						} else if (command.equals("끝말이 맞지 않습니다 다시 단어를 입력하세요.")) {
							// System.out.println("끝말이 맞지 않습니다.");
							singleMode.setSingleWarning_text("끝말이 맞지 않습니다 다시 단어를 입력하세요.");
						} else if (command.equals("DB에 끝말로 시작하는 단어가 없습니다. 사용자가 끝말을 이어서 작성하여 주세요.")) {
							System.out.println(command);
							System.out.println(br.readLine());
							singleMode.setSingleWarning_text("DB에 끝말로 시작하는 단어가 없습니다. 사용자가 끝말을 이어서 작성하여 주세요.");
							singleMode.setSinShowWord(singleMode.getSendWord());
							singleScore++;
							singleMode.stopTimer();
							singleMode.resetTimer();
							singleMode.startTimer();
							// 버튼에 타이머 관여 못하게 하고 따로 동작
						} else if (command.length() < 6) {
							System.out.println(command); // GUI에 출력하기
							singleMode.setSinShowWord(command);
							if (!singleMode.getSingleWarning_text().equals("")) {
								singleMode.setSingleWarning_text("");
							}
							singleScore++;
							singleMode.stopTimer();
							singleMode.resetTimer();
							singleMode.startTimer();
							// 점수 라벨 만들고 , 업데이트
							// 버튼에 타이머 관여 못하게 하고 따로 동작
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			singleMode.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
//--------------------------------------------------------------------------------------------------------------------------
			multiMode.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					pw.println("Bye Bye");
					pw.flush();
					wordTx.interrupt();
					wordRx.interrupt();

					try {
						wordTx.join();
						wordRx.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						if (socket != null) {
							socket.close();
						}
						if (br != null) {
							br.close();
						}
						if (pw != null) {
							pw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
			// 멀티에서 게임 종료 버튼 눌렀을때 모드선택으로 전환
			multiMode.setExitMulti(new ActionListener() {// 게임의 라운드 종료시 DB의 double_check 값을 1로 초기화 시켜주는 작업도 필요함
				@Override
				public void actionPerformed(ActionEvent e) {
					String userID = list.get(0);
					String roomName = roomTitle.get(0);
					pw.println(9);
					pw.flush();
					pw.println(roomName);
					pw.println(userID);
					pw.flush();
					try {
						wordTx.interrupt();
						wordTx.join();
						System.out.println("스래드 1이 종료되었스니다.");
						wordRx.interrupt();
						wordRx.join();
						System.out.println("스래드 2이 종료되었스니다.");
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					multiMode.setVisible(false);
					modeChoice.setVisible(true);
					roomTitle.remove(roomName);
					multiMode.gameStartbtn.setEnabled(true);
				}
			});
			multiMode.setGameStart(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(11);
					pw.flush();
					pw.println(roomTitle.get(0));
					pw.flush();
					multiMode.gameStartbtn.setEnabled(false);
				}
			});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("---- EXIT ----");
	}

	private static class WordTx implements Runnable {
		private PrintWriter pw;

		public WordTx(PrintWriter pw) {
			this.pw = pw;
		}

		@Override
		public void run() {
			// 멀티에서 단어전송 버튼 엑션 리스너 동작 만들기
			multiMode.setWordSendListner(new ActionListener() {// 멀티의 버튼 리서너로 변경해야함
				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(99); //
					pw.flush();
					pw.println(multiMode.getMulti_UserInput());
					pw.println(list.get(0));
					pw.flush();
				}
			});
		}
	}

	// 멀티에서 해결해야할 문제 단어입력 순서 정하기
	private static class WordRx implements Runnable {
		private BufferedReader br;
		private boolean go = true;

		public WordRx(BufferedReader br) {
			this.br = br;
		}

		@Override
		public void run() { // 멀티쪽 단어전송버튼으로 엑션리스너 이벤트가 발생할때마다 전송을하고 서버에서 받는 문구는 이 쓰래드에서 처리
			try {
				while (go && !Thread.currentThread().isInterrupted()) {
					Thread.sleep(100);
					String command = br.readLine();
					String comment = br.readLine();
					System.out.println(command);
					System.out.println(comment);
					if (command.equals("1")) {
						if (comment.equals("중복된 단어는 사용할 수 없습니다.")) {
							System.out.println("중복된 단어는 사용이 불가능합니다.");
							multiMode.setWarning_text(comment);// 경고문구라벨에 경고문구띄우기
						} else if (comment.equals("유효하지 않는 단어입니다. 다시 입력하세요.")) {
							System.out.println("유효하지 않는 단어입니다. 다시 입력하세요.");
							multiMode.setWarning_text(comment);// 경고문구라벨에 경고문구띄우기
						} else if (comment.equals("한글자 사용 불가능.")) {
							System.out.println("한글자 사용 불가능.");
							multiMode.setWarning_text(comment);// 경고문구라벨에 경고문구띄우기
						} else if (comment.equals("끝말이 맞지 않습니다 다시 단어를 입력하세요.")) {
							System.out.println("끝말이 맞지 않습니다.");
							multiMode.setWarning_text(comment);// 경고문구라벨에 경고문구띄우기
						} else if (comment.equals("게임 시작")) {
							System.out.println(comment); // "게임 시작" GUI에 나타내기
							multiMode.btnSendButton.setEnabled(true);

						} else {
							String userID = br.readLine();
							multiMode.setMultiShowID(userID);
							multiMode.setMultiShowWord(comment);
							System.out.println(userID + " : " + comment);
							// 멀티 게임 창에 입력한 단어 띄워주기
							if (!multiMode.getWarning_text().equals("")) {
								multiMode.setWarning_text("");
							} // 올바른 단어 입력시 경고문구 초기화
							if (userID.equals(list.get(0))) {
								multiMode.btnSendButton.setEnabled(false);
								multiMode.resetTimer();
								multiMode.stopTimer();
								multiMode.startTimer();// 버튼 활성화시 타이머 스타트
							} else {
								multiMode.btnSendButton.setEnabled(true);
								multiMode.resetTimer();
								multiMode.stopTimer();
								multiMode.startTimer();// 버튼 활성화시 타이머 스타트
							}
						}
					} else if (command.equals("2")) {
						if (multiMode.getMulti_user1().equals("")) {
							multiMode.setMulti_user1(comment);
							multiMode.gameStartbtn.setEnabled(false);
						} else if (multiMode.getMulti_user1().equals(comment)) {
							System.out.println(comment);
						} else if (multiMode.getMulti_user2().equals("")) {
							multiMode.setMulti_user2(comment);
							multiMode.gameStartbtn.setEnabled(true);
							System.out.println("2번 유저 확인 : " + comment);
						} else if (multiMode.getMulti_user2().equals(comment)) {
							System.out.println(comment);
						} else if (multiMode.getMulti_user3().equals("")) {
							multiMode.setMulti_user3(comment);
							multiMode.gameStartbtn.setEnabled(true);
						}
					} else if (command.equals("3")) {
						multiMode.setMulti_user1("");
						multiMode.setMulti_user2("");
						multiMode.setMulti_user3("");
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}