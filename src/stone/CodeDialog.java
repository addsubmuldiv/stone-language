package stone;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;


/**
 * 用于输入stone代码的窗口
 */
public class CodeDialog extends Reader {
    private String buffer = null;
    private int pos=0;


    @Override
    public int read(@NotNull char[] cbuf, int off, int len) throws IOException {
        if(buffer==null) {
            String in = showDialog();
            if(in==null)
                return -1;
            else{
                print(in);
                buffer=in+'\n';
                pos=0;
            }
        }

        int size=0;
        int length=buffer.length();
        while(pos<length&&size<len)
            cbuf[off+size++]=buffer.charAt(pos++);
        if(pos==length)
            buffer=null;

        return size;
    }

    protected void print(String s) {
        System.out.println(s);
    }

    @Override
    public void close() throws IOException {}

    /**
     * 显示窗口，获取编辑区输入的字符串并返回
     * @return
     */
    protected String showDialog() {
        JTextArea area = new JTextArea(20,40);
        JScrollPane pane=new JScrollPane(area);

        /*Set lookAndFeel of the dialog*/
        String lookAndFeel =
                UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        int result=JOptionPane.showOptionDialog(null,pane,"input",JOptionPane.OK_CANCEL_OPTION,
                                                                JOptionPane.PLAIN_MESSAGE,null,null,null);
        if(result==JOptionPane.OK_OPTION)
            return area.getText();
        else
            return null;
    }

    /**
     * 用于从文件中读取stone代码
     * @return
     * @throws FileNotFoundException
     */
    public static Reader file() throws FileNotFoundException {
        JFileChooser chooser=new JFileChooser();
        if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
            return new BufferedReader(new FileReader(chooser.getSelectedFile()));
        else
            throw new FileNotFoundException("no file specified");
    }

}



















