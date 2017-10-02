package stone;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static String regexPat
            ="\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
            + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<Token> queue =new ArrayList<>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader r){
        hasMore=true;
        reader=new LineNumberReader(r);
    }

    /**
     * read is used to read token from the queue of tokens, and once it read a token,
     * it will remove the token from the queue.
     * @return
     * @throws ParseException
     */
    public Token read() throws ParseException{
        if(fillQueue(0))
            return queue.remove(0);
        else
            return Token.EOF;
    }

    /**
     * peek is used to see the (i)th token which will be read after the read token when build an abstract syntax tree
     * @param i
     * @return
     * @throws ParseException
     */
    public Token peek(int i) throws ParseException {
        if(fillQueue(i))
            return queue.get(i);
        else
            return Token.EOF;
    }

    /**
     * fillQueue is used to read a line once from the source code, and add it to the queue of tokens, if the i is
     * greater than the size of the queue, and there is still rest code in the source file,
     * then read a line from source code, and return true, else return false;
     * @param i
     * @return
     * @throws ParseException
     */
    private boolean fillQueue(int i) throws ParseException {
        while(i>=queue.size())
        {
            if(hasMore)
                readline();
            else
                return false;
        }
        return true;
    }

    /**
     * read a line once and
     * @throws ParseException
     */
    protected void readline() throws ParseException{
        String line;
        try {
            line=reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if(line == null) {
            hasMore=false;
            return;
        }
        int lineNo=reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos=0;
        int endPos=line.length();
        while(pos<endPos) {
            matcher.region(pos,endPos);
            if(matcher.lookingAt()) {
                addToekn(lineNo,matcher);
                pos=matcher.end();
            }
            else
                throw new ParseException("bad token at line "+lineNo);
        }
        queue.add(new IdToken(lineNo,Token.EOL));
    }


    protected void addToekn(int lineNo, Matcher matcher) {
        String m = matcher.group(1);
        if(m!=null)
            if(matcher.group(2)==null) {
                Token token;
                if(matcher.group(3)!=null)
                    token=new NumToken(lineNo,Integer.parseInt(m));
                else if(matcher.group(4)!=null)
                    token=new StrToken(lineNo,m);
                else
                    token=new IdToken(lineNo,m);
                queue.add(token);
            }
    }

    /**
     * @param s
     * @return
     */
    protected String toStringLiteral(String s) {
        StringBuilder sb=new StringBuilder();
        int len=s.length()-1;
        for(int i=1;i<len;i++) {
            char c = s.charAt(i);
            if(c=='\\'&&i+1<len) {      //Here the c handles the slash of escape character
                int c2=s.charAt(i+1);   //Here the character itself;
                if(c2=='"'||c2=='\\')
                    c=s.charAt(++i);
                else if(c2=='n') {
                    ++i;
                    c='\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 很不爽，英文写起来太难受了，之后注释就全部用中文了……
     */
    protected static class NumToken extends Token {
        private int value;

        protected NumToken(int line, int v) {
            super(line);
            value=v;
        }

        public boolean isNumber() {return true;}
        public String getText() {return Integer.toString(value);}
        public int getNumber() {return value;}
    }

    protected static class IdToken extends Token {
        private String text;
        protected IdToken(int line, String id) {
            super(line);
            text=id;
        }
        public boolean isIdentifier() {return true;}
        public String getText() {return text;}
    }

    protected static class StrToken extends Token {
        private String literal;
        protected StrToken(int line,String str) {
            super(line);
            literal=str;
        }
        public boolean isString() { return true; }
        public String getText() { return literal; }
    }
}
