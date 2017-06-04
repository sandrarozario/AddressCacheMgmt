package com.service.helper;

import java.net.InetAddress;

public interface CacheServiceHelper {

	public boolean add(InetAddress address); 
	

	/**
	 * remove() method will return true if the address was successfully removed
	 * @param address
	 * @return
	 */
	public boolean remove(InetAddress address);
	

	/**
	 * The peek() method will return the most recently added element, 
	 * null if no element exists.
	 * @return
	 */
	public InetAddress peek();

	/**
	 * take() method retrieves and removes the most recently added element 
	 * from the cache and waits if necessary until an element becomes available.
	 * @return
	 */
	public InetAddress take();
}
