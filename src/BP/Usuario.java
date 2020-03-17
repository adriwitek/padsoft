package BP;

public class Usuario extends Proponente {
	private String NIF;
	private String nombre;
	private String contraseña;
	private int id;
	private EstadoUsuario estado;
	public Usuario(String nif, String nomb, String contra, int i, EstadoUsuario est) {
		NIF = nif; nombre = nomb; contraseña = contra; id = i; setEstado(est);
	}
	public void bloquear() {
		if(getEstado() == EstadoUsuario.OPERATIVO) {
			setEstado(EstadoUsuario.BLOQUEADO);
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
		}
	}
	
	public void rechazar() {
		if(getEstado() == EstadoUsuario.PENDIENTE) {
			setEstado(EstadoUsuario.RECHAZADO);
		}
	}
	
	public EstadoUsuario getEstado() {
		return estado;
	}
	public void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
}
