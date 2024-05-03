import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class GameRule extends JFrame {
	private ActionListener btnGameRuleBack;
	private LineBorder bb = new LineBorder(Color.WHITE, 1, true);

	public GameRule() {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		getContentPane().setLayout(null);
		setTitle("게임 방법");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 564, 519);
		getContentPane().add(tabbedPane);

		// 첫 번째 탭 - 게임 설명
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("게임 방법", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GameRule.class.getResource("/img/기본 게임룰.png")));
		lblNewLabel.setBounds(0, 0, 559, 490);
		panel_1.add(lblNewLabel);

		// 두 번째 탭 - 게임 규칙
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("싱글모드", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(GameRule.class.getResource("/img/싱글모드 룰.png")));
		lblNewLabel_1.setBounds(0, 0, 559, 490);
		panel_2.add(lblNewLabel_1);

		// 세 번째 탭 - 추가 내용
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("멀티모드", null, panel_3, null);
		panel_3.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(GameRule.class.getResource("/img/멀티모드.png")));
		lblNewLabel_2.setBounds(0, 0, 559, 490);
		panel_3.add(lblNewLabel_2);

		// 뒤로가기 버튼
		JButton btn뒤로가기 = new JButton();
		btn뒤로가기.setBorderPainted(false);
		btn뒤로가기.setContentAreaFilled(false);
		btn뒤로가기.setOpaque(false);
		btn뒤로가기.setIcon(new ImageIcon(GameRule.class.getResource("/img/뒤로가기 버튼.png")));
		btn뒤로가기.setBounds(475, 528, 100, 30);
		getContentPane().add(btn뒤로가기);

		btn뒤로가기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnGameRuleBack != null) {
					btnGameRuleBack.actionPerformed(e);
				}
			}
		});
	}

	// 뒤로가기 버튼 액션 리스너 설정 메소드
	public void setGameRuleBackListener(ActionListener listener) {
		this.btnGameRuleBack = listener;
	}
}
