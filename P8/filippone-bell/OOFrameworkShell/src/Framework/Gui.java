package Framework;

import javax.swing.JFrame;


abstract public class Gui extends JFrame {
    
    Calc calc;
    /* Creates new form calcGui */
    public Gui(String name) {
        super(name);
        initComponents();
    }

    abstract void initComponents();

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

    public void display( ) {
        Framework.Gui cg = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                cg.setVisible(true);
            }
        });
    }
}
