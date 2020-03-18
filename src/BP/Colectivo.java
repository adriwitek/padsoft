package BP;

import java.util.HashSet;

public class Colectivo extends Proponente{
	private String nombre;
	private Usuario usuarioRepresentanteDeColectivo;
	private String idColectivo;
	private HashSet<Usuario> participantes;
	
	public Colectivo(Usuario uRepresentante, String nombre, String identificador) {
		
		this.nombre = nombre;
		usuarioRepresentanteDeColectivo = uRepresentante;
		idColectivo = identificador;
		participantes = new HashSet<>();
	}

	
	
	
	
	
	
	
	
	
	
	/**** getters y setter generarles    ****/
	
	
	
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the usuarioRepresentanteDeColectivo
	 */
	public Usuario getUsuarioRepresentanteDeColectivo() {
		return usuarioRepresentanteDeColectivo;
	}


	/**
	 * @return the idColectivo
	 */
	public String getIdColectivo() {
		return idColectivo;
	}



	/**
	 * @return the participantes
	 */
	public HashSet<Usuario> getParticipantes() {
		return participantes;
	}
	
	
	
	
	
	
	
	
	
	
}
