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
	 * 
	 * @param u
	 * @return
	 */
	public String obtenerInformePopularidad(Usuario u) {
		return "numero de apoyos: " + getNumeroApoyosActualesValidos();
	}
	
	
	
	
	
	//Funcion llamada desde usuario
	
	/**
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
	 * 
	 */
	public void validarProyecto() {
		this.estadoProyecto = EstadoProyecto.OPERATIVO;
	}
	
	
	
	
	//llamado desde la applicacion al inicio con el control de fechas
	
	/**
	 * 
	 */
	public void caducarProyecto() {
		this.estadoProyecto= EstadoProyecto.CADUCADO;
	}
	
	
	
	
	
	/***********************Getter y setters simples generados ************************/
	
	

	/**
	 * @return the proponente
	 */
	public Proponente getProponente() {
		return proponente;
	}



	/**
	 * @return the usuarioCreador
	 */
	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}




	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}




	/**
	 * @return the descripcionLarga
	 */
	public String getDescripcionLarga() {
		return descripcionLarga;
	}




	/**
	 * @param descripcionLarga the descripcionLarga to set
	 */
	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}




	/**
	 * @return the descripcionCorta
	 */
	public String getDescripcionCorta() {
		return descripcionCorta;
	}




	/**
	 * @param descripcionCorta the descripcionCorta to set
	 */
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	


	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}




	/**
	 * @return the fechaUltimoApoyo
	 */
	public Date getFechaUltimoApoyo() {
		return fechaUltimoApoyo;
	}




	/**
	 * @param fechaUltimoApoyo the fechaUltimoApoyo to set
	 */
	public void setFechaUltimoApoyo(Date fechaUltimoApoyo) {
		this.fechaUltimoApoyo = fechaUltimoApoyo;
	}



	/**
	 * @return the coste
	 */
	public double getCoste() {
		return coste;
	}



	/**
	 * @return the financiacionRecibida
	 */
	public double getFinanciacionRecibida() {
		return financiacionRecibida;
	}



	/**
	 * @param financiacionRecibida the financiacionRecibida to set
	 */
	public void setFinanciacionRecibida(double financiacionRecibida) {
		this.financiacionRecibida = financiacionRecibida;
	}





	/**
	 * @return the estadoProyecto
	 */
	public EstadoProyecto getEstadoProyecto() {
		return estadoProyecto;
	}




	
	public String 	getProjectTitle() {
		return this.nombre;
	}

	
	public String getProjectDescription() {
		if(this.descripcionLarga.length()>500) {
			return this.descripcionCorta;
			
		}else {
			return this.descripcionLarga;
		}
	}
	
	
	public double getRequestedAmount() {
		return this.coste;
	}







	/**
	 * @return the idSeguimientoSistemaFinanciacion
	 */
	public String getIdSeguimientoSistemaFinanciacion() {
		return idSeguimientoSistemaFinanciacion;
	}
	
	
	
}
