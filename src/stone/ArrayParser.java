package stone;

import javassist.gluonj.Reviser;
import stone.ast.ArrayLiteral;
import stone.ast.ArrayRef;

import static stone.Parser.rule;
/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 11:12 2017/10/16
 * @Modified_by:
 */
@Reviser public class ArrayParser extends FuncParser {
    /**
     * 定义数组的语法,[1,2,3,4,...]
     */
    Parser elements= rule(ArrayLiteral.class)
                        .ast(expr).repeat(rule().sep(",").ast(expr));
    public ArrayParser() {
        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        /**
         * 数组下标访问语法
         */
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
}
