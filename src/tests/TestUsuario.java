package tests;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import BP.EstadoUsuario;
import BP.Usuario;
public class TestUsuario {
	private static Usuario testUser;
	@BeforeClass
	public static void setUp() throws Exception{
	 testUser = new Usuario("12345678X", "Pepe", "1234", 1, EstadoUsuario.PENDIENTE);
	}
	
	@Test
	public void testBloquear() {
		//inicialmente con pendiente, asi que lo aceptamos para que se pueda bloquear
		testUser.aprobar();
		testUser.bloquear("spam");
		//Comprobar estado
		assertEquals("ERROR, Bloquear usuario",EstadoUsuario.BLOQUEADO,testUser.getEstado());
		//Comprobar notificacion
		assertTrue("ERROR, Notificaci�n bloqueo usuario",testUser.getnBloqueoDeAdmin().getDescripcion().contains("spam") );
	}
	
	public void testDesbloquear() {
		testUser.aprobar();
		testUser.bloquear("Bloqueado por spam");
		testUser.desbloquear();
		//Comprobar estado
		assertEquals("ERROR, Desbloquear usuario",EstadoUsuario.OPERATIVO,testUser.getEstado());
		//Comprobar notificacion
		assertTrue("ERROR, Notificaci�n desbloqueo usuario no eliminada",testUser.getnBloqueoDeAdmin().getDescripcion() == null);
	}
	
	public void testAprobar() {
		testUser.aprobar();
		//Comprobar estado
		assertEquals("ERROR, Aprobar usario", EstadoUsuario.OPERATIVO, testUser.getEstado());
	}
	
	public void testRechazar() {
		testUser.rechazar("nombre ofensivo");
		//Comprobar estado
		assertEquals("ERROR, Rechazar usuario",EstadoUsuario.OPERATIVO,testUser.getEstado());
		//Comprobar notificaci�n
		assertTrue("ERROR, Notificaci�n bloqueo usuario",testUser.getnBloqueoDeAdmin().getDescripcion().contains("nombre ofensivo") );
	}
}