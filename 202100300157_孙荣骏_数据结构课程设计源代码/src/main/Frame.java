package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 游戏窗口
public class Frame extends JFrame implements ActionListener
{

	//游戏棋盘
	private Panel panel;
	
	// 菜单组件
	JMenuItem jmi_easy,jmi_normal,jmi_hard,jmi_custom;

	//自定义难度窗口
	JFrame jf = new JFrame ();
	int irows=0,ilines=0,imines=0;
	JTextField rowsInput = new JTextField();
	JTextField linesInput = new JTextField();
	JTextField minesInput = new JTextField();

	public Frame()
	{
		try
		{
			//窗口
			this.setTitle("扫雷");
			this.setLayout(null);
			this.setBackground(Color.WHITE);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);






			//菜单
			JMenuBar jmb = new JMenuBar();



			//菜单上的'游戏'按键
			JMenu jm_game = new JMenu("游戏");
			jm_game.setFont(new Font("宋体",Font.PLAIN,15));
			//'游戏'按键下的'重新开始'
			JMenuItem jmi_new = jm_game.add("  重新开始");
			jmi_new.setFont(new Font("宋体",Font.PLAIN,15));
			jmi_new.addActionListener(this);
			jmi_new.setActionCommand("new");

			jm_game.addSeparator();

			//'游戏'按键下的'退出'
			JMenuItem jmi_exit = jm_game.add("  退出");
			jmi_exit.setFont(new Font("宋体",Font.PLAIN,15));
			jmi_exit.addActionListener(this);
			jmi_exit.setActionCommand("exit");

			//将'游戏'按键加入到菜单里
			jmb.add(jm_game);




			//菜单上的'难度'按键
			JMenu jm_degree = new JMenu("难度");
			jm_degree.setFont(new Font("宋体",Font.PLAIN,15));

			//'难度'按键下的'初级'
			this.jmi_easy = jm_degree.add("  初级");
			this.jmi_easy.setFont(new Font("宋体",Font.PLAIN,15));
			this.jmi_easy.addActionListener(this);
			this.jmi_easy.setActionCommand("easy");
			//'难度'按键下的'中级'
			this.jmi_normal = jm_degree.add("√ 中级");
			this.jmi_normal.setFont(new Font("宋体",Font.PLAIN,15));
			this.jmi_normal.addActionListener(this);
			this.jmi_normal.setActionCommand("normal");
			//'难度'按键下的'高级'
			this.jmi_hard = jm_degree.add("  高级");
			this.jmi_hard.setFont(new Font("宋体",Font.PLAIN,15));
			this.jmi_hard.addActionListener(this);
			this.jmi_hard.setActionCommand("hard");

			//分割区域
			jm_degree.addSeparator();

			//'难度'按键下的'自定义'
			this.jmi_custom = jm_degree.add("自定义");
			this.jmi_custom.setFont(new Font("宋体",Font.PLAIN,15));
			this.jmi_custom.addActionListener(this);
			this.jmi_custom.setActionCommand("custom");


			//将'难度'按键加入到菜单里
			jmb.add(jm_degree);



			//设置自定义难度的窗体
			FlowLayout flow = new FlowLayout ();
			jf.setLayout(flow);
			jf.setTitle ("自定义难度");
			jf.setSize(50,70);
			jf.setVisible(false);
			jf.setLocationRelativeTo(null);
			jf.setSize(320,250);


			JLabel rows = new JLabel();
			rows.setText("行数(10-16)");
			Dimension rowsdim = new Dimension(200,40);
			rowsInput.setPreferredSize(rowsdim);

			JLabel lines = new JLabel();
			lines.setText("列数(10-30)");
			Dimension linesdim = new Dimension(200,40);
			linesInput.setPreferredSize(linesdim);

			JLabel mines = new JLabel();
			mines.setText("雷数(10-99)");
			Dimension minesdim = new Dimension(200,40);
			minesInput.setPreferredSize(minesdim);


			JButton btn = new JButton ();
			btn.setText("设置完成");
			btn.addActionListener(this);
			btn.setActionCommand("ok");



			jf.add(rows);jf.add(rowsInput);
			jf.add(lines);jf.add(linesInput);
			jf.add(mines);jf.add(minesInput);
			jf.add(btn);











			//菜单上的'操作说明'按键
			JMenu jm_ops = new JMenu("操作说明");
			jm_degree.setFont(new Font("宋体",Font.PLAIN,12));

			JMenuItem jmi_line1 = jm_ops.add("点击鼠标左键为探查");
			jmi_line1.setFont(new Font("宋体",Font.PLAIN,15));
			JMenuItem jmi_line2 = jm_ops.add("点击鼠标右键为插旗");
			jmi_line2.setFont(new Font("宋体",Font.PLAIN,15));
			JMenuItem jmi_line3 = jm_ops.add("点击鼠标右键两次为标记为问号");
			jmi_line3.setFont(new Font("宋体",Font.PLAIN,15));

			//将'难度'按键加入到菜单里
			jmb.add(jm_ops);


			//将菜单加入到窗口
			this.setJMenuBar(jmb);


			//棋盘
			this.panel = new Panel();
			this.add(this.panel);


			//设置窗口大小及显示位置
			this.panel.setLevel(this.panel.NORMAL,16,16,40);//起始设置为中等难度
			this.setSize(this.panel.getWidth() + 15,this.panel.getHeight() + 60);
			this.setLocationRelativeTo(null);   //居中
			this.setBounds(this.getX() - 63,this.getY() - 63,this.getWidth(),this.getHeight());//略微偏向左上角
			this.setVisible(true);

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this,"一阵神秘力量把你吹到了异世界0.0\r\n\r\n"+e.toString(),"提示",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	//功能：事件监听
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if("new".equals(command))
		{
			this.panel.newGame();
		}
		else if("easy".equals(command))
		{
			this.jmi_easy.setText("√ 初级");
			this.jmi_normal.setText("  中级");
			this.jmi_hard.setText("  高级");
			this.panel.setLevel(this.panel.EASY,9,9,10);
			this.setSize(this.panel.getWidth() + 15,this.panel.getHeight() + 60);
		}
		else if("normal".equals(command))
		{
			this.jmi_easy.setText("  初级");
			this.jmi_normal.setText("√ 中级");
			this.jmi_hard.setText("  高级");
			this.panel.setLevel(this.panel.NORMAL,16,16,40);
			this.setSize(this.panel.getWidth() + 15,this.panel.getHeight() + 60);
		}
		else if("hard".equals(command))
		{
			this.jmi_easy.setText("  初级");
			this.jmi_normal.setText("  中级");
			this.jmi_hard.setText("√ 高级");
			this.panel.setLevel(this.panel.HARD,16,30,99);
			this.setSize(this.panel.getWidth() + 15,this.panel.getHeight() + 60);
		}
		else if("custom".equals(command)){
			jf.setVisible(true);

		}
		else if("ok".equals(command)){
			try{
			irows= Integer.parseInt(rowsInput.getText());
			ilines= Integer.parseInt(linesInput.getText());
			imines= Integer.parseInt(minesInput.getText());
			if(irows>9&&irows<17&&ilines>9&&ilines<31&&imines>9&&imines<100)
			{
				this.jmi_easy.setText("  初级");
				this.jmi_normal.setText("  中级");
				this.jmi_hard.setText("  高级");
				this.panel.setLevel(this.panel.CUSTOM,irows,ilines,imines);
				this.setSize(this.panel.getWidth() + 15,this.panel.getHeight() + 60);
				jf.setVisible(false);
				clearinput();
			}else {
				JOptionPane.showMessageDialog(this,"请按要求输入♪(^∇^*)\r\n\r\n","提示",JOptionPane.ERROR_MESSAGE);
				clearinput();
			}
			}catch (Exception ex){
				JOptionPane.showMessageDialog(this,"请不要不友好的测试我哦(*￣︿￣)\r\n\r\n","提示",JOptionPane.ERROR_MESSAGE);
				clearinput();

			}

		}
		else if("exit".equals(command))
		{
			System.exit(0);
		}

	}

	public void clearinput(){
		rowsInput.setText("");
		linesInput.setText("");
		minesInput.setText("");
	}

}
