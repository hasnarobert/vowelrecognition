/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Users.java
 *
 * Created on Jul 2, 2011, 12:31:26 AM
 */
package vpower.gui;

import vpower.engine.DatabaseHandler;

/**
 *
 * @author ninu
 */
public class Users extends javax.swing.JPanel {
    private DatabaseHandler handler;
    private UI gui;
    
    /** Creates new form Users */
    public Users(DatabaseHandler handler, UI gui) {
        initComponents();
        this.handler = handler;
        this.gui = gui;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        username_label = new javax.swing.JLabel();
        username_combo = new javax.swing.JComboBox();
        add_user_name = new javax.swing.JTextField();
        add_user_button = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        username_label.setText("Username:");

        username_combo.setModel(new javax.swing.DefaultComboBoxModel());
        username_combo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                username_comboItemStateChanged(evt);
            }
        });

        add_user_button.setText("Clear user data");
        add_user_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_user_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(username_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(add_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(add_user_button)
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username_label)
                    .addComponent(username_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_user_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void add_user_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_user_buttonActionPerformed
        if (evt.getActionCommand().equalsIgnoreCase("Add user")) {
            handler.addUser(add_user_name.getText());
            username_combo.addItem(add_user_name.getText());
        }
        else {
            String username = (String)username_combo.getSelectedItem();
            handler.clearUserData(username);
            handler.getDatabase().removeUser(username);
            username_combo.removeItemAt(username_combo.getSelectedIndex());
        }
    }//GEN-LAST:event_add_user_buttonActionPerformed

    private void username_comboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_username_comboItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            if (((String)evt.getItem()).equalsIgnoreCase("New User")) {
                System.out.println("Select or create an user");
            }
            else {
                System.out.println("User " + evt.getItem() + " selected");
            }
            
            
            if (((String)evt.getItem()).equalsIgnoreCase("New User")) {
                add_user_name.setVisible(true);
                add_user_button.setText("Add User");
                add_user_name.setText("");
            }
            else {
                add_user_name.setVisible(false);
                add_user_button.setText("Clear user data");
            }
            gui.pack();
            gui.repaint();
        }
    }//GEN-LAST:event_username_comboItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton add_user_button;
    javax.swing.JTextField add_user_name;
    javax.swing.JSeparator jSeparator1;
    javax.swing.JComboBox username_combo;
    javax.swing.JLabel username_label;
    // End of variables declaration//GEN-END:variables
}
