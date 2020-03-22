package BP;

import java.util.HashSet;

public class Aplicacion {
	
	private static final long serialVersionUID = 1111;
	private static Aplicacion INSTANCE = null;
	
	
	private String nombreAdmin;
	private String contraseñaAdmin;
	private Integer numMinApoyos;
	
	private HashSet<Proyecto> proyectosSolicitandoFinanciacion;
	
	//Constructor
	public Aplicacion(String nomAdmin, String contrasena, Integer numMinApoyos) {
		
		this.nombreAdmin = nomAdmin;
		this.contraseñaAdmin = contrasena; 
		this.numMinApoyos = numMinApoyos;
		
		
		this.proyectosSolicitandoFinanciacion = new HashSet<Proyecto>();
		
	}
	
	
	/* Constructor singletown */
	public static Aplicacion getInstancia(String userAdmin, String passwordAdmin,Integer numMinApoyos) {
		if (INSTANCE == null) {
			INSTANCE = new Aplicacion(userAdmin, passwordAdmin,numMinApoyos);
		}
		return INSTANCE;
	}
	
	
	
	
	
	//inicar app
		//caducarProyectos()
	
	


	
	
	public Boolean addSolicitudFinanciacionProyecto(Proyecto p){
		
		if(this.proyectosSolicitandoFinanciacion.contains(p)) {
			return false;
		}else {
			this.proyectosSolicitandoFinanciacion.add(p);
			return true;
		}
		
	}
	
	
	
	
	
	
	
}

