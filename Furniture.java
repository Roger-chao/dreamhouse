//左方傢俱列
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Furniture extends JPanel {
    
	//背景顏色設定
	private Color backgroundColor = new Color(227,216,167);// R,G,B
    JButton addFurniBtn = null;
    JComboBox<JButton> comboBox1;
    JComboBox<JButton> comboBox2;
    JComboBox<JButton> comboBox3;
    JComboBox<JButton> comboBox4;
    JComboBox<JButton> comboBox5;
    int chooseaddfurni;
    
    Canvas canvas;
    DreamHouse parent;
    public Furniture(DreamHouse parent, Canvas canvas) {
    	this.canvas = canvas;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(250, 400));
        
        this.canvas = canvas;
        this.parent = parent;

        //5個下拉式選單
        comboBox1 = new JComboBox<>(getButtonArray(1));
        comboBox2 = new JComboBox<>(getButtonArray(2));
        comboBox3 = new JComboBox<>(getButtonArray(3));
        comboBox4 = new JComboBox<>(getButtonArray(4));
        comboBox5 = new JComboBox<>(getButtonArray(5));
        
        //各選單呈現方式(呼叫ButtonListCellRenderer()將選單內容變按鈕)
        comboBox1.setRenderer(new ButtonListCellRenderer());
        comboBox2.setRenderer(new ButtonListCellRenderer());
        comboBox3.setRenderer(new ButtonListCellRenderer());
        comboBox4.setRenderer(new ButtonListCellRenderer());
        comboBox5.setRenderer(new ButtonListCellRenderer());
        
        //點擊選單點擊時，呼叫handleComboBoxSelection();
        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleComboBoxSelection(comboBox1);
                chooseaddfurni=1;
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleComboBoxSelection(comboBox2);
                chooseaddfurni=2;
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleComboBoxSelection(comboBox3);
                chooseaddfurni=3;
            }
        });
        comboBox4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleComboBoxSelection(comboBox4);
                chooseaddfurni=4;
            }
        });
        comboBox5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleComboBoxSelection(comboBox5);
                chooseaddfurni=5;
            }
        });

        //5個下拉式選單版面配置
        JPanel comboBoxPanel_1 = new JPanel();
        comboBoxPanel_1.setBackground(backgroundColor);
        comboBoxPanel_1.setLayout(new BorderLayout());
        JLabel label_1 = new JLabel("門窗");
        label_1.setHorizontalAlignment(JLabel.CENTER); //"桌子"在center
        comboBoxPanel_1.add(label_1, BorderLayout.NORTH);  //"桌子那整行東西"在選單上north 
        comboBoxPanel_1.add(comboBox1, BorderLayout.CENTER); //選單內容的部分
        add(comboBoxPanel_1); //所有東西的顯示

        JPanel comboBoxPanel_2 = new JPanel();
        comboBoxPanel_2.setBackground(backgroundColor);
        comboBoxPanel_2.setLayout(new BorderLayout());
        JLabel label_2 = new JLabel("桌子");
        label_2.setHorizontalAlignment(JLabel.CENTER);
        comboBoxPanel_2.add(label_2, BorderLayout.NORTH);
        comboBoxPanel_2.add(comboBox2, BorderLayout.CENTER);
        add(comboBoxPanel_2);

        JPanel comboBoxPanel_3 = new JPanel();
        comboBoxPanel_3.setBackground(backgroundColor);
        comboBoxPanel_3.setLayout(new BorderLayout());
        JLabel label_3 = new JLabel("椅子");
        label_3.setHorizontalAlignment(JLabel.CENTER);
        comboBoxPanel_3.add(label_3, BorderLayout.NORTH);
        comboBoxPanel_3.add(comboBox3, BorderLayout.CENTER);
        add(comboBoxPanel_3);
        
        JPanel comboBoxPanel_4 = new JPanel();
        comboBoxPanel_4.setBackground(backgroundColor);
        comboBoxPanel_4.setLayout(new BorderLayout());
        JLabel label_4 = new JLabel("床");
        label_4.setHorizontalAlignment(JLabel.CENTER); //"桌子"在center
        comboBoxPanel_4.add(label_4, BorderLayout.NORTH);  //"桌子那整行東西"在選單上north 
        comboBoxPanel_4.add(comboBox4, BorderLayout.CENTER); //選單內容的部分
        add(comboBoxPanel_4); //所有東西的顯示
        
        JPanel comboBoxPanel_5 = new JPanel();
        comboBoxPanel_5.setBackground(backgroundColor);
        comboBoxPanel_5.setLayout(new BorderLayout());
        JLabel label_5 = new JLabel("其他");
        label_5.setHorizontalAlignment(JLabel.CENTER); //"桌子"在center
        comboBoxPanel_5.add(label_5, BorderLayout.NORTH);  //"桌子那整行東西"在選單上north 
        comboBoxPanel_5.add(comboBox5, BorderLayout.CENTER); //選單內容的部分
        add(comboBoxPanel_5); //所有東西的顯示
    }

    //傢俱列基本版面配置
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
    }
    
    //下拉式選單內按鈕被點擊時
    private void handleComboBoxSelection(JComboBox<JButton> comboBox) {
        JButton selectedButton = (JButton) comboBox.getSelectedItem();    
        if (selectedButton != null) {
        	
        	if(comboBox.getSelectedIndex() == 0) {
        		
        		int canvasWidth = 0;
        		int canvasHeight = 0;
        		// show對話框
        		String inputWidth = JOptionPane.showInputDialog("請輸入傢俱寬度(1~1300cm):");
        		String inputHeight = JOptionPane.showInputDialog("請輸入傢俱長度(1~576cm)");
        		//JOptionPane.showMessageDialog(this, "請注意，存檔時請將畫面放至最大!");
        		try {
        		    //便整數
        		    canvasWidth = Integer.parseInt(inputWidth);
        		    canvasHeight = Integer.parseInt(inputHeight);
        		} catch (NumberFormatException e) {
        		    JOptionPane.showMessageDialog(null, "請輸入有效數字", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
        		    return;
        		}
        		
        		parent.addFurni(canvasWidth+2,canvasHeight+2);
        	}else { 
        		
        		if((canvas.status==Status.active)||(canvas.status==Status.creatingFurniture)) {
        		
	                canvas.status = Status.ready2CreateAFurniture;
	                Icon icon = selectedButton.getIcon();
	                if(icon instanceof ImageIcon) {
	                	ImageIcon imageIcon = (ImageIcon) icon;
	                	String imagePath=imageIcon.getDescription();
	                	System.out.println(imagePath);
	                	canvas.createAFurniture(imagePath);
	                	
	                }
        		}
        	}
        	
            // 系統顯示點擊哪個按鈕(看有沒有正確點擊的而已)(查看Console內容)
            System.out.println("Button Clicked: " + selectedButton.getText()+chooseaddfurni);
            
        }
        
    }
    
  //創建按鈕陣列(下拉式選單內的按鈕)
    private JButton[] getButtonArray(int comboBoxNumber) {
    	
        JButton[] buttons;
        switch (comboBoxNumber) {
        	case 1: //放門窗圖片!
        		buttons = new JButton[] {
        				new JButton(new ImageIcon("images/add furni.png")),
        				new JButton(new ImageIcon("images/windowdoor_img/wooddoor.png")),
        				new JButton(new ImageIcon("images/windowdoor_img/irondoor.png")),
        				new JButton(new ImageIcon("images/windowdoor_img/shutter.png")),
        				new JButton(new ImageIcon("images/windowdoor_img/window.png"))
        		};
        		break;
        	case 2: //放桌子圖片!
        		buttons = new JButton[] {
        				new JButton(new ImageIcon("images/add furni.png")),
        				new JButton(new ImageIcon("images/table_img/Computer Desk.png")),
        				new JButton(new ImageIcon("images/table_img/Dining Table.png")),
        				new JButton(new ImageIcon("images/table_img/Table.png"))
        				
        		};
        		break;
        	case 3: //放椅子圖片!
        		buttons = new JButton[] {
        				new JButton(new ImageIcon("images/add furni.png")),
        				new JButton(new ImageIcon("images/chair_img/single sofa.png")),
        				new JButton(new ImageIcon("images/chair_img/three seater sofa.png")),
        				new JButton(new ImageIcon("images/chair_img/Dining chair.png"))
        				
        		};
        		break;
        	case 4: //放床圖片!
        		buttons = new JButton[] {
        				new JButton(new ImageIcon("images/add furni.png")),
        				new JButton(new ImageIcon("images/bed_img/Single Bed.png")),
        				new JButton(new ImageIcon("images/bed_img/Double Bed.png")),
        				new JButton(new ImageIcon("images/bed_img/Double Bed with headboard cabinet.png")),
        		};
        		break;
        	case 5: //放其他圖片!
        		buttons = new JButton[] {
        				new JButton(new ImageIcon("images/add furni.png")),
        				new JButton(new ImageIcon("images/other_img/wall.png")),
        				new JButton(new ImageIcon("images/other_img/sink.png")),
        				new JButton(new ImageIcon("images/other_img/sink_2.png")),
        				new JButton(new ImageIcon("images/other_img/stove.png")),
        				new JButton(new ImageIcon("images/other_img/toilet.png")),
        				new JButton(new ImageIcon("images/other_img/tub.png"))
        				
        		};
        		break;
        	default:
        		buttons = new JButton[] {new JButton(new ImageIcon("images/add furni.png"))};
        		break;
        		
        }
        return buttons;
    }

    //下拉式選單的呈現方式 (變成按鈕!!)
    private static class ButtonListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JButton button = new JButton();
            
            //看是否是JButton
            if (value instanceof JButton) {
                JButton originalButton = (JButton) value;

                // 取得原始圖片
                Icon originalIcon = originalButton.getIcon();

                // 只有在原始圖標!=null才設置新按鈕的圖標
                if (originalIcon != null) {
                    
                    button.setIcon(originalIcon);
                    // 調整按鈕大小
                    int targetWidth = 80;
                    int targetHeight = 80;
                    // 取得原始圖片
                    Image originalImage = ((ImageIcon) originalIcon).getImage();
                    // 創建縮小版的圖像
                    Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                    // 創建縮小版的圖標
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    // 設置新按鈕的圖標
                    button.setIcon(scaledIcon);
                }
            }
            
            return button;
        }
    }

    //捲動軸範圍
  	public Dimension getPreferredSize() {
  		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(250,screen.height-220);
    }
    

}