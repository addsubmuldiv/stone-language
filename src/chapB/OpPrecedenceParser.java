package chapB;

import stone.CodeDialog;
import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.ast.ASTLeaf;
import stone.ast.ASTree;
import stone.ast.BinaryExpr;
import stone.ast.NumberLiteral;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 23:34 2017/10/11
 * @Modified_by:
 */
public class OpPrecedenceParser {
    private Lexer lexer;

    /**
     * 这个哈希表是用来存操作符的，键名是操作符，对应的值就是一个Precedence对象，记录优先级和结合性
     */
    protected HashMap<String,Precedence> operators;

    /**
     * value是运算符的优先级，第二个参数表示的是结合性，如果是true那么就是左结合，否则是右结合。
     */
    public static class Precedence {
        int value;
        boolean leftAssoc;
        public Precedence(int v, boolean a) {
            value=v;
            leftAssoc=a;
        }
    }

    public OpPrecedenceParser(Lexer p) {
        lexer = p;
        operators=new HashMap<>();
        operators.put("<",new Precedence(1,true));
        operators.put(">",new Precedence(1,true));
        operators.put("+",new Precedence(2,true));
        operators.put("-",new Precedence(2,true));
        operators.put("*",new Precedence(3,true));
        operators.put("/",new Precedence(3,true));
        operators.put("^",new Precedence(4,false));
    }

    /**
     * 读入左操作数为right,预读操作符，然后调用doShift
     * @return
     * @throws ParseException
     */
    public ASTree expression() throws ParseException {
        ASTree right=factor();
        Precedence next;
        while((next=nextOperator())!=null)
            right=doShift(right,next.value);

        return right;
    }

    /**
     * 传入参数左操作数和中间的操作符的优先级，因为操作符是预读的，所以要先真正把它读进来，然后再把右操作数
     * 读进来right，再预读下一个操作符，如果右边是表达式，那么就把现在的right作为左操作数和预读的操作符
     * 递归调用doShift,否则返回把左操作数，运算符和右操作数组成的语法树，即BinaryExpr
     * @param left
     * @param prec
     * @return
     * @throws ParseException
     */
    private ASTree doShift(ASTree left,int prec) throws ParseException {
        ASTLeaf op = new ASTLeaf(lexer.read());
        ASTree right=factor();
        Precedence next;
        while((next=nextOperator())!=null && rightIsExpr(prec,next))
            right=doShift(right,next.value);

        return new BinaryExpr(Arrays.asList(left,op,right));
    }

    /**
     * 预读下一个字符，如果是操作符，返回它的优先级和结合性，并不真正读进来
     * @return
     * @throws ParseException
     */
    private Precedence nextOperator() throws ParseException {
        Token t=lexer.peek(0);
        if(t.isIdentifier())
            return operators.get(t.getText());
        else
            return null;
    }

    /**
     * 把当前读到的操作符和下一个会读到的操作符的优先级作比较，如果下一个优先级更高且为左结合，那么
     * 就说明当前操作符的右侧是一个表达式,要先建立起语法树，就继续doShift了
     * @param prec
     * @param nextPrec
     * @return
     */
    private static boolean rightIsExpr(int prec,Precedence nextPrec) {
        if(nextPrec.leftAssoc)
            return prec<nextPrec.value;
        else
            return prec <= nextPrec.value;
    }

    public ASTree factor() throws ParseException {
        if (isToken("(")) {
            token("(");
            ASTree e = expression();
            token(")");
            return e;
        }
        else {
            Token t = lexer.read();
            if (t.isNumber()) {
                NumberLiteral n = new NumberLiteral(t);
                return n;
            }
            else
                throw new ParseException(t);
        }
    }
    void token(String name) throws ParseException {
        Token t = lexer.read();
        if (!(t.isIdentifier() && name.equals(t.getText())))
            throw new ParseException(t);
    }
    boolean isToken(String name) throws ParseException {
        Token t = lexer.peek(0);
        return t.isIdentifier() && name.equals(t.getText());
    }

    public static void main(String[] args) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        OpPrecedenceParser p = new OpPrecedenceParser(lexer);
        ASTree t = p.expression();
        System.out.println("=> " + t);
    }
}


