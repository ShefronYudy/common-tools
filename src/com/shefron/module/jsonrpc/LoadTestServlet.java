package com.shefron.module.jsonrpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jsonrpc4j.JsonRpcServer;

public class LoadTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private JsonRpcServer jsonRpcServer;

	public void init() {
		jsonRpcServer = new JsonRpcServer(new JsonRpcServiceImpl());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		jsonRpcServer.handle(req, resp);
	}
}
