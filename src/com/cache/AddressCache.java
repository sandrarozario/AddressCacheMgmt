package com.cache;


import java.net.InetAddress;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.addressCache.helper.AddressConstants;

/*
 * AddressCache, is used as a central repository to store addresses
 * and perform operations on the data stored in cache.
 * @author Sandra Rozario
 * 3rd June 2017
 */
public class AddressCache<K, V> extends GenericConcurrentCacheImpl<K, V> {

	private static AddressCache<InetAddress, Long > instance = null;
	private AddressCache(){
		
	}
	public static AddressCache<InetAddress, Long> getInstance(){
		if(instance==null){
			instance = new AddressCache<InetAddress, Long>();			
		}
		return instance;
	}
	@Override
	public void startTask() {		
		if (!isStarted) {
			timer = new Timer("AddresCache clear");
			TimerTask task = createTask(0);			
			timer.schedule(task, 0, AddressConstants.addressCacheClearIntrvlTime);		// time can be configured..
			isStarted = true;
		}
	}
	
	private TimerTask createTask(int taskType) {
		return new CacheClearTimer();
	}
	
	private class CacheClearTimer extends TimerTask {
		public void run() {
    		runTask();
		}
	}

	@Override
	protected void runTask() {
		deleteExpiredObject();
	}
	
	/*
	 * deleteExpiredObject - clears the objects from cache which have reached their maximum age of expiry
	 * @author Sandra Rozario
	 * 3rd June 2017
	 */
	
	private void deleteExpiredObject() {
		System.out.println("Inside deleteExiredObject - start");
		if(instance != null && instance.getCacheSize()>0){
			Set<InetAddress> keyList = instance.getKeys();
			if(keyList!=null && keyList.size()>0){
				for(InetAddress key : keyList){
					if(null != key){
						Long keyElement = instance.getValue(key);
						if(null != keyElement){
							long objStartTime = keyElement.longValue();
							long currTime = System.currentTimeMillis();
					          		long diff = currTime - objStartTime;
							if(diff>=AddressConstants.addressCachedObjExpMili){
								instance.removeValue(key);
								System.out.println("Element cleared is " + key);
							}
						}
					}
				}
			}
		}
		System.out.println("Inside deleteExpiredObject - finish");
	}
}
