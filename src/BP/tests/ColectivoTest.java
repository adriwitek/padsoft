package BP.tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import BP.Colectivo;
import BP.EstadoUsuario;
import BP.Usuario;


public class ColectivoTest {
	
	private static Colectivo colectivo1;
	private static Usuario usuario1;
	private static Usuario usuario2;
	private static Usuario usuario3;
	private static Colectivo colectivoAux;
	
	@BeforeClass
	public static void setUp() throws Exception {
        usuario1 = new Usuario("5555", "Paco", "paco123");
        usuario2 = new Usuario("4444", "Pepe", "pepe2000");
        usuario3 = new Usuario("6666", "Solla", "solla2000");
		colectivo1 = new Colectivo(usuario1,"nombre",null);
        colectivoAux = new Colectivo(usuario1,"nombreAux",null);
    }
	
	@Test
	public void crearSubcolectivoTest() {
		Colectivo colectivo2 = colectivo1.crearSubcolectivo("name");
		
		
		assertNotEquals("Error al crear subcolectivo", colectivo2 , null);
		assertTrue(colectivo1.getSubcolectivos().contains(colectivo2));
	}
	
	@Test 
	public void obtenerAfinidadTest() {
		
		double afinidad = colectivo1.obtenerAfinidad(colectivoAux);
		
		assertNotEquals("Error al obtener afinidad",afinidad, null);
		
	}
	
	@Test
	public void suscribirseColectivoTest() {
		
		colectivo1.suscribirseColectivo(usuario2);
		
		assertTrue(colectivo1.getParticipantes().contains(usuario2));
		
	}
	
	@Test
	public void getNombreTest() {
		String nombre = colectivo1.getNombre();
		assertEquals(nombre, "nombre");
		
	}
	
	@Test
	public void getUsuarioRepresentanteDeColectivoTest() {
		Usuario usuariox = colectivo1.getUsuarioRepresentanteDeColectivo();
		assertNotEquals(usuariox, null);
	}
	
	
	@Test
	public void getParticipantesTest() {
		HashSet<Usuario> listUs = colectivo1.getParticipantes();
		assertTrue(listUs.isEmpty());
	}

	@Test
	public void getColectivoPadreTest() {
		Colectivo padre = colectivo1.getColectivoPadre();
		assertEquals(padre, null);
	}
	
	@Test
	public void getSubcolectivosTest() {
		HashSet<Colectivo> sub = colectivo1.getSubcolectivos();
		assertNotEquals(sub, null);
	}
	
	@Test
	public void getIsUsuarioEnColectivoSubcolectivo() {
		
		colectivo1.suscribirseColectivo(usuario2);
		assertTrue(colectivo1.getIsUsuarioEnColectivoSubcolectivo(usuario2));
	}
}
