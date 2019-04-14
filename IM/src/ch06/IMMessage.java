package ch06;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextMeasurer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;



public class IMMessage extends JFrame
	implements ActionListener,Runnable{
	private JTextField txtMag;
	private JButton btnSend;
	private JTextArea txtArea;
	private JComboBox cmbUserList;

	private Socket socket;
	//通过登录窗口传递的socket
	public void setSocket(Socket socket){
		this.socket=socket;
		//创建一个线程对象
		Thread t=new Thread(this);
		t.start();
	}
	private String userName;
	public void addUser(String ustr){
		this.userName=ustr;
		//cmbUserList.addItem(ustr);
		this.setTitle(ustr+"正在聊天");
	}
	
	public IMMessage(){
		this.setTitle("用户聊天窗口");
		
		this.setSize(500, 450);
		this.setLocationRelativeTo(null);//窗口居中显示
		txtMag=new JTextField();
		btnSend=new JButton("发送");
		cmbUserList=new JComboBox();
		txtArea=new JTextArea();
		//小面板
		JPanel panxiao=new JPanel();
		panxiao.setLayout(new GridLayout(1, 2));
		panxiao.add(cmbUserList);
		panxiao.add(btnSend);
		
		//大面板
		JPanel panDmb=new JPanel();
		panDmb.setLayout(new GridLayout(2, 1));
		panDmb.add(txtMag);//输入框独占一行
		panDmb.add(panxiao);//把小面板放进来
		
		txtArea=new JTextArea();
		JScrollPane span=new JScrollPane(txtArea);
		span.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//按钮添加监听
		
		btnSend.addActionListener(this);
		//设置字体
		//txtArea=new JTextArea();
		JScrollPane spane=new JScrollPane(txtArea);
		txtArea.setLineWrap(true);
		txtArea.setFont(new Font("宋体",0,30));
		
		//this.getContentPane().add(btnSend) ;
		
		
		 try{//读取聊天记录
			 String msg =FileTest.ReaderMessage();
			 txtArea.append(msg);
		 }catch(Exception e){
			 JOptionPane.showMessageDialog(null, "聊天记录");
		 }
			
		

		this.setLayout(new BorderLayout());
		this.add(panDmb,BorderLayout.NORTH);
		this.add(spane,BorderLayout.CENTER);
		this.setVisible(true);
		//创建一个线程对象
		Thread t=new Thread(this);
		t.start();//开启一个线程		
	}//将想要发出的消息文本取出放在文本域

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String text=txtMag.getText();
		if(!text.equals(""))
		{
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//String t=df.format(d);
			String msg="\t"+df.format(d)+"\n"+userName+"："+"\n  "+text+"\n";
			txtMag.setText("");
			txtArea.append(msg);
			try{//向文件中写
				FileTest.WriterMassage(msg);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(null, "写入文件出错");
			}
		}else{
			JOptionPane.showMessageDialog(null, "请输入你想要说的");
		}
		//向服务器端发送数据
		try{
			if(text!=null&&!text.equals("")){}
			OutputStream os=socket.getOutputStream();
			OutputStreamWriter osw=new OutputStreamWriter(os);
			PrintWriter pw=new PrintWriter(osw,true);
			String um=(String)this.cmbUserList.getSelectedItem();
			pw.println("msg%"+um+":"+text);
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
	//线程方法主体

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			InputStream is=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			while(true){
			//输出反馈数据----接收服务器的反馈----
			String huifu=br.readLine();
			//和服务器端做一个信息约定
			//add%新的用户--->刷新下拉框将新用户添加进下拉列表
			//msg%用户名：信息内容--->用户退出系统，从下拉框移除一个用户
			String [] ms=huifu.split("%");
			if(ms[0].equals("add")){
				this.cmbUserList.addItem(ms[1]);
			}else if(ms[0].equals("msg")){
				this.txtArea.append("\n"+ms[1]);
			}else if(ms[0].equals("exit")){
				this.cmbUserList.removeItem(ms[1]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
