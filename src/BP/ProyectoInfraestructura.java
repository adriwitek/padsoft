package BP;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;

import es.uam.eps.sadp.grants.GrantRequest;

public class ProyectoInfraestructura extends Proyecto {
	private String croquisPath;
	private String imgPath;
	private HashSet<String> distritos;
	public ProyectoInfraestructura(Proponente p, Usuario uCreador,String nombre, String descrL, String descC , double cost,String croquis ,String imgPath,HashSet<String> distritos) {
		
		super(p, uCreador, nombre, descrL, descC, cost);
		this.distritos = new HashSet<String>();
		
		//Filtramos los distritos no admitidos
		for(String d :distritos) {
			if(Aplicacion.getInstancia(null, null, null).getDistritos().contains(d)) {
				this.distritos.add(d); 
			}
		}
		
		
		try {
			File sourceImg = new File(imgPath);
			File sourceCroquis = new File(croquis);

			this.imgPath = "img/" +   this.uniqueID + "img" + sourceImg.getName();
			this.croquisPath = "img/" +  this.uniqueID + "_croquis" +sourceCroquis.getName();

			
	        File dest1 = new File(this.imgPath);
	        File dest2 = new File(this.croquisPath);

	        
			Files.copy(sourceImg.toPath(), dest1.toPath());
			Files.copy(sourceCroquis.toPath(), dest2.toPath());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Boolean addDistrito(String d) {
		if(Aplicacion.getInstancia(null, null, null).getDistritos().contains(d)) {
			this.distritos.add(d); 
			return true;
		}
		return false;
	}
	
	
	

	public String getExtraData() {
		String cadena = "";
		if(this.distritos.size() == 1) {
			
			cadena= "El distrito del proyecto es" + this.distritos.toString();
		}else {
			cadena= "Los distritos a los que pertenece el proyecto son" + this.distritos.toString();
		}
		
		return cadena;
	}
	
	
	public GrantRequest.ProjectKind getProjectKind(){
		return GrantRequest.ProjectKind.Infrastructure;
	}
}
