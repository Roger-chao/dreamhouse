import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class createAndShowGUI { 
	public static void main(String[] args) {        
		SwingUtilities.invokeLater(new Runnable() {            
			@Override            
			public void run() {                
				createAndShowGUI();            
				}        
			});    
	} 
   
	private static JCheckBox customSizeCheckBox;    
	private static JTextField lengthTextField;    
	private static JTextField widthTextField;    
	private static JPanel canvasPanel;    
	private static JFrame frame;    
	private static main main1;
	
	private static void createAndShowGUI() {        
		Color backgroundColor = new Color(227,216,167);        // 主視窗屬性設定        
		frame = new JFrame("夢幻大家園-居家設計");        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		frame.setLayout(new BorderLayout()); // 為內容窗格設置BorderLayout
		
		// 創建選項面板        
		JPanel optionsPanel = new JPanel();        
		optionsPanel.setLayout(new GridLayout(3, 1, 0, 5)); // 0, 5分別是水平和垂直的間距        
		optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 大框  
		
		// 第一排        
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));        
		row1.setBackground(backgroundColor);        
		row1.add(new JLabel("選擇畫布大小"));        
		optionsPanel.add(row1);
		
		// 第二排        
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));        
		customSizeCheckBox = new JCheckBox("系統自訂大小");        
		row2.add(customSizeCheckBox);        
		row2.add(new JLabel("(推薦使用!)"));
		optionsPanel.add(row2);
  		
		// 第三排        
		JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));        
		JCheckBox userDefinedSizeCheckBox = new JCheckBox("使用者自行設定大小");        
		row3.add(userDefinedSizeCheckBox);        
		lengthTextField = new JTextField(5);        
		row3.add(new JLabel("長度:"));        
		row3.add(lengthTextField);     
		row3.add(new JLabel("(公分)"));
		widthTextField = new JTextField(5);        
		row3.add(new JLabel("寬度:"));
		row3.add(widthTextField);    
		row3.add(new JLabel("(公分)"));
		optionsPanel.add(row3);      
		
		// 將選項面板添加到主框架的北部（上方）        
		frame.add(optionsPanel, BorderLayout.NORTH);    
		
		// 創建確認按鈕        
		JButton confirmButton = new JButton("確認");  
		 
		confirmButton.addActionListener(new ActionListener() {            
			@Override            
			public void actionPerformed(ActionEvent e) {               
				boolean customSizeSelected = customSizeCheckBox.isSelected();                
				boolean userDefinedSizeSelected = userDefinedSizeCheckBox.isSelected();                
				if (customSizeSelected && userDefinedSizeSelected) {                    
					// 重複選取，顯示錯誤訊息                    
					JOptionPane.showMessageDialog(frame, "請重新選取");                
					} 
				else if (customSizeSelected || userDefinedSizeSelected) {                    
	                
					// 使用者選取了一項，顯示畫布                    
					if (customSizeSelected) {                        
						createCanvas(1100, 500);                    
						} 
					else {                        
						try {                            
							int length = Integer.parseInt(lengthTextField.getText());                            
							int width = Integer.parseInt(widthTextField.getText());                            
							createCanvas(width, length);                        
							} 
						catch (NumberFormatException ex) {                            
							JOptionPane.showMessageDialog(frame, "請輸入有效的數字");                            
							return;                        
							}                    
						}                    
					// 隱藏選項面板，確保只有畫布被顯示                    
					optionsPanel.setVisible(false);                    
					confirmButton.setVisible(false);                
					}            
				}        
			});      
		
		
		// 設定按鈕的大小(與第三排相同)
        confirmButton.setPreferredSize(new Dimension(500,30));//(寬、高)
        // 使用FlowLayout將按鈕置於底部中央
        JPanel confirmButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmButtonPanel.add(confirmButton);
        //frame.add(confirmButtonPanel, BorderLayout.SOUTH);
        frame.add(confirmButtonPanel,BorderLayout.CENTER);


        // 將選項面板添加到主框架的北部
        frame.add(optionsPanel, BorderLayout.NORTH);
		
		// 設定主框架大小和顯示        
		frame.setSize(500, 200);        
		frame.setLocationRelativeTo(null); 
		
		// 置中        
		frame.setVisible(true);    
	}    
	
	private static void createCanvas(int length, int width) {        
		// 如果已經有畫布存在，則不再創建        
		if (canvasPanel != null) {            
			return;        
		}
		//呼叫主畫面
		DreamHouse designer = new DreamHouse(length,width);
		
		designer.setVisible(true);    
		// 關閉當前的主畫面
	    frame.setVisible(false);
		
	}    
	
	// 新增移除畫布的方法    
	private static void removeCanvas() {        
		if (canvasPanel != null) {            
			frame.getContentPane().remove(canvasPanel);            
			frame.revalidate();            
			frame.repaint();            
			canvasPanel = null; // 設置為 null，以便下次可以正確地創建新畫布      
			}    
		}
	
}