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
		//监听端口号
		int port =10086;
		
		try{
			//建立连接
			ServerSocket serverSocket=new ServerSocket(port);
			System.out.println("服务器端启动......");
			//获得连接（accept（）阻塞）
			while(true){
			Socket socket=serverSocket.accept();
			System.out.println("服务器获得连接......");
			//将下面代码提取为类
			MyThread st=new MyThread();
			st.setSocket(socket);
			st.setMap(userMap);
			st.start();//开启线程
			}
			//接收客户端发送内容
			/*is=socket.getInputStream();
			byte[] b=new byte[1024];
			while(true){
				String str=null;
				int n=is.read(b);
				if(n>=1){
					//输出
					str=new String(b,0,n);
					//str--->用户名=密码
					System.out.println("客户端发送内容为："+str);
				}
				Thread.sleep(1000);
				if(str!=null&&str.length()>3){
					String [] split=str.split("=");
					if(split[0].equals("ac")&&split[1].equals("1234")){
						//向客户端发送反馈内容
					os=socket.getOutputStream();
						//if(CheckUser.check(split[0],split[1])){
						os=socket.getOutputStream();
						//str+="服务器>>:"+str;
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
				//关闭流和连接
				os.close();
				is.close();
				socket.close();
				serverSocket.close();
			}catch(Exception e){
				
			}*/
		}
	}
}
