/**
 *
 */
package main.java.com.forks.search.ui.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import main.java.com.forks.search.ui.AbstractFrame;

/**
 * @author Raman_Pliashkou
 */
public class MainFrame extends AbstractFrame {

    private static final long serialVersionUID = 1L;

    private int defaultWidth = 315;

    private int defaultHeight = 470;

    private Dimension dimension = new Dimension();

    /**
     * Create the frame.
     */
    public MainFrame() {

        setTitle("search");
        setDefaultSize();
        dimension.setSize(defaultWidth, defaultHeight);
        setMinimumSize(dimension);
        setAlignment(MainFrame.CENTRE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
    }

}
