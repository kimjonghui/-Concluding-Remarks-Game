import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

public class MyPage extends JFrame {
	private JLabel MyPage_ID;
	private JLabel MyPage_SingleScore;
	private JLabel MyPage_Winner_Count;
	private ActionListener exitMypage;

	public MyPage() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		setTitle("마이페이지");
		getContentPane().setLayout(null);

		JButton btn마이페이지_뒤로가기 = new JButton();
		btn마이페이지_뒤로가기.setBorderPainted(false);
		btn마이페이지_뒤로가기.setContentAreaFilled(false);
		btn마이페이지_뒤로가기.setOpaque(false);
		btn마이페이지_뒤로가기.setIcon(new ImageIcon(MyPage.class.getResource("/img/뒤로가기 버튼.png")));
		btn마이페이지_뒤로가기.setBounds(414, 252, 100, 30);
		getContentPane().add(btn마이페이지_뒤로가기);

		btn마이페이지_뒤로가기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (exitMypage != null) {
					exitMypage.actionPerformed(e);
				}
			}
		});

		JLabel lbl닉네임 = new JLabel("닉네임");
		lbl닉네임.setBounds(93, 56, 57, 15);
		lbl닉네임.setFont(new Font("휴먼옛체", Font.PLAIN, 15));
		getContentPane().add(lbl닉네임);

		JLabel lbl싱글_최고점수 = new JLabel("싱글 최고점수");
		lbl싱글_최고점수.setBounds(93, 145, 100, 15);
		lbl싱글_최고점수.setFont(new Font("휴먼옛체", Font.PLAIN, 15));
		getContentPane().add(lbl싱글_최고점수);

		JLabel lbl멀티우승횟수 = new JLabel("멀티 우승 횟수");
		lbl멀티우승횟수.setBounds(93, 243, 116, 15);
		lbl멀티우승횟수.setFont(new Font("휴먼옛체", Font.PLAIN, 15));
		getContentPane().add(lbl멀티우승횟수);

		MyPage_ID = new JLabel("");
		MyPage_ID.setBounds(205, 53, 116, 21);
		getContentPane().add(MyPage_ID);

		MyPage_SingleScore = new JLabel("");
		MyPage_SingleScore.setBounds(205, 142, 116, 21);
		getContentPane().add(MyPage_SingleScore);

		MyPage_Winner_Count = new JLabel("");
		MyPage_Winner_Count.setBounds(205, 240, 116, 21);
		getContentPane().add(MyPage_Winner_Count);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MyPage.class.getResource("/img/마이-cutout.png")));
		lblNewLabel.setBounds(0, 0, 600, 572);
		getContentPane().add(lblNewLabel);

	}

	public void setMyPageID(String userID) {
		this.MyPage_ID.setText(userID);
	}

	public void setMyPageSingleScore(String MyPage_SingleScore) {
		this.MyPage_SingleScore.setText(MyPage_SingleScore);
	}

	public void setMultiWinnerCount(String MyPage_Winner_count) {
		this.MyPage_Winner_Count.setText(MyPage_Winner_count);
	}

	public void setExitMypage(ActionListener listener) {
		this.exitMypage = listener;
	}

	public String getMyPageID() {
		return MyPage_ID.getText();
	}

	public String getMyPageSingleScore() {
		return MyPage_SingleScore.getText();
	}

	public String getMyPageMultiWinnerCount() {
		return MyPage_Winner_Count.getText();
	}

}