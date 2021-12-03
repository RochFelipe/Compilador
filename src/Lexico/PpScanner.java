package Lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import Exceptions.*;

public class PpScanner{

    private char[] content;
    private int estado;
    private int pos;
    private int column;
    private int line;
    private String nome;
    private int i;

    public PpScanner(String filename) {
        try {
            line = 1;
            column = 0;
            String txtConteudo;
            txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            System.out.println("E lá vamos nós!");
            System.out.println(txtConteudo += " ");
            System.out.println("----------");
            content = txtConteudo.toCharArray();
            pos = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Token nextToken() {
        char currentChar;
        String term = "";
        Token token;
        if (isEOF()) {
            return null;
        }
        estado = 0;
        while (true) {
            currentChar = nextChar();
            switch (estado) {
                case 0:
                    if (isChar(currentChar) || isApostrofo(currentChar)) {
                        term += currentChar;
                        estado = 1;
                    } else if (isDigit(currentChar)) {
                        term += currentChar;
                        estado = 2;
                    } else if (isSpace(currentChar)) {
                        estado = 0;
                    } else if (isOp_Rel(currentChar)) {
                        term += currentChar;
                        estado = 5;

                    } else if (isOp_Ari(currentChar)) {
                        term += currentChar;
                        estado = 7;
                    } else if (isCaracter_esp(currentChar)) {
                        term += currentChar;
                        estado = 8;
                    } else if (isPontuation(currentChar)) {
                        back();
                        term += currentChar;
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_PONTUATION);
                        nome = "Pontuação     ";
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else {
                        if (isEOF()) {
                            System.out.println("\n\n\n\n\n\n\n\n\n\n                   MINHA NOSSA NOSSA NOSSAAAAAAAAAAAA\n\n\n"+
"                                      .,..,;;;;;;;;,            .\n" +
"                                          ```'`<!!!!!!;;,.       `!;\n" +
"                                    ,;;;!!!!!!;;,;!!!!!!!!!;,     !!!;\n" +
"                                ,;!!!!!!!!!!!!!!!!!!!!!!!!!!!!;   `!!!!;\n" +
"                             ,;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!> <!!!!!;\n" +
"                           ;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!><!!!!!!;\n" +
"                         ;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
"                       ;<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
"                      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
"                    ;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
"                  ,<!'`!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'\n" +
"              ,;-''' .<!!!!''''```````'`<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'\n" +
"              ,;;;>''`'               .,.'`!!!!!!!!!!!!!!!!!!!!!!!!!''\n" +
"                                      !!!>;,!!!!!!!!!!!!!!!!!!!'`'\n" +
"                                     !!!!!!!!!!!!!!!!!!!!!!'''\n" +
"                                    !!!!!!!!!!!!!!!!!!!!!'\n" +
"                                  `;!!'''`!!!!!!!!!!!!!!'\n" +
"                              ,;!>.!! ,;, `!!!!!!!!!!!!!\n" +
"                             ``!!!!!!!!!!!>'!!!!!!!!!!!!\n" +
"                           uMMx`!'' .,.'`!!;!!!!!!!!!!!!>\n" +
"                         nMMMM>  ,JMMMMb.`!!!!!!!!!!!!!!!!.\n" +
"                       ,JMMMMM  uMMMMMMMM <!!!!!!!!!!!!!!!!>\n" +
"                      ,MMMMMMM ,MMMMMMMMM>'!!!!!!!!!!!!!!!!>\n" +
"<$c                  ,MMMP\"   uMMMMMMMMMM>'!!!!!!!!!!!!!!!!>\n" +
"$\"$h          ,ccc, ;MMMP    ,MMMMMP\"   \" ;!!!!!!!!!!!!!!!!\n" +
"$ `?$.       z$$$$$ JMMM     4MMMMP       !!!!!!!!!!!!!!!!!\n" +
"$ < ?$$c     $$$$$$ MMMM     4MMMP       ;!!!'``_,,_```!!!\n" +
"?h`; $$$$c,  ??$$$$ MMMM    .`MMM        ``.zc$$$$$$$h.'`!\n" +
"` <;`$$$$$$hcc,_`\\\" \\\"\\\"\\\"\\\" ,c$h MMM        c$$$$$$$$$$$$$hc\n"  +
"\"   `$' `$$$$$$$$$$$$$$$$$$$$$$.`MM      z$$$$$$$$$$$$$$$$$h\n" +
"     ' `$$$$$$$$$$$$$$$$$$$$$h.`4r  ,c$$$$$P\"\"\"   .,$$$$$$r\n" +
"        `?$$$$$$$$$$$$$$$$$$$$$hccc$$$$$$$$cd\" cd$$$$$$$$$F\n" +
"          ?$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$P\",J$$$$$$$$$$$\n" +
"           `?$$$$$$$$$$$$$$$$$$$$$$$$$$$$P\" z$$$$$$$$$$$$F\n" +
"              `\"??$$$$$$$$$$$$$$$$$$$$$P\"  c$$$$$$$$$$$P\"\n" +
"                   _\"\"??????$$$$$???\"\"    J$$$$$$$??\"\"\n" +
"                   `$$.  ,;;- ,;;;;<!! .z$$$$\"\"\"\n" +
"                    ?$h ;!!! <!!!!!' ,c$$P\"\"\n" +
"                     $$F`!!!!!!!'`,c$??\"..- JM,\n" +
"                     `$$ !!!!'`,c$P\".zc$$\",MMMMb\n" +
"                      $$.' .,cP\"\",c$$$$$\" MMMMMMr\n" +
"                   .nx \"$??\"' zJ$$$$??\"\" 4MMMMMMM\n" +
"                  ,MMMMn.nmMM $$P\"\".nJMMMnMMMMMMMb\n" +
"                 ,MMMMMMMM\" . \".nJMMMMMMMMMMMMMMMM\n" +
"                 JMMMMMMMMnM\".nMMMMMMMMMMMMMMMMMMP\n" +
"                ;MMMMMMMMMMMMMMMMMMMMMMMMMMMMM\"'..\n" +
"                4MMMMMMMMMMMMMMMMMMMMMMMMMMM\" ;!!!\n" +
"             , .4MM\" JMMMMMMMMMMMMMMMMMMMP\",;!!!!!>\n" +
"            ; xb M\" JMMMMMMMMMMMMMMMMP\"\" ;!!!!!!!!!\n" +
"           , uMMr ,;MMMMMMMMMMMMPP\".xnn .!!!!!!!!!!\n" +
"          ,! MMMM, JMMMMMMMMPP\".xnMMMM ;!!!!!!!!!!!,\n" +
"          ! JMMMMM 4MMMMMP\".nmMMMMMMM ;!!!!!!!!!!!!!\n" +
"         ,!,MMMMMM,4MMMP uMMMMMMMMMMP !!!!!!!!!!!!!!\n" +
"         ! JMMMMMML MP ,JMMMMMMMMMMM <!!! `!!!!!!!!!\n" +
"        ;! MMMMMMMM,\" uMMMMMMMMMMMM';!!!!! `!!!!!!!!>");
                            System.exit(0);
                        } else {
                            throw new RuntimeException("Caracter inserido não é reconhecido por essa linguagem");
                        }

                    }
                 break;

                case 1:
                    if (isChar(currentChar) || isApostrofo(currentChar)) {
                        estado = 1;

                        term += currentChar;
                        if (isReserved_char(term)) {
                            token = new Token();
                            token.setText(term);
                            token.setType(token.TK_CResv);
                            nome = "Reservados    ";
                            token.setNome(nome);
                            return token;
                        } else if (isIntegrantes(term)) {
                            token = new Token();
                            token.setText(term);
                            token.setType(token.TK_Integrantes);
                            nome = "Integrante    ";
                            token.setNome(nome);
                            return token;
                        }
                    } else if (isDigit(currentChar) || isSpace(currentChar) || isOp_Rel(currentChar)
                            || isOp_Ari(currentChar) || isCaracter_esp(currentChar) || isEOF(currentChar)) {
                        if (!isEOF(currentChar)) {
                            back();
                        }
                        token = new Token();
                        token.setType(Token.TK_CHARACTER);
                        token.setText(term);
                        nome = "Caracter      ";
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;

                    } else {
                        throw new LexicalException("Malformed Identifier");
                    }
                    break;

                case 2:
                    if (isDigit(currentChar)) {
                        estado = 2;
                        term += currentChar;
                    } else if (isChar(currentChar)) {
                        term += currentChar;
                        estado = 1;
                    } else if (isSpace(currentChar) || isOp_Rel(currentChar) || isOp_Ari(currentChar)
                            || isCaracter_esp(currentChar) || isEOF(currentChar)) {
                        if (!isEOF(currentChar)) {
                            back();
                        }
                        token = new Token();
                        token.setType(Token.TK_CHARACTER);
                        token.setText(term);
                        nome = "Numérico      ";
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (isFloat(currentChar)) {
                        estado = 3;
                        term += currentChar;
                    } else {
                        throw new LexicalException("Simbolo numerico nao reconhecido");
                    }
                    break;

                case 3:
                    if (isDigit(currentChar)) {
                        estado = 4;
                        term += currentChar;

                    } else {
                        throw new LexicalException("Simbolo numerico nao asçksbfuiashbfklasnfojbureconhecido");
                    }
                    break;

                case 4:
                    if (isDigit(currentChar)) {
                        estado = 4;
                        term += currentChar;
                    } else {
                        back();
                        token = new Token();
                        token.setType(Token.TK_Float);
                        token.setText(term);
                        nome = "Float      ";
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    }
                    break;

                case 5:
                    // caso optar para o sistema de cada op rel ser um token dif usar a atribuição
                    // do tolen dentro dos ifs
                    // assim como os nomes.
                    
                    if (currentChar != '=') {
                        token = new Token();
                        if (term.compareTo("<") == 0) {
                            nome = "Op Relacional (menor que)";
                            i= token.TK_OPRelMenQ;
                        } else if (term.compareTo(">") == 0) {
                            nome = "Op Relacional (maior que)";
                            i = token.TK_OPRelMaiQ;
                        } else if (term.compareTo("=") == 0) {
                            nome = "Op Aritmetico (atri )";
                            back();                            
                            token.setText(term);
                            token.setNome(nome);
                            token.setType(token.TK_OPAriAtri);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        }
                    } else {
                       token = new Token();
                        term += currentChar;
                        if (term.charAt(0) == '!') {
                            nome = "Op Relacional (diferente)";
                            i = token.TK_OPRelDif;
                        } else if (term.charAt(0) == '<') {
                            nome = "Op Relacional (menor ou igual que)";
                            i=token.TK_OPRelMenIg;
                        } else if (term.charAt(0) == '>') {
                            nome = "Op Relacional (maior ou igual que)";
                            i=token.TK_OPRelMaiIg;
                        } else if (term.charAt(0) == '=') {
                            nome = "Op Relacional (igual)";
                            i= token.TK_OPRelIgual;
                        }

                    }
                    estado = 6;
                    break;

                case 6:
                    back();
                    token = new Token();
                    token.setType(i);
                    token.setText(term);
                    token.setNome(nome);
                    token.setLine(line);
                    token.setColumn(column - term.length());
                    return token;
                case 7:
                    // caso optar para o sistema de cada op arit ser um token dif usar a atribuição
                    // do tolen dentro dos ifs assim como os nomes.
                    if (term.compareTo("+") == 0) {
                        back();
                        nome = "Op Aritmetico (soma )";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_OPAriSoma);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo("-") == 0) {
                        back();
                        nome = "Op Aritmetico (subtr)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_OPAriSubt);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo("*") == 0) {
                        back();
                        nome = "Op Aritmetico (mult )";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_OPAriMult);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo("/") == 0) {
                        back();
                        nome = "Op Aritmetico (divi )";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_OPAriDiv);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    }
                case 8:
                    if (term.compareTo("(") == 0) {
                        back();
                        nome = "Op Aritmetico (abreP)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_CEspAbreP);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo(")") == 0) {
                        back();
                        nome = "Op Aritmetico (fechP)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_CEspFechP);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo("{") == 0) {
                        back();
                        nome = "Op Aritmetico (abreC)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_CEspAbreC);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo("}") == 0) {
                        back();
                        nome = "Op Aritmetico (fechC)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_CEspFechC);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo(",") == 0) {
                        back();
                        nome = "Op Aritmetico (virgu)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_CEspVirgu);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    } else if (term.compareTo(";") == 0) {
                        back();
                        nome = "Op Aritmetico (PtVirg)";
                        token = new Token();
                        token.setText(term);
                        token.setType(token.TK_CEsPtVirg);
                        token.setNome(nome);
                        token.setLine(line);
                        token.setColumn(column - term.length());
                        return token;
                    }

            }
        }
    }

    private boolean isFloat(char c) {
        return c == '.';
    }

    private boolean isDigit(char c) {// 1
        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {// 2
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isApostrofo(char c) {
        return (c == '"' || c == '‘' || c == '’');
    }

    private boolean isPontuation(char c) {// 3

        return c == '?' || c == '.' || c == '.';
    }

    private boolean isOp_Rel(char c) {// 4

        return c == '<' || c == '>' || c == '!' || c == '=';
    }

    private boolean isOp_Ari(char c) {// 5
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isCaracter_esp(char c) { // 6
        return c == ')' || c == '(' || c == '{' || c == '}' || c == ',' || c == ';';
    }

    private boolean isReserved_char(String c) {
        return c.equals("main") || c.equals("if") || c.equals("else") || c.equals("while") || c.equals("do")
                || c.equals("for") || c.equals("int") || c.equals("float") || c.equals("char");
    }

    private boolean isIntegrantes(String c) {
        return c.equals("Aroldo") || c.equals("Felipe") || c.equals("Germain") || c.equals("Leticia")
                || c.equals("Rodrigo") || c.equals("Ryan");
    }

    private boolean isSpace(char c) {
        if (c == '\n' || c == '\r') {
            line++;
            column = 0;
        }
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private boolean isEOF() {

        return pos >= content.length;
    }

    private boolean isEOF(char c) {
        return c == '\0';
    }

    private char nextChar() {
        if (isEOF()) {
            return '\0';
        }
        return content[pos++];

    }

    public void back() {
        pos--;
        column--;
    }
}
