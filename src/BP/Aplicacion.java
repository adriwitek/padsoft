package BP;

import java.util.HashSet;

public class Aplicacion {
	private String nombreAdmin;
	private String contrase�aAdmin;
	private Integer numMinApoyos;
	
	private HashSet<Proyecto> proyectosSolicitandoFinanciacion;
	
	//Constructor
	public Aplicacion(String nomAdmin, String contrasena, Integer numMinApoyos) {
		
		this.nombreAdmin = nomAdmin;
		this.contrase�aAdmin = contrasena; 
		this.numMinApoyos = numMinApoyos;
		
		
		this.proyectosSolicitandoFinanciacion = new HashSet<Proyecto>();
		
	}
	
	
	
	
	
	public Boolean addSolicitudFinanciacionProyecto(Proyecto p){
		
		if(this.proyectosSolicitandoFinanciacion.contains(p)) {
			return false;
		}else {
			this.proyectosSolicitandoFinanciacion.add(p);
			return true;
		}
		
	}
	
	
	
	
	
	
	
}

