/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dateisystemerfassung;

import java.awt.event.KeyEvent;

/**
 *
 * @author sunny
 */
public class DateisystemAusgebenUI extends javax.swing.JFrame {

    Dateisystem dS = new Dateisystem();

    /**
     * Creates new form DateisystemAusgeben
     */
    public DateisystemAusgebenUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBeschreibung = new javax.swing.JLabel();
        txtUsereingabe = new javax.swing.JTextField();
        btnSuchen = new javax.swing.JButton();
        lblAusgabe = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAusgabe = new javax.swing.JTextArea();
        btnZurueck = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBeschreibung.setText("Gesuchte(s) Datei oder Verzeichnis eingeben:");

        txtUsereingabe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsereingabeActionPerformed(evt);
            }
        });
        txtUsereingabe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsereingabeKeyPressed(evt);
            }
        });

        btnSuchen.setText("Suchen");
        btnSuchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuchenActionPerformed(evt);
            }
        });

        lblAusgabe.setText("Ausgabe:");

        txtAusgabe.setColumns(20);
        txtAusgabe.setRows(5);
        jScrollPane1.setViewportView(txtAusgabe);

        btnZurueck.setText("Zurück");
        btnZurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZurueckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(txtUsereingabe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBeschreibung)
                            .addComponent(lblAusgabe))
                        .addGap(0, 362, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnSuchen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnZurueck)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBeschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtUsereingabe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuchen)
                    .addComponent(btnZurueck))
                .addGap(18, 18, 18)
                .addComponent(lblAusgabe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuchenActionPerformed
        String userabfrage = txtUsereingabe.getText();
        dS.getAllByName(userabfrage);
        String ausgabe = "";
        for (int i = 0; i < dS.dSsGet.size(); i++) {
            ausgabe = ausgabe.concat(dS.dSsGet.get(i).toString());
            txtAusgabe.setText(ausgabe);
        }
    }//GEN-LAST:event_btnSuchenActionPerformed

    private void txtUsereingabeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsereingabeActionPerformed

    }//GEN-LAST:event_txtUsereingabeActionPerformed

    private void txtUsereingabeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsereingabeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String userabfrage = txtUsereingabe.getText();
            dS.getAllByName(userabfrage);
            String ausgabe = "";
            for (int i = 0; i < dS.dSsGet.size(); i++) {
                ausgabe = ausgabe.concat(dS.dSsGet.get(i).toString());
                txtAusgabe.setText(ausgabe);
            }
        }
    }//GEN-LAST:event_txtUsereingabeKeyPressed

    private void btnZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZurueckActionPerformed
        new UserUI().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnZurueckActionPerformed

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
            java.util.logging.Logger.getLogger(DateisystemAusgebenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DateisystemAusgebenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DateisystemAusgebenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DateisystemAusgebenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DateisystemAusgebenUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSuchen;
    private javax.swing.JButton btnZurueck;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAusgabe;
    private javax.swing.JLabel lblBeschreibung;
    private javax.swing.JTextArea txtAusgabe;
    private javax.swing.JTextField txtUsereingabe;
    // End of variables declaration//GEN-END:variables
}
