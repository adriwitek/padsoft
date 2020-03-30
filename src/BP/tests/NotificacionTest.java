package BP.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import BP.Notificacion;

public class NotificacionTest {
	
	private static Notificacion notificacion1;
	
	@BeforeClass
	public static void setUp() throws Exception{
		notificacion1 = new Notificacion("titulo", "descripcion");
	}

	@Test 
	public void getTituloTest() {
		assertNotEquals(notificacion1.getTitulo(),null);
	}
	
	@Test
	public void getDescripcionTest() {
		assertNotEquals(notificacion1.getDescripcion(),null);
	}
	
	@Test
	public void getFechaCreacionTest() {
		assertNotEquals(notificacion1.getFechaCreacion(),null);
	}
	
	
}
