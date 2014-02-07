package dk.convergens.ydelse.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import dk.convergens.ydelse.model.Ydelse;
import dk.convergens.ydelse.model.YdelseServiceBean;

@RunWith(MockitoJUnitRunner.class)
public class YdelseServiceBeanFindTest {

	// The tested class instance
	private static YdelseServiceBean ydelseService = null;

	@Mock(
			answer = Answers.RETURNS_DEEP_STUBS)
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
	 * Test that results are returned
	 */
	@Test
	public void testFindWithNamedQueryString() {

		// new Ydelse(1L,"271190-0000",99,"01-01-2001","Type1")

		List<Ydelse> list = new ArrayList<Ydelse>();

		Ydelse ydelse1 = mock(Ydelse.class);
		Ydelse ydelse2 = mock(Ydelse.class);
		Ydelse ydelse3 = mock(Ydelse.class);

		list.add(ydelse1);
		list.add(ydelse2);
		list.add(ydelse3);

		int size = list.size();

		String namedQuery = "Ydelse.findAll";

		when(entityManager.createNamedQuery(namedQuery).getResultList()).thenReturn(list);

		List<Ydelse> list2 = ydelseService.findWithNamedQuery(namedQuery);

		assertNotNull(list);
		assertEquals(size, list2.size());
	}

	/**
	 * Test if list is empty throw NullPointerException
	 */
	@Test(
			expected = NullPointerException.class)
	public void testFindWithNamedQueryStringNullPointerException() {

		String namedQuery = "Ydelse.findAll";

		when(entityManager.createNamedQuery(namedQuery).getResultList()).thenReturn(null);

		ydelseService.findWithNamedQuery(namedQuery);

	}

	/**
	 * Test that EJBException is... caught... or...
	 */
	@SuppressWarnings("unchecked")
	@Test(
			expected = EJBException.class)
	public void testFindWithNamedQueryStringEJBException() {

		String namedQuery = "Ydelse.findAll";

		when(entityManager.createNamedQuery(namedQuery).getResultList()).thenThrow(EJBException.class);

		ydelseService.findWithNamedQuery(namedQuery);
	}

	/**
	 * Test result is returned.
	 */
	@Test
	public void testFindWithNamedQueryStringMapOfStringObject() {
		
		List<Ydelse> list = new ArrayList<Ydelse>();

		Ydelse ydelse1 = mock(Ydelse.class);
		Ydelse ydelse2 = mock(Ydelse.class);
		Ydelse ydelse3 = mock(Ydelse.class);

		list.add(ydelse1);
		list.add(ydelse2);
		list.add(ydelse3);

		int size = list.size();

		String namedQuery = "Ydelse.findAll";
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		String cpr = "271190-0000";
		
		param.put("cpr", cpr);
		
		when(entityManager.createNamedQuery(namedQuery).getResultList()).thenReturn(list);

		List<Ydelse> list2 = ydelseService.findWithNamedQuery(namedQuery, param);

		assertNotNull(list);
		assertEquals(size, list2.size());
		
	}
	
	/**
	 * Test if list is empty throw NullPointerException
	 */
	@Test (expected = NullPointerException.class)
	public void testFindWithNamedQueryStringMapOfStringObjectNullPointerException() {
		
		String namedQuery = "Ydelse.findAll";
		
		when(entityManager.createNamedQuery(namedQuery).getResultList()).thenReturn(null);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		String cpr = "271190-0000";
		
		param.put("cpr", cpr);
		
		ydelseService.findWithNamedQuery(namedQuery, param);
		
	}
	
	/**
	 * Test if EJBException.. again.. not sure how to test it.
	 */
	@SuppressWarnings("unchecked")
	@Test (expected = EJBException.class)
	public void testFindWithNamedQueryStringMapOfStringObjectEJBException() {
		
		String namedQuery = "Ydelse.findAll";
		
		when(entityManager.createNamedQuery(namedQuery).getResultList()).thenThrow(EJBException.class);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		String cpr = "271190-0000";
		
		param.put("cpr", cpr);
		
		ydelseService.findWithNamedQuery(namedQuery, param);
		
	}
	

}
