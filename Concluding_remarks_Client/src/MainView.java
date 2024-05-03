import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class MainView extends JFrame {
	private ActionListener selectMode;
	private ActionListener myPage;
	private ActionListener gameExplanation;
	private ActionListener mainLogout;

	public MainView() {
		getContentPane().setBackground(Color.LIGHT_GRAY);

		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		setTitle("메인화면");
		getContentPane().setLayout(null);

		JButton btn모드선택 = new JButton();
		btn모드선택.setBounds(98, 79, 100, 30);
		btn모드선택.setIcon(new ImageIcon(MainView.class.getResource("/img/모드-cutout.png")));
		btn모드선택.setBorderPainted(false);
		btn모드선택.setContentAreaFilled(false);
		btn모드선택.setOpaque(false);
		getContentPane().add(btn모드선택);

		btn모드선택.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectMode != null) {
					selectMode.actionPerformed(e);
				}
			}
		});

		JButton btn마이페이지 = new JButton();
		btn마이페이지.setBounds(98, 155, 100, 30);
		btn마이페이지.setIcon(new ImageIcon(MainView.class.getResource("/img/마이페이지 버튼1.png")));
		btn마이페이지.setBorderPainted(false);
		btn마이페이지.setContentAreaFilled(false);
		btn마이페이지.setOpaque(false);
		getContentPane().add(btn마이페이지);

		btn마이페이지.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myPage != null) {
					myPage.actionPerformed(e);
				}
			}

		});

		JButton btn게임방법 = new JButton();
		btn게임방법.setBounds(98, 231, 100, 30);
		btn게임방법.setIcon(new ImageIcon(MainView.class.getResource("/img/게임방법 버튼.png")));
		btn게임방법.setBorderPainted(false);
		btn게임방법.setContentAreaFilled(false);
		btn게임방법.setOpaque(false);
		getContentPane().add(btn게임방법);

		btn게임방법.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameExplanation != null) {
					gameExplanation.actionPerformed(e);
				}
			}
		});

		JButton btn메인화면_로그아웃 = new JButton();
		btn메인화면_로그아웃.setBounds(475, 10, 100, 30);
		btn메인화면_로그아웃.setIcon(new ImageIcon("/Users/min-yechan/Desktop/6_팀플사진_240229/로그아웃.png"));
		btn메인화면_로그아웃.setBorderPainted(false);
		btn메인화면_로그아웃.setContentAreaFilled(false);
		btn메인화면_로그아웃.setOpaque(false);
		getContentPane().add(btn메인화면_로그아웃);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainView.class.getResource("/img/컴2.png")));
		lblNewLabel.setBounds(0, 1, 599, 571);
		getContentPane().add(lblNewLabel);

		btn메인화면_로그아웃.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mainLogout != null) {
					mainLogout.actionPerformed(e);
				}
			}
		});
	}

	public void setSelectMode(ActionListener listener) {
		this.selectMode = listener;
	}

	public void setMypage(ActionListener listener) {
		this.myPage = listener;
	}

	public void setGameExplanation(ActionListener listener) {
		this.gameExplanation = listener;
	}

	public void setMainLogout(ActionListener listener) {
		this.mainLogout = listener;
	}
}