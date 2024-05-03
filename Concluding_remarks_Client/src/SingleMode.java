import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;

public class SingleMode extends JFrame {
	private JLabel 싱글_CPU가주는단어;
	private JTextField 싱글_나의단어;
	private JLabel 싱글_나의프로필;
	private ActionListener exitSingleMode;
	private ActionListener sendWord;
	public JLabel timerLabel;
	public JProgressBar progressBar;
	public Timer timer;
	private JLabel Sing_Warning_text;
	private JButton btnSendWord;
	private Client client;

	public SingleMode(Client client) {
		this.client = client;
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		getContentPane().setLayout(null);
		setTitle("싱글모드");

		JLabel lbl싱글모드 = new JLabel("싱글모드");
		lbl싱글모드.setBounds(12, 10, 57, 15);
		getContentPane().add(lbl싱글모드);

		JButton btn싱글모드_게임종료 = new JButton("");
		btn싱글모드_게임종료.setIcon(new ImageIcon(SingleMode.class.getResource("/img/게임종료 버튼.png")));
		btn싱글모드_게임종료.setBounds(475, 10, 97, 23);
		getContentPane().add(btn싱글모드_게임종료);

		// 게임 종료 버튼 액션 리스너 설정
		btn싱글모드_게임종료.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(SingleMode.this, "게임을 종료하시겠습니까?", "게임 종료",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					stopTimer(); // 타이머 중지
					resetTimer(); // 타이머 초기화

					if (exitSingleMode != null) {
						exitSingleMode.actionPerformed(e);
					}
				}
			}
		});

		싱글_CPU가주는단어 = new JLabel();
		싱글_CPU가주는단어.setBounds(74, 76, 419, 127);
		getContentPane().add(싱글_CPU가주는단어);
		//싱글_CPU가주는단어.setColumns(10);

		싱글_나의단어 = new JTextField();
		싱글_나의단어.setBounds(137, 350, 274, 69);
		getContentPane().add(싱글_나의단어);
		싱글_나의단어.setColumns(10);

		싱글_나의프로필 = new JLabel();
		싱글_나의프로필.setIcon(new ImageIcon(SingleMode.class.getResource("/img/man.png")));
		싱글_나의프로필.setBounds(224, 451, 116, 100);
		getContentPane().add(싱글_나의프로필);

		btnSendWord = new JButton("");
		btnSendWord.setIcon(new ImageIcon(SingleMode.class.getResource("/img/단어전송 버튼.png")));
		btnSendWord.setBounds(423, 373, 97, 23);
		getContentPane().add(btnSendWord);
		btnSendWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		
				// 입력된 단어 확인
				String word = getSendWord().trim(); // 입력된 단어의 앞뒤 공백을 제거합니다.
				if (word.isEmpty()) { // 입력된 단어가 없으면 경고 메시지를 표시합니다.
					JOptionPane.showMessageDialog(SingleMode.this, "단어를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
					return; // 단어가 없으므로 이후 코드를 실행하지 않고 메서드를 종료합니다.
				}

				if (sendWord != null) {
					sendWord.actionPerformed(e);
				}
			}
		});

		// 타이머
		timerLabel = new JLabel("10");
		timerLabel.setOpaque(true);
		timerLabel.setForeground(Color.BLUE);
		timerLabel.setFont(new Font("맑은고딕", Font.PLAIN, 20));
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		timerLabel.setBounds(20, 230, 492, 23);
		getContentPane().add(timerLabel);

		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(41, 260, 492, 23);
		getContentPane().add(progressBar);

		JLabel lblNewLabel = new JLabel("이어서 작성하세요!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(111, 43, 331, 23);
		getContentPane().add(lblNewLabel);

		Sing_Warning_text = new JLabel();
		Sing_Warning_text.setText("");
		Sing_Warning_text.setHorizontalAlignment(SwingConstants.CENTER);
		Sing_Warning_text.setBounds(41, 303, 492, 23);
		getContentPane().add(Sing_Warning_text);

		// 타이머가 종료될 때의 이벤트 설정

	}

	public void showTimeoutDialog() {
		JDialog dialog = new JDialog(SingleMode.this, "타임 아웃", true);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("시간 초과되었습니다!");
		label.setFont(new Font("SansSerif", Font.BOLD, 16));
		label.setForeground(Color.RED);
		panel.add(label, BorderLayout.CENTER);
		dialog.getContentPane().add(panel);
		dialog.setSize(300, 150);
		dialog.setLocationRelativeTo(SingleMode.this);
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

	public void setExitSingleMode(ActionListener listener) {
		this.exitSingleMode = listener;
	}

	public void setSingleSendWord(ActionListener listener) {
		this.sendWord = listener;
	}

	public String getSinWarning_text() {
		return Sing_Warning_text.getText();
	}

	public void setSinWarning_text(String warning) {
		this.Sing_Warning_text.setText(warning);
	}

	// 타이머를 시작하는 메소드
	public void startTimer() {
		timer = new Timer(100, new ActionListener() {
			int count = 100;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count >= 0) {
					timerLabel.setText(Integer.toString(count / 10));
					progressBar.setValue(count);
					count--;
				} else{
					resetTimer();
					timer.stop();

					System.out.println(count);
					
					showTimeoutDialog(); // 타이머가 종료되면 다이얼로그 표시

				}

				if (count == 0) {
					resetTimer();
					timer.stop();

					
					client.timerFinished();

					showTimeoutDialog(); // 시간 초과 다이얼로그를 표시합니다.
					// 게임의 라운드 종료시 DB의 double_check 값을 1로 초기화 시켜주는 작업

				}
			}
		});

		timer.start();
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

	public String getSendWord() {
		return 싱글_나의단어.getText();
	}
	//---------------------------경고문구 라벨에 받아오기
		public String getSingleWarning_text() {
			return Sing_Warning_text.getText();
		}
		
		public void setSingleWarning_text(String SlngWarning) {
			this.Sing_Warning_text.setText(SlngWarning);
		}
	//---------------------------경고문구 라벨에 받아오기
		//---------------------------멀티모드 유저 입력단어 받아오기
		public String getSinShowWord() {
			return 싱글_CPU가주는단어.getText();
		}

		public void setSinShowWord(String ShowWord) {
			this.싱글_CPU가주는단어.setText(ShowWord);
		}
		//---------------------------멀티모드 유저 입력단어 받아오기
}
