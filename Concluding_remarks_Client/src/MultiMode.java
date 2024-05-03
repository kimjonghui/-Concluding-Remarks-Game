import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.ImageIcon;

public class MultiMode extends JFrame {
	private JLabel MultiShowID;
	private JLabel Multi_timer;
	private JLabel Multi_user1;
	private JLabel Multi_user2;
	private JLabel Multi_user3;
	private TextField Multi_UserInput;
	private ActionListener exitMulti;
	private ActionListener wordSend;
	private ActionListener gamestart;
	private JLabel timerLabel;
	private JProgressBar progressBar;
	private Timer timer;
	private JLabel Mul_Warning_text;
	public JButton btnSendButton;
	public JButton gameStartbtn;
	private JLabel MultiShowWord;
	Client client;
	private Timer wateTimer;
	private int count2 = 100;

	public MultiMode() {

		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		setTitle("멀티 모드");
		getContentPane().setLayout(null);

		JLabel lbl멀티모드 = new JLabel("멀티 모드");
		lbl멀티모드.setBounds(12, 10, 57, 15);
		getContentPane().add(lbl멀티모드);

		JButton btn멀티모드_게임종료 = new JButton("");
		btn멀티모드_게임종료.setIcon(new ImageIcon(MultiMode.class.getResource("/img/게임종료 버튼.png")));
		btn멀티모드_게임종료.setBounds(475, 10, 97, 23);
		getContentPane().add(btn멀티모드_게임종료);

		btn멀티모드_게임종료.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(MultiMode.this, "게임을 종료하시겠습니까?", "게임 종료",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					stopTimer(); // 타이머 중지
					resetTimer(); // 타이머 초기화

					if (exitMulti != null) {
						exitMulti.actionPerformed(e);
					}
				}
			}
		});

		MultiShowID = new JLabel();
		MultiShowID.setBounds(106, 100, 106, 100);
		MultiShowID.setBackground(Color.LIGHT_GRAY);
		MultiShowID.setText("아이디");
		getContentPane().add(MultiShowID);

		Multi_timer = new JLabel();
		Multi_timer.setBounds(41, 234, 499, 21);
		getContentPane().add(Multi_timer);

		Multi_user1 = new JLabel("");
		Multi_user1.setBounds(40, 487, 136, 64);
		Multi_user1.setBackground(Color.PINK);
		FlowLayout fl_유저1_패널 = (FlowLayout) Multi_user1.getLayout();
		getContentPane().add(Multi_user1);
		System.out.println(Client.list.toString());

		Multi_user2 = new JLabel();
		Multi_user2.setBounds(245, 487, 137, 64);
		Multi_user2.setText("");
		getContentPane().add(Multi_user2);

		Multi_user3 = new JLabel();
		Multi_user3.setBounds(388, 487, 137, 64);
		Multi_user3.setText("");
		getContentPane().add(Multi_user3);

		Multi_UserInput = new TextField(20);
		Multi_UserInput.setBounds(145, 321, 249, 88);
		getContentPane().add(Multi_UserInput);

		Mul_Warning_text = new JLabel();
		Mul_Warning_text.setBounds(106, 292, 331, 23);
		Mul_Warning_text.setText("");
		getContentPane().add(Mul_Warning_text);
		Mul_Warning_text.setHorizontalAlignment(JLabel.CENTER);

		btnSendButton = new JButton("");
		btnSendButton.setIcon(new ImageIcon(MultiMode.class.getResource("/img/단어전송 버튼.png")));
		btnSendButton.setBounds(297, 419, 97, 23);
		getContentPane().add(btnSendButton);
		btnSendButton.setEnabled(false);

		btnSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// stopTimer(); // 타이머 중지
				// resetTimer(); // 타이머 초기화
				// startTimer(); // 타이머 시작

				// 입력된 단어 확인
				String word = getMulti_UserInput().trim(); // 입력된 단어의 앞뒤 공백을 제거합니다.
				if (word.isEmpty()) { // 입력된 단어가 없으면 경고 메시지를 표시합니다.
					JOptionPane.showMessageDialog(MultiMode.this, "단어를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
					return; // 단어가 없으므로 이후 코드를 실행하지 않고 메서드를 종료합니다.
				}

				if (wordSend != null) {
					wordSend.actionPerformed(e);
				}
			}
		});

		// 타이머
		timerLabel = new JLabel("10");
		timerLabel.setBounds(20, 230, 492, 23);
		timerLabel.setOpaque(true);
		timerLabel.setForeground(Color.BLUE);
		timerLabel.setFont(new Font("맑은고딕", Font.PLAIN, 20));
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(timerLabel);

		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(41, 260, 492, 23);
		getContentPane().add(progressBar);

		JLabel lblNewLabel = new JLabel("이어서 작성하세요!");
		lblNewLabel.setBounds(106, 72, 331, 23);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);

		MultiShowWord = new JLabel("단어");
		getContentPane().add(MultiShowWord);
		MultiShowWord.setBounds(265, 99, 117, 103);

		gameStartbtn = new JButton("");
		gameStartbtn.setIcon(new ImageIcon(MultiMode.class.getResource("/img/게임시작 버튼.png")));
		gameStartbtn.setBounds(477, 416, 117, 29);
		getContentPane().add(gameStartbtn);
		gameStartbtn.setEnabled(false);
		gameStartbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(MultiMode.this, "게임을 시작하시겠습니까?", "게임 시작",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					if (gamestart != null) {
						gamestart.actionPerformed(e);
					}
				}
			}
		});

	}

	public void showTimeoutDialog() {
		JDialog dialog = new JDialog(MultiMode.this, "타임 아웃", true);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("시간 초과되었습니다!");
		label.setFont(new Font("SansSerif", Font.BOLD, 16));
		label.setForeground(Color.RED);
		panel.add(label, BorderLayout.CENTER);
		dialog.getContentPane().add(panel);
		dialog.setSize(300, 150);
		dialog.setLocationRelativeTo(MultiMode.this);
		dialog.setVisible(true);

		// 다이얼로그가 닫힐 때 처리할 작업을 추가합니다.
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				resetTimer(); // 타이머 초기화
				stopTimer(); // 이 부분에서 타이머를 중지시킵니다.
			}
		});

		dialog.setVisible(false);

	}

	public void showWinnerDialog() {
		JDialog dialog = new JDialog(MultiMode.this, "", true);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("승리하였습니다!");
		label.setFont(new Font("SansSerif", Font.BOLD, 16));
		label.setForeground(Color.RED);
		panel.add(label, BorderLayout.CENTER);
		dialog.getContentPane().add(panel);
		dialog.setSize(300, 150);
		dialog.setLocationRelativeTo(MultiMode.this);
		dialog.setVisible(true);

		// 다이얼로그가 닫힐 때 처리할 작업을 추가합니다.
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				resetWateTimer(); // 타이머 초기화
				stopWateTimer(); // 이 부분에서 타이머를 중지시킵니다.
			}
		});

		dialog.setVisible(false);

	}

	// 타이머를 시작하는 메소드
	public void startTimer() {
		timer = new Timer(100, new ActionListener() {
			int count = 100;

			@Override
			public void actionPerformed(ActionEvent e) {
				count--;
				if (count >= 0) {
					timerLabel.setText(Integer.toString(count / 10));
					progressBar.setValue(count);
				} else {
					resetTimer();
					timer.stop();
					showTimeoutDialog();
					;

				}
			}
		});
		timer.start();
	}

	public void userWaitTimer() {
		wateTimer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				count2--;
				if (count2 >= 0) {
					System.out.println(Integer.toString(count2 / 10));
					// progressBar.setValue(count);
				} else {
					resetTimer();
					wateTimer.stop();
					client.timerFinished();

				}
			}
		});
		wateTimer.start();
	}

	// 타이머를 중지하는 메소드
	public void stopWateTimer() {
		if (wateTimer != null) {
			wateTimer.stop();
		}
	}

	// 타이머를 초기화하는 메소드
	public void resetWateTimer() {
		count2 = 100;
	}

	// 타이머를 중지하는 메소드
	public void stopTimer() {
		if (timer != null) {
			timer.stop();
		}
	}

	// 타이머를 초기화하는 메소드
	public void resetTimer() {
		timerLabel.setText("10");
		progressBar.setValue(100);
	}

	public void setGameStart(ActionListener listener) {
		this.gamestart = listener;

	}

	// ---------------------------멀티모드 유저 입력단어 받아오기
	public String getMultiShowWord() {
		return MultiShowWord.getText();
	}

	public void setMultiShowWord(String ShowWord) {
		this.MultiShowWord.setText(ShowWord);
	}
	// ---------------------------멀티모드 유저 입력단어 받아오기

	// ---------------------------멀티모드 유저 입력단어 받아오기
	public String getMultiShowID() {
		return MultiShowID.getText();
	}

	public void setMultiShowID(String ShowId) {
		this.MultiShowID.setText(ShowId);
	}
	// ---------------------------멀티모드 유저 입력단어 받아오기

	// ---------------------------경고문구 라벨에 받아오기
	public String getWarning_text() {
		return Mul_Warning_text.getText();
	}

	public void setWarning_text(String warning) {
		this.Mul_Warning_text.setText(warning);
	}
	// ---------------------------경고문구 라벨에 받아오기

	// ---------------------------입장유저 1 2 3
	public String getMulti_user1() {
		return Multi_user1.getText();
	}

	public void setMulti_user1(String userID) {
		this.Multi_user1.setText(userID);
	}

	public String getMulti_user2() {
		return Multi_user2.getText();
	}

	public void setMulti_user2(String userID) {
		this.Multi_user2.setText(userID);
	}

	public String getMulti_user3() {
		return Multi_user3.getText();
	}

	public void setMulti_user3(String userID) {
		this.Multi_user3.setText(userID);
	}

	// ---------------------------입장유저 1 2 3
	public String getMulti_UserInput() {
		return Multi_UserInput.getText();
	}

	public ActionListener getExitMulti() {
		return exitMulti;
	}

	public void setExitMulti(ActionListener listener) {
		this.exitMulti = listener;
	}

	public void setWordSendListner(ActionListener wordSend) {
		this.wordSend = wordSend;
	}
}