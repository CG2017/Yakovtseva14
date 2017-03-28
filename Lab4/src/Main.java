import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JFrame
{
    private String[] acceptableTags = {
            "File Name", "File Size", "X Resolution", "Y Resolution", "Image Height", "Image Width",
            "Bits Per Sample", "Color Planes", "Color Space", "Compression", "File Modified Date",
            "Flash", "Orientation", "Photometric Interpretation", "Resolution Info", "Resolution Unit",
            "Rows Per Strip", "Samples Per Pixel", "Saturation"
    };


    private class MyTable extends JTable
    {
        MyTable(Object[][] rowData, Object[] columnNames) {
            super(rowData, columnNames);
        }

        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component = super.prepareRenderer(renderer, row, column);
            int rendererWidth = component.getPreferredSize().width;
            TableColumn tableColumn = getColumnModel().getColumn(column);
            tableColumn.setPreferredWidth(
                    Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
            return component;
        }
    }


    private JScrollPane scroller;


    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lab4");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton chooseImgButton = new JButton("Choose folder/image");
        chooseImgButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseImgButton.addActionListener(e -> chooseLocation());

        add(chooseImgButton);
        scroller = new JScrollPane();
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scroller);
        pack();
        chooseLocation();
    }

    private void chooseLocation() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showOpenDialog(Main.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFile().listFiles();
            if (files != null) {
                formTable(files);
            }
        }
    }

    private void formTable(File[] files) {
        Set<String> columnNames = new HashSet<>(Arrays.asList(acceptableTags));
        List<Map<String, String>> info = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Metadata metadata = ImageMetadataReader.readMetadata(file);
                    Map<String, String> fileInfo = new HashMap<>();
                    for (Directory directory : metadata.getDirectories()) {
                        for (Tag tag : directory.getTags()) {
//                            System.out.format("%s = %s\n", tag.getTagName(), tag.getDescription());
//                            columnNames.add(tag.getTagName());
                            if (columnNames.contains(tag.getTagName())) {
                                fileInfo.put(tag.getTagName(), tag.getDescription());
                            }
                        }
                        if (directory.hasErrors()) {
                            for (String error : directory.getErrors()) {
                                System.err.format("ERROR: %s", error);
                            }
                        }
                    }
                    info.add(fileInfo);
                } catch (ImageProcessingException | IOException ex) {
//                    ex.printStackTrace();
                }

                System.out.println();
                System.out.println();
                System.out.println();
            }
        }

        displayData(acceptableTags, info);
    }


    private void displayData(Set<String> columnNames, List<Map<String, String>> info) {
        String[] columns = columnNames.toArray(new String[0]);
        System.out.println(Arrays.toString(columns));
        displayData(columns, info);
    }

    private void displayData(String[] columns, List<Map<String, String>> info) {

        String[][] data = new String[info.size()][columns.length];

        for (int i = 0; i < info.size(); i++) {
            for (int j = 0; j < columns.length; j++) {
                data[i][j] = info.get(i).getOrDefault(columns[j], "---");
            }
        }


        JTable infoTable = new MyTable(data, columns);
        infoTable.setFillsViewportHeight(true);
        infoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn column;
        for (int i = 0; i < infoTable.getColumnCount(); i++) {
            column = infoTable.getColumnModel().getColumn(i);
            column.setResizable(true);
        }


        scroller.setViewportView(infoTable);
        pack();
        revalidate();
    }

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            }
        }

        JFrame frame = new Main();
        frame.pack();
        frame.setVisible(true);
    }
}
