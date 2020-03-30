package BP.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import BP.Aplicacion;
import BP.EstadoProyecto;
import BP.EstadoUsuario;
import BP.Proponente;
import BP.ProyectoInfraestructura;
import BP.ProyectoSocial;
import BP.Usuario;

public class ProyectoSocialTest {

	private static ProyectoSocial proyectoSoc1;
	private static Usuario proponente1;
	private static Usuario usuarioCreador;
	
	
	
	@BeforeClass
	public static void setUp() throws Exception{
		proponente1 = new Usuario("111", "Pepe", "pepe123");
		usuarioCreador = new Usuario("222", "Juan", "juan2000");
		Aplicacion.getInstancia("pepe","1234",10);
		proyectoSoc1 = new ProyectoSocial(usuarioCreador, usuarioCreador,"nombre", "descL", "descC" , 1000 ,"gSocial", true);
	}

	@Test
	public void getNumeroApoyosActualesValidosTest() {
		proyectoSoc1.apoyarProyecto(proponente1);
		
		int Apoyantes = proyectoSoc1.getNumeroApoyosActualesValidos();
		
		assertNotEquals(Apoyantes, null);
		
	}
	
	@Test 
	public void obtenerInformePopularidadTest() {
		String informe = proyectoSoc1.obtenerInformePopularidad(proponente1);
		
		assertNotEquals(informe, null);
	}
	
	@Test
	public void solicitarFinanciacionTest() {
		assertTrue(!proyectoSoc1.solicitarFinanciacion());
	}
	
	@Test
	public void getProponenteTest() {
		assertEquals(proyectoSoc1.getProponente(),usuarioCreador);	
	}
	
	@Test
	public void getUsuarioCreadorTest() {
		assertEquals(proyectoSoc1.getUsuarioCreador(),usuarioCreador);	
	}
	
	@Test 
	public void getNombreTest() {
		assertEquals(proyectoSoc1.getNombre(),"nombre");	
	}

	@Test
	public void getDescripcionLargaTest() {
		assertEquals(proyectoSoc1.getDescripcionLarga(),"descL");
	}
	

	@Test
	public void getDescripcionCortaTest() {
		assertEquals(proyectoSoc1.getDescripcionCorta(),"descC");
	}
	
	@Test
	public void getFechaCreacion() {
		assertNotEquals(proyectoSoc1.getDescripcionCorta(),null);
	}
	
	@Test
	public void getCosteTest() {
		assertEquals(proyectoSoc1.getCoste(),1000,0);
	}
	
	@Test
	public void getFinanciacionRecibidaTest() {
		proyectoSoc1.setFinanciacionRecibida(900);
		assertEquals(proyectoSoc1.getFinanciacionRecibida(), 900, 0);
	}
	
	@Test
	public void getEstadoProyectoTest() {
		assertNotEquals(proyectoSoc1.getEstadoProyecto(), -1);
	}
	
	@Test 
	public void getExtraDataTest() {
		assertNotEquals(proyectoSoc1.getExtraData(),null);
	}
	
	@Test
	public void getProjectKindTest() {
		assertNotEquals(proyectoSoc1.getProjectKind(), -1);
	}
	
}
