package ch06;
import java.io.*;

import javax.net.ssl.SSLContext;
public class FileTest {
	public static void main(String args[])throws Exception{
		ReaderMessage();
		WriterMassage("");
		
	}

	public static void WriterMassage(String str) throws Exception{
		// TODO Auto-generated method stub
		FileOutputStream fos=new FileOutputStream("src/ch06/聊天记录.txt",true);
		OutputStreamWriter osw=new OutputStreamWriter(fos,"GBK");
		BufferedWriter out=new BufferedWriter(osw);
		System.out.println(str);
		//写入相关文件
		out.write(str);
		out.newLine();
		//清除缓存
		out.flush();
		out.close();
	}
	
	public static String ReaderMessage()throws Exception {
		// TODO Auto-generated method stub
		//读取文件（字符流）
		FileInputStream fis=new FileInputStream("d:\\聊天记录.txt");
		InputStreamReader isr=new InputStreamReader(fis,"GBK");
		BufferedReader in=new BufferedReader(isr);
		//读取数据
		//循环取出数据
		String str=null;
		StringBuffer ss=new StringBuffer();
		while((str=in.readLine())!=null){
			System.out.println(str);
			ss.append(str+"\n");
			//写入相关文件
		}
		//关闭流
		in.close();
		return ss.toString();
	}
}
