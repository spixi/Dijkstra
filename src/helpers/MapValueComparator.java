/**
* MapValueComparator 
* <p>
* This is a generic comparator which compares a map's key entries over their values.
* @author Marius Spix
*/

package helpers;

import java.util.Comparator;
import java.util.Map;

public class MapValueComparator<K,V> implements Comparator<K> {
	
	private Map<K,V> map;
	private Comparator<? super V> comparator;
	
	public MapValueComparator(Map<K,V> map, Comparator<? super V> comparator) {
		this.map        = map;
		this.comparator = comparator;
	}

	@Override
	public int compare(K o1, K o2) {
		V value1 = map.get(o1);
		V value2 = map.get(o2);
		
		return comparator.compare(value1, value2);
	}

}
