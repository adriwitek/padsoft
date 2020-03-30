package BP.demostrador;
import java.io.IOException;

import BP.*;


public class main {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		
		Aplicacion app = Aplicacion.getInstancia("admin", "1234",100); //login del admin
		app.loadAplicacion();
		
		// USUARIOS
		Usuario u1 = app.solicitarRegistro("1234456789A", "Guillermo", "1234");
		Usuario u2 =app.solicitarRegistro("98726543321A", "Adrian", "1234");
		Usuario u3 =app.solicitarRegistro("12344567893A", "Tapia", "1234");
		if(u1 == null ||u2 == null || u3 == null   ) System.out.println("Los u devueltos son nulos");
		
		
		
		
		//COLECTIVOS Y SUBCOLECTIVOS 
		
			//TODO
		
		
		
		
		//VALIDAMOS LOS REGISTROS
		app.loginAdmin("admin", "1234");
		
		if (app.getRegistrosPendientesDeAprobacion().size() !=3) System.out.println("Error, el numero de usuairo pendientes de aprobacion no coincide: " + app.getRegistrosPendientesDeAprobacion().size());
		app.validarRegistro(u1);
		app.validarRegistro(u2);
		app.validarRegistro(u3);
		app.logOut();
		app.loginUser("Guillermo", "1234");
		System.out.println("Usuario logueado: " + app.getUsuarioConectado());

	
		
		//SAVE Y LOAD
		app.saveAplicacion();
		app.exit();
		if(!app.loadAplicacion()) System.out.println("No se ha podido cargar el backup");

		if (!app.loginUser("Guillermo", "1234")) System.out.println("Error, no se ha podido iniciar sesion");
		System.out.println("Usuario logueado: " + app.getUsuarioConectado());

		
		
		//CREACION PROYECTOS
		
		
		
		
		ProyectoSocial pSocial1 = app.crearProyectoSocial( u1, "Proyecto Social 1", "Descrip Laaarga", "Description corta" ,  10000.0 ,"Grupo 1",  true); //debe acpetarse la financiacion en el
		ProyectoSocial pSocial2 = app.crearProyectoSocial( u2, "Proyecto Social 2", "Descrip Laaarga", "Description corta" ,  500.0 ,"Grupo 2",  false); //debe acpetarse la financiacion en el

		ProyectoInfraestructura
		
		
		
		
		
		//Caducidad
		
		
		//Financiacion
		
	}
	

	
}
