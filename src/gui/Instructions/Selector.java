package gui.Instructions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class Selector extends JPanel{
	private final int DEFAULT_OPTION = 0;
	private final int DEFAULT_NUM_OPTION = 24;
	
	private String selection;
	private String[] options;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4992769687694255933L;

	/**
	 * constructor
	 * @param options. A string array representing the options.
	 */
	public Selector(String[] options) {
		this.options = new String[options.length];
		
		for (int i = 0; i < options.length; i ++) {
			this.options[i] = options[i];
		}
		
		JComboBox<String> selections = new JComboBox<String>(this.options);
		selections.addActionListener(
				/**
				 * ActionListner object to detect button clicks.
				 */
				new ActionListener() {
					/**
					 * When Selector is changed is clicked it calls the onTap function.
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						selection = (String) selections.getSelectedItem();
					}
				}
			);
		
		add(selections, BorderLayout.CENTER);
		selection = options[DEFAULT_OPTION];
		
		if (Character.isDigit(selection.charAt(0))) {
			selection = options[DEFAULT_NUM_OPTION];
			selections.setSelectedIndex(DEFAULT_NUM_OPTION);
		}
	}
	
	/**
	 * Returns the current selection
	 * @return string representing the selection.
	 */
	public String getSelection () {
		if(Character.isDigit(selection.charAt(0))) {
			return selection;
		} else {
			return selection.toLowerCase();
		}
	}
	
	/**
	 * Used by the layout manager.
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(100, 500);
	}
	
	/**
	 * Used by the layout manager.
	 */
	@Override
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}
}