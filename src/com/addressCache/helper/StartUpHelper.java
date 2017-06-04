package com.addressCache.helper;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.cache.AddressCache;

/*
 * StartUpServlet - Contains the server start code      
 * @author Sandra Rozario
 * 3rd June 2017
 */
public class StartUpHelper implements Servlet{

	@Override 
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		try {
			System.out.println("Inside start task - start");
			AddressCache.getInstance().startTask();
			System.out.println("Inside start task - finish");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	

}
