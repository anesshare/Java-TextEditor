package pckg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {
	JTextArea area = new JTextArea();
	JScrollPane scroll = new JScrollPane(area);
	JSpinner fontSpinner = new JSpinner();
	JLabel fontlabel = new JLabel();
	JButton btn = new JButton("Color");
	JComboBox fontbox = new JComboBox();
	JMenuBar menubar = new JMenuBar();
	JMenu filemenu = new JMenu("File");
	JMenuItem openItem = new JMenuItem("Open");
	JMenuItem saveItem = new JMenuItem("Save");
	JMenuItem closeItem = new JMenuItem("Exit");

	TextEditor(){
		this.setTitle("Text Editor");
		this.setSize(500,500);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(new Font("Arial",Font.PLAIN,20));
		fontlabel.setText("Font");
		scroll.setPreferredSize(new Dimension(450,450));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		fontSpinner.setPreferredSize(new Dimension(50,25));
		fontSpinner.setValue(20);
		fontSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				area.setFont(new Font(area.getFont().getFamily(),Font.PLAIN,(int)fontSpinner.getValue()));
			}});

	
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for (String font : fonts) {
			fontbox.addItem(font);
		}
		
		btn.addActionListener(this);
		fontbox.addActionListener(this);
		fontbox.setSelectedItem("Arial"); 
		
		//--menubar
		filemenu.add(openItem);
		filemenu.add(saveItem);
		filemenu.add(closeItem);
		menubar.add(filemenu);
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		closeItem.addActionListener(this);
		
		this.setJMenuBar(menubar);
		this.add(fontlabel);
		this.add(fontSpinner);
		this.add(btn);
		this.add(fontbox);
		this.add(scroll);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn) {
			JColorChooser chooser = new JColorChooser();
			Color color = chooser.showDialog(null, "Pick a color", Color.black);
			area.setForeground(color);
		}
		if(e.getSource()==fontbox) {
			area.setFont(new Font((String)fontbox.getSelectedItem(),Font.PLAIN,area.getFont().getSize()));
		}
		if(e.getSource()== openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
			fileChooser.setFileFilter(filter);
			int resp = fileChooser.showOpenDialog(null);
			if(resp == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn=null;
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine() + "\n";
							area.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
		}
		if(e.getSource()== saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int resp= fileChooser.showSaveDialog(null);
			if(resp ==JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(area.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		if(e.getSource()== closeItem) {
			System.exit(0);
		}
		
	}

}
