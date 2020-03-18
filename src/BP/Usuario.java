package BP;

import java.util.HashSet;
import java.util.List;

public class Usuario extends Proponente {
	
	
	
	
	private String NIF;
	private String nombre;
	private String contrase�a;
	private int id;
	private EstadoUsuario estado;
	private HashSet<Colectivo> conjuntoColectivosSuscrito;
	
	private Notificacion nPendienteRegistro;
	private Notificacion nBloqueoDeAdmin;
	
	private List<Notificacion> nSuscripcionEstadoProyecto;
	
	private HashSet<Proyecto> proyectosPropuestos;
	private List<Notificacion> nRechazoProyectoProponente;

	
	

	
	
	
	/*Constructor*/
	public Usuario(String nif, String nomb, String contra, int i, EstadoUsuario est) {
		NIF = nif; nombre = nomb; setContrase�a(contra); id = i; setEstado(est);
		this.conjuntoColectivosSuscrito = new HashSet<Colectivo>();
		this.nPendienteRegistro = new Notificacion("Pendiente de Registro","Debes de esperar a la validacion del administrador");
		this.proyectosPropuestos = new HashSet<Proyecto>();
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
	
	public void aprobar() {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.OPERATIVO);
			this.nPendienteRegistro = null;
		}
	}
	
	public void rechazar() {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.RECHAZADO);
		}
	}
	
	
	
	public EstadoUsuario getEstado() {
		return this.estado;
	}
	public void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	
	
	
	
	
	public HashSet<Colectivo> getListaColectivosSuscrito() {
		return this.conjuntoColectivosSuscrito;
	}
	
	
	public void suscribirseColectivo(Colectivo c) {
		this.conjuntoColectivosSuscrito.add(c);
	}


	
	public void suscribirseAProyecto(Proyecto p) {
		p.suscribirseANotificacionesDeProyecto( this);
	}
	
	
	//funcion llamada desde proyecto!
	public void anniadirNotificacionDeProyecto(Notificacion n) {
		this.nSuscripcionEstadoProyecto.add(n);
	}
	
	

	public Boolean anniadirProyectoPropuesto(Proyecto p) {
		
		if(this.proyectosPropuestos.contains(p)) {
			return false;
		}else {
			this.proyectosPropuestos.add(p);
			return true;
		}

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



	public Notificacion getnPendienteRegistro() {
		return nPendienteRegistro;
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
