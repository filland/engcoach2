package com.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.services.Utility;
import com.services.TranscriptionService;
import com.model.EngCoach;


/*

Выполняется:  ...


                                ##### Рефакторинг ######
              


Выполнено:

> Create a feature that allows to add new sections
> Установка ширины в зависимости от выбранного пунка (для предложений намного шире)
> what the heck with FastTrain initialization here???
> Вынести статические методы и поля из класса ContentHandler
> тысячи слов в arrayCheck в режиме Fast train
> Добавить возможность установки скорости показа слов в режиме Fast train
> Добавить возможность выбора секции 
> добавить инфу об общем кол-ве слов в режиме Fast train
> при нажатии кнопки Стоп в поле для установки кол-ва слов помещать кол-во не показанных
слов
> выбор языка оригинала
> разбить метод getWord() на методы:
1-й выбирает слова
2-й устанавливает значения в поля
> класс для функциональности "Fast mode"
> парсить оригинал и перевод можно намного проще -- с помощью split   
> автоматический показ слов без перевода - выполнено
> добавление новых слов и предложений из программы - выполнено
> класс для функциональности "Add new content" - выполнено

*/

public class EngCoachGUI extends JFrame implements ActionListener, ItemListener, KeyListener
{
	
	private JPanel pan=new JPanel(new BorderLayout(5, 5));
//	private JPanel panForFields=new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel panForFields=new JPanel(new GridLayout(3, 1));
	
	private JTextField firstField=new JTextField(55);
	private JTextField secondField=new JTextField(55);
	private JTextField transcryptField=new JTextField();
	
	private JButton nextBut=new JButton("Дальше");
	
	private JMenuBar menuBar=new JMenuBar();
	private JMenu menuFirst=new JMenu("Settings");
	private ButtonGroup group = new ButtonGroup();
	private JRadioButtonMenuItem radBut1 = new JRadioButtonMenuItem("Eng-Rus");
	private JRadioButtonMenuItem radBut2 = new JRadioButtonMenuItem("Rus-Eng");
	private JMenuItem menuItemFirstOne = new JMenuItem("Fast train");
	private JCheckBoxMenuItem setTranscryptMenuItem=new JCheckBoxMenuItem("Show transcryption", false);
	private JMenuItem menuItemAddNewSection = new JMenuItem("Add/Change section");
	private JMenuItem menuItemFirstAddingContent = new JMenuItem("Add new content");
	private JMenuItem menuItemSetSourcePath = new JMenuItem("Change path to source file");
	
	private JMenu menuSecond=new JMenu("Set section");
	private ButtonGroup group2 = new ButtonGroup();
	
	private JRadioButtonMenuItem sectionsMenuItems [];
	private String nameOfSections []=Utility.getSectionsName();
	
	private JMenu menuThird=new JMenu("Info");
	private JMenuItem menuItemAbout = new JMenuItem("About");
	private JMenuItem menuItemControl = new JMenuItem("Control");
	
	private Font customFont = new Font( "Serif", Font.PLAIN, 20 ) ;
	private Font newFont = new Font("Verdana", 0, 14);
	// системные переменные
	
	
	private String iconPath=Utility.getEngCoachProperty("icon");


	private boolean transcryptIsSet=false;
	
	private EngCoach eng=new EngCoach(firstField, secondField);
	

	public EngCoachGUI()
	{
		super("EngCoach");
		//setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(pan);
		setFonts(newFont);
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// set the icon
		ImageIcon duke = new ImageIcon(iconPath) ;
		setIconImage(duke.getImage());

		
		pan.setFocusable(true);
		
		initMenuComponents();
		
		initBorderLayoutComponents();
		
		
		// задает размер окна (если удалить строку, то элементы на Panel слипнуться)
		setSize(400, 230);                                        									
		
		// Frame в центр экрана
		setLocationRelativeTo(null);                           
		setVisible(true);
		
		nextBut.addActionListener(this);
				
	}
	
	private void initBorderLayoutComponents(){
		firstField.setHorizontalAlignment(JTextField.CENTER);
		secondField.setHorizontalAlignment(JTextField.CENTER);
		transcryptField.setHorizontalAlignment(JTextField.CENTER);
		
		firstField.setFont(customFont);
		secondField.setFont(customFont);
		transcryptField.setFont(customFont);
		
		panForFields.add(firstField);
		panForFields.add(secondField);
		panForFields.add(transcryptField);
		
		panForFields.getComponent(2).setVisible(false);
		
		pan.add(panForFields, BorderLayout.CENTER);
		
		JPanel pan2=new JPanel(new FlowLayout(FlowLayout.CENTER));
		pan2.add(nextBut);
		pan.add(pan2, BorderLayout.SOUTH);
	}
	
	private void initMenuComponents(){
		radBut1.setSelected(true);
		
		menuBar.add(menuFirst);                 // добавляем меню в бар
		group.add(radBut1);						// добавляем радкнопку в группу
		menuFirst.add(radBut1);					// добавлем радкнопку в меню
		group.add(radBut2);
		menuFirst.add(radBut2);
		menuFirst.addSeparator();				// рисуем разделительную черту
		menuFirst.add(setTranscryptMenuItem);
		menuFirst.addSeparator();
		menuFirst.add(menuItemFirstOne);		// добавляем в меню Item 
		menuFirst.add(menuItemAddNewSection);
		menuFirst.add(menuItemFirstAddingContent);
		menuFirst.add(menuItemSetSourcePath);
		
		menuBar.add(menuSecond);
		
		setSectionsMenuItems(Integer.parseInt(Utility.getEngCoachProperty("sections")));
		
		menuBar.add(menuThird);
		menuThird.add(menuItemAbout);
		menuThird.add(menuItemControl);
	
		// добавляем бар со всеми приблудами на панель
		pan.add(menuBar);
		
		// это строка, по видимому, устанавливает панель для меню
		setJMenuBar(menuBar);
		
		radBut1.addItemListener(this);
		radBut2.addItemListener(this);
		
		menuItemFirstOne.addActionListener(this);
		setTranscryptMenuItem.addActionListener(this);
		menuItemAddNewSection.addActionListener(this);
		menuItemFirstAddingContent.addActionListener(this);
		menuItemSetSourcePath.addActionListener(this);
		
		menuItemAbout.addActionListener(this);
		menuItemControl.addActionListener(this);
	}
	
	
	
	
	private void setFrameSizeForWordSection(){
		setSize(400, 230);  
		setLocationRelativeTo(null);
	}
	
	private void setNormalFrameSize(){
		setSize(850, 230);
		setLocationRelativeTo(null);
	}
	
	
	private void setSectionsMenuItems(int number) {
		
		sectionsMenuItems=new JRadioButtonMenuItem[number];
		for (int i = 0; i < sectionsMenuItems.length; i++) {
			sectionsMenuItems[i]=
					new JRadioButtonMenuItem(Utility.getEngCoachProperty("section"+(i+1)));
			group2.add(sectionsMenuItems[i]);
			menuSecond.add(sectionsMenuItems[i]);
			
			sectionsMenuItems[i].addItemListener(this);
		}
		
		sectionsMenuItems[1].setSelected(true);
	}

	
	private void setFonts(Font f){
		
		UIManager.put("Label.font", f);
		UIManager.put("TextField.font", f);
		UIManager.put("Button.font", f);
		UIManager.put("ComboBox.font", f);
		UIManager.put("MenuBar.font", f);
	}
	
	/*
	 * this method takes a boolean value
	 * if true then show trascryption field and check the checkbox
	 * if false then hide trascryption field and uncheck the checkbox
	 * */
	private void changeTranscryptionFeature(boolean comand){
		if (comand) {
			panForFields.getComponent(2).setVisible(true);
			setTranscryptMenuItem.setSelected(true);
		} else {
			panForFields.getComponent(2).setVisible(false);
			setTranscryptMenuItem.setSelected(false);
		}
	}
	
	public void actionPerformed(ActionEvent event)	
	{
		
		if(event.getSource()==nextBut)		// Кнопка "Дальше"	
		{
			eng.setWordInField();
			
			// show transcryption
			if (transcryptIsSet==false) {
				transcryptIsSet=true;
				transcryptField.setText("");
			} else {
				transcryptField.setText(TranscriptionService.getTranscription(eng.getEnglishWord()));
				transcryptIsSet=false;
			}
		}	
		
		// показ набора слов через короткий инт времени
		if(event.getSource()==menuItemFirstOne) {
			FastTrainGUI gui=new FastTrainGUI(this);			
		}
		
		// add/remove transcryption field
		if(event.getSource()==setTranscryptMenuItem){
			if(setTranscryptMenuItem.getState())  
				// if checked show field for transcryptions
				changeTranscryptionFeature(true);
			else 
				// hide this field
				changeTranscryptionFeature(false);
		}
		
		// Кнопка "Add/Change section"
		if (event.getSource()==menuItemAddNewSection) {
			AddChangeSectionGUI a=new AddChangeSectionGUI();
		}
		
		// Кнопка "Добавить новый контент"
		if(event.getSource()==menuItemFirstAddingContent) {			
			AddNewContentGUI newCont=new AddNewContentGUI();
		}
		
		
		// change path to the source file
		if (event.getSource()==menuItemSetSourcePath) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			
			// if a file was chosen
			if(returnVal==0){
				File f=fc.getSelectedFile();
				Utility.setPathToSourceFile(f.getPath());
			}
		}
		
		
		

		if(event.getSource()==menuItemAbout)
		{
			JOptionPane.showMessageDialog(this,
			"Программа предназначена для помощи\n"
			+ "изучающим иностранные языки\n\n"
			+ "Версия программы: 5.0.1\n"
			+ "Создатель программы — Курбатов Алексей",
			"About", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		if(event.getSource()==menuItemControl)
		{
			JOptionPane.showMessageDialog(this,
			"• Язык оригинала\n"
			+ "В программе существует возможность установить язык оригинала при\n"
			+ "переводе в разделе \"Settings\": либо с Rus "+
			"на Eng, либо наоборот.\n• Можно выбрать раздел для тренировки в меню\"Set section\":\n"+ 
			"- All together — слова из всех разделов\n"+
			"- Words — в этом разделе только слова (почти)\n"+
			"- Introd & others — тут всякие полезные штуки для разговора\n"+
			"- Other sentences — в этом разделе только предложения, скаченные из интернета\n"+
			"- Phrasal verbs — тут только фразовые глаголы\n"+
			"- My sentences — тут собраны предложения, которые автор данной программы\n"
			+ "посчитал очень полезными и жизненными\n"
			+ "• Fast train\n"
			+ "Это режим для тренировки быстрого перевода слов и преложений. Возможно\n"
			+ "установить интервал между показом слов (предложений).\n"+
			"• Добавление новых слов и предложений\n"
			+ "В файл, где хранятся слова и предложения, можно добавить новые данные.\n"
			+ "Для этого нужно открыть меню Settings, затем нажать на Add new content.\n"
			+ "Далее следовать здравому смыслу\n"
			+ "• Исходный файл\n"
			+ "Чтобы изменить путь к исходному файлу нужно открыть меню Settings, затем\n"
			+ "нажать на Change path to source file и выбрать нужный файл.\n"
			+ "",
			"Control", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	
	
	public void itemStateChanged(ItemEvent event)
	{
		
		
		if(event.getItemSelectable()==radBut1) {
			// translate from Eng to Rus
			eng.setOriginalLang(false);
		}
		
		// in the first line will be set Rus text
		if(event.getItemSelectable()==radBut2) {
			eng.setOriginalLang(true);
		}
		
		
		
		// to chose the first section called 'all together'
		if(event.getItemSelectable()==sectionsMenuItems[0]) {
			// in order to all content get the first mark and the last one
			eng.setBoundaries(Utility.getBoundary(0), 
					          Utility.getBoundary(Utility.getBoundaries().length-1));
		}
		
		
		// listen to each sections' item by iterating the array sectionsMenuItems
		for (int i = 0; i < sectionsMenuItems.length; i++) {
			
			
			if(event.getSource()==sectionsMenuItems[0] && 
			   event.getStateChange()==ItemEvent.SELECTED) {
				
				//System.out.println("Выбран неподдерживаемый в этом цикле раздел");
				
				setNormalFrameSize();
				changeTranscryptionFeature(false);
				break;
			}
			
			// triggers when a certain item is selected
			if(event.getSource()==sectionsMenuItems[i] && 
			   event.getStateChange()==ItemEvent.SELECTED) {
				
				// make the window width more narrow
				// when the section Words is selected
				if(event.getSource()==sectionsMenuItems[1]){
					setFrameSizeForWordSection();
				} else {
					setNormalFrameSize();
					
					// hide transcryption field as it is 
					// unuseful with these sections
					changeTranscryptionFeature(false);
				}
				
				
				System.out.println("Передано число - "+i);
				eng.setBoundaries(i-1);
				break;
			}
		}
	}
	
	public void keyPressed(KeyEvent e3){}
	public void keyTyped(KeyEvent e3){}
	public void keyReleased(KeyEvent e3)			// создание доп клавиш для переключения
	{
//		System.out.println(e3.getKeyCode());
//		
//		if(e3.getKeyCode()==32||e3.getKeyCode()==10)
//		{
//			getWord();
//		}
	}
	
}



