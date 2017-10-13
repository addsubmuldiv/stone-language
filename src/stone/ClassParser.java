package stone;

import stone.ast.ClassBody;
import stone.ast.ClassStmnt;
import stone.ast.Dot;

import static stone.Parser.rule;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 13:11 2017/10/13
 * @Modified_by:
 */
public class ClassParser extends ClosureParser {
    Parser member = rule().or(def, simple);
    Parser class_body = rule(ClassBody.class).sep("{").option(member)
                            .repeat(rule().sep(";",Token.EOL).option(member)).sep("}");
    Parser defclass = rule(ClassStmnt.class).sep("class").identifier(reserved)
                            .option(rule().sep("extends").identifier(reserved)).ast(class_body);
    public ClassParser() {
        postfix.insertChoice(rule(Dot.class).sep(".").identifier(reserved));
        program.insertChoice(defclass);
    }
}
