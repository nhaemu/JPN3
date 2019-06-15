package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Test {

	private static Tree tree;

	public static void main(String[] args) {
		try {
			File inputFile = new File("JP_Issue_vocab.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("lesson");

			Display display = new Display();

			Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE & ~SWT.MIN & ~SWT.MAX));
			shell.setSize(800, 500);

			GridLayout layout = new GridLayout(1, true);
			shell.setLayout(layout);

			Monitor primary = display.getPrimaryMonitor();
			Rectangle bounds = primary.getBounds();
			Rectangle rect = shell.getBounds();

			int x = bounds.x + (bounds.width - rect.width) / 2;
			int y = bounds.y + (bounds.height - rect.height) / 3;

			shell.setLocation(x, y);

//			Composite weekNDays = new Composite(shell, SWT.NONE);
//			weekNDays.setLayout(new GridLayout(2, true));
//			weekNDays.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//
//			GridLayout groupLayout = new GridLayout();
//			groupLayout.marginHeight = 10;
//
//			Group weekGroup = new Group(weekNDays, SWT.NONE);
//			weekGroup.setLayout(groupLayout);
//			weekGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//			weekGroup.setText("Choose Week");
//
//			Group dayGroup = new Group(weekNDays, SWT.NONE);
//			dayGroup.setLayout(groupLayout);
//			dayGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//			dayGroup.setText("Choose Day");
//
//			Combo weekCombo = new Combo(weekGroup, SWT.READ_ONLY | SWT.NONE);
//			weekCombo.setItems(new String[] { "1", "2", "3", "4", "5", "6" });
//			weekCombo.select(0);
//
//			Combo dayCombo = new Combo(dayGroup, SWT.READ_ONLY);
//			dayCombo.setItems(new String[] { "1", "2", "3", "4", "5", "6", "extra" });
//			dayCombo.select(0);
//
//			weekCombo.pack();
//			dayCombo.pack();

			// Button startButton = new Button(shell, SWT.NONE);
			// startButton.setText("Start");

			List<Word> wordList = new ArrayList<Word>();

			GridData gd = new GridData(GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL);

			tree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			tree.setLayoutData(gd);
			TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
			treeColumn.setText("");
			treeColumn.setWidth(900);

			FontData[] fontData = tree.getFont().getFontData();
			fontData[0].setHeight(14);
			fontData[0].setName("游ゴシック");
			tree.setFont(new Font(display, fontData[0]));

			// TreeColumn treeColumn1 = new TreeColumn(tree, SWT.LEAD);
			// treeColumn1.setText("");
			// treeColumn1.setWidth(130);

//			weekCombo.addSelectionListener(new SelectionListener() {
//
//				@Override
//				public void widgetSelected(SelectionEvent arg0) {
//
//					populateData(nodeList, weekCombo, dayCombo, wordList);
//				}
//
//				@Override
//				public void widgetDefaultSelected(SelectionEvent arg0) {
//				}
//			});
//
//			dayCombo.addSelectionListener(new SelectionListener() {
//
//				@Override
//				public void widgetSelected(SelectionEvent arg0) {
//					populateData(nodeList, weekCombo, dayCombo, wordList);
//				}
//
//				@Override
//				public void widgetDefaultSelected(SelectionEvent arg0) {
//				}
//			});

			// startButton.addListener(SWT.Selection, new Listener() {
			//
			// @Override
			// public void handleEvent(Event arg0) {
			// populateData(nodeList, weekCombo, dayCombo, wordList);
			// }
			//
			// });

			// Table table = new
			// Table(shell,SWT.CHECK|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
			// for (int i = 0; i < wordList.size(); i++) {
			// TableItem item = new TableItem(table, SWT.NONE);
			// item.setText(wordList.get(i).getWord());
			// }

			populateData(nodeList, wordList);

			shell.layout();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			display.dispose();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void populateData(NodeList nodeList, List<Word> wordList) {
		wordList.clear();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;

					Word w = new Word();
					w.setWord(e.getElementsByTagName("kaki").item(0).getTextContent());
					w.setValue(e.getElementsByTagName("yomi").item(0).getTextContent());
					w.setValue(e.getElementsByTagName("meaning").item(0).getTextContent());

					wordList.add(w);
			}
		}

		for (TreeItem item : tree.getItems()) {
			item.dispose();
		}
		
		wordList = wordList.stream().sorted(Comparator.comparing(Word::getWord)).collect(Collectors.toList());
		for (int i = 0; i < wordList.size(); i++) {

			TreeItem item = new TreeItem(tree, 0);
			item.setText(wordList.get(i).getWord());

			for (int j = 0; j < wordList.get(i).getValue().size(); j++) {
				TreeItem c = new TreeItem(item, 0);
				c.setText(wordList.get(i).getValue().get(j));
			}
			// TreeItem c = new TreeItem(item, 0);
			// c.setText(new String[] {wordList.get(i).getValue().get(0),
			// wordList.get(i).getValue().get(1)});
		}
		tree.setVisible(true);
		tree.setFocus();
	}
}
