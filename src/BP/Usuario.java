package BP;

import java.util.HashSet;
import java.util.List;

public class Usuario extends Proponente {
	
	private static final long serialVersionUID = 1011;

	
	private String NIF;
	private String nombre;
	private String contrase�a;
	private int id;
	private EstadoUsuario estado;
	
	//Notificaiones
	private Notificacion nRechazoRegistro;
	private Notificacion nBloqueoDeAdmin;
	private List<Notificacion> nSuscripcionEstadoProyecto;
	private List<Notificacion> nRechazoProyectoProponente;
	

	//private HashSet<Proyecto> proyectosPropuestos;

	
	

	
	
	
	/*Constructor*/
	public Usuario(String nif, String nomb, String contra, int i, EstadoUsuario est) {
		NIF = nif; nombre = nomb; 
		setContrase�a(contra); 
		id = i;
		setEstado(est);
		//this.proyectosPropuestos = new HashSet<Proyecto>();
	}
	
	
	
	//Setters de estado del Usuario
	
	public void bloquear(Notificacion nBloqueoDeAdmin) {
		if(getEstado() == EstadoUsuario.OPERATIVO) {
			setEstado(EstadoUsuario.BLOQUEADO);
			this.nBloqueoDeAdmin = nBloqueoDeAdmin;
		}
	}
	
	public void desbloquear() {
		if(getEstado() == EstadoUsuario.BLOQUEADO) {
			setEstado(EstadoUsuario.OPERATIVO);
		}
	}
	
	public Boolean aprobar() {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.OPERATIVO);
			this.nRechazoRegistro = null;
			return true;
		}
		return false;
	}
	
	public Boolean rechazar(String motivo) {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.RECHAZADO);
			this.nBloqueoDeAdmin = new Notificacion("Solicitud de Registro Rechazada","El administrador ha rechazado tu solicitud de alta en el "
					+ "sistema debido a:" + motivo);
			return true;
		}
		return false;
	}
	
	
	
	public EstadoUsuario getEstado() {
		return this.estado;
	}
	public void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
	



	
	public void suscribirseAProyecto(Proyecto p) {
		p.suscribirseANotificacionesDeProyecto( this);
	}
	
	
	//funcion llamada desde proyecto!
	public void anniadirNotificacionDeProyecto(Notificacion n) {
		this.nSuscripcionEstadoProyecto.add(n);
	}
	
	

	
	
	
	
	
	
	

	
	
	
	/****** Getters y Setters de Campos de Usuario *******/

	public String getNIF() {
		return NIF;
	}



	public String getNombre() {
		return nombre;
	}



	public String getContrase�a() {
		return contrase�a;
	}



	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}



	public int getId() {
		return id;
	}



	public Notificacion getnRechazoRegistro() {
		return nRechazoRegistro;
	}



	public Notificacion getnBloqueoDeAdmin() {
		return nBloqueoDeAdmin;
	}



	public List<Notificacion> getnSuscripcionEstadoProyecto() {
		return nSuscripcionEstadoProyecto;
	}



	public List<Notificacion> getnRechazoProyectoProponente() {
		return nRechazoProyectoProponente;
	}
	
	
	
}
