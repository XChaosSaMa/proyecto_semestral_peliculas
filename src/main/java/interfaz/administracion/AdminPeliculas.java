/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.administracion;

import java.io.*;
import java.util.Formatter;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author PC
 */
public class AdminPeliculas extends javax.swing.JFrame{
    
    String barra = File.separator;
    String ubicacion = System.getProperty("user.dir")+barra+"DB"+barra+"Peliculas"+barra;
    String reportes = System.getProperty("user.dir")+barra+"Reportes"+barra;
    
    
    File contenedor = new File(ubicacion);
    File [] registros = contenedor.listFiles();
    
    String[] titulos = {"ID","Nombre","Formato","Precio","Stock"};
    DefaultTableModel dtm = new DefaultTableModel(null, titulos);
    
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
    
    private void RegTabla(){
        for(int i=0; i<registros.length; i++){
            
            File url = new File(ubicacion+registros[i].getName());
            try{
                FileInputStream fis = new FileInputStream(url);
                Properties mostrar = new Properties();
                mostrar.load(fis);
                
                String filas[] = {registros[i].getName().replace(".dat", ""),
                mostrar.getProperty("Nombre"),mostrar.getProperty("Formato"),
                mostrar.getProperty("Precio"),mostrar.getProperty("Stock")
                };
                
                dtm.addRow(filas);
            }
            catch(Exception e){System.out.print("");}
        }
        jTable2.setModel(dtm);
        jTable2.setRowSorter(sorter);
    }
    
    private void ActualizarTabla(){
        registros = contenedor.listFiles();
        dtm.setRowCount(0);
        RegTabla();
    }
    
    private void Crear(){
        String archivo = jTextField4.getText()+".dat";
        File crear_ubicacion = new File(ubicacion);
        File crear_archivo = new File(ubicacion+archivo);
        if(jTextField4.getText()==""){
            JOptionPane.showMessageDialog(rootPane,"No hay ID");
        }else{
            
            try{
                
            if(crear_archivo.exists()){
                JOptionPane.showMessageDialog(rootPane,"El registro ya existe");
            }else{
                crear_ubicacion.mkdirs();
                Formatter crea = new Formatter(ubicacion+archivo);
                crea.format("%s\r\n%s\r\n%s\r\n%s\r\n%s", "ID="+jTextField4.getText(),
                "Nombre="+jTextField2.getText(),
                "Formato="+jTextField3.getText(),
                "Precio="+jTextField5.getText(),
                "Stock="+jTextField1.getText()
                );
                crea.close();
                JOptionPane.showMessageDialog(rootPane,"Película ingresada con exito");
                jComboBox1.removeAllItems();
                registros = contenedor.listFiles();
                MostrarCombo();
                ActualizarTabla();
            }
            
            }catch(Exception e){
                ActualizarTabla();
            }
         
        }
    }
    
    public void Mostrar(){
        File url = new File(ubicacion+jTextField4.getText()+".dat");
        
         if(jTextField4.equals("")){
                JOptionPane.showMessageDialog(rootPane,"Indique el ID para mostrar");
            }else{
             
             if(url.exists()){
                 
                 try{
                     FileInputStream fis = new FileInputStream(url);
                     Properties mostrar = new Properties();
                     mostrar.load(fis);
                     
                     jTextField2.setText(mostrar.getProperty("Nombre"));
                     jTextField3.setText(mostrar.getProperty("Formato"));
                     jTextField5.setText(mostrar.getProperty("Precio"));
                     jTextField1.setText(mostrar.getProperty("Stock"));
                 }catch(Exception e){}
                 
             }else{}
             
         }
    }
    
    private void Editar(){
        File url = new File(ubicacion+jTextField4.getText()+".dat");
        
        if(jTextField4.getText()==""){
            JOptionPane.showMessageDialog(rootPane,"Indique el registro a editar");
        }
        else{
            
            if(url.exists()){
                try{
                    FileWriter permite_escrito = new FileWriter(ubicacion+jTextField4.getText()+".dat");

                    String id = "ID=";
                    String nombre = "Nombre=";
                    String formato ="Formato=";
                    String precio ="Precio=";
                    String stock ="Stock=";

                    PrintWriter guardar = new PrintWriter(permite_escrito);
                    guardar.println(id+jTextField4.getText());
                    guardar.println(nombre+jTextField2.getText());
                    guardar.println(formato+jTextField3.getText());
                    guardar.println(precio+jTextField5.getText());
                    guardar.println(stock+jTextField1.getText());
                    permite_escrito.close();
                    JOptionPane.showMessageDialog(rootPane,"Película editada correctamente");
                    ActualizarTabla();
                }
                catch(Exception e){
                 JOptionPane.showMessageDialog(rootPane,"Error: "+e);
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"El registro no existe");
            }

        }
    }
    
    private void Borrar(){
        File url = new File(ubicacion+jTextField4.getText()+".dat");
        String botones[] = {"Eliminar","Cancelar"}; 
        
        if(jTextField4.equals("")){
            JOptionPane.showMessageDialog(rootPane,"Indique el registro a eliminar");
        }
        else{
        
            if(url.exists()){
                
                try{
                    FileInputStream cerrar = new FileInputStream(url);
                    cerrar.close();
                    System.gc();
                    
                    int seguro = JOptionPane.showOptionDialog(rootPane,
                        "¿Está seguro de que quiere eliminar la película?"+jTextField4.getText(),
                        "Eliminar", 0, 0, null, botones, null);
                    
                    if(seguro == JOptionPane.YES_OPTION){
                        url.delete();
                        JOptionPane.showMessageDialog(rootPane,"Película eliminada con éxito");
                        jComboBox1.removeItem(jTextField4.getText());
                        ActualizarTabla();
                    }
                    if(seguro == JOptionPane.NO_OPTION){
                    }
                    
                }catch(Exception e){}
                
            }
            else{
                JOptionPane.showMessageDialog(rootPane,"Esa película no existe");
            }
            
        }
    }
    
    private void MostrarCombo(){
        
        for(int i=0;i<registros.length;i++){
            jComboBox1.addItem(registros[i].getName().replace(".dat",""));
        }
        
    }
    
    private void Reporte(){
        try {

            String ruta = reportes+"ReportePelículas.report";
            
            BufferedWriter bfw = new BufferedWriter(new FileWriter(ruta));

            bfw.write("ID | Nombre | Formato | Precio | Stock");
            bfw.newLine();
            
                for (int i = 0 ; i < jTable2.getRowCount(); i++) //realiza un barrido por filas.
                {
                    for(int j = 0 ; j < jTable2.getColumnCount();j++) //realiza un barrido por columnas.
                    {
                        bfw.write((String)(jTable2.getValueAt(i,j)));
                        if (j < jTable2.getColumnCount() -1) { //agrega separador "|" si no es el ultimo elemento de la fila.
                            bfw.write(" | ");
                        }
                    }
                    bfw.newLine(); //inserta nueva linea.
                }

            bfw.close(); //cierra archivo!
            JOptionPane.showMessageDialog(rootPane,"El reporte fué generado con éxito");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane,"ERROR: Ocurrió un problema al generar el reporte");
        }
    }
    
    public AdminPeliculas(){
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        MostrarCombo();
        RegTabla();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(30, 160, 250));

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel1.setText("Películas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jButton1.setText("Atras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Stock:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("ID:");

        jLabel5.setText("Precio:");

        jLabel6.setText("Formato:");

        jButton2.setText("Crear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Editar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Borrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton6.setText("Reporte");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(206, 206, 206))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jButton5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Administrar administrar = new Administrar();
        administrar.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Crear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Editar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Borrar();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String copiar = (String) jComboBox1.getSelectedItem();
        jTextField4.setText(copiar);
        Mostrar();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Reporte();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int selection = jTable2.rowAtPoint(evt.getPoint());
        jTextField4.setText(String.valueOf(jTable2.getValueAt(selection, 0)));
        jTextField2.setText(String.valueOf(jTable2.getValueAt(selection, 1)));
        jTextField3.setText(String.valueOf(jTable2.getValueAt(selection, 2)));
        jTextField1.setText(String.valueOf(jTable2.getValueAt(selection, 3)));
        jTextField5.setText(String.valueOf(jTable2.getValueAt(selection, 4)));
    }//GEN-LAST:event_jTable2MouseClicked

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
            java.util.logging.Logger.getLogger(AdminPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPeliculas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
