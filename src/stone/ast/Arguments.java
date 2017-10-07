package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition: 实参有size()方法返回参数的数量,注意Arguments类是Postfix类的子类
 * @Date: Created in 13:56 2017/10/5
 * @Modified by:
 */
public class Arguments extends Postfix {
    public Arguments(List<ASTree> list) {
        super(list);
    }

    public int size() { return numChildren(); }
}
