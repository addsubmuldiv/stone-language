package chapA;


import stone.CodeDialog;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author: Lighters_c
 * @Discrpition: Reader类的read()方法，每次读取一个字符，返回的是字符的ascii码，所以需要强制转换为char类型，
 * Lexer类有一个字段，用来作为缓冲区，如果这个字段不为NULL，那么就说明这个字段的字符并没有被正确读取进来，这个
 * 时候，不会调用reader.read()把新的字符读进来，以此防止当自动机进入停止状态的时候，多读一个字符被丢失，在read()
 * 方法的最后也有一个ungetChar()的调用，也是为了防止同样的问题
 * @Date: Created in 20:41 2017/10/9
 * @Modified_by:
 */
public class Lexer {
    private Reader reader;
    private static final int EMPTY=-1;
    private int lastChar=EMPTY;
    public Lexer(Reader r) { reader=r; }
    private int getChar() throws IOException {
        if(lastChar==EMPTY)
            return reader.read();
        else {
            int c=lastChar;
            lastChar=EMPTY;
            return c;
        }
    }
    private void ungetChar(int c) {lastChar=c;}
    public String read() throws IOException {
        StringBuilder sb=new StringBuilder();
        int c;
        do {
            c=getChar();
        } while(isSpace(c));
        if(c<0)
            return null;
        else if(isDigit(c)) {
            do {
                sb.append((char)c);
                c=getChar();
            } while(isDigit(c));
        }
        else if(isLetter(c)) {
            do {
                sb.append((char)c);
                c=getChar();
            } while(isLetter(c)||isDigit(c));
        }
        else if(c == '=') {
            c=getChar();
            if(c=='=')
                return "==";
            else {
                ungetChar(c);
                return "=";
            }
        }
        else
            throw new IOException();
        //防止当自动机进入停止状态的时候，它就不读了，然后丢失掉多读的字符
        if(c>=0)
            ungetChar(c);

        return sb.toString();
    }

    private static boolean isDigit(int c) { return '0' <= c && c <= '9'; }
    private static boolean isSpace(int c) { return 0 <= c && c <= ' '; }
    private static boolean isLetter(int c) {
        return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z';
    }
    public static void main(String[] args) throws Exception {
        Lexer l = new Lexer(new CodeDialog());
        for (String s; (s = l.read()) != null; )
            System.out.println("-> " + s);
    }
}
