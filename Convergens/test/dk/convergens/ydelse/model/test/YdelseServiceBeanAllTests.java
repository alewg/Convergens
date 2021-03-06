package dk.convergens.ydelse.model.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Run all tests on YdelseServiceBean.
 * 
 * @author Alexander
 */
@RunWith(Suite.class)
@SuiteClasses({ YdelseServiceBeanCreateTest.class, YdelseServiceBeanDeleteTest.class, YdelseServiceBeanFindTest.class,
		YdelseServiceBeanUpdateTest.class })
public class YdelseServiceBeanAllTests {

}
