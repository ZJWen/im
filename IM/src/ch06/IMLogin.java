package ch06;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextMeasurer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IMLogin extends JFrame 
implements ActionListener{
	private JTextField txtUser;
	private JPasswordField txtpass;
	private JButton btnLong;
	private JButton btnReg;
	private JPanel imagePanel;
	 private ImageIcon background;
	
	public IMLogin(){
		 this.setSize(430,335);//Set the frame size
	        this.setResizable(false);//不让窗口改变大小
	       
	        
	        //设置窗口位置
	        this.setLocationByPlatform(true);
	        this.setLocation(295,218);
	        setTitle("用户登录");//设置标题
	        this.setLocationRelativeTo(null);//窗口居中显示
	        //this.getContentPane().setVisible(false);//添加背景色
	        //this.setBackground(Color.blue);
	        String filename="src/ch06/icon.jpg";
			ImageIcon icon=new ImageIcon(filename);
			this.setIconImage(icon.getImage());
			this.setLayout(null);//绝对布局
	        //添加图片
			
			 background = new ImageIcon("src/ch06/1.jpg");// 背景图片
			  JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
			  // 把标签的大小位
			  // 设置为图片刚好填充整个面板
			  label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
			  // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
			  imagePanel = (JPanel) this.getContentPane();
			  imagePanel.setOpaque(false);
			
			  // 把背景图片添加到分层窗格的最底层作为背景
			  this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
			  this.setSize(background.getIconWidth()+8, background.getIconHeight()+8);
			  this.getLayeredPane().setLayout(null);
			  
			 
	        JLabel labUser=new JLabel();
	        labUser.setText("用户名:");
	        labUser.setBounds(30, 155, 60, 33);
	        this.add(labUser);
	        //文本输入框
	         txtUser=new JTextField();
	        txtUser.setSize(260,50);
	        txtUser.setBounds(134, 155, 260, 33);
	        txtUser.setText("test");
	        this.add(txtUser);
	        
	        JLabel labPass=new JLabel();
	        labPass.setText("密    码:");
	        labPass.setBounds(30, 195, 60, 33);
	        this.add(labPass);
	        
	         txtpass=new JPasswordField();
	        txtpass.setSize(260,50);
	        txtpass.setBounds(134,195,260,33);
	        txtpass.setText("1234");
	        this.add(txtpass);
	        //建按钮
	        btnLong=new JButton(" 登 录 ");
	        btnLong.setBounds(58,230,130,40);
	        //登录按钮添加到监听器
	        btnLong.addActionListener(this);
	        this.add(btnLong);
	        
	         btnReg=new JButton(" 注 册");
	        btnReg.setBounds(227,230,130,40);
	        this.add(btnReg);
	         
	        this.setVisible(true);//窗口显示
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	}//当按钮动作发生时候，该方法处理按钮的动作
		//actionevent 动作事件对象
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String ustr=txtUser.getText();
		String pwd=txtpass.getText();
		System.out.println(ustr+"----"+pwd);
		
		Socket socket=null;
		OutputStream os=null;
		InputStream is=null;
		//服务器端IP地址
		String serverIP="127.0.0.1";
		//服务器端端口号
		int port=10086;
		//发送内容-----发的使我们输入的用户名和密码----
		String data =ustr+"="+pwd;
		try{
			//建立连接
			socket=new Socket(serverIP, port);
			System.out.println("客户端启动...");
			//while(true){
				//向服务器端发送数据
				OutputStream os1=socket.getOutputStream();
				OutputStreamWriter osw=new OutputStreamWriter(os1);
				PrintWriter pw=new PrintWriter(osw,true);
				pw.println(data);
				//os=socket.getOutputStream();
				//os.write(data.getBytes());
				//接收服务器端发回数据
				InputStream is1=socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(is1);
				BufferedReader br=new BufferedReader(isr);
				//is=socket.getInputStream();
				//byte[] b=new byte[1024];
				//read读取服务器端返回的数据实际长度
				//int n=is.read(b);
				String huifu =br.readLine();
				//输出反馈数据---接收服务器的反馈---
				//data=new String(b,0,n);
				System.out.println("服务器反馈："+huifu);
				//if(data!=null&&data.length()>1){
					//break;
				//}
				
			//}
		//}
		if(huifu.equals("ok")){
			JOptionPane.showMessageDialog(null, "用户登录成功");
			//弹出消息框
			IMMessage mes=new IMMessage();
			mes.setSocket(socket);
			mes.addUser(ustr);
			
			this.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(null, "用户登录失败");
		}
		}catch(Exception e){
			e.printStackTrace();
		}/*finally{
			try{
				os.close();
			is.close();
			socket.close();
			}catch(Exception e2){
				
			}
		}*/
		
		}
	public static void main(String[] args) {
		IMLogin IM = new IMLogin();
	}
	
}
