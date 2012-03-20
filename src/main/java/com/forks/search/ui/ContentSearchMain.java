/**
 *
 */
package main.java.com.forks.search.ui;

import java.awt.EventQueue;

import main.java.com.forks.search.ui.frame.MainFrame;
import main.java.com.forks.search.ui.panel.TPanel;

/**
 * @author Raman_Pliashkou
 */
public class ContentSearchMain {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.add(new TPanel());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
