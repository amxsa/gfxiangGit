package common.DB;

import java.io.IOException;

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

public class ConnConf {
	private static Log log = LogFactory.getLog(ConnConf.class);
	private String driverClassName;
	private String user;
	private String password;
	private String url;
	private boolean showsql = true;
	private boolean showjvm;

	public boolean isShowsql() {
		return showsql;
	}

	public void setShowsql(boolean showsql) {
		this.showsql = showsql;
	}

	public boolean isShowjvm() {
		return showjvm;
	}

	public void setShowjvm(boolean showjvm) {
		this.showjvm = showjvm;
	}

	public static Properties DB = null;

	

	public ConnConf(Resource resource) {
		try {
			DB = new Properties();
			DB.load(resource.getInputStream());
			this.driverClassName = DB.getProperty("jdbc.driverClassName");
			this.url = DB.getProperty("jdbc.url");
			this.user = DB.getProperty("jdbc.username");
			this.password = DB.getProperty("jdbc.password");
			log.info("JDBC初始化配置完毕");
		} catch (IOException e) {
			throw new RuntimeException("加载文件异常", e);
		}
	}
	public ConnConf(String driverClassName, String user, String password,
			String url) {
		this.driverClassName = driverClassName;
		this.user = user;
		this.password = password;
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
