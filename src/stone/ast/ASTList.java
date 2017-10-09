package stone.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {

    protected List<ASTree> children;

    public ASTList(List<ASTree> list) {
        children=list;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    //ASTList的location()方法返回的是它的子节点，也就是ASTLeaf的location()，也就是token的行号
    @Override
    public String location() {
        for(ASTree t:children) {
            String s = t.location();
            if(s!=null)
                return s;
        }
        return null;
    }

    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append('(');
        String sep="";
        for(ASTree t:children) {
            builder.append(sep);
            sep=" ";
            builder.append(t.toString());
        }
        return builder.append(')').toString();
    }
}
