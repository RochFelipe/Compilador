package Lexico;

public class Token {

    public static final int TK_Space = 0;//espaço ou enter
    public static final int TK_IDENTIFIER = 1;//
    public static final int TK_NUMBER = 2;//numero
    public static final int TK_CHARACTER = 3;//String
    public static final int TK_PONTUATION = 4;//pontuação
    public static final int TK_CResv = 5;//palavra reservda
    public static final int TK_Float = 6;//float
    public static final int TK_Integrantes = 7;//palavras reservados dos menbros

    public static final int TK_OPRelMenQ = 8;  // <
    public static final int TK_OPRelMaiQ = 8;  // >
    public static final int TK_OPRelMenIg = 10;  // <=
    public static final int TK_OPRelMaiIg = 11; // >=
    public static final int TK_OPRelDif = 12; // !=
    public static final int TK_OPRelIgual = 13; // ==

    public static final int TK_OPAriSoma = 14; // +
    public static final int TK_OPAriSubt = 15; // -
    public static final int TK_OPAriDiv = 16; // /
    public static final int TK_OPAriMult = 17; // *
    public static final int TK_OPAriAtri = 18; // =

    public static final int TK_CEspAbreP = 19; // (
    public static final int TK_CEspFechP = 20; // )
    public static final int TK_CEspAbreC = 21; // {
    public static final int TK_CEspFechC = 22; // }
    public static final int TK_CEspVirgu = 23; // ,
    public static final int TK_CEsPtVirg = 24; // ;

    public static final String TK_TEXT[] = {
        "Space", "IDENTIFIER", "NUMBER", "CHARACTER", "PONTUATION", "CResv", "Float",
        "Integrantes", "PRelMenQ", "OPRelMaiQ", "OPRelMenIg", "OPRelMaiIg", "OPRelDif", "OPRelIgual",
        "OPAriSoma", "OPAriSubt", "OPAriDiv", "OPAriMult", "OPAriAtri", "CEspAbreP", "CEspFechP", "CEspAbreC", "CEspFechC", "CEspVirgu", "CEsPtVirg"
    };

    private int type;
    private String text;
    private String nome;
    private int line;
    private int column;

    public Token(int type, String text) {
        super();
        this.type = type;
        this.text = text;
    }

    public Token() {
        super();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Token type= " + type + " - " + nome + " -  text=  " + text;
    }

}
