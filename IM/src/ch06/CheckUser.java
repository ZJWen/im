package ch06;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class CheckUser {
	/**
	 * ���û����������õ�������ȥ��֤
	 * @param user
	 * @param pwd
	 * @return ����û����ڷ���true����������ڷ���false
	 * @throws exception
	 */
	public static boolean check(String user,String pwd )throws Exception{
		boolean falg=false;
		//1.�������ݿ�����
		Class.forName("com.mysql.jdbc.Driver");
		//2.ͨ�����ӹ����������ݿ����Ӷ���
		String url="jdbc:mysql:///im";
		String us="root";
		String password="root";
		Connection conn=
				DriverManager.getConnection(url,us,password);
		//3.ƴ�Ӳ�ѯ�ַ���
		String sql ="select * from users"+" where userName='"+user+"' and userPwd='"+pwd+"'";
		System.out.println(sql);
		//4.����ִ�ж���
		Statement st=conn.createStatement();
		//5.ִ�в�ѯ�õ��ṹ
		ResultSet rs=st.executeQuery(sql);
		//6.�Բ�ѯ�Ľ�������ж�
		if(rs.next()){
			falg=true;
		}
		//7.�ر����ж���
		rs.close();
		st.close();
		conn.close();
		
		return falg;
		
	}
	public static void main(String[] args) {
		try{
			boolean is=check("test","1234");
			System.out.println(is);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

}
