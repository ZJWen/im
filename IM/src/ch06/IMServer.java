package ch06;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.portable.ApplicationException;

//import ch05.CheckUser;
//import ch05.ServerThread;
//import ch06.MyThread;

public class IMServer {
	public static void main(String[] args){
		//ServerSocket serverSocket=null;
		//Socket socket=null;
		//OutputStream os=null;
		//InputStream is=null;
		Map<String, Socket> userMap=
				new HashMap<String,Socket>();
		//�����˿ں�
		int port =10086;
		
		try{
			//��������
			ServerSocket serverSocket=new ServerSocket(port);
			System.out.println("������������......");
			//������ӣ�accept����������
			while(true){
			Socket socket=serverSocket.accept();
			System.out.println("�������������......");
			//�����������ȡΪ��
			MyThread st=new MyThread();
			st.setSocket(socket);
			st.setMap(userMap);
			st.start();//�����߳�
			}
			//���տͻ��˷�������
			/*is=socket.getInputStream();
			byte[] b=new byte[1024];
			while(true){
				String str=null;
				int n=is.read(b);
				if(n>=1){
					//���
					str=new String(b,0,n);
					//str--->�û���=����
					System.out.println("�ͻ��˷�������Ϊ��"+str);
				}
				Thread.sleep(1000);
				if(str!=null&&str.length()>3){
					String [] split=str.split("=");
					if(split[0].equals("ac")&&split[1].equals("1234")){
						//��ͻ��˷��ͷ�������
					os=socket.getOutputStream();
						//if(CheckUser.check(split[0],split[1])){
						os=socket.getOutputStream();
						//str+="������>>:"+str;
						os.write("ok".getBytes());
					}else{
						os.write("err".getBytes());
					}
					
				}
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/*try{
				//�ر���������
				os.close();
				is.close();
				socket.close();
				serverSocket.close();
			}catch(Exception e){
				
			}*/
		}
	}
}
