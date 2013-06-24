/**
* AllTest
* <p>
* Test suite for the dijkstra project
* @author Marius Spix
*/

package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ModelTest.class, FileTest.class, PathfinderTest.class})
public class AllTests {

}
