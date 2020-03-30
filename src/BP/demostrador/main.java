package BP.demostrador;
import java.io.IOException;

import BP.*;


public class main {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		

		Aplicacion app = Aplicacion.getInstancia("admin", "1234",100); //login del admin
		app.loadAplicacion();
		//añadir usuarios
		app.solicitarRegistro("123456789A", "Guillermo", "1234");
		Usuario user=app.solicitarRegistro("987654321A", "Adrian", "1234");
		app.solicitarRegistro("123456789A", "Tapia", "1234");
		app.loginUser("Guillermo", "1234");
		//VALIDAMOS EL REGISTRO
		for(Proponente p: app.getRegistrosPendientesDeAprobacion()) {
			if(p.getClass().getSimpleName().equals("Usuario") ) {
				user=(Usuario)p;
				app.validarRegistro(user);
				System.out.println("Validando registro de Usuario:" + user.getNombre() +" "+ user.getNIF());
			}
				
		}	
		app.loginUser("Guillermo", "12344");
		System.out.println(app.getUsuarioConectado());
		//inmobi.leeFicheroEntrada("entrada.txt");
		app.saveAplicacion();
	
	}
	

	
}
