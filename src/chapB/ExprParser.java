package chapB;

import stone.CodeDialog;
import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.ast.*;

import java.util.Arrays;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 23:16 2017/10/11
 * @Modified_by:
 */
public class ExprParser {
    private Lexer lexer;

    public ExprParser(Lexer p) {
        lexer=p;
    }

    /**
     * 读取一个term(),term()一般为乘/除法表达式或者一些优先级更高的运算式子，然后判断下一个即将读取的是否是
     * 加号或者减号，如果是的话就读取进来，然后再读取运算符(加号/减号)的右操作数，然后把整个加/减法表达式新建
     * 为一个BinaryExpr的AST节点返回
     * @return
     * @throws ParseException
     */
    public ASTree expression() throws ParseException {
        ASTree left=term();
        while(isToken("+") || isToken("-")) {
            ASTLeaf op=new ASTLeaf(lexer.read());
            ASTree right=term();
            left=new BinaryExpr(Arrays.asList(left,op,right));
        }
        return left;
    }

    /**
     * 读取factor,一般这里的factor要么返回数字字面量，要么返回表达式，然后判断下一个即将读取的是不是乘除号
     * 如果是的话，就将其读取，然后再读取右边的factor()，然后把整个乘/除法表达式新建为一个BinaryExpr的AST
     * 节点并返回
     * @return
     * @throws ParseException
     */
    public ASTree term() throws ParseException {
        ASTree left = factor();
        while(isToken("*")||isToken("/")) {
            ASTLeaf op=new ASTLeaf(lexer.read());
            ASTree right= factor();
            left=new BinaryExpr(Arrays.asList(left,op,right));
        }
        return left;
    }

    /**
     * factor()方法先判断即将读入的符号是不是"("，如果是，则调用token()读取左括号,然后按铁路图调用expression()
     * 把括号中间的表达式读取进来，再调用token()读取右括号，返回括号中间的表达式。
     * 如果一开始判断即将读入的不是"("，那就直接读取出来，根据铁路图，读到的一定是数字，所以就新建数字字面量并
     * 返回，出错则抛出异常
     * @return
     * @throws ParseException
     */
    public ASTree factor() throws ParseException {
        if(isToken("(")) {
            token("(");
            ASTree e= expression();
            token(")");
            return e;
        } else {
            Token t=lexer.read();
            if(t.isNumber()) {
                NumberLiteral n = new NumberLiteral(t);
                return n;
            } else {
                throw new ParseException(t);
            }
        }
    }

    /**
     * token()方法用来判断传入的符号从词法分析器中读取的是否一致,实际读取
     * @param name
     * @throws ParseException
     */
    void token(String name) throws ParseException {
        Token t = lexer.read();
        if(!(t.isIdentifier()&&name.equals(t.getText())))
            throw new ParseException(t);
    }

    /**
     * 同token(),但不实际从词法分析器中读取
     * @param name
     * @return
     * @throws ParseException
     */
    boolean isToken(String name) throws ParseException {
        Token t= lexer.peek(0);
        return t.isIdentifier() && name.equals(t.getText());
    }

    public static void main(String[] args) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        ExprParser p = new ExprParser(lexer);
        ASTree t = p.expression();
        System.out.println("=> "+t);
    }

}
