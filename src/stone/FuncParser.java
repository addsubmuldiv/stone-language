package stone;


import stone.ast.Arguments;
import stone.ast.DefStmnt;
import stone.ast.ParameterList;

import static stone.Parser.rule;

/**
 * @Author: Lighters_c
 * @Discrpition: 坑爹的作者，写书夹杂大量私货，代码一行注释都不给！一行都没有！！
 * @Date: Created in 13:23 2017/10/5
 * @Modified by:
 */
public class FuncParser extends BasicParser {
    Parser param = rule().identifier(reserved);
    Parser params = rule(ParameterList.class).ast(param).repeat(rule().sep(",").ast(param));
    //paramList的BNF，使用maybe，即使省略了参数，也会有一个根节点，没有子节点，表示没有参数
    Parser paramList = rule().sep("(").maybe(params).sep(")");
    //def 定义函数
    Parser def = rule(DefStmnt.class).sep("def").identifier(reserved).ast(paramList).ast(block);
    Parser args = rule(Arguments.class).ast(expr).repeat(rule().sep(",").ast(expr));
    Parser postfix = rule().sep("(").maybe(args).sep(")");

    public FuncParser() {
        reserved.add(")");
        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);
    }
}
