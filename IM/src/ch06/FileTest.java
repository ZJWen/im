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
		FileOutputStream fos=new FileOutputStream("src/ch06/�����¼.txt",true);
		OutputStreamWriter osw=new OutputStreamWriter(fos,"GBK");
		BufferedWriter out=new BufferedWriter(osw);
		System.out.println(str);
		//д������ļ�
		out.write(str);
		out.newLine();
		//�������
		out.flush();
		out.close();
	}
	
	public static String ReaderMessage()throws Exception {
		// TODO Auto-generated method stub
		//��ȡ�ļ����ַ�����
		FileInputStream fis=new FileInputStream("d:\\�����¼.txt");
		InputStreamReader isr=new InputStreamReader(fis,"GBK");
		BufferedReader in=new BufferedReader(isr);
		//��ȡ����
		//ѭ��ȡ������
		String str=null;
		StringBuffer ss=new StringBuffer();
		while((str=in.readLine())!=null){
			System.out.println(str);
			ss.append(str+"\n");
			//д������ļ�
		}
		//�ر���
		in.close();
		return ss.toString();
	}
}
