package com.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/*
 * GenericConcurrentCacheImpl, is a generic cache 
 * which can be extended by any class with any data type for specific key and value
 * @author Sandra Rozario
 * 3rd June 2017
 */
public class GenericConcurrentCacheImpl<K, V> {
	protected Map <K, V> hashMap = null;
	protected Timer timer = null;
	protected boolean isStarted = false;

	/**
	 * Constructor to create the cache object to store key -values
	 * @param isAutoClear, if auto clear is false then cache
	 * will not be cleared automatically.
	 */
	public GenericConcurrentCacheImpl() {
		hashMap = new ConcurrentHashMap<K, V>();
	}
	
	
	/**
	 * Get the value for the given key
	 */
	public V getValue(K key) {
		
		if (key != null) {
			
			if (hashMap.containsKey(key)) {
				V value = hashMap.get(key);
				return value;
			}
		}
		return null;
	}
	
	

	/**
	 * Get the collection of values stored in the cache.
	 */
	public Collection <V> getValues() {
		
		if (hashMap != null) {
			Collection <V> values = hashMap.values();
			return values;
		}
		return null;
	}
	
	/**
	 * Get the collection of values stored in the cache.
	 */
	public Set <K> getKeys() {
		
		if (hashMap != null) {
			Set <K> keys = hashMap.keySet();
			return keys;
		}
		return null;
	}
	
	/**
	 * Map the key and value.Returns the previous value associated with the cache.
	 */
	public V putValue(K key, V value) {
		
		if (key != null && hashMap != null) {
			V v = hashMap.put(key, value);
			
			return v;
		}
		return null;
	}
	
	/**
	 * Removes the value associated with the key
	 * Returns the previous value associated with the key.
	 */
	public V removeValue(K key) {
		
		if (key != null) {
			
			if (hashMap!= null && !hashMap.isEmpty()) {
				V value = hashMap.remove(key);
				return value;
			}
		}
		return null;
	}
	
	/**
	 * Clear all the cached entries.
	 */
	public void clearCache() {
		if (hashMap != null) {
			Collection c = getValues();
			
			for (Object o :c) {
			
				if (o != null) {
					o = null;
				}
			}
			hashMap.clear();
		}
	}

	
	public void loadCache() {
		
	}
	/**
	 * Check if the key is available
	 * @param key, the key to fetch the value.
	 * @return, returns the value
	 */
	public boolean isContainsKey(K key) {
		
		if (key != null && hashMap != null) {
			return hashMap.containsKey(key);
		}
		return false;
	}
	
	
	public Set<Entry<K,V>> getEntrySet(){
		return hashMap.entrySet();
	}
	/**
	 * Gets the number of entries stored in the cache.
	 * @return, returns the size of the cache
	 */
	public int getCacheSize() {
		
		if (hashMap != null && !hashMap.isEmpty()) {
			int count = hashMap.size();
			return count;
		}
		return 0;
	}
	
	/**
	 * Stop auto clear cache process
	 * This method is not synchronized.
	 */
	public void stopTask() {
		
		if (isStarted) {
			System.out.println("stop auto clear... ");
			timer.cancel();
			isStarted = false;
		}
	}
	
	/**
	 * Start the auto clear cache process.
	 * This process will clear the cache every t1 seconds and starts
	 * the clearing process after t0 seconds from start.
	 * This method is not synchronized.
	 * t0 & t1 are configured.
	 */
	public void startTask() {
		
		if (!isStarted) {
			System.out.println("start auto clear... ");
			timer = new Timer("Cache clear");
			TimerTask task = createTask(0);			
			timer.schedule(task, 0, 86400000);
			isStarted = true;
		}
	}
	
	private TimerTask createTask(int taskType) {
		return new CacheClearTimer();
	}
	
	
	protected void runTask() {
		System.out.println("Before clear..." + hashMap + " " + new Date());
		
		if (hashMap != null ) {
				hashMap.clear();
	    		System.out.println("After clear..." + hashMap);
	    		loadCache();
	    		System.out.println("After laod again..." + hashMap + " " + new Date());
	    		System.out.println("*************************************************");
			}
	}


	/**
	 * Timer task class to clear the cache. 
	 */
	private class CacheClearTimer extends TimerTask {

		public void run() {
    		System.out.println("Before clear..." + hashMap + " " + new Date());
    		runTask();
		}
	}

	@Override
	public String toString() {
		System.out.println(" The cache is ");
		System.out.println(hashMap);
		return super.toString();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String []args) {
		GenericConcurrentCacheImpl <String, Integer> cacheMap = new GenericConcurrentCacheImpl<String, Integer>();
		String key = "1";
		Integer value = 1;
		cacheMap.putValue(key, value);
		Collection <Integer> values = new ArrayList<Integer>();
		values = cacheMap.getValues();
		
		 key = "2";
		 value = 2;
		 cacheMap.putValue(key, value);
		
		 key = "3";
		 value = 3;
		 cacheMap.putValue(key, value);
		
		 key = "4";
		 value = 4;
		 cacheMap.putValue(key, value);
		
		 key = "5";
		 value = 5;
		 cacheMap.putValue(key, value);

		 key = "6";
		 value = 6;
		 cacheMap.putValue(key, value);
		 cacheMap.startTask();
		 
		 key = "7";
		 value = 7;
		 cacheMap.putValue(key, value);
		
		 key = "8";
		 value = 8;
		 cacheMap.putValue(key, value);

		 key = "9";
		 value = 9;
		 cacheMap.putValue(key, value);
		 cacheMap.stopTask();
		 cacheMap.startTask();
	}
}
