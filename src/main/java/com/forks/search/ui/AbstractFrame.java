package main.java.com.forks.search.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * @author Raman_Pliashkou
 */
public class AbstractFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private int defaultWidth;

    private int defaultHeight;

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public static final int CENTRE = 1;

    public void setDefaultSize() {
        setSize(defaultWidth, defaultHeight);
    }

    public void setAlignment(int type) {
        if (type == CENTRE) {

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int fWidth = getSize().width;
            int fHeight = getSize().height;
            int x = (dim.width - fWidth) / 2;
            int y = (dim.height - fHeight) / 2;
            setLocation(x, y);
        }
    }

    public AbstractFrame() {
        setResizable(false);
    }
}
