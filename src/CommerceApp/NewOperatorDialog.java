/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommerceApp;

import Adapters.JDBCAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objects.Validation;
import util.FileProcess;
import util.Operation;

/**
 *
 * @author DELL
 */
public class NewOperatorDialog extends OperatorDialog implements Validation{
    private  Operation operation;
    final private String sql1 = "SELECT ID FROM ";
    final private String sql2 = " ORDER BY ID DESC LIMIT 1";
    private String sql;
    private JDBCAdapter table;
    /**
     * Creates new form NewOperatorDialog
     */
    public NewOperatorDialog(JFrame frm, Operation op) {
        super(frm,op, FileProcess.CREATE);
        operation = op;
        initComponents();
        setIdTextField();
        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    okButtonActionPerformed();

            }
        });
        okButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER){
                    okButtonActionPerformed();
                }
            }
        });
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NewOperatorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewOperatorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewOperatorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewOperatorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewOperatorDialog dialog = new NewOperatorDialog(null,
                        Operation.CUSTOMER);
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

    private void setIdTextField() {
        table = JDBCAdapter.connect();
        String SQL;
        SQL = sql1 + operation.getTableName() + sql2;
        table.executeQuery(SQL);
        int result;
        if (table.getRowCount()>0){
            result = (int)table.getValueAt(0, 0) + 1 ;
        }else{
            result =1;
        }
        id.setText(Integer.toString(result));
    }
       
    private void okButtonActionPerformed(){
        String desig = designation.getText();
        if (desig.equals("") || desig.equals(" ")){
            JOptionPane.showMessageDialog(this, "Ce champs doit être renseigner!!!");
        }else{
            record();
            dispose();
        }
    }
    @Override
    public void record() {
        //Verify wether the designation exist in table or not
        // if it exists add some extra characters 
        // and submit it to the database
        String desig = designation.getText();
        String SQL = "SELECT ID FROM ";
        
        sql = "INSERT INTO ";
        String f = "";
        switch(operation){
            case PROVIDER:
                f += "four ";
            break;
            case CUSTOMER:
                f += "client ";
            break;
        }
        sql += f;
        SQL += f + "WHERE NOM ='" + desig;
        JDBCAdapter search = JDBCAdapter.connect();
        search.executeQuery(SQL);
        if (search.getRowCount() <= 0){ //there is no such a Name
            
            int choice = JOptionPane.showConfirmDialog(this,
                            "Voulez vous enregistrer ");
            if (choice == 0){
                createNewOperator();
            }else{
                dispose();
            }
        } 
    }
    public void createNewOperator(){    
            //verify witch field are not impty
            //create new operator in table
            // with the verified not empty fields        
            String columnNames = "(NOM";
            String values = "('" + designation.getText() + "'";
            if (!adresse.getText().equals("") && !adresse.getText().equals(" ")){
                columnNames += ",ADR";
                values += ",'" + adresse.getText() + "'";
            }
            if (!wilaya.getText().equals("") && ! wilaya.getText().equals(" ")){
                columnNames += ",WILAYA";
                values += ",'" + wilaya.getText() + "'";
            }
            if (!nrc.getText().equals("") && !nrc.getText().equals(" ")){
                columnNames += ",NRC";
                values += ",'" + nrc.getText() + "'";
            }
            if (!nfi.getText().equals("") && !nfi.getText().equals(" ")){
                columnNames += ",NFI";
                values += ",'" + nfi.getText() + "'";
            }
            if (!nar.getText().equals("") && !nar.getText().equals(" ")){
                columnNames += ",NAR";
                values += ",'" + nar.getText() +"'";
            }
            if (!fax.getText().equals("") && !fax.getText().equals(" ")){
                columnNames += ",FAX";
                values += ",'" + fax.getText() + "'";            
            }
            if (!tel1.getText().equals("") && !tel1.getText().equals(" ")){
                columnNames += ",TEL1";
                values += ",'" + tel1.getText() + "'";
            }
            if (!tel2.getText().equals("") && !tel2.getText().equals(" ")){
                columnNames += ",TEL2";
                values += ",'" + tel2.getText() + "'";
            }
            if (!tel3.getText().equals("") && !tel3.getText().equals(" ")){
                columnNames += ",TEL3";
                values += ",'" + tel3.getText() + "'";
            }
            if (!email.getText().equals("") && !email.getText().equals(" ")){
                columnNames += ",EMAIL";
                values += ",'" + email.getText() + "'";
            }
            if (!obs.getText().equals("") && !obs.getText().equals(" ")){
                columnNames += ",OBS";
                values += ",'" + obs.getText() + "'";
            }
            sql += columnNames + ") VALUES " + values + ")";
            JOptionPane.showMessageDialog(this, sql);

            JDBCAdapter table = JDBCAdapter.connect();
            table.executeUpdate(sql);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}