package ch06;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class MyThread extends Thread{
	private Socket socket;
	public void setSocket(Socket socket){
		this.socket=socket;
	}
	private Map<String, Socket> userHashMap;
	public void setMap(Map<String, Socket> userMap){
		this.userHashMap=userMap;
	}
	public void run(){
		try{
			//���տͻ��˷���������
			InputStream is=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			String str=br.readLine();
			//str--->�û���=����
			System.out.println("�ͻ��˷�������Ϊ��"+str);
			Thread.sleep(1000);
			
			if(str!=null&&str.length()>3){
				String [] split=str.split("=");
				//��ͻ��˷��ͷ�������
				OutputStream os=socket.getOutputStream();
				OutputStreamWriter osw=new OutputStreamWriter(os);
				PrintWriter pw=new PrintWriter(osw,true);
			
			
			//String uandps=br.readLine();
			//String [] ups=uandps.split("=");
			//String u=ups[0];
			//String p=ups[1];
			//������ͻ���
			
			//�������ݿ���֤
				String userName=split[0];
			if(CheckUser.check(userName,split[1])){
				//�����û������ֺ�socket��Ӧ��ϵ
				userHashMap.put(userName,socket);
				pw.println("ok");
				//�����û���ӽ���--->���㲥�������û�
				for(String us:userHashMap.keySet()){
					if(!us.equals(userName)){
					Socket s=userHashMap.get(us);
					OutputStream os1=s.getOutputStream();
					OutputStreamWriter osw1=new OutputStreamWriter(os1);
					PrintWriter pw1=new PrintWriter(osw1,true);
					pw1.println("add%"+split[0]);
				}
					}
				//�������˵����ַ��͸��Լ�
				for(String tu:userHashMap.keySet()){
					if(!tu.equals(userName)){//�Լ��ų�
						pw.println("add%"+tu);
					}
				}
				//str+="������>>:"+str;
				
				//pw.println("ok");
				//���Ͻ��տͻ��˷��͵���Ϣ
				while(true){
					//Thread.sleep(1000);//����
					String msg=br.readLine();
					//�ͷ���������һ����ϢԼ��
					//add%�µ��û�-->ˢ�����������û���ӽ������б�
					//msg%�û�������Ϣ����--->����Ϣ��ʾ���ı���
					//exit%�û���--->�û��˳�ϵͳ�����������Ƴ�һ���û�
					if(msg!=null){
						System.out.println(msg);
						String [] ms=msg.split("%");
						if(ms[0].equals("msg")){
							String [] um=ms[1].split(":");
							//ת����Ϣ
							String to=um[0];
							String mess=um[1];
							Socket ts=userHashMap.get(to);
							//for(String us2:userHashMap.keySet()){
								//if(us2.equals(um[0])){
									
									OutputStream tos=ts.getOutputStream();
									OutputStreamWriter tosw=new OutputStreamWriter(tos);
									PrintWriter tpw=new PrintWriter(tosw,true);
									tpw.println("msg%"+userName+":"+mess);
								//}
							//}
						}else if(ms[0].equals("exit")){
							userHashMap.remove(ms[1]);
							for(String us:userHashMap.keySet()){
								Socket s=userHashMap.get(us);
								OutputStream os1=s.getOutputStream();
								OutputStreamWriter osw1=new OutputStreamWriter(os1);
								PrintWriter pw1=new PrintWriter(osw1,true);
								pw1.println("exit"+ms[1]);
							}
						}
					}
				}
					//String mesage=br.readLine();
				//System.out.println(mesage);	
				
			}else{
				pw.println("err");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}