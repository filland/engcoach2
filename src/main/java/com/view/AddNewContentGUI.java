package com.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import com.services.Utility;
import com.model.AddNewContent;


public class AddNewContentGUI {

	private JFrame frame;	
	
	private JComboBox<String> combo;
	private JTextField o1s[];
	private JTextField t1s[];

	
	private JPanel pan;
	private JPanel panComboBox;
	private	JPanel panFields;
	private	JPanel panButtons;
	
	private	JLabel lab;
	
	private JButton add;
	private JButton cancel;
	
	
	// names of sections
	String sections[];
	
	// mark-boundaries which are for getting content from the file
	String boundaries [];
	
	
	private AddNewContent cont;

	
	
	
	public AddNewContentGUI() {
		sections = Utility.getSectionsName();
		sections[0]="--//--";
		
		boundaries = Utility.getBoundaries();
		
		createGUI();
	 }

	private void createGUI() {
		frame = new JFrame();
		frame.setTitle("Add new content");
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(1);

		pan = new JPanel(new BorderLayout(10, 10));
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panComboBox = new JPanel();
		panFields = new JPanel(new GridLayout(6, 2, 5, 5));
		panButtons = new JPanel(new FlowLayout());
		
		combo = new JComboBox<>(sections);
		panComboBox.setLayout(new GridLayout(2, 2));
		panComboBox.add(new JLabel("Choose a section to load: "));
		panComboBox.add(combo);
		lab = new JLabel("Content will be loaded into: " + sections[0]);
		panComboBox.add(lab);
		
		o1s = new JTextField[5];
		t1s = new JTextField[5];

		// 
		cont=new AddNewContent(o1s, t1s);
		
		
		for (int i = 0; i < o1s.length; i++) {
			if (i == 0){
				panFields.add(new JLabel("Original: "));
				panFields.add(new JLabel("Translation: "));
			}
			
			o1s[i] = new JTextField(30);
			panFields.add(o1s[i]);
			t1s[i] = new JTextField(30);
			panFields.add(t1s[i]);
		}

		add = new JButton("Add");
		add.setEnabled(false);
		cancel = new JButton("Cancel");
		panButtons.add(add);
		panButtons.add(cancel);

		pan.add(panComboBox, BorderLayout.NORTH);
		pan.add(panFields, BorderLayout.CENTER);
		pan.add(panButtons, BorderLayout.SOUTH);

		frame.add(pan);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent itemEvent) {

				if ((itemEvent.getItemSelectable() == combo)
						&& (itemEvent.getStateChange() == ItemEvent.SELECTED)) {

					String subject = (String) itemEvent.getItem();

					for (int i = 0; i < sections.length; i++) {
						if(sections[i].equals(sections[0])){ 
							add.setEnabled(false);
							continue;
						}
						
						
						if(sections[i]==subject) {
							cont.setTargetSection(i-1);
							add.setEnabled(true);
							break;
						}
					}

					lab.setText("Content will be loaded into: " + 
												sections[cont.getDestination()]);
				}
			}
		});
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// add content from text fields
				cont.addContentInSection();
				// write that content in the file
				cont.writeFile();
				
				JOptionPane.showMessageDialog(null, 
								"Данные успешно добавлены в файл", "Инфо", 1);
			}
		});
		
		
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
	}

}
