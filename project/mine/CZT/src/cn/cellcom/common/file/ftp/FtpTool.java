package cn.cellcom.common.file.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import cn.cellcom.common.file.FileTool;

public class FtpTool {
	private static Logger log = Logger.getLogger(FtpTool.class);
	private FTPClient client = null;
	private FtpConf ftpConf = null;

	// 下载某个文件到本地
	public File downloadFile(String filePath, String localFolder) {
		if (StringUtils.isBlank(filePath))
			return null;
		if (!login()) {
			log.info("登录FTP服务器[URL=" + ftpConf.getUrl() + ",username="
					+ ftpConf.getUsername() + ",password="
					+ ftpConf.getPassword() + "] failed....!");
		}
		String[] tmpDir = filePath.replace("\\", "/").split("/");
		StringBuffer remotePath = new StringBuffer();
		for (int i = 0; i < tmpDir.length - 1; i++) {
			remotePath.append(tmpDir[i] + "/");
		}
		try {
			// 转移到FTP服务器目录
			client.changeWorkingDirectory(remotePath.toString());
		} catch (IOException e1) {
			throw new RuntimeException("FTP 目录未找到", e1);

		}

		String fileName = tmpDir[tmpDir.length - 1];
		String localPath = localFolder + filePath;
		OutputStream os = null;
		InputStream is = null;
		try {
			File localFile = new File(localPath); // 本地文件命名
			FileTool.isExistDir(localFile.getParentFile(), true);
			os = new FileOutputStream(localFile);
			is = client.retrieveFileStream(fileName);
			byte[] bytes = new byte[1024];
			int size = 0;
			long countSize = 0;
			while ((size = is.read(bytes, 0, 1024)) != -1) {
				os.write(bytes, 0, size);
				os.flush();
				countSize += size;
			}
			client.logout();
			if (client.isConnected()) {
				client.disconnect();
			}
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			log.info("fpt下载文件名为：" + fileName + ", 文件大小：" + countSize + " 个字节");

			return localFile;
		} catch (Exception e) {
			log.error("Fpt下载文件并重命名失败!", e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;

	}

	public boolean login() {
		client = new FTPClient();
		try {
			client.connect(ftpConf.getUrl(),
					Integer.parseInt(ftpConf.getPort()));
			client.setControlEncoding(StringUtils.isBlank(ftpConf.getEncode()) ? "GBK"
					: ftpConf.getEncode());
			FTPClientConfig conf = new FTPClientConfig(
					FTPClientConfig.SYST_UNIX);
			conf.setServerLanguageCode("zh");

			// 登录FTP
			client.login(ftpConf.getUsername(), ftpConf.getPassword());
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
				client.disconnect();
				return false;
			}
			log.info("登录FTP服务器[URL=" + ftpConf.getUrl() + ",username="
					+ ftpConf.getUsername() + ",password="
					+ ftpConf.getPassword() + "] success!");
			return true;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public FtpTool(FtpConf ftpConf) {
		this.ftpConf = ftpConf;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
