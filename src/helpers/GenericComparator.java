/**
* GenericComparator 
* <p>
* This is a generic comparator class.
* @author Marius Spix
*/

package helpers;

import java.util.Comparator;

public class GenericComparator<T extends Comparable<? super T>> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return
		    o1 == null ? -1 :
		    o2 == null ?  1 :
		    o1.compareTo(o2);
	}


}
