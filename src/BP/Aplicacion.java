package BP;

import java.util.HashSet;

public class Aplicacion {
	
	private static final long serialVersionUID = 1111;
	private static Aplicacion INSTANCE = null;
	
	
	private String nombreAdmin;
	private String contraseñaAdmin;
	private Integer numMinApoyos;
	
	private HashSet<Proyecto> proyectosSolicitandoFinanciacion;
	private HashSet<Proyecto> proyectos;

	
	
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
	
	


	
	
	public static Boolean addSolicitudFinanciacionProyecto(Proyecto p){
		
		if(this.proyectosSolicitandoFinanciacion.contains(p)) {
			return false;
		}else {
			this.proyectosSolicitandoFinanciacion.add(p);
			return true;
		}
		
	}
	
	
	
	
	
	public ProyectoSocial crearProyectoSocial(Proponente p,String nombre, String descrL, String descC , double cost ,String gSocial, Boolean nac){
		
		ProyectoSocial proyecto;
		
		if(p.getClass().getName().equals("Colectivo")) {
			Colectivo c = (Colectivo) p;
			proyecto = new ProyectoSocial(p,c.getUsuarioRepresentanteDeColectivo() ,nombre,descrL, descC, cost,gSocial, nac);
			
		}else {//Usuario
			Usuario u = (Usuario) p;
			proyecto = new ProyectoSocial(u,u,nombre,descrL, descC, cost,gSocial, nac);
			
		}
		p.proponerProyecto(proyecto);
		
		this.proyectos.add(proyecto);
		return proyecto;
		
	}
	
	
	public ProyectoInfraestructura crearPoryectoInfraestructura(Proponente p,String nombre, String descrL, String descC , double cost,String dist,String croquis ,String imagen){
		ProyectoInfraestructura proyecto;
		
		if(p.getClass().getName().equals("Colectivo")) {
			Colectivo c = (Colectivo) p;
			proyecto = new ProyectoInfraestructura(p,c.getUsuarioRepresentanteDeColectivo() , nombre,  descrL,  descC ,  cost , dist, croquis , imagen);
			
		}else {//Usuario
			Usuario u = (Usuario) p;
			proyecto = new ProyectoInfraestructura(u,u , nombre,  descrL,  descC ,  cost , dist, croquis , imagen);			
		}
		p.proponerProyecto(proyecto);
		
		this.proyectos.add(proyecto);
		return proyecto;
		
	}
	
}

