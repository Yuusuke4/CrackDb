package bearcat;

import MssqlDbBlasting.MsSQLScript;
import MysqlDbBlasting.MySQLScript;

/**
 * 程序入口
 * 
 * @author Bearcat Mail:hacker.bear.cat@qq.com
 * @since JDK 1.8
 * @version 1.0 2017-04-2
 */
public class Payload {
	public static void main(String[] args) {
		MySQLScript ms = new MySQLScript();
		MsSQLScript mss = new MsSQLScript();
		if(args.length < 1){
			System.out.println("[~]使用说明	: CrackDb.jar <--mysql> <字典:username.CrackDb>||<root>");
			System.out.println("[~]支持的数据库	: Access | Mysql | SQL Server | Oracle");
			System.out.println("[~]版本		: V 1.0");
			System.out.println("[~]字典配置	: CrackDb.jar打开方式为WinRAR xxxxxDbBlasting\\*.CrackDb");
			System.out.println("[~]关于		: http://www.secfree.com/");
			System.out.println("[~]作者		: Bearcat Mail:hacker.bear.cat@qq.com");
		}else{
			String code = args[0];
			
			switch (code) {
			case "--mysql":ms.Mysql(args[1]);
				break;
			case "--mssql":mss.Mssql(args[1]);
				break;
			default:
				System.out.println("Access\t\t\t--access");
				System.out.println("Mysql\t\t\t--mysql");
				System.out.println("SQL Server\t\t--mssql");
				System.out.println("Oracl\t\t\t--oracle");
			}
		}
	}
}
