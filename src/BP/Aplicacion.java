package BP;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;

public class Aplicacion {
	
	private static final long serialVersionUID = 1111;
	private static Aplicacion INSTANCE = null;
	
	//Admin
	private String nombreAdmin;
	private String contraseñaAdmin;
	private Integer numMinApoyos;
	
	//Listados
	//private static HashSet<Proyecto> proyectosSolicitandoFinanciacion;
	private HashSet<Proyecto> proyectos;
	private HashSet<Proponente> proponentes;
	private static int lastProjectUniqueID;
	private static int lastColectivoUniqueID;
	private static int lastUsuarioUniqueID;
	private Usuario usuarioConectado;//Usuario estandar que esta usando en este momento la apliacion
	
	
	
	//Constructor
	public Aplicacion(String nomAdmin, String contrasena, Integer numMinApoyos) {
		
		this.nombreAdmin = nomAdmin;
		this.contraseñaAdmin = contrasena; 
		this.numMinApoyos = numMinApoyos;
		
		
		//this.proyectosSolicitandoFinanciacion = new HashSet<Proyecto>();//Tambien se podrian recorrer los proyectos, revisar!
		this.proyectos = new HashSet<Proyecto>();
		this.proponentes = new HashSet<Proponente>();
		this.lastProjectUniqueID = 0;
		this.lastColectivoUniqueID =0;
		
	}
	
	// **** CONSTRUCTOR SINGLETON ***/
	public static Aplicacion getInstancia(String userAdmin, String passwordAdmin,Integer numMinApoyos) {
		if (INSTANCE == null) {
			INSTANCE = new Aplicacion(userAdmin, passwordAdmin,numMinApoyos);
		}
		return INSTANCE;
	}
	
	
	//inicar app
	
	
	//Save

	
	//Load
	
	public void caducarProyectosAntiguos() {
		
		Date hoy = new Date();
		Date fecha;
		long days;
		for(Proyecto p: proyectos) {
			fecha = p.getFechaUltimoApoyo();
			days = ChronoUnit.DAYS.between(hoy.toInstant(),fecha.toInstant());
			if(days > 30) {
				p.caducarProyecto();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	

	
	//	***FUNCIONES LLAMADAS POR EL ADMIN***
	
	//loginAdmin
	
	public boolean loginAdmin(String user, String password) {
		//Inicio sesion administrador
		if(user.equals(this.nombreAdmin) && password.equals(this.contraseñaAdmin)) { 
				return true;
		}
		return false;
	}
	
	public HashSet<Usuario> getRegistrosPendientesDeAprobacion(){
		
		Usuario u;
		HashSet<Usuario> pendientes = new HashSet<Usuario>();
		for(Proponente p: this.proponentes) {
			if( p.getClass().getName() == "Usuario" ) {
				u = (Usuario)p;
				if(u.getEstado()== EstadoUsuario.PENDIENTE) {
					pendientes.add(u);
				}
				
			}
		}
		return pendientes;
	}
	
	
	
	public void validarRegistro(Usuario u) {
		u.aprobar();
	}
	
	
	
	public void rechazarRegistro(Usuario u,String motivo) {
		u.rechazar(motivo);
		this.proponentes.remove(u);
		
	}
	

	public HashSet<Proyecto> getProyectosSolicitandoFinanciacion(){
		
		HashSet<Proyecto> listado = new HashSet<Proyecto>();
		for(Proyecto p: this.proyectos) {
			if(p.getEstadoProyecto() == EstadoProyecto.PENDIENTEFINANCIACION) {
				listado.add(p);
			}
		}
		return listado;
	} 
	
		
	
	
	
	
	
	
	
	
	
	
	
	//	*** FUNCIONES LLAMADAS POR EL USUARIO LOGUEADO ***
	
	public boolean loginUser(String nombre, String contraseña) {
		Usuario  aux;
		//Buscar usuario
		for(Proponente p: this.proponentes ) {
			if(p.getClass().getName() == "Usuario") {
				aux = (Usuario)p;
				//Coinciden creedenciales y el usuario está operativo
				if(aux.getEstado().equals(EstadoUsuario.OPERATIVO) && aux.getNombre().equals(nombre) && aux.getContraseña().equals(contraseña)) {
					usuarioConectado = aux;
					return true;

				}	
			}
		
		}
		return false;
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
		this.lastProjectUniqueID++;
		return proyecto;
		
	}
	
	
	public ProyectoInfraestructura crearProyectoInfraestructura(Proponente p,String nombre, String descrL, String descC , double cost,String dist,String croquis ,String imagen){
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
		this.lastProjectUniqueID++;
		return proyecto;
		
	}
	
	
	
	
	
	
	
	
	
	
	//	***FUNCIONES VARIAS INTERNAS***

	//nuevo registro usuario hecho por GSOLLA el 27/03/2020
	public Usuario solicitarRegistro(String nif, String nombre, String contraseña) {
		Usuario newUser, aux;
		Proponente newProp;
		//comprobar que en el sistema no hay usuarios con ese nombre
		for(Proponente p: this.proponentes ) {
			if(p.getClass().getName() == "Usuario") {
				aux = (Usuario)p;
				if(aux.getNombre().equals(nombre) || aux.getNIF().equals(nif)) {
					return null;
				}	
			}
		}
		newUser = new Usuario(nif, nombre, contraseña, getNewUsuarioUniqueId(), EstadoUsuario.PENDIENTE);
		newProp = (Proponente)newUser;
		this.proponentes.add(newProp);
		return newUser;
	}
	//revisar
	public static Boolean addSolicitudFinanciacionProyecto(Proyecto p){
		
		if(getProyectosSolicitandoFinanciacion().contains(p)) {
			return false;
		}else {
			getProyectosSolicitandoFinanciacion().add(p);
			return true;
		}
		
	}

	public static int getNewProjectUniqueId() {
		return  lastProjectUniqueID +1;
	}
	
	public static int getNewColectivoUniqueId() {
		lastColectivoUniqueID++;
		return  lastColectivoUniqueID;
	}
	
	public static int getNewUsuarioUniqueId() {
		lastUsuarioUniqueID++;
		return  lastUsuarioUniqueID;
	}
	
}


