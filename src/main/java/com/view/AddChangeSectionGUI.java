package com.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.services.Utility;
import com.model.AddChangeSection;


public class AddChangeSectionGUI implements ActionListener, ListSelectionListener {

	
	//				###  ###
	private AddChangeSection ad;
	
	private String sectionName;
	private String numberOfLines;
	
	
	
	
	//				### System variables ###
	private String arr []=Utility.getSectionsName();
	
	
	
	//				### GUI components  ###
	private JFrame frame;
	private JPanel pan;
	
	
	private JPanel centerPan;
	private JLabel sectionNameTitle;
	private JLabel sectionNameLab;
	private JButton editButton;
	
	
	private JPanel leftPan;
	private JList<String> list;
	private JScrollPane listScroller;
	private JButton addNewSectionBut;
	
	
	private JPanel editPan;
	private JLabel toBeChanged;
	private JLabel sectionNameLab2;
	private JLabel newNameLab;
	private JTextField newNameField;
	private JLabel statusTitle;
	private JLabel statusLab;
	private JButton saveBut;
	private JButton cancelBut;
	
	
	private JPanel addPan;
	private JLabel newNameTitle;
	private JTextField createNameField;
	private JLabel statusAddTitle;
	private JLabel statusAddLab;
	private JButton addBut;
	private JButton cancelAddBut;
	
	
	public AddChangeSectionGUI() {
		ad=new AddChangeSection();
		sectionName=arr[0];
		
		mainInit();
	}
	
	private void mainInit(){
		
		frame=new JFrame();
		frame.setTitle("Add/Change section");
		frame.setDefaultCloseOperation(1);
		
		pan=new JPanel(new BorderLayout(10, 10));
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.add(pan);
		
		
		initLeftPan();
		
		initCenterPan();
		
		initEditPan();
		
		initAddPan();
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void initLeftPan(){
		leftPan=new JPanel();
		leftPan.setLayout(new BorderLayout(10, 10));
		pan.add(leftPan, BorderLayout.WEST);
		list=new JList<>(Utility.getSectionsName());
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(100, 120));
		
		addNewSectionBut=new JButton("Add new section");
		leftPan.add(listScroller, BorderLayout.CENTER);
		leftPan.add(addNewSectionBut, BorderLayout.SOUTH);
		
		list.addListSelectionListener(this);
		addNewSectionBut.addActionListener(this);
	}
	
	public void initCenterPan(){
		centerPan=new JPanel(new BorderLayout(10, 10));
		
		centerPan.setPreferredSize(new Dimension (230, 170));
		sectionNameTitle=new JLabel("Name: ");
		sectionNameLab=new JLabel(sectionName);
		editButton=new JButton("Edit");
		JPanel pan1=new JPanel(new GridLayout(1, 2));
//		pan1.setLayout(new BoxLayout(pan1, BoxLayout.LINE_AXIS));
		pan1.add(sectionNameTitle);
		pan1.add(sectionNameLab);
		centerPan.add(pan1, BorderLayout.NORTH);
		
		JPanel pan4=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pan4.add(editButton);
		centerPan.add(pan4, BorderLayout.SOUTH);
		
		pan.add(centerPan, BorderLayout.CENTER);
		
		editButton.addActionListener(this);
	}
	
	public void initEditPan(){
		editPan=new JPanel(new BorderLayout(10, 10));
		
		JPanel pan2=new JPanel();
		//pan2.setLayout(new BoxLayout(pan2, BoxLayout.PAGE_AXIS));
		pan2.setLayout(new GridLayout(3, 2));
		toBeChanged=new JLabel("Current name: ");
		sectionNameLab2=new JLabel(sectionName);
		newNameLab=new JLabel("New name: ");
		newNameField=new JTextField(15);
		statusTitle=new JLabel("Status: ");
		statusLab=new JLabel();
		//newNameField.setPreferredSize(new Dimension(40, 20));
		pan2.add(toBeChanged);
		pan2.add(sectionNameLab2);
		pan2.add(newNameLab);
		pan2.add(newNameField);
		pan2.add(statusTitle);
		pan2.add(statusLab);
		editPan.add(pan2, BorderLayout.NORTH);
		
		JPanel pan3=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		saveBut=new JButton("Save");
		cancelBut=new JButton("Cancel");
		pan3.add(saveBut);
		pan3.add(cancelBut);
		editPan.add(pan3, BorderLayout.SOUTH);
		
		//pan.add(editPan, BorderLayout.CENTER);
		
		saveBut.addActionListener(this);
		cancelBut.addActionListener(this);
	}
	
	
	public void initAddPan(){

		addPan=new JPanel(new BorderLayout(5, 5));
		
		JPanel pan5=new JPanel(new GridLayout(2, 2));
		newNameTitle=new JLabel("Enter name:");
		createNameField=new JTextField();
		statusAddTitle=new JLabel("Status:");
		statusAddLab=new JLabel();
		pan5.add(newNameTitle);
		pan5.add(createNameField);
		pan5.add(statusAddTitle);
		pan5.add(statusAddLab);
		
		JPanel pan6=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addBut=new JButton("Add");
		cancelAddBut=new JButton("Cancel");
		pan6.add(addBut);
		pan6.add(cancelAddBut);
		
		addPan.add(pan5, BorderLayout.NORTH);
		addPan.add(pan6, BorderLayout.SOUTH);
		
		addBut.addActionListener(this);
		cancelAddBut.addActionListener(this);
	}
	
	// to swap editPan and centerPan back and forth
	private void replaceAPanelTo(JPanel panel){
		// to disable addNewSectionBut button 
		// when addPan is opened
		if(panel == addPan) addNewSectionBut.setEnabled(false);
		else addNewSectionBut.setEnabled(true);
		
		pan.remove(centerPan);
		pan.remove(editPan);
		pan.remove(addPan);
		
		// apply changes
		pan.validate();
		pan.repaint();
		
		pan.add(panel, BorderLayout.CENTER);
		
		// apply changes
		pan.validate();
		pan.repaint();
		
		// clear field
		clearInputFields();
	}
	
	// to change text in the labels
	private void changeSectionLabels(String name){
		sectionNameLab.setText(name);
		sectionNameLab2.setText(name);
	}
	
	// to dispay if the section was changed successfully or not
	private void changeStatusLabel(JLabel lab, boolean result){
		if (result) {
			lab.setText("Success");
			
		} else {
			lab.setText("Fail");
		}
	}
	
	private void refreshLeftPan(){

		// overwrite the array with section names
		arr =Utility.getSectionsName();
		
		pan.remove(leftPan);
		
		// apply changes
		pan.validate();
		pan.repaint();
		
		// initialize left panel again
		initLeftPan();
		
		// select the last component (?)
		list.setSelectedIndex((arr.length-1));

		// apply changes
		pan.validate();
		pan.repaint();
	}
	
	private void clearInputFields() {
		
		// clear field in editPan
		newNameField.setText("");
		//clear the field in addPan
		createNameField.setText("");
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//			 ### CenterPan components	###
		
		if(e.getSource()==editButton){
			replaceAPanelTo(editPan);
			
		}
		
		
		
		
		//			 ### EditPan components	###
		
		// the "Save" button clicked
		if (e.getSource()==saveBut) {
			if(newNameField.getText().isEmpty()){
				changeStatusLabel(statusLab, false);
				JOptionPane.showMessageDialog(null, "Введите название раздела");
			} else {
			
				ad.changeSectionName(newNameField.getText());
				changeStatusLabel(statusLab, true);
				
				// update name of this section in the JList which is at left
				refreshLeftPan();
				
				JOptionPane.showMessageDialog(null, "Restart the program to apply the changes");
			}
		}
		
		// the "Cancel" button clicked
		if(e.getSource()==cancelBut){
			replaceAPanelTo(centerPan);
		}
		
		
		
		
		//					###  AddPan   ###
		
		// open pane to create new section
		if(e.getSource()==addNewSectionBut){
			replaceAPanelTo(addPan);
		}
		
		if (e.getSource()==cancelAddBut) {
			replaceAPanelTo(centerPan);
		}
		
		// create new section
		if (e.getSource()==addBut) {
			if(createNameField.getText().isEmpty()){
				changeStatusLabel(statusAddLab, false);
				
				JOptionPane.showMessageDialog(null, "Enter a name of the section");
			} else {
			
				ad.addNewSection(createNameField.getText());
				changeStatusLabel(statusAddLab, true);
				
				// update name of this section in the JList which is at left
				refreshLeftPan();
				
				JOptionPane.showMessageDialog(null, "Restart the program to apply the changes");
			}
			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// this "if" statement is to skip double
		// calling of the method valueChanged 
		// by unchecking one item and by checking the other
		
		if(e.getValueIsAdjusting()==false){
			
			for (int i = 0; i < arr.length; i++) {
				if(list.getSelectedValue().equals(arr[i])){
					// send number of this section to change it
					ad.setCurrentSection(i);
					
					sectionName=arr[i];
					
					// change labels in the frame
					changeSectionLabels(sectionName);
					
					System.out.println("номер раздела - "+sectionName);
					break;
				}
			}
		}

	}
	
	
}
