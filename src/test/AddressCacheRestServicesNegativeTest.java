package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cache.AddressCache;
import com.service.AddressCacheRestServices;
/*
 * AddressCacheRestServicesNegativeTest - Contains the negative test scenarios    
 * @author Sandra Rozario
 * 3rd June 2017
 */
public class AddressCacheRestServicesNegativeTest {
	
	AddressCache<InetAddress, Long> cacheObj = null;
	AddressCacheRestServices addressCacheService = new AddressCacheRestServices();
	
	@Before
	public void cacheInit(){
		 cacheObj = AddressCache.getInstance();
		try {
			cacheObj.putValue(InetAddress.getByName("10.10.10.10"), System.currentTimeMillis()+1);
			cacheObj.putValue(InetAddress.getByName("10.10.10.20"), System.currentTimeMillis()+6);
			cacheObj.putValue(InetAddress.getByName("10.10.10.30"), System.currentTimeMillis()+5);
			cacheObj.putValue(InetAddress.getByName("10.10.10.40"), System.currentTimeMillis()+2);
			cacheObj.putValue(InetAddress.getByName("10.10.10.60"), System.currentTimeMillis()+8);
			cacheObj.putValue(InetAddress.getByName("10.10.10.70"), System.currentTimeMillis()+3);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void t1_testputvalueInCache(){
		    String ip = StringUtils.EMPTY;
			ip = "10.10.10.70";
			Assert.assertNotSame("Not same","Element" + ip + "successfully added", addressCacheService.addDataInCache(ip));
		}
	@Test
	public void t4_testRemoveValueFromCache(){
		    String ip = StringUtils.EMPTY;
			ip = "10.10.10.100";
			Assert.assertNotNull(addressCacheService.removeDataFromCache(ip));
		}
	
	@Test
	public void t2_testPeekFromcache(){
		String ip = "10.10.10.40";
		Assert.assertNotSame("Values doesnt match","the recent added element is" + ip+ "",addressCacheService.peekDataFromCache());
	}
	
	@Test
	public void t3_testTakeFromCache(){
		String ip = "10.10.10.40";
		Assert.assertNotSame("values doesnt match","the recent taken and removed element is" + ip+ "",addressCacheService.takeDataFromCache());
	}
	@After
	public void tearDown() throws Exception {
		cacheObj.clearCache();
		cacheObj = null;
	}
	
}
