package BP.tests;

import org.junit.Test;

import BP.*;
import BP.demostrador.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.After;
public class AplicacionTest {
	
	private static Aplicacion app = null;
	@BeforeClass
	public static void setUp() throws Exception{
		app =Aplicacion.getInstancia("admin", "1234", 4);
		}
	
	
	
	@Test
	public void testLoginAdmin() {
		//introducir credenciales bien
		assertTrue(app.loginAdmin("admin", "1234"));
		//introducir credenciales mal
		app.logOut();
		assertFalse(app.loginAdmin("admin", "12345"));
	}

	@Test
	public void testGetRegistroPendientesDeValidacion() {
		//añadir registros 
		app.loginAdmin("admin", "1234");
		Usuario u1 = app.solicitarRegistro("12333456789A", "nombreaa", "contraseña");
		app.validarRegistro(u1);
		Usuario u2 = app.solicitarRegistro("1234563789C", "nomaabre2", "contraseña");
		assertTrue(app.getRegistrosPendientesDeAprobacion().contains(u2));
		assertFalse(app.getRegistrosPendientesDeAprobacion().contains(u1));
	}
	@Test
	public void testValidarRegistro() {
		//NO ACTIVADO MODO ADMIN
		Usuario u1 = app.solicitarRegistro("123444456789A", "nombfffre", "contraseña");
		assertFalse(app.validarRegistro(u1));
		//ACTIVADO, COMP RETURN
		app.loginAdmin("admin", "1234");
		assertTrue(app.validarRegistro(u1));
		//COMP USUARIO VALIDADO
		assertEquals(u1.getEstado(), EstadoUsuario.OPERATIVO);
	}
	@Test
	public void testRechazarRegistro() {
		//NO ACTIVADO MODO ADMIN
		Usuario u1 = app.solicitarRegistro("1234567ss89A", "noombre", "contraseña");
		assertFalse(app.rechazarRegistro(u1,""));
		//ACTIVADO, COMP RETURN
		app.loginAdmin("admin", "1234");
		assertTrue(app.rechazarRegistro(u1,""));
		//COMP USUARIO VALIDADO
		assertEquals(u1.getEstado(), EstadoUsuario.RECHAZADO);
	}
	@Test
	public void testGetProyectosSolicitandoFinanciacion() {
		//NO ACTIVADO MODO ADMIN
		app.loginAdmin("admin", "1234");

		Usuario u1 = app.solicitarRegistro("12345673389A", "nombr11e1", "contraseña");
		Usuario u2 = app.solicitarRegistro("12345673389B", "nombree2", "contraseña");
		Usuario u3 = app.solicitarRegistro("12345673389C", "noembre3", "contraseña");
		Usuario u4 = app.solicitarRegistro("12345633789D", "nombree4", "contraseña");
		app.validarRegistro(u1);
		app.validarRegistro(u2);
		app.validarRegistro(u3);
		app.validarRegistro(u4);
		Proyecto p1 = app.crearProyectoSocial(u1, "proyeceto", "Larga", "Corta",1780, "obreros", true);
		p1.validarProyecto();
		p1.apoyarProyecto(u1);
		p1.apoyarProyecto(u2);
		p1.apoyarProyecto(u3);
		p1.apoyarProyecto(u4);
		assertTrue(p1.solicitarFinanciacion());
		assertTrue(app.getProyectosSolicitandoFinanciacion().contains(p1));
	}
	
	@Test 
	public void testlogOut() {
		Usuario u1 = app.solicitarRegistro("123456789A", "nombre1", "contraseña");
		app.loginAdmin("admin", "1234");
		app.logOut();
		//funcion que precisa que admin este loggeado
		assertFalse(app.validarRegistro(u1));
	}
	
	@Test
	public void testLoginUser() {
		Usuario u1 = app.solicitarRegistro("12345226789A", "nombre11", "contraseña");
		assertFalse(app.loginUser("nombre11", "contraseña"));
		app.loginAdmin("admin", "1234");
		app.validarRegistro(u1);
		app.logOut();
		assertTrue(app.loginUser("nombre11", "contraseña"));
		assertEquals(app.getUsuarioConectado(), u1);
	}
	
	@Test
	public void testCrearProyectoSocial() {
		Usuario u1 = app.solicitarRegistro("1234522622789A", "noaambre1", "contraseña");
		app.validarRegistro(u1);
		Proyecto p1 = app.crearProyectoSocial(u1, "proyecto", "Larga", "Corta",1780, "obreros", true);
	}
	
	@Test 
	public void testSolicitarRegistro() {
		app.loginAdmin("admin", "1234");
		Usuario u1 = app.solicitarRegistro("1234aa56789A", "nomaare1", "contraseña");
		assertNotNull(u1);
		assertTrue(app.getRegistrosPendientesDeAprobacion().contains(u1));
		
	}
	
	@After
	public void after() {
		app.exit();
	}
	
	/*
	public void test
	public void test
	public void test
	public void test
	*/
}
