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
	//ͨ����¼���ڴ��ݵ�socket
	public void setSocket(Socket socket){
		this.socket=socket;
		//����һ���̶߳���
		Thread t=new Thread(this);
		t.start();
	}
	private String userName;
	public void addUser(String ustr){
		this.userName=ustr;
		//cmbUserList.addItem(ustr);
		this.setTitle(ustr+"��������");
	}
	
	public IMMessage(){
		this.setTitle("�û����촰��");
		
		this.setSize(500, 450);
		this.setLocationRelativeTo(null);//���ھ�����ʾ
		txtMag=new JTextField();
		btnSend=new JButton("����");
		cmbUserList=new JComboBox();
		txtArea=new JTextArea();
		//С���
		JPanel panxiao=new JPanel();
		panxiao.setLayout(new GridLayout(1, 2));
		panxiao.add(cmbUserList);
		panxiao.add(btnSend);
		
		//�����
		JPanel panDmb=new JPanel();
		panDmb.setLayout(new GridLayout(2, 1));
		panDmb.add(txtMag);//������ռһ��
		panDmb.add(panxiao);//��С���Ž���
		
		txtArea=new JTextArea();
		JScrollPane span=new JScrollPane(txtArea);
		span.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//��ť��Ӽ���
		
		btnSend.addActionListener(this);
		//��������
		//txtArea=new JTextArea();
		JScrollPane spane=new JScrollPane(txtArea);
		txtArea.setLineWrap(true);
		txtArea.setFont(new Font("����",0,30));
		
		//this.getContentPane().add(btnSend) ;
		
		
		 try{//��ȡ�����¼
			 String msg =FileTest.ReaderMessage();
			 txtArea.append(msg);
		 }catch(Exception e){
			 JOptionPane.showMessageDialog(null, "�����¼");
		 }
			
		

		this.setLayout(new BorderLayout());
		this.add(panDmb,BorderLayout.NORTH);
		this.add(spane,BorderLayout.CENTER);
		this.setVisible(true);
		//����һ���̶߳���
		Thread t=new Thread(this);
		t.start();//����һ���߳�		
	}//����Ҫ��������Ϣ�ı�ȡ�������ı���

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String text=txtMag.getText();
		if(!text.equals(""))
		{
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//String t=df.format(d);
			String msg="\t"+df.format(d)+"\n"+userName+"��"+"\n  "+text+"\n";
			txtMag.setText("");
			txtArea.append(msg);
			try{//���ļ���д
				FileTest.WriterMassage(msg);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(null, "д���ļ�����");
			}
		}else{
			JOptionPane.showMessageDialog(null, "����������Ҫ˵��");
		}
		//��������˷�������
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
	//�̷߳�������

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			InputStream is=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			while(true){
			//�����������----���շ������ķ���----
			String huifu=br.readLine();
			//�ͷ���������һ����ϢԼ��
			//add%�µ��û�--->ˢ�����������û���ӽ������б�
			//msg%�û�������Ϣ����--->�û��˳�ϵͳ�����������Ƴ�һ���û�
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
