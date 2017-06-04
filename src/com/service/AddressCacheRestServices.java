package com.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cache.AddressCache;
import com.service.helper.CacheServiceHelperImpl;

/*
 * AddressCacheRestServices, is a REST service class  
 * Contains method which perform various operations on the cache
 * @author Sandra Rozario
 * 3rd June 2017
 */

@Path("/CacheService") 
public class AddressCacheRestServices {

	
	/*
	 * addDataInCache - adds addresses to the cache
	 * input parameter - String address
	 * output parameter - String to denote if addition is success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@GET 
	   @Path("/put") 
	   @Produces(MediaType.TEXT_PLAIN) 
	public String addDataInCache(@QueryParam("address") String address) {
		CacheServiceHelperImpl cacheHelper = new CacheServiceHelperImpl();
		boolean isSucess = false;
		InetAddress inetVal = null;
		try {
			inetVal = InetAddress.getByName(address);
			isSucess = cacheHelper.add(inetVal);
			if (isSucess)
				return "Element" + inetVal + "successfully added";
			else
				return "Element not added as the element is already there or for some other reason";
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}  
	
	
	
	/*
	 * peekDataFromCache - fetch the most recent added address from the cache
	 * input parameter
	 * output parameter - String to denote if addition is success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@GET 
	   @Path("/peek") 
	   @Produces(MediaType.TEXT_PLAIN) 
	public String peekDataFromCache() {
		CacheServiceHelperImpl cacheHelper = new CacheServiceHelperImpl();
		try {
			InetAddress addressVal = cacheHelper.peek();
			if (null != addressVal) {
				return "the recent added element is" + addressVal.getHostName()
						+ "";
			} else {
				return "no element retrieved as there is no element in cache or some other reason";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}  
	
	/*
	 * takeDataFromCache - fetch and remove the most recent added address from the cache
	 * input parameter
	 * output parameter - String to denote if removal is success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@GET 
	   @Path("/take") 
	   @Produces(MediaType.TEXT_PLAIN) 
	public String takeDataFromCache() {
		CacheServiceHelperImpl cacheHelper = new CacheServiceHelperImpl();
		try {
			InetAddress addressVal = cacheHelper.take();
			if (null != addressVal) {
				return "the recent taken and removed element is" + addressVal
						+ "";
			} else {
				return "no element retrieved as there is no element in cache or some other reason";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}  
	
	
	/*
	 * removeDataFromCache - remove an address from the cache
	 * input parameter - Address to be removed
	 * output parameter - String to denote if removal is success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@GET 
	   @Path("/remove") 
	   @Produces(MediaType.TEXT_PLAIN) 
	public String removeDataFromCache(@QueryParam("address") String address) {
		CacheServiceHelperImpl cacheHelper = new CacheServiceHelperImpl();
		InetAddress inetVal = null;
		boolean isSucess = false;
		try {
			inetVal = InetAddress.getByName(address);
			isSucess = cacheHelper.remove(inetVal);
			if (isSucess) {
				return "Element" + inetVal + "successfully removed";
			} else {
				return "Element not removed as the element is not there in the cache or for some other reason";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
	/*
	 * clearCache - remove objects from cache which have exceeded their age
	 * This method can be used for REST operation if not added as a part of StartUpServlet
	 * input parameter - 
	 * output parameter - String to denote if removal is success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@GET
	@Path("/clearCache")
	@Produces(MediaType.TEXT_PLAIN)
	public String clearCache() {
		AddressCache.getInstance().startTask();
		return "cache clear task completed";
	}
	
}
