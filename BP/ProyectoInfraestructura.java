package BP;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;

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
			File sourceCroquis = new File(croquisPath);

			this.imgPath = "img/" + nombre + "_" + this.uniqueID + "_img";
			this.imgPath = "img/" + nombre + "_" + this.uniqueID + "_croquis";

			
	        File dest1 = new File(this.imgPath);
	        File dest2 = new File(this.croquisPath);

	        
			Files.copy(sourceImg.toPath(), dest1.toPath());
			Files.copy(sourceCroquis.toPath(), dest2.toPath());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
