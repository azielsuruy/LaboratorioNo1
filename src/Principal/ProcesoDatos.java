

package Principal;

import java.util.ArrayList;


public class ProcesoDatos {
    
    private ArrayList<Object> a= new ArrayList<Object>();
    
    public ProcesoDatos(){
        
    }
    
    public ProcesoDatos(ArrayList<Object> a){
        
        this.a=a;
    }
    
    
    //Crear Archivo 
    
    public void crearRegistro(Datos_Personales p){
        this.a.add(p);
    }
    
    //modificar registro
    
    public void modificarRegistro(int i,Datos_Personales p){
        
        this.a.set(i, p);
        
    }
    
    //eliminar_registro
    
    public void eliminarRegistros(int i){
        
        this.a.remove(i);
    }
    
    //Obtener los datos_personales
    
    public Datos_Personales obtenerRegistro(int i){
        
        return (Datos_Personales)a.get(i);
        
    }
    
    //Determine la cantidad de registros encontrados
    
    public int cantidadRegistros(){
        
       return this.a.size();
    }
    
    //Buscardor 
    
    public int buscarCodigo(int codigo){
        
        for(int i=0; i<cantidadRegistros(); i++){
        
            if(codigo==obtenerRegistro(i).getCodigo())return i;
    }
        return -1;
    }
    
    
}