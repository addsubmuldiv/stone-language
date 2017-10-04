package stone;

import stone.ast.*;

import java.util.HashSet;
import static stone.Parser.Operators;
import static stone.Parser.rule;
//使用Parser库使得能够以近似于BNF的格式写语法分析，
public class BasicParser {
    HashSet<String> reserved =new HashSet<>();
    Operators operators = new Operators();
    //这里有疑问！！！！！！
    Parser expr0 = rule();
    //基本构成元素，括号括起来的表达式，数字字面量，标识符，字符串
    Parser primary = rule(PrimaryExpr.class)
                .or(rule().sep("(").ast(expr0).sep(")"),
                    rule().number(NumberLiteral.class),
                    rule().identifier(Name.class, reserved),
                    rule().string(StringLiteral.class)
                    );
    //因子，基本构成元素前面加个负号，或者就是基本构成元素
    Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary),primary);

    //表达式，两个基本构成元素中间加个双目运算符
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);
    //这里也有疑问！！！！！
    Parser statement0 = rule();
    //代码块，用大括号括起语句，语句之间用分号或者换行符(EOL)隔开
    Parser block = rule(BlockStmnt.class)
            .sep("{").option(statement0)
            .repeat(rule().sep(";",Token.EOL).option(statement0))
            .sep("}");
    //简单表达式，仅由表达式构成的语句
    Parser simple = rule(PrimaryExpr.class).ast(expr);
    Parser statement=statement0.or(
            rule(IfStmnt.class).sep("if").ast(expr).ast(block)
                    .option(rule().sep("else").ast(block)),
            rule(WhileStmnt.class).sep("while").ast(expr).ast(block),
            simple);
    //表示一个完整的语句，或者是一个空行
    Parser program = rule().or(statement, rule(NullStmnt.class)).sep(";",Token.EOL);


    public BasicParser() {
        // 因为所有符号都会被识别为标识符，所以把"}",";",和换行符设为保留
        // 而"{"因为算法的原因不会被识别，所以无需添加
        reserved.add("}");
        reserved.add(";");
        reserved.add(Token.EOL);

        operators.add("=",1,Operators.RIGHT);
        operators.add("==",2,Operators.LEFT);
        operators.add(">",2,Operators.LEFT);
        operators.add("<",2,Operators.LEFT);
        operators.add("+",3,Operators.LEFT);
        operators.add("-",3,Operators.LEFT);
        operators.add("*",4,Operators.LEFT);
        operators.add("/",4,Operators.LEFT);
        operators.add("%",4,Operators.LEFT);

    }

    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}
