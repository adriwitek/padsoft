package BP.demostrador;
import java.io.IOException;
import java.util.HashSet;

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
		Colectivo c1 = new Colectivo(u1,"Colectivo 1",null);
		Colectivo c2 = new Colectivo(u2,"Colectivo 2",null);
		Colectivo subC2 = c2.crearSubcolectivo("Sucolectivo 2,hijo de colectivo 2");
		
		
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
		ProyectoSocial pSocial3 = app.crearProyectoSocial( u2, "Proyecto Social 3", "Descrip Laaarga", "Description corta" ,  1000000.0 ,"Grupo 3",  true); //debe acpetarse la financiacion en el
		
		String pathImg1 = "testImages/img1.png";
		String pathImg2 = "testImages/img2.png";
		String pathCroquis1 = "testImages/croquis1.png";
		String pathCroquis2 = "testImages/croquis2.png";
		HashSet<String> setDistritos1 = new HashSet<String>();
		HashSet<String> setDistritos2 = new HashSet<String>();
		setDistritos1.add("Usera");
		setDistritos2.add("Salamanca");
		setDistritos2.add("Latina");

		ProyectoInfraestructura pInfraestructura1 =  app.crearProyectoInfraestructura(u1 ,"P. Infra 1",  "Descrip Laaarga", "Description corta",  100000,pathCroquis1  , pathImg1,setDistritos1);
		//ProyectoInfraestructura pInfraestructura2 =  app.crearProyectoInfraestructura(u1 ,"P. Infra 2",  "Descrip Laaarga", "Description corta",  100000, pathCroquis2 , pathImg2,setDistritos2);

		/*
		//PROPOSICION DE PROYECTOS
		u2.proponerProyecto(pSocial1);
		u2.proponerProyecto(pInfraestructura1);
		
		c1.proponerProyecto(pSocial2);
		
		c2.proponerProyecto(pSocial3);
		subC2.proponerProyecto(pInfraestructura2);
		
		*/
		//Caducidad
		
		
		//Financiacion
		
	}
	

	
}
