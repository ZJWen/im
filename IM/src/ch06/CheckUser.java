package ch06;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//dfg
//asdasdasdas
public class CheckUser {
	/**
	 * 将用户名和密码拿到服务器去验证
	 * @param user
	 * @param pwd
	 * @return 如果用户存在返回true，如果不存在返回false
	 * @throws exception
	 */
	public static boolean check(String user,String pwd )throws Exception{
		boolean falg=false;
		//1.加载数据库启动
		Class.forName("com.mysql.jdbc.Driver");
		//2.通过连接管理类获得数据库连接对象
		String url="jdbc:mysql:///im";
		String us="root";
		String password="root";
		Connection conn=
				DriverManager.getConnection(url,us,password);
		//3.拼接查询字符串
		String sql ="select * from users"+" where userName='"+user+"' and userPwd='"+pwd+"'";
		System.out.println(sql);
		//4.创建执行对象
		Statement st=conn.createStatement();
		//5.执行查询得到结构
		ResultSet rs=st.executeQuery(sql);
		//6.对查询的结果进行判断
		if(rs.next()){
			falg=true;
		}
		//7.关闭所有对象
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
