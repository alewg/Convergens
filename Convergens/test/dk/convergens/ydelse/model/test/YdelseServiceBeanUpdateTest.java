package dk.convergens.ydelse.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;

import junit.framework.TestCase;

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
public class YdelseServiceBeanUpdateTest {

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
	 * Test that update() returns the updated entity.
	 */
	@Test
	public final void testUpdate() {

		Long id = 4L;
		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);

		doReturn(y).when(entityManager).getReference(y.getClass(), id);
		when(entityManager.merge(y)).thenReturn(y);

		Ydelse ydelse = ydelseService.update(y);
		
		assertEquals(cpr, ydelse.getCpr());
		assertEquals(kr, ydelse.getKr());
		assertEquals(dato, ydelse.getDato());
		assertEquals(type, ydelse.getType());
		
	}
	
	/**
	 * Test that EntityNotFoundException is thrown if Entity Manager can't find reference to entity.
	 */
	@Test (expected = EntityNotFoundException.class)
	public final void testUpdateEntityNotFoundException() {

		Long id = 4L;
		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);
		
		doReturn(null).when(entityManager).getReference(y.getClass(), id);
		
		ydelseService.update(y);
		
	}
	
	/**
	 * Test that EJBException is thrown when merge() fails (Maybe not that good?)
	 */
	@Test (expected = EJBException.class)
	public final void testUpdateEJBException() {

		Long id = 4L;
		String cpr = "271190-0000";
		int kr = 100;
		String dato = "01-01-2001";
		String type = "type2";

		Ydelse y = new Ydelse(id, cpr, kr, dato, type);
		
		doReturn(y).when(entityManager).getReference(y.getClass(), id);
		Mockito.doThrow(EJBException.class).when(entityManager).merge(y);
		
		ydelseService.update(y);
	}
	

}
