
package Principal;

import Complementario.IngresoTabla;
import Complementario.Limpiar_Archivo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class JDDatosPersonales extends javax.swing.JDialog {

    Limpiar_Archivo l= new Limpiar_Archivo();
    
    private String ruta_txt="umg.txt";
    
    Datos_Personales p;
    ProcesoDatos pd;
    int clic_tabla;
    
    
    public JDDatosPersonales(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pd=new ProcesoDatos();
        
        try{
            mostrar_txt();
            listarRegistro();
            
            
        }catch(Exception ex){
            
            mensaje("No existe el archivo txt");
        }
    }
    
    
    public void limpiar(){
        
        txtcodigo.setText("");
        txtnombres.setText("");
        txtapellidos.setText("");
        txtdireccion.setText("");
        txtdpi.setText("");
        txtedad.setText("");
        txtsexo.setText("");
        txttelefono.setText("");
    }
    
    public void mostrar_txt(){
        
        File ruta= new File(ruta_txt);
        
        try{
            FileReader fr= new FileReader(ruta);
            BufferedReader bu= new BufferedReader(fr);
            
            String linea=null;
            
           while((linea=bu.readLine())!=null){
               
               StringTokenizer st= new StringTokenizer(linea,",");
               
               p= new Datos_Personales();
               p.setCodigo(Integer.parseInt(st.nextToken()));
               p.setNombre(st.nextToken());
               p.setApellidos(st.nextToken());
               p.setDireccion(st.nextToken());
               p.setDpi(st.nextToken());
               p.setEdad(st.nextToken());
               p.setSexo(st.nextToken());
               p.setTelefono(st.nextToken());
               
               pd.crearRegistro(p);
               
           }
           
           bu.close();
           
        }catch(Exception e){
            
            mensaje("Error al abrir archivo"+e.getMessage());
            System.out.println(e.getMessage());
        }
       
    }
    
    public void mensaje(String texto){
    
        JOptionPane.showMessageDialog(null, texto);
}
    
    public void guardar_txt(){
        
        FileWriter fw;
        PrintWriter pw;
        
        try{
            fw= new FileWriter(ruta_txt);
            pw=new PrintWriter(fw);
            
            for(int i=0; i<pd.cantidadRegistros(); i++){
                
                p=pd.obtenerRegistro(i);
                pw.println(String.valueOf(p.getCodigo()+ "," + p.getNombre()+","+ p.getApellidos()+"," + p.getDireccion()+"," + p.getDpi()+"," + p.getEdad()+","+ p.getSexo()+","+p.getTelefono() ));
            }
            
            pw.close();
            
        }catch(Exception e){
            
          mensaje("Error al abrir el Archivo" + e.getMessage());
          System.out.println(e.getMessage());
        }
        
    }
    
    
    public void ingresarRegistro(File ruta){
        
        try{
            if(leerCodigo()==-666)mensaje("Ingresar codigo entero");
            else if (leerNombre()==null)mensaje("Ingresar Nombre");
            else if (leerApellido()==null) mensaje("Ingresar Apellido");
            else if (leerDireccion()==null)mensaje("Ingresar Direccion");
            else if (leerDpi()==null)mensaje("Ingresa DPI");
            else if (leerEdad()==null)mensaje ("Ingresa tu Edad");
            else if (leerSexo()==null)mensaje("Ingres tu Sexo");
            else if (leerTelefono()==null) mensaje ("Ingresa tu Telefono");
            
            else{
                p= new Datos_Personales(leerCodigo(), leerNombre(), leerApellido(), leerDireccion(), leerDpi(), leerEdad(), leerSexo(),(String) leerTelefono());
                
                if(pd.buscarCodigo(p.getCodigo())!=-1)mensaje("Este codigo ya existe");
                
                else pd.crearRegistro(p);
                
                guardar_txt();
                listarRegistro();
                l.Limpiar_Archivo(jPanel1);
            }
            
        }catch(Exception ex){
            
            mensaje(ex.getMessage());
        }
    }
    
    
    
    public void modificarRegistro(File ruta){
        
        try{
            
            if(leerCodigo()==-666)mensaje("Ingresar Codigo entero");
            else if(leerNombre()==null)mensaje("Ingresar Nobmre");
            else if(leerApellido()==null)mensaje("Ingresar Apellido");
            else if(leerDireccion()==null)mensaje("Ingresar Direccion");
            else if(leerDpi()==null)mensaje("Ingresar Dpi");
            else if(leerEdad()==null)mensaje("Ingresar Edad");
            else if(leerSexo()==null)mensaje("Ingresar Sexo");
            else if(leerTelefono()==null)mensaje("Ingresar Telefono");
            
            else{
                int codigo= pd.buscarCodigo(leerCodigo());
                
                p= new Datos_Personales(leerCodigo(), leerNombre(), leerApellido(), leerDireccion(), leerDpi(), leerEdad(), leerSexo(), (String)leerTelefono());
           
                if(codigo==-1)pd.crearRegistro(p);
                else pd.modificarRegistro(codigo, p);
                
                guardar_txt();
                listarRegistro();
                l.Limpiar_Archivo(jPanel1);
            }
            
        }catch(Exception e){
            
            mensaje(e.getMessage());
        }
        
    } 
    
    
    public void eliminarRegistro(){
        
        try{
            
            if(leerCodigo()==-666)mensaje("Ingresa codigo entero");
            
            else{
                
                int codigo=pd.buscarCodigo(leerCodigo());
                if(codigo==-1)mensaje("Codigo no existe");
                
                else{
                    
                    int s=JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar a la persona","Si/no",0);
                    
                    if(s==0){
                        pd.eliminarRegistros(codigo);
                        guardar_txt();
                        listarRegistro();
                        l.Limpiar_Archivo(jPanel1);
                    }
                }
            }
        }catch(Exception ex){
            
            mensaje(ex.getMessage());
        }
    }
    
    
    public void listarRegistro(){
        
        DefaultTableModel dt= new DefaultTableModel(){
            
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Apellido");
        dt.addColumn("Direccion");
        dt.addColumn("Dpi");
        dt.addColumn("Edad");
        dt.addColumn("Sexo");
        dt.addColumn("Telefono");
        
        tbldatos.setDefaultRenderer(Object.class, new IngresoTabla());
        
        Object fila[]= new Object[dt.getColumnCount()];
        
        for(int i=0; i<pd.cantidadRegistros(); i++){
            p=pd.obtenerRegistro(i);
            fila[0]=p.getCodigo();
            fila[1]=p.getNombre();
            fila[2]=p.getApellidos();
            fila[3]=p.getDireccion();
            fila[4]=p.getDpi();
            fila[5]=p.getEdad();
            fila[6]=p.getSexo();
            fila[7]=p.getTelefono();
            
            dt.addRow(fila);
        }
        tbldatos.setModel(dt);
        tbldatos.setRowHeight(60);
    }
    
    public int leerCodigo(){
        
        try{
            
            int codigo=Integer.parseInt(txtcodigo.getText().trim());
            return codigo;
        }catch(Exception ex){
            
            return -666;
        }
    }
    
    public String leerNombre(){
        
        try{
            
            String nombre=txtnombres.getText().trim().replace(" ", "_");
            return nombre;
        }catch(Exception ex){
            
            return null;
        }
    }
    
    public String leerApellido(){
        
        try{
           
        String apellido=txtapellidos.getText().trim().replace(" ", "_");
        return apellido;
        
        }catch(Exception ex){
            
            return null;
        }
    }
    
    public String leerDireccion(){
        try{
        String direccion=txtdireccion.getText().trim().replace(" ", "_");
        return direccion;
        
    }catch(Exception ex){
        return null;
    }
    }

    public String leerDpi(){
        
        try{
            
            String dpi= txtdpi.getText().trim().replace(" ", "_");
            
            return dpi;
            
        }catch(Exception e){
        
            return null;
    }
    }
    
    
    
    public String leerEdad(){
        
        try{
            
            String edad=txtedad.getText().trim().replace(" ", "_");
            return edad;
            
        }catch(Exception ex){
            
            return null;
        }
    }
    
    
    public String leerSexo(){
        
        try{
            String sexo= txtsexo.getText().trim().replace(" ", "_");
            
            return sexo;
            
        }catch(Exception e){
            return null;
            
        }
    }
    
    public Object leerTelefono(){
        
        try{
            
            String telefono=txttelefono.getText().trim().replace(" ", "_");
            
            return telefono;
            
        }catch(Exception ex){
            return null;
        }
    }
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtcodigo = new javax.swing.JTextField();
        txtnombres = new javax.swing.JTextField();
        txtapellidos = new javax.swing.JTextField();
        txtdireccion = new javax.swing.JTextField();
        txtdpi = new javax.swing.JTextField();
        txtedad = new javax.swing.JTextField();
        txtsexo = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnguardar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldatos = new javax.swing.JTable();
        lblruta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Codigo");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nombres");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Apellidos");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Direccion");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("DPI");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Edad");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Sexo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
            .addComponent(txtnombres)
            .addComponent(txtapellidos)
            .addComponent(txtdireccion)
            .addComponent(txtdpi)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtsexo, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(txtedad, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(txttelefono))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtnombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtdpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(txtedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtsexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Telefono ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(jLabel1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel3)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(1, 1, 1)
                                                            .addComponent(jLabel2))
                                                        .addComponent(jLabel5)))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(64, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(104, 104, 104)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Ingreso Datos Personales ");

        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnguardar)
                .addGap(43, 43, 43)
                .addComponent(btnmodificar)
                .addGap(33, 33, 33)
                .addComponent(jButton3)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar)
                    .addComponent(btnmodificar)
                    .addComponent(jButton3))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tbldatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbldatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbldatos);

        lblruta.setText("ruta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblruta)
                        .addGap(150, 150, 150)
                        .addComponent(jLabel8))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(86, 86, 86)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(lblruta))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
       
        File ruta= new File(lblruta.getText());
        
        this.ingresarRegistro(ruta);
        
        limpiar();
        
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        
        File ruta= new File(lblruta.getText());
        
        this.modificarRegistro(ruta);
        
        limpiar();
        
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        
        this.eliminarRegistro();
        
        limpiar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tbldatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldatosMouseClicked
        
        clic_tabla= tbldatos.rowAtPoint(evt.getPoint());
        
        int codigo= (int)tbldatos.getValueAt(clic_tabla, 0);
        String nombre=""+ tbldatos.getValueAt(clic_tabla,1);
        String apellido="" + tbldatos.getValueAt(clic_tabla, 2);
        String direccion="" + tbldatos.getValueAt(clic_tabla, 3);
        String dpi=""+ tbldatos.getValueAt(clic_tabla, 4);
        String edad="" + tbldatos.getValueAt(clic_tabla, 5);
        String sexo="" + tbldatos.getValueAt(clic_tabla, 6);
        String telefono="" + tbldatos.getValueAt(clic_tabla, 7);
        
        txtcodigo.setText(String.valueOf(codigo));
        txtnombres.setText(nombre);
        txtapellidos.setText(apellido);
        txtdireccion.setText(direccion);
        txtdpi.setText(dpi);
        txtedad.setText(edad);
        txtsexo.setText(sexo);
        txttelefono.setText(telefono);
        
        try{
            
            JLabel lbl= (JLabel)tbldatos.getValueAt(clic_tabla, 8);
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_tbldatosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDDatosPersonales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDDatosPersonales dialog = new JDDatosPersonales(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblruta;
    private javax.swing.JTable tbldatos;
    private javax.swing.JTextField txtapellidos;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtdpi;
    private javax.swing.JTextField txtedad;
    private javax.swing.JTextField txtnombres;
    private javax.swing.JTextField txtsexo;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
