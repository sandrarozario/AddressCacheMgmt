package com.service.helper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.addressCache.helper.AddressConstants;
import com.cache.AddressCache;

/*
 * CacheServiceHelperImpl - Ths class implements the helper class methods 
 * @author Sandra Rozario
 * 3rd June 2017
 */
public class CacheServiceHelperImpl implements CacheServiceHelper{

	
	/*
	 * add - The method adds address to the cache
	 * input parameter - String address
	 * output parameter - String to indicate if addition was success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@Override
	public boolean add(InetAddress address) {
		boolean isSucessfullyAdded  = false;
		if(null != address){
			AddressCache<InetAddress,Long> cacheObj = AddressCache.getInstance();
			if (null != cacheObj && cacheObj.getCacheSize()>0 && cacheObj.isContainsKey(address)) {
				 System.out.println("Key : " + address + "already present in cache");
				 }else{
					 long startTimeInMilli = System.currentTimeMillis();
					 cacheObj.putValue(address, startTimeInMilli) ;
					 System.out.println("Key : " + address + "successfully added to cache");
					 Iterator it= cacheObj.getEntrySet().iterator();
					    while (it.hasNext())
					    {
					        Map.Entry pairs = (Map.Entry) it.next();
					        System.out.println("Key  "+pairs.getKey()+"-- value"+pairs.getValue() + "inside get");
					    }
					 isSucessfullyAdded = true;
				 }
		}
		return isSucessfullyAdded;
	}

	/*
	 * remove - The method removes address from the cache
	 * input parameter - String address
	 * output parameter - String to indicate if removal was success or failure
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@Override
	public boolean remove(InetAddress address) {
		boolean isSuccessfullyRemoved = false;
		if(null != address){
			AddressCache<InetAddress,Long> cacheObj = AddressCache.getInstance();
			//the element to be deleted is there in the cache
			if (null != cacheObj && cacheObj.getCacheSize() > 0 && cacheObj.isContainsKey(address)) {
				 System.out.println("Key : " + address + " present in cache");
				 cacheObj.removeValue(address);
				 System.out.println("Key : " + address + "removed successfully");
				 isSuccessfullyRemoved = true;
				 Iterator it= cacheObj.getEntrySet().iterator();
				    while (it.hasNext())
				    {
				        Map.Entry pairs = (Map.Entry) it.next();
				        System.out.println("Key  "+pairs.getKey()+"-- value"+pairs.getValue() + "inside remove");
				    }
				 }
			else{
				//the element to be added is not there in cache
				System.out.println("key :" + address + "not present in map");
			}
		}
		return isSuccessfullyRemoved;
	}

	/*
	 * peek - The method returns the recent added address from the cache
	 * input parameter - 
	 * output parameter - String to indicate if it returned an element 
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	
	@Override
	public InetAddress peek() {
		InetAddress recentAdd = null;
		AddressCache<InetAddress, Long> cacheObj = AddressCache.getInstance();
		recentAdd = getRecentAddedElement(cacheObj);
		if (null != recentAdd)
			System.out.println("the peek element is" + recentAdd);

		if (null != cacheObj && cacheObj.getCacheSize() > 0) {
			Iterator it = cacheObj.getEntrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println("Key  " + pairs.getKey() + "-- value"
						+ pairs.getValue() + "insiade peek");
			}
		}
		return recentAdd;
	}

	
	/*
	 * take - The method returns and removes the recent added address from the cache
	 * input parameter - 
	 * output parameter - String to indicate if it removed an element 
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	@Override
	public InetAddress take() {
		AddressCache<InetAddress, Long> cacheObj = AddressCache.getInstance();
		InetAddress recentAdd = null;
		
			int maxRetryCount = AddressConstants.maxretryCountForTake;
			int index = 0;
			while((cacheObj == null || cacheObj.getCacheSize() == 0 ) && index < maxRetryCount){
				try {
					Thread.sleep(AddressConstants.threadSleepTimeMili);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				index ++;
			}
			
			if (null != cacheObj && cacheObj.getCacheSize() > 0) {
				recentAdd = getRecentAddedElement(cacheObj);
				System.out.println("the take element is" + recentAdd);
				cacheObj.removeValue(recentAdd);
				Iterator it = cacheObj.getEntrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					System.out.println("Key  " + pairs.getKey() + "-- value"
							+ pairs.getValue() + "inside take");
				}
			}
		
		return recentAdd;
	}

	/*
	 * getRecentAddedElement -  common method used by peek and take method
	 * input parameter - Address cache object
	 * output parameter - Inet address 
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	public InetAddress getRecentAddedElement(AddressCache<InetAddress,Long> cacheObj){
	
		 InetAddress val = null;
		
		 if(null != cacheObj && cacheObj.getCacheSize() > 0){
		 List<Map.Entry<InetAddress, Long>> list =
	                new LinkedList<Map.Entry<InetAddress, Long>>(cacheObj.getEntrySet());

	        Collections.sort(list, new Comparator<Map.Entry<InetAddress, Long>>() {
	            public int compare(Map.Entry<InetAddress, Long> o1,
	                               Map.Entry<InetAddress, Long> o2) {
	                return (o2.getValue()).compareTo(o1.getValue());
	            }
	        });

		
	     Map.Entry<InetAddress, Long> entry  = list.get(0);
	      val =    entry.getKey();
	     Long time= entry.getValue();
		 }
	     return val;
	}
	
	public static void main(String[] args){
		CacheServiceHelperImpl cache = new CacheServiceHelperImpl();
		AddressCache<InetAddress,Long> cacheObj = AddressCache.getInstance();
		try {
			cacheObj.putValue(InetAddress.getByName("10.10.10.10"), System.currentTimeMillis()+1);
			cacheObj.putValue(InetAddress.getByName("10.10.10.20"), System.currentTimeMillis()+6);
			cacheObj.putValue(InetAddress.getByName("10.10.10.30"), System.currentTimeMillis()+5);
			cacheObj.putValue(InetAddress.getByName("10.10.10.40"), System.currentTimeMillis()+2);
			cacheObj.putValue(InetAddress.getByName("10.10.10.60"), System.currentTimeMillis()+8);
			cacheObj.putValue(InetAddress.getByName("10.10.10.70"), System.currentTimeMillis()+3);
			Iterator it= cacheObj.getEntrySet().iterator();
		    while (it.hasNext())
		    {
		        Map.Entry pairs = (Map.Entry) it.next();
		        System.out.println("Key  "+pairs.getKey()+"-- value"+pairs.getValue());
		    }

			cache.add(InetAddress.getByName("10.20.92.9"));
			cache.remove(InetAddress.getByName("10.10.10.70"));
			cache.peek();
			cache.take();
			cache.peek();
			cache.take();
			cache.peek();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
	}
}
