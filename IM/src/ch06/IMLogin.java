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
	        this.setResizable(false);//���ô��ڸı��С
	       
	        
	        //���ô���λ��
	        this.setLocationByPlatform(true);
	        this.setLocation(295,218);
	        setTitle("�û���¼");//���ñ���
	        this.setLocationRelativeTo(null);//���ھ�����ʾ
	        //this.getContentPane().setVisible(false);//��ӱ���ɫ
	        //this.setBackground(Color.blue);
	        String filename="src/ch06/icon.jpg";
			ImageIcon icon=new ImageIcon(filename);
			this.setIconImage(icon.getImage());
			this.setLayout(null);//���Բ���
	        //���ͼƬ
			
			 background = new ImageIcon("src/ch06/1.jpg");// ����ͼƬ
			  JLabel label = new JLabel(background);// �ѱ���ͼƬ��ʾ��һ����ǩ����
			  // �ѱ�ǩ�Ĵ�Сλ
			  // ����ΪͼƬ�պ�����������
			  label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
			  // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��
			  imagePanel = (JPanel) this.getContentPane();
			  imagePanel.setOpaque(false);
			
			  // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����
			  this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
			  this.setSize(background.getIconWidth()+8, background.getIconHeight()+8);
			  this.getLayeredPane().setLayout(null);
			  
			 
	        JLabel labUser=new JLabel();
	        labUser.setText("�û���:");
	        labUser.setBounds(30, 155, 60, 33);
	        this.add(labUser);
	        //�ı������
	         txtUser=new JTextField();
	        txtUser.setSize(260,50);
	        txtUser.setBounds(134, 155, 260, 33);
	        txtUser.setText("test");
	        this.add(txtUser);
	        
	        JLabel labPass=new JLabel();
	        labPass.setText("��    ��:");
	        labPass.setBounds(30, 195, 60, 33);
	        this.add(labPass);
	        
	         txtpass=new JPasswordField();
	        txtpass.setSize(260,50);
	        txtpass.setBounds(134,195,260,33);
	        txtpass.setText("1234");
	        this.add(txtpass);
	        //����ť
	        btnLong=new JButton(" �� ¼ ");
	        btnLong.setBounds(58,230,130,40);
	        //��¼��ť��ӵ�������
	        btnLong.addActionListener(this);
	        this.add(btnLong);
	        
	         btnReg=new JButton(" ע ��");
	        btnReg.setBounds(227,230,130,40);
	        this.add(btnReg);
	         
	        this.setVisible(true);//������ʾ
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	}//����ť��������ʱ�򣬸÷�������ť�Ķ���
		//actionevent �����¼�����
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String ustr=txtUser.getText();
		String pwd=txtpass.getText();
		System.out.println(ustr+"----"+pwd);
		
		Socket socket=null;
		OutputStream os=null;
		InputStream is=null;
		//��������IP��ַ
		String serverIP="127.0.0.1";
		//�������˶˿ں�
		int port=10086;
		//��������-----����ʹ����������û���������----
		String data =ustr+"="+pwd;
		try{
			//��������
			socket=new Socket(serverIP, port);
			System.out.println("�ͻ�������...");
			//while(true){
				//��������˷�������
				OutputStream os1=socket.getOutputStream();
				OutputStreamWriter osw=new OutputStreamWriter(os1);
				PrintWriter pw=new PrintWriter(osw,true);
				pw.println(data);
				//os=socket.getOutputStream();
				//os.write(data.getBytes());
				//���շ������˷�������
				InputStream is1=socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(is1);
				BufferedReader br=new BufferedReader(isr);
				//is=socket.getInputStream();
				//byte[] b=new byte[1024];
				//read��ȡ�������˷��ص�����ʵ�ʳ���
				//int n=is.read(b);
				String huifu =br.readLine();
				//�����������---���շ������ķ���---
				//data=new String(b,0,n);
				System.out.println("������������"+huifu);
				//if(data!=null&&data.length()>1){
					//break;
				//}
				
			//}
		//}
		if(huifu.equals("ok")){
			JOptionPane.showMessageDialog(null, "�û���¼�ɹ�");
			//������Ϣ��
			IMMessage mes=new IMMessage();
			mes.setSocket(socket);
			mes.addUser(ustr);
			
			this.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(null, "�û���¼ʧ��");
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
