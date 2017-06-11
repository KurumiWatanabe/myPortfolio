import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class time extends JPanel implements ActionListener{
  JPanel panel; 
  JButton button;
  JLabel label; 
  
  long timecount = 0;
  int i=0;

  public void actionPerformed(ActionEvent e){
    
    
    i++;
    if(i % 2 == 0){
       button.setText("start");
       timecount = System.currentTimeMillis() - timecount;
       label.setText(Long.toString(timecount)); 
    }
    else{
      button.setText("stop");
      timecount = System.currentTimeMillis();
      label.setText("0"); 
    }
  }
  time(){ 
    panel = new JPanel(); 
    panel.setLayout(new BorderLayout ());
    panel.setBackground(Color.CYAN); 
    add(panel);

  
    label = new JLabel();
    label.setPreferredSize(new Dimension(170, 70));
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(label.getFont().deriveFont(64.0f));
    label.setText(Long.toString(timecount)); 
    panel.add(label, BorderLayout.NORTH); 
    
    button = new JButton(); 
    button.setPreferredSize(new Dimension(170, 70));
    button.setText("start"); 
    panel.add(button, BorderLayout.SOUTH);
    button.addActionListener(this); 
  }
  public static void main(String[] args){
    //make frame
    JFrame frame = new JFrame("timer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(200,200);
    Container contentPane = frame.getContentPane();
    
    contentPane.add(new time());
    frame.setVisible(true);
  }
}