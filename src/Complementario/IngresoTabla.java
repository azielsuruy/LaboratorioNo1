

package Complementario;


import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


public class IngresoTabla extends DefaultTableCellRenderer {
    
    
  public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected, boolean hasFocus, int row, int colum){
      
      if(value instanceof JLabel){
      
      JLabel lbl=(JLabel)value;
      return lbl;
      
  }
      
      if(value instanceof JButton){
          
          JButton button=(JButton)value;
          
          if(isSelected ){
              
              button.setForeground(table.getSelectionForeground());
              button.setBackground(table.getSelectionBackground());
          }
          
          else{
              
              button.setBackground(table.getForeground());
              
              button.setBackground(UIManager.getColor("Button.background"));
          }
          
          return button;
  }
      return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);
  }
  
       
}
