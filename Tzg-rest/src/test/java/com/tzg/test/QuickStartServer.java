package com.tzg.test;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车快速重新加载应用.
 * 
 * @author hycao
 */
public class QuickStartServer {

	public static final int PORT = 9090;
	public static final String CONTEXT = "/";
	public static final String BASE_URL = "http://192.168.1.168:9090";
	public static final String BASE_URL_SSL = "https://192.168.1.168:8443";
	public static final String[] TLD_JAR_NAMES = new String[] {
			"spring-webmvc", "shiro-web", "springside-core" };

	public static void main(String[] args) throws Exception {
		// 设定Spring的profile
		//System.setProperty("spring.profiles.active", "production");
		// System.setProperty("spring.profiles.active", "development");
		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		ServerConnector httpsConnector = getHttpsConnector(server,8443);
		server.addConnector(httpsConnector);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
		try {
			org.apache.ibatis.logging.LogFactory.useLog4JLogging(); 
			server.start();
			System.out.println("Server running at " + BASE_URL +" and "+BASE_URL_SSL);
			System.out.println("Hit 'r' to reload the application");
			while (true) {
				char c = (char) System.in.read();
				if (c == 'r') {
					JettyFactory.reloadContext(server);
				} else if (c == 'q') {
					break;
				}
				else if(c=='a'){
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(-1);
		}
	}
	
	
	private static ServerConnector getHttpsConnector(Server server,int port) {
		HttpConfiguration https_config = new HttpConfiguration();
		https_config.setSecureScheme("https");
		https_config.setSecurePort(port);
		https_config.setOutputBufferSize(32768);
		https_config.addCustomizer(new SecureRequestCustomizer());

		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath("C:/Users/Administrator/keystore");
		sslContextFactory.setKeyStorePassword("OBF:19iy19j019j219j419j619j8");
		sslContextFactory.setKeyManagerPassword("OBF:19iy19j019j219j419j619j8");

		ServerConnector httpsConnector = new ServerConnector(server,
		        new SslConnectionFactory(sslContextFactory,"http/1.1"),
		        new HttpConnectionFactory(https_config));
		httpsConnector.setPort(port);
		httpsConnector.setIdleTimeout(500000);
		
		return httpsConnector;
	}
}
