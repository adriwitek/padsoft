package BP;

/** 
* 
* @author Adián Rubio adrian.rubiop@estudiante.uam.es, Guillermo Solla guillermo.solla@estudiante.uam.es 
* y Diego Tapia diego.tapias@estudiante.uam.es
*/

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import es.uam.eps.sadp.grants.CCGG;
import es.uam.eps.sadp.grants.GrantRequest;



public abstract class Proyecto implements java.io.Serializable, GrantRequest{
	
	private static final long serialVersionUID = 1010;

	
	private Proponente proponente;
	private Usuario usuarioCreador;
	private HashSet<Usuario> usuariosaApoyantes;
	
	protected int uniqueID;
	private String nombre;
	private String descripcionLarga, descripcionCorta;
	private Date fechaCreacion, fechaUltimoApoyo;
	private double coste, financiacionRecibida;
	private EstadoProyecto estadoProyecto;
	private String idSeguimientoSistemaFinanciacion;
	
	private HashSet<Usuario> usuariosSuscritosNotificaciones;
	
	
	 /**
	  * Constructor, con los datos a implementar
	  * @param p que asignar al proponente 
	  * @param uCreador para asignar al usuario que ha creado el proyecto
	  * @param nombre para asignar al proyecto
	  * @param descrL para asignar al proyecto
	  * @param descC para asignar al proyecto
	  * @param cost para asignar a la financiacón que se pretende obtener para crear el proyecto
	  */
	
	public Proyecto(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost ) {
		
		
		if(nombre.length()> 25 || nombre.length() == 0 || Objects.isNull(nombre) || Objects.isNull(p) || Objects.isNull(uCreador) 
				|| Objects.isNull(descrL) || Objects.isNull(descC)) {
			throw new IllegalArgumentException("El nombre del proyecto no puede tener mas de 25 caracteres");
		}else if( descC.length()>500) {
			throw new IllegalArgumentException("La descripcion corta no puede tener una longituda superior a 500 caracteres");
		}
		
		this.proponente = p;
		this.usuarioCreador = uCreador;
		this.usuariosaApoyantes = new HashSet<Usuario>();
		this.nombre = nombre; 
		this.setDescripcionLarga(descrL); 
		this.setDescripcionCorta(descC);
		this.fechaCreacion = new Date();
		this.setFechaUltimoApoyo(new Date());
		this.coste = cost;
		this.setFinanciacionRecibida(0);
		this.usuariosSuscritosNotificaciones= new HashSet<Usuario>()  ;
		this.uniqueID= Aplicacion.getInstancia(null,null,null).getNewProjectUniqueId();
		this.idSeguimientoSistemaFinanciacion = null;
	}


	
	
	
	
	
	/**
	 * Esta funcion permite que un usuario apoye a un proyecto especifico, siempre y cuando el proyecto este en estado OPERATIVO
	 * 
	 * @param u
	 */
	public void apoyarProyecto(Usuario u ) {
		if(this.estadoProyecto == EstadoProyecto.OPERATIVO) {
			this.usuariosaApoyantes.add(u);
			Date d1 = new Date();
			setFechaUltimoApoyo(d1);
		}
		
	}
	
	
	
	/**
	 * Esta funcion permite que un colectivo apoye a un proyecto haciendo que todos sus miembros tambien lo apoyen (Apoyar como colectivo),
	 * siempre y cuando el estado del proyecto sea OPERATIVO
	 * 
	 * @param p
	 */
	public void apoyarProyectoComoColectivo(Colectivo c) {
		if(this.estadoProyecto == EstadoProyecto.OPERATIVO) {
			for(Usuario u : c.getParticipantes() ) {
				this.usuariosaApoyantes.add(u);
			}

		}
		Date d1 = new Date();
		setFechaUltimoApoyo(d1);
	}
	
	
	
	
	
	/**
	 * Esta funcion muestra el numero de apoyos a un proyecto siempre y cuando los apoyantes se encuentren en estado OPERATIVO
	 * 
	 * @return the n
	 */
	public int getNumeroApoyosActualesValidos() {
		
		int n =0;
		for(Usuario u : this.usuariosaApoyantes) {
			if(u.getEstado()== EstadoUsuario.OPERATIVO) {
				n++;
			}
		}
		return n;
	}
	
	
	
	
	

	
	
	
	/**
	 * Esta funcion devuele el informe de popularidad, es decir, el numero de apoyos validos de un proyecto
	 * 
	 * @param u
	 * @return
	 */
	public String obtenerInformePopularidad(Usuario u) {
		return "numero de apoyos: " + getNumeroApoyosActualesValidos();
	}
	
	
	
	
	
	//Funcion llamada desde usuario
	
	/**
	 * Esta funcion permite que un usuario se suscriba a las notificaciones de un proyecto
	 * 
	 * @param u
	 */
	public void suscribirseANotificacionesDeProyecto(Usuario u) {
		this.usuariosSuscritosNotificaciones.add(u);
	}
	
	
	
	

	
	/****METODOS DE CAMBIO DE ESTADO EN EL PROYECTO ***/
	
	/**
	 * Si la api ha financiado el proyecto,llamar a este metodo desde App
	 * 
	 * @return
	 */
	public Boolean solicitarFinanciacion() {
		int contadorIntentosMaximos = 4;
		
		while(contadorIntentosMaximos !=0) {
			
			try {
				if(this.getEstadoProyecto() == EstadoProyecto.OPERATIVO && this.getNumeroApoyosActualesValidos() >= Aplicacion.getInstancia(null, null, null).getNumeroMinimimoApoyos()) {
					this.idSeguimientoSistemaFinanciacion= CCGG.getGateway().submitRequest(this);
					this.estadoProyecto = EstadoProyecto.PENDIENTEFINANCIACION;
					return true;
				}else {
					return false;
				}
				
			}catch(Exception e) {
				contadorIntentosMaximos--;
			}
			
			
			
		}
	
		
		return false;
		
	}
	
	/**
	 * Esta funcion permite financiar un proyecto a una cantidad especifica de dinero
	 * 
	 * @param dinero
	 */
	public void financiarProyecto(double dinero) {
		
		String titulo;
		String descripcion;
		Notificacion not;
		this.estadoProyecto = EstadoProyecto.FINANCIACIONACEPTADA;
		this.financiacionRecibida = dinero;
		  titulo= "Financiacion Aceptada";
		  descripcion= "La financiacion del proyecto: " + this.nombre + " ha sido aceptada. Se ha recibido: " + this.financiacionRecibida
				  		+" euros del coste propuesto inicialmente de:" + this.coste + " euros";
		   not = new Notificacion(titulo,descripcion);
		  
		  for(Usuario u : this.usuariosSuscritosNotificaciones) {
			  u.anniadirNotificacionDeProyecto( not);

		  }
		  
		
		
	}
	
	
	
	
	
	//llamado desde el admin
	
	/**
	 * Esta funcion rechaza un proyecto que ha sido propuesto, dejando el proyecto en estado FINANCIACIONRECHAZADO
	 * 
	 * @param motivo
	 * 
	 */
	public void rechazarProyecto(String motivo) {
		String titulo;
		String descripcion;
		Notificacion not;
		
		this.estadoProyecto = EstadoProyecto.FINANCIACIONRECHAZADO;
		this.financiacionRecibida =0;
		 titulo= "Financiacion Rechazada";
		 descripcion= "La financiacion del proyecto: " + this.nombre + " ha sido rechazada por el administrador. El motivo del rechazo ha sido: " + motivo;
		 not = new Notificacion(titulo,descripcion);
		  
		  for(Usuario u : this.usuariosSuscritosNotificaciones) {
			  u.anniadirNotificacionDeProyecto( not);

		  }
		  
	}
	
	/**
	 * Esta funcion se encarga de validar un proyecto propuesto
	 */
	public void validarProyecto() {
		this.estadoProyecto = EstadoProyecto.OPERATIVO;
	}
	
	
	
	
	//llamado desde la applicacion al inicio con el control de fechas
	
	/**
	 * Esta funcion se encarga de caducar un proyecto especifico
	 */
	public void caducarProyecto() {
		this.estadoProyecto= EstadoProyecto.CADUCADO;
	}
	
	
	
	
	
	/***********************Getter y setters simples generados ************************/
	
	

	/**
	 * Esta funcion se encarga de devolver el proponente del proyecto
	 * 
	 * @return the proponente
	 */
	public Proponente getProponente() {
		return proponente;
	}



	/**
	 * Esta funcion se encarga de devolver el usuario creador de un proyecto
	 * 
	 * @return the usuarioCreador
	 */
	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}




	/**
	 * Esta funcion devuelve el nombre de un proyecto
	 * 
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}




	/**
	 * Esta funcion se encarga de devolver la descripcion larga de un proyecto
	 * 
	 * @return the descripcionLarga
	 */
	public String getDescripcionLarga() {
		return descripcionLarga;
	}




	/**
	 * Esta funcion se encarga de introducir una decripcion larga a un proyecto
	 * 
	 * @param descripcionLarga the descripcionLarga to set
	 */
	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}




	/**
	 * Esta funcion devuelve la descripcion corta de un proyecto
	 * 
	 * @return the descripcionCorta
	 */
	public String getDescripcionCorta() {
		return descripcionCorta;
	}




	/**
	 * Esta funcion se encarga de introducir un descripcion corta en un proyecto
	 * 
	 * @param descripcionCorta the descripcionCorta to set
	 */
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	


	/**
	 * Esta funcion se encarga de devolver la fecha de creacion de un proyecto
	 * 
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}




	/**
	 * Esta funcion devuelve la fecha del ultimo apoyo realizado al proyecto
	 * 
	 * @return the fechaUltimoApoyo
	 */
	public Date getFechaUltimoApoyo() {
		return fechaUltimoApoyo;
	}




	/**
	 * Esta funcion introduce una nueva ultima fecha de apoyo a un proyecto
	 * 
	 * @param fechaUltimoApoyo the fechaUltimoApoyo to set
	 */
	public void setFechaUltimoApoyo(Date fechaUltimoApoyo) {
		this.fechaUltimoApoyo = fechaUltimoApoyo;
	}



	/**
	 * Esta funcion devuelve el coste de un proyecto
	 * 
	 * @return the coste
	 */
	public double getCoste() {
		return coste;
	}



	/**
	 * Esta funcion devuelve la financiacion recibida
	 * 
	 * @return the financiacionRecibida
	 */
	public double getFinanciacionRecibida() {
		return financiacionRecibida;
	}



	/**
	 * Esta funcion introduce en un proyecto una financiacionRecibida
	 * 
	 * @param financiacionRecibida the financiacionRecibida to set
	 */
	public void setFinanciacionRecibida(double financiacionRecibida) {
		this.financiacionRecibida = financiacionRecibida;
	}





	/**
	 * Esta funcion devuelve el estado de un proyecto
	 * 
	 * @return the estadoProyecto
	 */
	public EstadoProyecto getEstadoProyecto() {
		return estadoProyecto;
	}




	/**
	 * Esta funcion devvuelve el titulo del proyecto
	 * 
	 * @return nombre
	 */
	public String 	getProjectTitle() {
		return this.nombre;
	}

	/**
	 * Esta funcion devuelve una descripcion ya sea la larga o la corta, esto dependera de si la decripcion larga supera los 500 caracteres
	 * si esto ocurre se devolvera la decripcion corta
	 */
	public String getProjectDescription() {
		if(this.descripcionLarga.length()>500) {
			return this.descripcionCorta;
			
		}else {
			return this.descripcionLarga;
		}
	}
	
	/**
	 * Esta funcion devuelve el coste
	 * @return coste
	 */
	public double getRequestedAmount() {
		return this.coste;
	}







	/**
	 * Esta funcion devuelve la idSeguimientoSistemaFinanciacion de un proyecto
	 * 
	 * @return the idSeguimientoSistemaFinanciacion
	 */
	public String getIdSeguimientoSistemaFinanciacion() {
		return idSeguimientoSistemaFinanciacion;
	}
	
	
	
}
