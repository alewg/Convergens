package dk.convergens.ydelse.model.test;

import static org.mockito.Mockito.doReturn;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

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
public class YdelseServiceBeanDeleteTest {

	//The tested class instance
	private static YdelseServiceBean ydelseService = null;
	
	//Mocked
	@Mock private static EntityManager entityManager;

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
	 * Test that remove can be reached (maybe not that good either?)
	 */
	@Test
	public void testDelete() {
		
		Long id = 4L;
		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);
		
		doReturn(y).when(entityManager).getReference(y.getClass(), y.getId());
		
		ydelseService.delete(y.getClass(), id);
	}
	
	/**
	 * Test if id doesn't exist then throw EntityNotFoundException
	 */
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteEntityNotFoundException() {
		
		Long id = 4L;
		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);
		
		doReturn(null).when(entityManager).getReference(y.getClass(), y.getId());
		
		ydelseService.delete(y.getClass(), id);
	}
	
	/**
	 * Test throwing EJBException (Makes no sense)
	 */
	@Test(expected = EJBException.class)
	public void testDeleteEJBException() {
		
		Long id = 4L;
		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);
		
		doReturn(y).when(entityManager).getReference(y.getClass(), y.getId());
		Mockito.doThrow(EJBException.class).when(entityManager).remove(y);
		
		ydelseService.delete(y.getClass(), id);
	}
	
	
}
