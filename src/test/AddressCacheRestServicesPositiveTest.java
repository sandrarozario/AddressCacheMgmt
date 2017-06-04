package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.cache.AddressCache;    
import com.service.AddressCacheRestServices;

/*
 * AddressCacheRestServicesPositiveTest - Contains the positive test scenarios    
 * @author Sandra Rozario
 * 3rd June 2017
 */
public class AddressCacheRestServicesPositiveTest {
	AddressCache<InetAddress, Long> cacheObj = null;
	AddressCacheRestServices addressCacheService = new AddressCacheRestServices();
	
	@Before
	public void cacheInit() throws InterruptedException{
		 cacheObj = AddressCache.getInstance();
		try {
			cacheObj.putValue(InetAddress.getByName("10.10.10.10"), System.currentTimeMillis());
			Thread.sleep(10);
			cacheObj.putValue(InetAddress.getByName("10.10.10.20"), System.currentTimeMillis());
			Thread.sleep(10);
			cacheObj.putValue(InetAddress.getByName("10.10.10.30"), System.currentTimeMillis());
			Thread.sleep(10);
			cacheObj.putValue(InetAddress.getByName("10.10.10.40"), System.currentTimeMillis());
			Thread.sleep(10);
			cacheObj.putValue(InetAddress.getByName("10.10.10.60"), System.currentTimeMillis());
			Thread.sleep(10);
			cacheObj.putValue(InetAddress.getByName("10.10.10.70"), System.currentTimeMillis());
			Thread.sleep(10);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void tA_testputvalueInCache(){
		    String ip = StringUtils.EMPTY;
			ip = "10.13.22.227";
			Assert.assertEquals("Element" + ip + "successfully added", addressCacheService.addDataInCache(ip));
		}
	
	@Test
	public void tB_testPeekFromcache(){
		String ip = "10.10.10.70";
		Assert.assertEquals("the recent added element is" + ip+ "",addressCacheService.peekDataFromCache());
	}
	
	@Test
	public void tC_testTakeFromCache(){
		String ip = "10.10.10.70";
		Assert.assertEquals("the recent taken and removed element is" + ip+ "",addressCacheService.takeDataFromCache());
	}
	
	@Test
	public void tD_testRemoveValueFromCache(){
		    String ip = StringUtils.EMPTY;
			ip = "10.10.10.30";
			Assert.assertEquals("Element" + ip + "successfully removed", addressCacheService.removeDataFromCache(ip));
		}
	@After
	public void tearDown() throws Exception {
		cacheObj.clearCache();
		cacheObj = null;
	}
}
