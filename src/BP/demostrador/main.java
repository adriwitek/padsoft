package BP.demostrador;
import java.io.IOException;

import BP.*;


public class main {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		

		Aplicacion app = Aplicacion.getInstancia("admin", "1234",100); //login del admin
		app.loadAplicacion();
		//añadir usuarios
		Usuario u1 = app.solicitarRegistro("123456789A", "Guillermo", "1234");
		Usuario u2 =app.solicitarRegistro("987654321A", "Adrian", "1234");
		Usuario u3 =app.solicitarRegistro("123456789A", "Tapia", "1234");
		
		
		//VALIDAMOS LOS REGISTROS
		app.loginAdmin("admin", "1234");
		
		if (app.getRegistrosPendientesDeAprobacion().size() !=3) System.out.println("Error, el numero de usuairo pendientes de aprobacion no coincide");
		app.validarRegistro(u1);
		app.validarRegistro(u2);
		app.validarRegistro(u3);
		app.logOut();
		
		
		
		app.loginUser("Guillermo", "1234");
		System.out.println("Usuario logueado: " + app.getUsuarioConectado());
		app.saveAplicacion();
	
		app.exit();
		
		
		if(!app.loadAplicacion()) { 
			System.out.println("No se ha podido cargar el backup");

		}
		
		if (app.loginUser("Guillermo", "1234")) System.out.println("Error, no se ha podido iniciar sesion");

		
		
		
	}
	

	
}
