package io.antmedia.shutdown;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AMSShutdownManager {
	
	protected Logger logger = LoggerFactory.getLogger(AMSShutdownManager.class);
	private static AMSShutdownManager instance = new AMSShutdownManager();

	private volatile boolean isShuttingDown = false;

	private ArrayList<IShutdownListener> listeners = new ArrayList<>();

	public static AMSShutdownManager getInstance() {
		return instance;
	}

	//make a private constructor for singleton instance
	private AMSShutdownManager() {
	}

	public void subscribe(IShutdownListener listener) {
		listeners.add(listener);
	}

	public synchronized void notifyShutdown() {
		if(!isShuttingDown) 
		{
			isShuttingDown = true;
			for (IShutdownListener listener : listeners) {
				try {
					listener.serverShuttingdown();
				}
				catch (Exception e) {
					logger.error(ExceptionUtils.getStackTrace(e));
				}
			}
		}

	}

	public List<IShutdownListener> getListeners() {
		return listeners;
	}

}
