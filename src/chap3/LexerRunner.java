package chap3;

import stone.CodeDialog;
import stone.Lexer;
import stone.ParseException;
import stone.Token;

/**
 * 仅用于词法分析器的测试
 */
public class LexerRunner {
    public static void main(String[] args) throws ParseException {
        Lexer lexer=new Lexer(new CodeDialog());
        for(Token t;(t= lexer.read())!= Token.EOF;)
            System.out.println("=>"+t.getText());
    }
}
