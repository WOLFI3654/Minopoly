package de.wolfi.gui.minopoly;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.inventory.meta.ItemMeta;

import de.wolfi.minopoly.components.Minopoly;

public class Main {

	private JFrame frame;

	private File currentFile = null;
	private Minopoly currentGame = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.put("FileChooser.useSystemExtensionHiding", false);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Main window = new Main();

					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private JTree game;

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		splitPane.setContinuousLayout(true);
		frame.getContentPane().add(splitPane);

		JTree tree = new JTree();
		splitPane.setRightComponent(tree);

		game = new JTree();
		splitPane.setLeftComponent(game);
		game.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("They call me the cool one ;D")));

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem open = new JMenuItem("Open...");

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
					JFileChooser chooser = new JFileChooser("Select Minopoly bin...");
					chooser.setFileFilter(new FileFilter() {

						@Override
						public String getDescription() {
							return "minopoly.bin";
						}

						@Override
						public boolean accept(File paramFile) {
							return paramFile.isDirectory() || paramFile.getName().endsWith(".bin");
						}
					});
					chooser.showOpenDialog(null);
					File file = chooser.getSelectedFile();
					if(file != null){
						loadFile(file);
					}

			}
		});
		mnNewMenu.add(open);

		JProgressBar progressBar = new JProgressBar();
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				progressBar.setValue((int) (Math.random() * 100));
			}
		});
		progressBar.setValue(50);
		progressBar.setToolTipText("Why is this even possible?");
		mnNewMenu.add(progressBar);

		JMenuItem save = new JMenuItem("Save");
		mnNewMenu.add(save);

		JMenuItem save_as = new JMenuItem("Save As...");
		mnNewMenu.add(save_as);

	}

	private void loadFile(File file) {
		try {
			this.currentFile = file;
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object ino = in.readObject();
			assert ino instanceof Minopoly;
			this.currentGame = (Minopoly) ino;
			updateTree();
			in.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateTree() {

		DefaultTreeModel mode = new DefaultTreeModel(new DefaultMutableTreeNode(currentGame));
		game.setModel(mode);
	}

}
