package BP.tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;

import BP.Aplicacion;
import BP.EstadoProyecto;
import BP.EstadoUsuario;
import BP.ProyectoInfraestructura;
import BP.Proyecto;
import BP.Proponente;
import BP.Usuario;

public class ProyectoInfraestructuraTest {

	private static ProyectoInfraestructura proyectoInf1;
	private static Usuario proponente1;
	private static Usuario usuarioCreador;
	
	@BeforeClass
	public static void setUp() throws Exception{
		proponente1 = new Usuario("111", "Pepe", "pepe123");
		usuarioCreador = new Usuario("222", "Juan", "juan2000");
		
		double cost = 1000;
		HashSet<String> distritos = null;
		Aplicacion app = Aplicacion.getInstancia("pepe","1234",10);

		
		proyectoInf1 = new ProyectoInfraestructura(proponente1, usuarioCreador,"name", "descL", "descC" , cost, "testImages/croquis1.png" , "testImages/img1.png" , app.getDistritos());
		
	}

	@Test
	public void getNumeroApoyosActualesValidosTest() {
		proyectoInf1.apoyarProyecto(proponente1);
		
		int Apoyantes = proyectoInf1.getNumeroApoyosActualesValidos();
		
		assertNotEquals(Apoyantes, null);
		
	}
	
	@Test 
	public void obtenerInformePopularidadTest() {
		String informe = proyectoInf1.obtenerInformePopularidad(proponente1);
		
		assertNotEquals(informe, null);
	}
	
	@Test
	public void solicitarFinanciacionTest() {
		
		assertTrue(!proyectoInf1.solicitarFinanciacion());

	}
	
	@Test
	public void getProponenteTest() {
		assertEquals(proyectoInf1.getProponente(),proponente1);	
	}
	
	@Test
	public void getUsuarioCreadorTest() {
		assertEquals(proyectoInf1.getUsuarioCreador(),usuarioCreador);	
	}
	
	@Test 
	public void getNombreTest() {
		assertEquals(proyectoInf1.getNombre(),"name");	
	}

	@Test
	public void getDescripcionLargaTest() {
		assertEquals(proyectoInf1.getDescripcionLarga(),"descL");
	}
	

	@Test
	public void getDescripcionCortaTest() {
		assertEquals(proyectoInf1.getDescripcionCorta(),"descC");
	}
	
	@Test
	public void getFechaCreacion() {
		assertNotEquals(proyectoInf1.getDescripcionCorta(),null);
	}
	
	@Test
	public void getCosteTest() {
		assertEquals(proyectoInf1.getCoste(),1000,0);
	}
	
	@Test
	public void getFinanciacionRecibidaTest() {
		proyectoInf1.setFinanciacionRecibida(900);
		assertEquals(proyectoInf1.getFinanciacionRecibida(), 900, 0);
	}
	
	@Test
	public void getEstadoProyectoTest() {
		assertNotEquals(proyectoInf1.getEstadoProyecto(), -1);
	}
	
	@Test
	public void addDistritoTest() {
		assertTrue(proyectoInf1.addDistrito("Usera"));
		
	}
	
	@Test 
	public void getExtraDataTest() {
		assertNotEquals(proyectoInf1.getExtraData(),null);
	}
	
	@Test
	public void getProjectKindTest() {
		assertNotEquals(proyectoInf1.getProjectKind(), -1);
	}
	
	
	
}
