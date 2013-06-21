package helpers;

import java.util.Collections;
import java.util.Map;
/**
 * 
 * @author Marius
 *
 * This class is a special forme of HashMap which is aware about its minimum value.
 */

public class MinimumAwareHashMap<K,V extends Comparable<? super V>> extends java.util.HashMap<K,V> {
	private K min;
	
	public V put (K key,V value) {
		V oldValue = super.put(key, value);
		
		if (key == min && key != null) {
			//The value of the key with the current minimum value is changed
			//So we must determine the new minimum value
			if(value.compareTo(oldValue) > 0) {
			    for (Map.Entry<K, V> e : this.entrySet())
			        if (e.getValue().compareTo(get(min)) < 0)
			        	min = e.getKey();
			}
		}
		//The put value is smaller than the minimum value
		else if (min == null || value.compareTo(get(min)) < 0) {
			//The new key is the key of the minimum value.
			min = key;
		}
		
		//to be compatible the superclass we have to return the old value
		return oldValue;
	}
	
	public V getMinValue() {
		return get(min);
	}
	
	public K getKeyOfMinValue() {
		return min;
	}
	
	private static final long serialVersionUID = 4456911221281217392L;
}
