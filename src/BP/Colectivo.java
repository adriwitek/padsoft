package BP;

import java.util.Collections;

/** 
* 
* @author Adián Rubio adrian.rubiop@estudiante.uam.es, Guillermo Solla guillermo.solla@estudiante.uam.es 
* y Diego Tapia diego.tapias@estudiante.uam.es
*/

import java.util.HashSet;
import java.util.Objects;

public class Colectivo extends Proponente{
	private String nombre;
	private Usuario usuarioRepresentanteDeColectivo;
	private Colectivo colectivoPadre;
	private HashSet<Usuario> participantes;
	private HashSet<Colectivo> subcolectivos;
	
	 /**
	  * Constructor, con los datos a implementar
	  * @param nombre que asignar al colectivo 
	  * @param uRepresentante para asignar al usuario que ha creado el colectivo
	  * @param colectivoPadre para asignar al colectivo del que es subcolectivo (en el caso de serlo)
	  */
	
	public Colectivo(Usuario uRepresentante, String nombre,Colectivo colectivoPadre) {
		if(nombre.isEmpty()|| Objects.isNull(uRepresentante)  ||  Objects.isNull(nombre) ) {
				throw new IllegalArgumentException("Debes de introducir los datos validos");
			}
		
		this.nombre = nombre;
		usuarioRepresentanteDeColectivo = uRepresentante;
		this.colectivoPadre = colectivoPadre;
		participantes = new HashSet<>();
		subcolectivos = new HashSet<>();

	}

	
	
	
	
	
	//TODO
	
	
	/**
	 * 
	 * @param nombre
	 * @return the c
	 */
	public Colectivo crearSubcolectivo(String nombre) {
		
		Colectivo c = new Colectivo(this.usuarioRepresentanteDeColectivo, nombre,this);		
		this.subcolectivos.add(c);
		return c;
	}
	
	/**
	 * 
	 * @param u
	 * @return 
	 */
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
	
	/**
	 * 
	 * @param u
	 */
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
	/**
	 * 
	 * @param u
	 * @return 
	 */
	public Boolean suscribirseColectivo(Usuario u) {
		
		if(getIsUsuarioEnColectivoSubcolectivo( u)) {
			return false;
		}else {
			this.participantes.add(u);
			return true;
		}
		
	}
	
	
	/**
	 * 
	 * @param c
	 * @return the afinidad
	 */
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
	 * 
	 * @return the usuarioRepresentanteDeColectivo
	 */
	public Usuario getUsuarioRepresentanteDeColectivo() {
		return usuarioRepresentanteDeColectivo;
	}


	

	/**
	 * @return the participantes
	 */
	public HashSet<Usuario> getParticipantes() {
		return participantes;
	}
	
	/**
	 * 
	 * @return the colectivoPadre
	 */
	public Colectivo getColectivoPadre() {
		return this.colectivoPadre;
	}






	public HashSet<Colectivo> getSubcolectivos() {
		return (HashSet<Colectivo>) Collections.unmodifiableSet(this.subcolectivos);
	}
	

}

