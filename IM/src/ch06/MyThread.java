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
			//接收客户端发来的数据
			InputStream is=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			String str=br.readLine();
			//str--->用户名=密码
			System.out.println("客户端发送内容为："+str);
			Thread.sleep(1000);
			
			if(str!=null&&str.length()>3){
				String [] split=str.split("=");
				//向客户端发送反馈内容
				OutputStream os=socket.getOutputStream();
				OutputStreamWriter osw=new OutputStreamWriter(os);
				PrintWriter pw=new PrintWriter(osw,true);
			
			
			//String uandps=br.readLine();
			//String [] ups=uandps.split("=");
			//String u=ups[0];
			//String p=ups[1];
			//输出到客户端
			
			//交给数据库验证
				String userName=split[0];
			if(CheckUser.check(userName,split[1])){
				//保存用户的名字和socket对应关系
				userHashMap.put(userName,socket);
				pw.println("ok");
				//有新用户添加进来--->发广播给所有用户
				for(String us:userHashMap.keySet()){
					if(!us.equals(userName)){
					Socket s=userHashMap.get(us);
					OutputStream os1=s.getOutputStream();
					OutputStreamWriter osw1=new OutputStreamWriter(os1);
					PrintWriter pw1=new PrintWriter(osw1,true);
					pw1.println("add%"+split[0]);
				}
					}
				//将其他人的名字发送给自己
				for(String tu:userHashMap.keySet()){
					if(!tu.equals(userName)){//自己排除
						pw.println("add%"+tu);
					}
				}
				//str+="服务器>>:"+str;
				
				//pw.println("ok");
				//不断接收客户端发送的消息
				while(true){
					//Thread.sleep(1000);//休眠
					String msg=br.readLine();
					//和服务器端做一个信息约定
					//add%新的用户-->刷新下拉框将新用户添加进下拉列表
					//msg%用户名：信息内容--->将消息显示到文本域
					//exit%用户名--->用户退出系统，从下拉框移除一个用户
					if(msg!=null){
						System.out.println(msg);
						String [] ms=msg.split("%");
						if(ms[0].equals("msg")){
							String [] um=ms[1].split(":");
							//转发信息
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