/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package DPlugIn;

/**
 *
 * @author dsb
 */
public class Gui extends javax.swing.JFrame {
    Calc calc;
    /** Creates new form calcGui */
    public Gui(String name) {
        super(name);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    void initComponents() {
        calc = new Calc();
        Result = new javax.swing.JTextField();
        Enter = new javax.swing.JButton();
        Clear = new javax.swing.JButton();
        Plus = new javax.swing.JButton();
        Minus = new javax.swing.JButton();
        Times = new javax.swing.JButton();
        Div = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Result.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Result.setText("0");

        Enter.setText("Enter");
        Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterActionPerformed(evt);
            }
        });

        Clear.setText("Clear");
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });

        Plus.setText("+");
        Plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlusActionPerformed(evt);
            }
        });

        Minus.setText("-");
        Minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinusActionPerformed(evt);
            }
        });

        Times.setText("*");
        Times.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimesActionPerformed(evt);
            }
        });

        Div.setText("/");
        Div.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DivActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Result, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(Plus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Minus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Times)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Div))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(Enter, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Clear)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Result, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Enter)
                    .addComponent(Clear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Plus)
                    .addComponent(Minus)
                    .addComponent(Times)
                    .addComponent(Div)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterActionPerformed
        calc.set(Result.getText());
    }//GEN-LAST:event_EnterActionPerformed

    void ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearActionPerformed
        calc.clear();
        Result.setText("0");
    }//GEN-LAST:event_ClearActionPerformed

    void PlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlusActionPerformed
        calc.add( Result.getText());
        Result.setText(calc.get());
    }//GEN-LAST:event_PlusActionPerformed

    void MinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinusActionPerformed
        calc.sub( Result.getText());
        Result.setText(calc.get());
    }//GEN-LAST:event_MinusActionPerformed

    void TimesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimesActionPerformed
        calc.mul( Result.getText());
        Result.setText(calc.get());
    }//GEN-LAST:event_TimesActionPerformed

    void DivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DivActionPerformed
        calc.div( Result.getText());
        Result.setText(calc.get());
    }//GEN-LAST:event_DivActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton Clear;
    javax.swing.JButton Div;
    javax.swing.JButton Enter;
    javax.swing.JButton Minus;
    javax.swing.JButton Plus;
    javax.swing.JTextField Result;
    javax.swing.JButton Times;
    // End of variables declaration//GEN-END:variables

    public void display() {
        Gui cg = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                cg.setVisible(true);
            }
        });
    }
}
