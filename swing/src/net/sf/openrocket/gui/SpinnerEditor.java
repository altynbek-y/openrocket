package net.sf.openrocket.gui;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Editable editor for a JSpinner.  Simply uses JSpinner.DefaultEditor, which has been made
 * editable.  Why the f*** isn't this possible in the normal API?
 * 
 * @author Sampo Niskanen <sampo.niskanen@iki.fi>
 */

//public class SpinnerEditor extends JSpinner.NumberEditor {
public class SpinnerEditor extends JSpinner.DefaultEditor {

	public SpinnerEditor(JSpinner spinner) {
		super(spinner);
		//super(spinner,"0.0##");
		getTextField().setEditable(true);
		
		DefaultFormatterFactory dff = (DefaultFormatterFactory) getTextField().getFormatterFactory();
		DefaultFormatter formatter = (DefaultFormatter) dff.getDefaultFormatter();
		formatter.setOverwriteMode(false);


		// Add listeners to select all the text when the field is focussed
		{
			getTextField().addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					selectAllText();
				}

				@Override
				public void focusLost(FocusEvent e) {
				}
			});

			getTextField().addMouseListener(new MouseListener() {
				private boolean isFocussed = false;    // Checks whether the text field was focussed when it was clicked upon

				@Override
				public void mouseClicked(MouseEvent e) {
					// If the text field was focussed when it was clicked upon instead of e.g. tab-switching to gain focus,
					// then the select all action from the focus listener is ignored (it is replaced by a cursor-click event).
					// So if we detect such a focus change, then redo the select all action.
					if (!isFocussed) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								JTextField tf = (JTextField) e.getSource();
								tf.selectAll();
							}
						});
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					JTextField tf = (JTextField) e.getSource();
					isFocussed = tf.hasFocus();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});
		}
	}

	/**
	 * Constructor which sets the number of columns in the editor.
	 * @param spinner
	 * @param cols
	 */
	public SpinnerEditor(JSpinner spinner, int cols ) {
		this(spinner);
		getTextField().setColumns(cols);
	}

	/**
	 * Highlights all the text in the text field.
	 */
	private void selectAllText() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getTextField().selectAll();
			}
		});
	}
	
}
