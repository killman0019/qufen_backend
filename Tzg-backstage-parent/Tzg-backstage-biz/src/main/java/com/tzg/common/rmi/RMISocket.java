package com.tzg.common.rmi;

import java.rmi.server.*;
import java.io.*;
import java.net.*;

/**
 * RMI通信固定端口。可以通过防火墙。
 * @author wuws
 *
 */
public class RMISocket extends RMISocketFactory {

	public Socket createSocket(String host, int port) throws IOException {
		return new Socket(host, port);
	}

	public ServerSocket createServerSocket(int port) throws IOException {
		return new ServerSocket(port);
	}
}
