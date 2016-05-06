package hu.hnk.beershop.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CartServiceTest.class, RestrictionCheckerServiceTest.class, StorageServiceTest.class,
		UserServiceTest.class, CargoServiceTest.class, EventLogFactoryTest.class })
public class AllTests {

}
