package BP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

import es.uam.eps.sadp.grants.CCGG;



public class Aplicacion implements java.io.Serializable {
	
	private static final long serialVersionUID = 1111;
	private static Aplicacion INSTANCE = null;
	private String ficheroCarga = "applicationBackup";
	private String distritosPath = "distritos/Distritos.txt";
	//Admin
	private String nombreAdmin;
	private String contraseñaAdmin;
	private Integer numMinApoyos;
	private Boolean modoAdmin;
	//Listados
	private HashSet<Proyecto> proyectos;
	private HashSet<Proponente> proponentes;
	private  int lastProjectUniqueID;
	private HashSet<String> distritosPermitidos;
	
	private Usuario usuarioConectado;//Usuario estandar que esta usando en este momento la apliacion
	
	 /**
	  * Constructor, con los datos a implementar
	  * @param nomAdmin que asignar a la aplicacion 
	  * @param contrasena para asignar a la aplicacion
	  * @param numMinApoyos para asignar a la aplicacion
	  */
	
	//Constructor
	public Aplicacion(String nomAdmin, String contrasena, Integer numMinApoyos) {
		
		if(nomAdmin.isEmpty()|| Objects.isNull(nomAdmin) ) {
			throw new IllegalArgumentException("Debes de introducir los datos validos");
		}
		
		this.nombreAdmin = nomAdmin;
		this.contraseñaAdmin = contrasena; 
		this.numMinApoyos = numMinApoyos;
		this.modoAdmin = false;
		
		this.proyectos = new HashSet<Proyecto>();
		this.proponentes = new HashSet<Proponente>();
		this.lastProjectUniqueID = 0;
		
		//Cargamos los distritos
		distritosPermitidos = new HashSet<String>();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(distritosPath));
	           String d;
	           while((d = br.readLine()) != null){
	        	   this.distritosPermitidos.add(d);	               //Leer la siguiente línea
	           }
		}catch (FileNotFoundException e) {
	            System.out.println("Error: Fichero no encontrado");
	            System.out.println(e.getMessage());
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}
	
	// **** CONSTRUCTOR SINGLETON ***/
	
	/**
	 * Esta funcion devuelve la instancia de la aplicacion
	 * 
	 * @param userAdmin
	 * @param passwordAdmin
	 * @param numMinApoyos
	 * @return
	 */
	public static Aplicacion getInstancia(String userAdmin, String passwordAdmin,Integer numMinApoyos) {
		if (INSTANCE == null) {
			INSTANCE = new Aplicacion(userAdmin, passwordAdmin,numMinApoyos);
		}
		return INSTANCE;
	}
	
	
	
	
	
	
	/***** Funciones de Carga/Guardado ****/
	
	
	//Save
	
	/**
	 * Esta funcion se encarga de hacer save en la aplicacion, devolvera true en caso correcto
	 * 
	 * @return
	 */
	public boolean saveAplicacion() {
		try {
			ObjectOutputStream objectFile = new ObjectOutputStream(new FileOutputStream(ficheroCarga));
			objectFile.writeObject(getInstancia(this.nombreAdmin, this.contraseñaAdmin, this.numMinApoyos));
		}catch(Exception e) {
			
		}
		return true;
	}
	

	
	
	
	/**
	 * Esta funcion se encarga de cargar una aplicacion
	 * 
	 * @return
	 */
	public Boolean loadAplicacion() {
		//cargar app serializada
		
		
		
		//TODO
		try {
			
	        FileInputStream fileIn= new FileInputStream(ficheroCarga);
            ObjectInputStream entrada= new ObjectInputStream(fileIn);
            Aplicacion app = (Aplicacion)entrada.readObject();
            if(app == null) {
            	return false;
            }else {
            	INSTANCE = app;
            	//Metodos de inicio y actuliazadion de datos de la App
    			caducarProyectosAntiguos();
    			actualizarProyectosFinanciados();
    			return true;
            }
		
		}catch(Exception e) {
			
			
		}
		
		return false;
	}
	
	
	/**
	 * Esta funcion cierra la aplicacion no sin antes hacer save de esta
	 * 
	 * @return
	 */
	public boolean exit() {
		this.logOut();
		INSTANCE = null;
		return  saveAplicacion();
		
	}
	
	/**
	 * Esta funcion se encarga de caducar proyectos antiguos
	 */
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
	
	
	/**
	 * Esta funcion de encarga de actualizar los proyecto financiados
	 */
	public void actualizarProyectosFinanciados() {
		
		Double financiacion;
		for(Proyecto p: this.proyectos) {
			if(p.getEstadoProyecto()== EstadoProyecto.PENDIENTEFINANCIACION) {
				
				try {
					financiacion = CCGG.getGateway().getAmountGranted( p.getIdSeguimientoSistemaFinanciacion() );
					
					if(financiacion > 0) {
						p.financiarProyecto(financiacion);
					}else{
						p.rechazarProyecto("El sistema de financiacion ha denegado la financiacion del proyecto");
					}
					
				} catch ( NullPointerException e) {
					// TODO Auto-generated catch block
				
				
				} catch ( Exception e) {
				// TODO Auto-generated catch block
				}
				
				
			}
			
		}
	}
	
	
	
	
	
	

	
	//	***FUNCIONES LLAMADAS POR EL ADMIN***
	
	//loginAdmin
	/**
	 * Esta funcion se encarga de logear a un administrador
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean loginAdmin(String user, String password) {
		//Inicio sesion administrador
		if(user.equals(this.nombreAdmin) && password.equals(this.contraseñaAdmin)) { 
			this.modoAdmin = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Esta funcion devuelve un lista con los registros pendientes de aprobacion
	 * 
	 * @return
	 */
	public HashSet<Usuario> getRegistrosPendientesDeAprobacion(){
		
		if(!this.modoAdmin) return null;
		
		Usuario u;
		HashSet<Usuario> pendientes = new HashSet<Usuario>();
		for(Proponente p: this.proponentes) {
			if( p.getClass().getSimpleName().equals("Usuario")) {
				u = (Usuario)p;
				if(u.getEstado()== EstadoUsuario.PENDIENTE) {
					pendientes.add(u);
				}
				
			}
		}
		return pendientes;
	}
	
	
	/**
	 * Esta funcion se encarga de validar un registro
	 * 
	 * @param u
	 * @return
	 */
	public Boolean validarRegistro(Usuario u) {
		if(!this.modoAdmin) return false ;
		u.aprobar();
		return true;
	}
	
	
	/**
	 * Esta funcion se encarga de rechazar un registro
	 * 
	 * @param u
	 * @param motivo
	 * @return
	 */
	public Boolean rechazarRegistro(Usuario u,String motivo) {
		if(!this.modoAdmin) return false;
		u.rechazar(motivo);
		this.proponentes.remove(u);
		return true;
	}
	
	/**
	 * Esta funcion se encarga de devolver una lista con los proyectos que han solicitado financiacion
	 * 
	 * @return
	 */
	public HashSet<Proyecto> getProyectosSolicitandoFinanciacion(){
		
		if(!this.modoAdmin) return null;
		HashSet<Proyecto> listado = new HashSet<Proyecto>();
		for(Proyecto p: this.proyectos) {
			if(p.getEstadoProyecto() == EstadoProyecto.PENDIENTEFINANCIACION) {
				listado.add(p);
			}
		}
		//return (HashSet<Proyecto>) Collections.unmodifiableSet(listado);
		//posteriormente hay que modificarlos, controlar la llamada a la fucnion
		return listado;
	} 
	
		
	
	
	
	
	
	
	
	
	
	
	
	//	*** FUNCIONES LLAMADAS POR EL USUARIO LOGUEADO ***
	/**
	 * Esta funcion se encarga de sacarte de la aplicacion
	 */
	public void logOut() {
		this.modoAdmin = false;
		this.usuarioConectado = null;
	}
	
	

	
	/**
	 * Esta funcion se encarga de iniciar sesion a un usuario 
	 * 
	 * @param nombre
	 * @param contraseña
	 * @return
	 */
	public boolean loginUser(String nombre, String contraseña) {
		Usuario  aux;
		//Buscar usuario
		for(Proponente p: this.proponentes ) {
			if(p.getClass().getSimpleName().equals("Usuario")) {
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
	
	/**
	 * Esta funcion se encarga de crear un proyecto social dentro de la aplicacion
	 * 
	 * @param p
	 * @param nombre
	 * @param descrL
	 * @param descC
	 * @param cost
	 * @param gSocial
	 * @param nac
	 * @return
	 */
	public ProyectoSocial crearProyectoSocial(Proponente p,String nombre, String descrL, String descC , double cost ,String gSocial, Boolean nac){
		
		ProyectoSocial proyecto;
		
		if(p.getClass().getSimpleName().equals("Colectivo")) {
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
	
	/**
	 * Esta funcion se encarga de crear un proyecto social dentro de la aplicacion
	 * 
	 * @param p
	 * @param nombre
	 * @param descrL
	 * @param descC
	 * @param cost
	 * @param croquis
	 * @param imagen
	 * @param distritos
	 * @return
	 */
	public ProyectoInfraestructura crearProyectoInfraestructura(Proponente p,String nombre, String descrL, String descC , double cost,String croquis ,String imagen,HashSet<String> distritos){
		ProyectoInfraestructura proyecto;
		
		if(p.getClass().getSimpleName().equals("Colectivo")) {
			Colectivo c = (Colectivo) p;
			proyecto = new ProyectoInfraestructura(p,c.getUsuarioRepresentanteDeColectivo() , nombre,  descrL,  descC ,  cost , croquis , imagen,distritos);
			
		}else {//Usuario
			Usuario u = (Usuario) p;
			proyecto = new ProyectoInfraestructura(u,u , nombre,  descrL,  descC ,  cost , croquis , imagen,distritos);			
		}
		p.proponerProyecto(proyecto);
		
		this.proyectos.add(proyecto);
		this.lastProjectUniqueID++;
		return proyecto;
		
	}
	
	
	
	
	
	
	
	
	
	
	//	***FUNCIONES VARIAS INTERNAS***

	//nuevo registro usuario hecho por GSOLLA el 27/03/2020
	
	/** 
	 * Esta funcion se encarga de solicitar el registro de un nuevo usuario en la aplicacion
	 * 
	 * @param nif
	 * @param nombre
	 * @param contraseña
	 * @return
	 */
	public Usuario solicitarRegistro(String nif, String nombre, String contraseña) {
		
		Usuario newUser, aux;
		for(Proponente p: this.proponentes ) {
			if(p.getClass().getSimpleName().equals("Usuario") ) {
				aux = (Usuario)p;
				if(aux.getNombre().equals(nombre) || aux.getNIF().equals(nif)) {
					return null;
				}	
			}
		}
		newUser = new Usuario(nif, nombre, contraseña);
		this.proponentes.add(newUser);
		return newUser;
	}
	
	
	
	/**
	 * Esta funcion se encarga de devolver la id de un proyecto
	 * 
	 * @return
	 */
	public  int getNewProjectUniqueId() {
		return  lastProjectUniqueID +1;
	}
	
	/**
	 * Esta funcions e encarga de activar el modo admin
	 * 
	 * @return
	 */
	public Boolean isModoAdmin() {
		return this.modoAdmin;
	}
	
	/**
	 * Esta funcion se encarga de devolver el numero minimo de apoyos necesarios en un proyecto
	 * 
	 * @return
	 */
	public Integer getNumeroMinimimoApoyos() {
		return numMinApoyos;
	}
	
	/**
	 * Esta funcion se encarga de devolver los distritos que contiene la aplicacion
	 * 
	 * @return
	 */
	public HashSet<String> getDistritos(){
		//return (HashSet<String>) Collections.unmodifiableSet(this.distritosPermitidos);
		return this.distritosPermitidos;
	}

	/**
	 * Esta funcion se encarga de devolver si un usuario esta conectado
	 * 
	 * @return
	 */
	public Usuario getUsuarioConectado() {
		return this.usuarioConectado;
	}

	
}



