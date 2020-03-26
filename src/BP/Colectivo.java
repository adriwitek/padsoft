package BP;

import java.util.HashSet;

public class Colectivo extends Proponente{
	private String nombre;
	private Usuario usuarioRepresentanteDeColectivo;
	private Integer idColectivo;
	private Colectivo colectivoPadre;
	private HashSet<Usuario> participantes;
	private HashSet<Colectivo> subcolectivos;
	
	public Colectivo(Usuario uRepresentante, String nombre,Colectivo colectivoPadre) {
		
		this.nombre = nombre;
		usuarioRepresentanteDeColectivo = uRepresentante;
		idColectivo = Aplicacion.getNewColectivoUniqueId();
		this.colectivoPadre = colectivoPadre;
		participantes = new HashSet<>();
		subcolectivos = new HashSet<>();

	}

	
	
	
	
	
	//TODO
	
	public Colectivo crearSubcolectivo(String nombre) {
		
		Colectivo c = new Colectivo(this.usuarioRepresentanteDeColectivo, nombre,this);		
		this.subcolectivos.add(c);
		return c;
	}
	
	
	public Boolean getIsUsuarioEnColectivoSubcolectivo(Usuario u) {
		
		if(this.participantes.contains(u)){
			return true;
		}else {
			for(@SuppressWarnings("unused") Colectivo c: this.subcolectivos) {
				 getIsUsuarioEnColectivoSubcolectivo(u);
			}
		}
		return false;
	}
	
	
	public void eliminarUsuarioDeColectivoSubcolectivos(Usuario u) {
		if(getIsUsuarioEnColectivoSubcolectivo( u)) {
			
			if(this.participantes.contains(u)){
				this.participantes.remove(u);
				return ;
			}else {
				for(@SuppressWarnings("unused") Colectivo c: this.subcolectivos) {
					eliminarUsuarioDeColectivoSubcolectivos( u);
				}
			}
			
			return;
		}
	}
	
	public Boolean suscribirseColectivo(Usuario u) {
		
		if(getIsUsuarioEnColectivoSubcolectivo( u)) {
			return false;
		}else {
			this.participantes.add(u);
			return true;
		}
		
	}
	
	
	
	public double obtenerAfinidad(Colectivo c) {
		double afinidad =0;
		HashSet<Usuario> participantesContenidos = new HashSet<Usuario>();
		
		participantesContenidos.addAll(this.participantes);
		for(@SuppressWarnings("unused") Colectivo subC: this.subcolectivos) {
			participantesContenidos.addAll(subC.participantes);

		}

		
		for(Usuario participante: participantesContenidos) {
			if(c.getIsUsuarioEnColectivoSubcolectivo(participante)) {
				afinidad++;
				
			}
		}
		
		return afinidad;
		
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
	public Integer getIdColectivo() {
		return idColectivo;
	}



	/**
	 * @return the participantes
	 */
	public HashSet<Usuario> getParticipantes() {
		return participantes;
	}
	

	public Colectivo getColectivoPadre() {
		return this.colectivoPadre;
	}
	

}
