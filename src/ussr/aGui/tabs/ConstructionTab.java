package ussr.aGui.tabs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of the tab called "1 Step: Construct Robot".  
 * @author Konstantinas
 */
public class ConstructionTab extends Tabs {



	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	/**
	 * Defines visual appearance of the tab called "1 Step: Construct Robot".
	 * @param tabTitle, the title of the tab
	 * @param jmeSimulation, the physical simulation.
	 * @param firstTabbedPane,
	 */
	public ConstructionTab(boolean firstTabbedPane,String tabTitle,JMESimulation jmeSimulation){
		this.firstTabbedPane = firstTabbedPane;
		this.tabTitle = tabTitle;
		this.jmeSimulation = jmeSimulation;
		/*instantiate new panel, which will be the container for all components situated in the tab*/		
		this.jComponent = new javax.swing.JPanel(new GridBagLayout());
		//this.gridBagConstraints =  new GridBagConstraints();
		//gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		initComponents();
	}



	/**
	 * Initializes the visual appearance of all components in the tab.
	 * Follows Strategy  pattern.
	 */
	public void initComponents(){


		jLabel10002 = new javax.swing.JLabel();		
		jLabel10002.setText("Shortcut:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jComponent.add(jLabel10002,gridBagConstraints);

		final ButtonGroup buttonGroup = new ButtonGroup() ;

		button1 =  new JRadioButton();
		button1.setText("ATRON");		
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(button1,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		jComponent.add(button1,gridBagConstraints);
		buttonGroup.add(button1);

		final javax.swing.AbstractButton button2 =  new JRadioButton();
		button2.setText("Odin");
		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(button2,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		jComponent.add(button2,gridBagConstraints);
		buttonGroup.add(button2);


		final javax.swing.AbstractButton button3 =  new JRadioButton();
		button3.setText("MTran");
		button3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(button3,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 10; 
		jComponent.add(button3,gridBagConstraints);
		buttonGroup.add(button3);

		javax.swing.AbstractButton button4 =  new JRadioButton();
		button4.setText("CKbot");
		button4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButtonGroupActionPerformed(button1,jmeSimulation);
			}
		});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		//gridBagConstraints.ipady = 40; 
		jComponent.add(button4,gridBagConstraints);
		buttonGroup.add(button4);		

		jLabel1000 = new javax.swing.JLabel();		
		jLabel1000.setText("Next select connectors (black and white geometric shapes).");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(5,0,0,0);  //top padding
		//gridBagConstraints.weighty = 0.5;   //request any extra vertical space
		jComponent.add(jLabel1000,gridBagConstraints);
		jLabel1000.setVisible(false);		


		jLabel10001 = new javax.swing.JLabel();		
		jLabel10001.setText("When done with constructing, go to the next tab.");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		jComponent.add(jLabel10001,gridBagConstraints);
		jLabel10001.setVisible(false);
		
		
		jLabel10003 = new javax.swing.JLabel();		
		jLabel10003.setText("Additional:");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		jComponent.add(jLabel10003,gridBagConstraints);
   


		//javax.swing.AbstractButton button =  new JButton();



		//button.setText("ODIN");

		//buttonGroup.add(button);
		//buttonGroup.add(button1);

		//jComponent.add(buttonGroup);

		/*Instantiation of tab components
		jComboBox2 = new javax.swing.JComboBox();		
		jComboBox3 = new javax.swing.JComboBox();		
		jComboBox1000 = new javax.swing.JComboBox();

		jLabel1000 = new javax.swing.JLabel();	

		Definition of visual appearance for each instantiated component
		jLabel1000.setText("1) Choose modular robot:");
		Add your component into panel.
		jComponent.add(jLabel1000);

		jComboBox1000.setToolTipText("Supported modular robots");
		jComboBox1000.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "ODIN", "MTRAN", "CKBOT" }));
		jComboBox1000.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox1ActionPerformed(jComboBox1000,jmeSimulation);
			}
		});
		jComponent.add(jComboBox1000);

		javax.swing.JLabel jLabel999 = new javax.swing.JLabel();
		jLabel999.setText(" and locate it in the simulation environment;");
		jComponent.add(jLabel999);

		javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
		jLabel3.setText("2) Choose module rotation (in the one of the following three components)");
		jComponent.add(jLabel3);       

		jComboBox2.setToolTipText("Standard rotations of modules");
		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
		jComboBox2.setEnabled(false);
		jComboBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jComboBox2ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jComboBox2);

		javax.swing.JButton jButton5 = new javax.swing.JButton();
		jButton5.setToolTipText("Rotate opposite");        
		jButton5.setText("Opposite");        
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton5ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jButton5);

		javax.swing.JButton jButton6 = new javax.swing.JButton();
		jButton6.setToolTipText("Variation of module rotations");
		jButton6.setText("Variation");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton6ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jButton6);

		javax.swing.JLabel jLabel998 = new javax.swing.JLabel();
		jLabel998.setText(" and select the module in the simulation environment to apply it to;");
		jComponent.add(jLabel998);

		javax.swing.JLabel jLabel997 = new javax.swing.JLabel();
		jLabel997.setText(" 3)Choose one of the following three construction tools:");
		jComponent.add(jLabel997);

		javax.swing.JButton jButton7 = new javax.swing.JButton();
		jButton7.setToolTipText("Variation of module rotations");
		jButton7.setText("On Selected Connector");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConstructionTabController.jButton7ActionPerformed(jmeSimulation);
			}
		});
		jComponent.add(jButton7);

		javax.swing.JLabel jLabel996 = new javax.swing.JLabel();
		jLabel996.setText("here select connector on the module(white or black geometric shapes) ");
		jComponent.add(jLabel996);


		jComboBox3.setToolTipText("Numbers of connectors on the module");
		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
		jComboBox3.setEnabled(false);
		jComboBox3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//ConstructionTabController.jComboBox3ActionPerformed(jComboBox1000,jmeSimulation);
			}
		});
		jComponent.add(jComboBox3);*/



	}


	public static javax.swing.JComboBox getJComboBox2() {
		return jComboBox2;
	}

	public static javax.swing.JComboBox getJComboBox3() {
		return jComboBox3;
	}

	public static javax.swing.AbstractButton getButton1() {
		return button1;
	}
	
	public static javax.swing.JLabel getJLabel1000() {
		return jLabel1000;
	}
	
	public static javax.swing.JLabel getJLabel10001() {
		return jLabel10001;
	}

	/*Declaration of tab components*/
	private static javax.swing.JComboBox jComboBox2;	
	private static javax.swing.JComboBox jComboBox3;
	private static javax.swing.JLabel jLabel1000;
	

	private static javax.swing.JLabel jLabel10001;
	private static javax.swing.JLabel jLabel10002;
	private static javax.swing.JLabel jLabel10003;
	

	private static javax.swing.JComboBox jComboBox1000;

	private static  javax.swing.AbstractButton button1;






}