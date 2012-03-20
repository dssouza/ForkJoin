/**
 *
 */
package main.java.com.forks.search.ui.panel;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.*;

import main.java.com.forks.search.*;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.*;

/**
 * @author Raman_Pliashkou
 */
public class TPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField eFolderName;

    private JTextField eSearchName;

    private ForkJoinPool forkJoinPool;

    int i = 0;

    /**
     * Create the panel.
     */
    public TPanel() {
        setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("min(100dlu;pref)"),
                FormFactory.RELATED_GAP_COLSPEC,},
            new RowSpec[] {
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                RowSpec.decode("max(160dlu;pref)"),
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblFolder = new JLabel("Folder: ");
        add(lblFolder, "2, 2, right, default");

        eFolderName = new JTextField();
        add(eFolderName, "4, 2, left, default");
        eFolderName.setColumns(20);

        JLabel lblSearchWord = new JLabel("Search word:");
        add(lblSearchWord, "2, 4, right, default");

        eSearchName = new JTextField();
        eSearchName.setColumns(20);
        add(eSearchName, "4, 4, left, default");

        JLabel lblFileType = new JLabel("File type:");
        add(lblFileType, "2, 6, right, default");

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "*.all", "*.txt", "*.xml" }));
        add(comboBox, "4, 6, left, default");

        final JButton btnSearch = new JButton("search");
        add(btnSearch, "2, 8");

        final JButton btnStop = new JButton("stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (forkJoinPool != null) {
                    forkJoinPool.shutdownNow();
                    btnSearch.setEnabled(true);
                    btnStop.setEnabled(false);
                }
            }
        });
        add(btnStop, "4, 8, left, default");
        btnStop.setEnabled(false);

        JLabel lblResult = new JLabel("Result:");
        add(lblResult, "2, 10");

        final TextArea tareport = new TextArea();
        add(tareport, "2, 12, 3, 2");
        tareport.setEditable(false);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runnable task = new SwingWorkerTask() {
                    String folderName;

                    String searchWord;

                    long countOfOccurrences = 0L;

                    long countFiles = 0L;

                    long countFolders = 0L;

                    long countFilesOfOccurrences = 0L;

                    List<String> fileOfOccurrences = null;

                    boolean complite = false;

                    long timeStart = 0;

                    long timeStop  = 0;

                    @Override
                    public void init() {

                        folderName = eFolderName.getText();
                        searchWord = eSearchName.getText();
                        if (folderName.length() < 0 || folderName.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Enter folder name.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (searchWord.length() < 0 || searchWord.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Enter word for search.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        tareport.setText("Please wait...\n");
                        btnSearch.setEnabled(false);
                        btnStop.setEnabled(true);
                        timeStart = System.currentTimeMillis();
                    }

                    @Override
                    public void update() {
                        timeStop = System.currentTimeMillis();
                        tareport.setText("");
                        if (!complite) {
                            tareport.append("Caution. Search was stopped." + "\nInformation may be incomplete.\n\n");
                        }
                        tareport.append("Result:\n");
                        tareport.append(countFolders + " folder(s) was found.\n");
                        tareport.append(countFiles + " file(s) was found.\n");
                        tareport.append(countOfOccurrences + " match(es) of occurrence(s) in "
                                + countFilesOfOccurrences + " file(s)\n");
                        tareport.append("files:\n");
                        for (String filename : fileOfOccurrences) {
                            tareport.append("+++" + filename + "\n");
                        }
                        tareport.append("\n\nFinish.\n");
                        tareport.append("time of execution: " + (timeStop-timeStart) + "ms;");
                    }

                    @Override
                    public void finish() {
                        btnSearch.setEnabled(true);
                        btnStop.setEnabled(false);
                        forkJoinPool.shutdown();

                    }

                    @Override
                    public void work() throws InterruptedException {
                        init();
                        try {
                            forkJoinPool = new ForkJoinPool();

                            Folder folder = Folder.fromDirectory(new File(folderName));
                            CompositeIterator<Folder> c = new CompositeIterator<Folder>(folder.createIterator());
                            fileOfOccurrences = new ArrayList<>();

                            try {
                                countOfOccurrences = forkJoinPool.invoke(new FolderSearchTask(folder, searchWord));
                                complite = true;
                            } catch (CancellationException ex) {
                                complite = false;
                            } catch (RejectedExecutionException rex) {
                                complite = false;
                            }
                            Folder f = null;
                            while ((f = c.next()) != null) {
                                for (Document doc : f.getDocuments()) {
                                    if (doc.isOccurrences()) {
                                        countFilesOfOccurrences++;
                                        fileOfOccurrences.add(doc.getName());
                                    }
                                    countFiles++;
                                }
                                countFolders++;
                            }
                            doUpdate();

                        } catch (IOException e) {
                            complite = false;
                        }
                        finish();
                    }
                };
                Thread thread = new Thread(task);
                thread.start();
            }
        });
    }

    /**
     * Extend this class to define an asynchronous task that updates a Swing UI.
     */
    abstract class SwingWorkerTask implements Runnable {
        /**
         * Place your task in this method. Be sure to call doUpdate(), not update(), to show the update after each unit
         * of work.
         */
        public abstract void work() throws InterruptedException;

        /**
         * Override this method for UI operations before work commences.
         */
        public void init() {
        }

        /**
         * Override this method for UI operations after each unit or work.
         */
        public void update() {
        }

        /**
         * Override this method for UI operations after work is completed.
         */
        public void finish() {
        }

        private void doInit() {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    init();
                }
            });
        }

        /**
         * Call this method from work() to show the update after each unit of work.
         */
        protected final void doUpdate() {
            if (done)
                return;
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    update();
                }
            });
        }

        private void doFinish() {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    finish();
                }
            });
        }

        public final void run() {
            try {
                done = false;
                work();
            } catch (InterruptedException ex) {
            } finally {
                done = true;
                doFinish();
            }
        }

        private boolean done;
    }
}
