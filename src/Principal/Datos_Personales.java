
package Principal;


public class Datos_Personales {
    private int codigo;
    private String nombre,apellidos, direccion,sexo,dpi,edad, telefono;
    
    
    public Datos_Personales(){
        
    }
    
    
    public Datos_Personales(int codigo,String nombre,String apellidos,String direccion,String dpi,String edad,String sexo,String  telefono){
        
        this.codigo=codigo;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.direccion=direccion;
        this.dpi=dpi;
        this.edad=edad;
        this.sexo=sexo;
        this.telefono=telefono;
        
             
    }
    
    //-----------------------Setter_Getter

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
