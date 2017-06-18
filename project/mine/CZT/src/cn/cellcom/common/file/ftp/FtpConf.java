package cn.cellcom.common.file.ftp;

public class FtpConf {

	public FtpConf(String url, String username, String password, String port) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	private String url;
	private String port;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	private String username;
	private String password;
	private String encode;

}
