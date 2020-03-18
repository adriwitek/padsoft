package BP;

import java.util.Date;
import java.util.HashSet;

public abstract class Proyecto {
	
	
	private Proponente proponente;
	private Usuario usuarioCreador;
	private HashSet<Usuario> apoyantes;
	
	private String nombre;
	private String descripcionLarga, descripcionCorta;
	private Date fechaCreacion, fechaUltimoApoyo;
	private double coste, financiacionRecibida;
	private EstadoProyecto estadoProyecto;
	
	private HashSet<Usuario> usuariosSuscritosNotificaciones;
	
	
	//Constructor
	public Proyecto(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost ) {
		
		this.proponente = p;
		this.usuarioCreador = uCreador;
		this.apoyantes = new HashSet<Usuario>();
		this.nombre = nombre; 
		this.setDescripcionLarga(descrL); 
		this.setDescripcionCorta(descC);
		this.fechaCreacion = new Date();
		this.setFechaUltimoApoyo(null);
		this.coste = cost;
		this.setFinanciacionRecibida(0);
		this.usuariosSuscritosNotificaciones= new HashSet<Usuario>()  ;
		
	}


	
	
	
	
	
	
	public void apoyarProyecto(Proponente p) {
		if(this.estadoProyecto == EstadoProyecto.OPERATIVO) {
			
			if( p.getClass().getName() == "Usuario") {
				this.apoyantes.add((Usuario) p);
			}else {
				
				for(Usuario u : ((Colectivo) p).getParticipantes() ) {
					this.apoyantes.add(u);
				}
				
			}
			
		}
		
	}
	
	
	public int getNumeroApoyosActualesValidos() {
		
		int n =0;
		for(Usuario u : this.apoyantes) {
			if(u.getEstado()== EstadoUsuario.OPERATIVO) {
				n++;
			}
		}
		return n;
	}
	
	
	
	
	

	
	
	
	
	public String obtenerInformePopularidad(Usuario u) {
		//TODO
		//????
		
		
		
		
		return null;
	}
	
	
	
	
	
	//Funcion llamada desde usuario
	public void suscribirseANotificacionesDeProyecto(Usuario u) {
		this.usuariosSuscritosNotificaciones.add(u);
	}
	
	
	
	

	
	/****METODOS DE CAMBIO DE ESTADO EN EL PROYECTO ***/
	
	
	public Boolean solicitarFinanciacion(Aplicacion app) {
		
		if(app.addSolicitudFinanciacionProyecto(this)) {
			this.estadoProyecto = EstadoProyecto.PENDIENTEFINANCIACION;
			return true;
		}
		return false;
		
	}
	
	
	public void financiarProyecto(int dinero) {
		
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
	public void rechazarProyecto(String motivo) {
		String titulo;
		String descripcion;
		Notificacion not;
		
		this.estadoProyecto = EstadoProyecto.FINANCIACIONRECHAZADO;

		 titulo= "Financiacion Rechazada";
		 descripcion= "La financiacion del proyecto: " + this.nombre + " ha sido rechazada por el administrador. El motivo del rechazo ha sido: " + motivo;
		 not = new Notificacion(titulo,descripcion);
		  
		  for(Usuario u : this.usuariosSuscritosNotificaciones) {
			  u.anniadirNotificacionDeProyecto( not);

		  }
		  
	}
	
	
	
	//llamado desde el admin
	public void validarProyecto() {
		this.estadoProyecto = EstadoProyecto.OPERATIVO;
	}
	
	//lamado desde la applicacion al inicio
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




	



	
	
	
	
	
}
