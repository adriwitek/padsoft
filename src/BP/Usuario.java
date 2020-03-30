package BP;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Usuario extends Proponente {
	
	private static final long serialVersionUID = 1011;

	
	private String NIF;
	private String nombre;
	private String contraseña;
	private EstadoUsuario estado;
	
	//Notificaiones
	private Notificacion nRechazoRegistro;
	private Notificacion nBloqueoDeAdmin;
	private List<Notificacion> nSuscripcionEstadoProyecto;
	private List<Notificacion> nRechazoProyectoProponente;
	
	
	
	/*Constructor*/
	
	/**Constructor, con los datos a implementar
	 * 
	 * @param nif nif
	 * @param nomb nombre
	 * @param contra pass
	 */
	public Usuario(String nif, String nomb, String contra) {
		/*if(nif.isEmpty()|| nombre.isEmpty()|| contra.isEmpty()
			|| Objects.isNull(nif) ||  Objects.isNull(nombre)|| Objects.isNull(contra)) {
			throw new IllegalArgumentException("Debes de introducir los datos obligatorios y no pueden ser vacios");
		}*/
		NIF = nif; nombre = nomb; 
		setContraseña(contra); 
		setEstado(EstadoUsuario.PENDIENTE);
	}
	
	
	
	
	
	
	//Setters de estado del Usuario
	
	/**
	 * Esta funcion bloquea un usuario
	 * 
	 * @param motivo motivo del bloquo
	 */
	public void bloquear(String motivo) {
		if(getEstado() == EstadoUsuario.OPERATIVO) {
			setEstado(EstadoUsuario.BLOQUEADO);
			this.nBloqueoDeAdmin  = new Notificacion("Tu cuenta se ha bloqueado","El motivo del bloqueo de tu cuenta es: " + motivo);
		}
	}
	
	/**
	 * Esta funcion desbloquea un usuario
	 */
	public void desbloquear() {
		if(getEstado() == EstadoUsuario.BLOQUEADO) {
			setEstado(EstadoUsuario.OPERATIVO);
			this.nBloqueoDeAdmin = null;
		}
	}
	
	/**
	 * Esta funcion aprueba a un usario
	 * 
	 * @return boolean ,ture en caso de erro
	 */
	public Boolean aprobar() {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.OPERATIVO);
			this.nRechazoRegistro = null;
			return true;
		}
		return false;
	}
	
	/**
	 * Esta funcion rechaza un usuario
	 * 
	 * @param motivo motivo rechazo
	 * @return ture si ok
	 */
	public Boolean rechazar(String motivo) {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.RECHAZADO);
			this.nBloqueoDeAdmin = new Notificacion("Solicitud de Registro Rechazada","El administrador ha rechazado tu solicitud de alta en el "
					+ "sistema debido a:" + motivo);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Esta funcion devuelve el estado del proyecto
	 * 
	 * @return estado 
	 */
	public EstadoUsuario getEstado() {
		return this.estado;
	}
	public void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
	



	/**
	 * Esta funcion permite que un usuario se suscriba a un proyecto
	 * 
	 * @param p proyecto
	 */
	public void suscribirseAProyecto(Proyecto p) {
		p.suscribirseANotificacionesDeProyecto( this);
	}
	
	
	//funcion llamada desde proyecto!
	
	/**
	 * Esta funcion devuelve nSuscripcionEstadoProyecto
	 * 
	 * @param n notificacion de un proyecto suscrito
	 */
	public void anniadirNotificacionDeProyecto(Notificacion n) {
		this.nSuscripcionEstadoProyecto.add(n);
	}
	
	

	
	
	
	
	
	
	

	
	
	
	/****** Getters y Setters de Campos de Usuario *******/

	/**
	 * Esta funcion devuelve el NIF
	 * 
	 * @return nif
	 */
	public String getNIF() {
		return NIF;
	}



	/**
	 * esta funcion devuelve el nombre
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * Esta funcion devuelve la contraseña
	 * 
	 * @return pass
	 */
	public String getContraseña() {
		return contraseña;
	}


	/**
	 * Esta funcion permite introducir una contraseña para un usuario
	 * 
	 * @param contraseña password del user
	 */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}




    /**
     * Esta funcion devuelve la notificacion de rechazo al registrarse
     * 
     * @return nRechazoRegistro
     */
	public Notificacion getnRechazoRegistro() {
		return nRechazoRegistro;
	}


	/**
	 * Esta funcion devuelve nBloqueoDeAdmin
	 * 
	 * @return nBloqueoDeAdmin
	 */
	public Notificacion getnBloqueoDeAdmin() {
		return nBloqueoDeAdmin;
	}


	/**
	 * Esta funcion devuelve el estado de los proyectos a los que estas suscrito
	 * 
	 * @return lista de todas las notificaciones de suscipciones a proyectos
	 */
	public List<Notificacion> getnSuscripcionEstadoProyecto() {
		return  Collections.unmodifiableList(nSuscripcionEstadoProyecto);
	}


	/**
	 * Esta funcion devuelve una lista de nRechazoProyectoProponente
	 * 
	 * @return notificacions de proyectos propuestos rechazados
	 */
	public List<Notificacion> getnRechazoProyectoProponente() {
		return Collections.unmodifiableList(nRechazoProyectoProponente);
	}
	
	/**
	 * Esta funcion devuelve el nombre del usuario
	 * 
	 * @return nombre
	 */
	public String toString() {
		return this.nombre;
	}
	
	
}
