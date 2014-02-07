package dk.convergens.ydelse.model.test;

import static org.junit.Assert.*;

import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import dk.convergens.ydelse.model.Ydelse;
import dk.convergens.ydelse.model.YdelseServiceBean;

@RunWith(MockitoJUnitRunner.class)
public class YdelseServiceBeanCreateTest {

	// The tested class instance
	private static YdelseServiceBean ydelseService = null;

	// Mocked
	@Mock
	private static EntityManager entityManager;

	/**
	 * Setup class instance to be tested.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (ydelseService == null)
			ydelseService = new YdelseServiceBean();
	}

	/**
	 * Destroys test class instance.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ydelseService = null;
	}

	/**
	 * Sets mocked Entity Manager to test class instance.
	 */
	@Before
	public void setUp() throws Exception {
		ydelseService.setEntityManager(entityManager);
	}

	/**
	 * Set test class instance to null
	 */
	@After
	public void tearDown() throws Exception {
		ydelseService.setEntityManager(null);
	}

	/**
	 * 
	 * Test that returned entity correspond (values not changed) to the entity
	 * in parameter.
	 * 
	 * @throws NamingException
	 */
	@Test
	public final void testCreate() throws NamingException {

		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse ydelse = ydelseService.create(new Ydelse(cpr, kr, dato, type));

		assertEquals(cpr, ydelse.getCpr());
		assertEquals(kr, ydelse.getKr());
		assertEquals(dato, ydelse.getDato());
		assertEquals(type, ydelse.getType());

	}

	/**
	 * 
	 * Test properly nothing, but expect EJBException when create() is called.
	 * 
	 * @throws NamingException
	 */
	@Test (expected = EJBException.class)
	public final void testCreateException() throws NamingException {

		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(cpr, kr, dato, type);

		Mockito.doThrow(EJBException.class).when(entityManager).persist(y);

		ydelseService.create(y);
	}

}
