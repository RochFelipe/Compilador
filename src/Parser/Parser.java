package Parser;

import java.util.LinkedList;
import Exceptions.*;
import Lexico.*;

public class Parser {
    // sintatico

    private PpScanner scanner;
    private Token token;
    private int countIF = 1;
    // semantico
    private int blocoCount = 0;
    private LinkedList<Variables> VarLL = new LinkedList<Variables>();
    Variables aux;

    public Parser(PpScanner scanner) {
        this.scanner = scanner;
    }

    public void Entry() throws InterruptedException, LexicalException {
        prog_int();
        token = scanner.nextToken();
        bloco();

    }

    private void prog_int() throws LexicalException, InterruptedException {
        token = scanner.nextToken();
        if (token.getType() != Token.TK_CResv || token.getText().compareTo("int") != 0) {
            throw new SyntaxException("\nQue cara mais engraçado!\nSe esperava \"int\" e se encontrou " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
        prog_main();
        token = scanner.nextToken();
        AbreP(); // abre parenteses
        token = scanner.nextToken();
        FechaP(); // fecha parenteses
    }

    public void prog_main() throws InterruptedException, LexicalException {
        token = scanner.nextToken();
        if (token.getType() != Token.TK_CResv || token.getText().compareTo("main") != 0) {
            throw new SyntaxException("\nAh, eu nao digo!\nSe esperava \"main\" e se encontrou " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }

    }

    private void AbreP() throws LexicalException {
        if (token.getType() != Token.TK_CEspAbreP) {
            throw new SyntaxException("\nEssa gente inventa cada coisa!\nSe esperava \"(\" e se encontrou " + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }

    }

    private void FechaP() throws LexicalException {
        if (token.getType() != Token.TK_CEspFechP) {
            throw new SyntaxException("\nEm todos esses anos nessa indústria vital, essa é a primeira vez que isso me acontece!\nSe esperava \")\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void bloco() throws LexicalException {
        blocoCount += 1;
        AbreC(); // abre chaves        
        decl_var();// declarar variaveis
        comando();// estrutura de repetição
        FechaC(); // fecha chaves
        if (this.token != null) {
            token = scanner.nextToken();
        }

    }

    private void AbreC() throws LexicalException {

        if (token.getType() != Token.TK_CEspAbreC) {
            throw new SyntaxException("Para aqueles que não prestam atenção no lance vamos mostrar com a magia do replay imediato!\nSe esperava \"{\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void FechaC() throws LexicalException {
        if (token.getType() != Token.TK_CEspFechC) {
            throw new SyntaxException("Vudu é pra jacu!\nSe esperava \"}\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void decl_var() throws LexicalException {
        aux = new Variables();
        token = scanner.nextToken();
        if (token.getType() == Token.TK_CResv || token.getType() == Token.TK_Integrantes) {
            if (token.getType() != Token.TK_CResv || token.getText().compareTo("int") != 0 && token.getText().compareTo("float") != 0
                    && token.getText().compareTo("char") != 0) {
            }else{

            aux.setType(token.getText());// semantico adiquirindo tipo
            token = scanner.nextToken();
            ID(); // id
            VariavelMesmoNome(); // verificar duplicatas iguais
            token = scanner.nextToken();
            PV(); // ponto e virgula
            VarLL.add(aux);
            System.out.println(aux);
            decl_var(); // recursão
            }
        }
    }

    private void comando() throws LexicalException {
        if (((token.getType() == Token.TK_CResv || token.getType() == Token.TK_Integrantes)
                && (token.getText().compareTo("if") == 0 || token.getText().compareTo("while") == 0))
                || (token.getType() == Token.TK_CHARACTER) || (token.getType() == Token.TK_CEspAbreC)) {

            if (token.getType() == Token.TK_CHARACTER || token.getType() == Token.TK_CEspAbreC) {
                comando_basico();
            } else if (token.getText().compareTo("while") == 0) {
                While();
            } else {
                IF();
            }
            comando();

        }
    }

    private void ID() throws LexicalException {
        if (token.getType() != Token.TK_CHARACTER && token.getType() != Token.TK_Float) {
            throw new SyntaxException("Essa gente inventa cada coisa!\nse encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }

    }

    private void VariavelMesmoNome() {// semantico regra de variavel com mesmo nome
        aux.setName(token.getText());
        aux.setBloco(blocoCount);
        if (VarLL.contains(aux)) {
            throw new SemanticException("Em todos esses anos nessa indústria vital, essa é a primeira vez que isso me acontece!\nNome de variavel (" 
                    + token.getText() + ") já escolhido na linha "
                    + token.getLine() + " e na coluna " + token.getColumn());
        } else {
            VarLL.add(aux);
        }
    }

    private void PV() throws LexicalException {
        if (token.getType() != Token.TK_CEsPtVirg) {
            throw new SyntaxException("Ah, eu nao digo!\nSe esperava \";\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void comando_basico() throws LexicalException {
        if (token.getType() == Token.TK_CHARACTER) {
            atribuicao();
        } else {
            bloco();
        }
    }

    private void atribuicao() throws LexicalException {
        aux = new Variables();
        if (token.getType() == Token.TK_CHARACTER) {
            ID();
            aux = VariavelExiste();// semantico variavel nÃ£o existente
            token = scanner.nextToken();
            if (token.getType() != Token.TK_OPAriAtri) {
                throw new SyntaxException("Para aqueles que não prestam atenção no lance vamos mostrar com a magia do replay imediato!\nSe esperava \"=\" e se encontrou " 
                        + Token.TK_TEXT[token.getType()] + " ("
                        + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
            }
            expr_arit();
            PV();
            token = scanner.nextToken();
        }
    }

    private Variables VariavelExiste() {//se a atribuição da variavel ja existe
        Variables a;
        for (int i = 0; i < VarLL.size(); i = i + 1) {
            a = (Variables) VarLL.toArray()[i];
            if (a.getName().compareTo(token.getText()) == 0) {
                return a;
            }

        }
        throw new SemanticException("Essa gente inventa cada coisa!\nA variavel não existe (" 
                + token.getText() + ") na linha " + token.getLine()
                + " e na coluna " + token.getColumn());

    }

    private void IF() throws LexicalException {

        if (token.getType() != Token.TK_CResv || token.getText().compareTo("if") != 0) {
            throw new SyntaxException("Que cara mais engraçado!\nSe esperava \"if\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
        token = scanner.nextToken();
        AbreP();
        expr_relacional();
        FechaP();
        token = scanner.nextToken();
        bloco();
        if (token.getType() == Token.TK_CResv && token.getText().compareTo("else") == 0) {
            token = scanner.nextToken();
            comando();
        }
    }

    private void expr_arit() throws LexicalException {
        // valor aux Ã© utilizado na geração de codigo
        token = scanner.nextToken();
        termo();
        switch (token.getType()) {
            case Token.TK_OPAriSoma:
                Soma();
                expr_arit();
                break;
            case Token.TK_OPAriSubt:
                Sub();
                expr_arit();
                break;
            default:
        }
    }

    private void termo() throws LexicalException {

        Fator();
        switch (token.getType()) {
            case Token.TK_OPAriMult:
                Multi();
                token = scanner.nextToken();
                termo();
                break;
            case Token.TK_OPAriDiv:
                Div();
                token = scanner.nextToken();
                termo();
                break;
            default:
        }
    }

    private void Fator() throws LexicalException {

        switch (token.getType()) {
            case Token.TK_CEspAbreP:
                AbreP();
                expr_arit();
                FechaP();
                break;
            case Token.TK_IDENTIFIER:
                ID();
                MesmoTipoOperacao(VariavelExiste());// Semantico mesmo tipo
                break;
            case Token.TK_Float:
                Float();
                MesmoTipo();
                break;
            case Token.TK_NUMBER:
                Int();
                MesmoTipo();
                break;
            case Token.TK_CHARACTER:
                Char();
                MesmoTipo();
                break;
            default:
                throw new SyntaxException("Em todos esses anos nessa indústria vital, essa é a primeira vez que isso me acontece!\nSe esperava Fator or expressão aritmetrica e se encontrou " + Token.TK_TEXT[token.getType()]
                        + " (" + token.getText() + ")  na linha " + token.getLine() + " ae na coluna " + token.getColumn());
        }

        token = scanner.nextToken();
    }

    private void MesmoTipoOperacao(Variables a) {
        if (aux.getTypeID() != a.getTypeID() && (aux.getTypeID() != 4 && a.getTypeID() != 1)) {
            throw new SemanticException(
                    "Que cara mais engraçado!\nSe esperava Atribuição errada. Operação (" 
                            + aux.getType().toUpperCase() + ") esperada, encontrada:("
                    + a.getType() + ") na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void MesmoTipo() {
        Variables a;

        if (aux.getTypeID() != token.getType() && (aux.getTypeID() != 6 || token.getType() != 1)) {
            
                        throw new SemanticException("Vudu é pra jacu!\nAtribuição errada. Tipo (" + aux.getType()
                                + ") esperado, encontrado:(" + Token.TK_TEXT[token.getType()] + ") na linha " + token.getLine()
                                + " e na coluna " + token.getColumn());

        }

    }

    private void Multi() {
        if (token.getType() != Token.TK_OPAriMult) {
            throw new SyntaxException("Em todos esses anos nessa indústria vital, essa é a primeira vez que isso me acontece!\nSe esperava \"*\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void Div() {
        if (token.getType() != Token.TK_OPAriDiv) {
            throw new SyntaxException("Para aqueles que não prestam atenção no lance vamos mostrar com a magia do replay imediato!\nSe esperava \"/\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void Soma() {
        if (token.getType() != Token.TK_OPAriSoma) {
            throw new SyntaxException("Ah, eu nao digo!\nSe esperava \"+\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void Sub() {
        if (token.getType() != Token.TK_OPAriSubt) {
            throw new SyntaxException("Que cara mais engraçado!\nSe esperava \"-\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void Float() {
        if (token.getType() != Token.TK_Float) {
            throw new SyntaxException("Essa gente inventa cada coisa!\nSe esperava float e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void Int() {
        if (token.getType() != Token.TK_NUMBER) {
            throw new SyntaxException("Em todos esses anos nessa indústria vital, essa é a primeira vez que isso me acontece!\nSe esperava int e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " (" + token.getText()
                    + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void Char() {
        if (token.getType() != Token.TK_CHARACTER) {
            throw new SyntaxException("Para aqueles que não prestam atenção no lance vamos mostrar com a magia do replay imediato!\nSe esperava char e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " (" + token.getText()
                    + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }

    private void While() throws LexicalException {

        if (token.getType() != Token.TK_CResv || token.getText().compareTo("while") != 0) {
            throw new SyntaxException("Essa gente inventa cada coisa!\nSe esperava \"while\" e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
        token = scanner.nextToken();
        AbreP();
        expr_relacional();
        FechaP();
        token = scanner.nextToken();
        bloco();
    }

    private void expr_relacional() throws LexicalException {
        expr_arit();
        OPR();
        expr_arit();
    }

    private void OPR() {
        if (token.getType() != Token.TK_OPRelDif && token.getType() != Token.TK_OPRelIgual
                && token.getType() != Token.TK_OPRelMaiQ && token.getType() != Token.TK_OPRelMenQ
                && token.getType() != Token.TK_OPRelMaiIg && token.getType() != Token.TK_OPRelMenIg) {
            throw new SyntaxException("Em todos esses anos nessa indústria vital, essa é a primeira vez que isso me acontece!\nSe esperava Operador Relacional  e se encontrou " 
                    + Token.TK_TEXT[token.getType()] + " ("
                    + token.getText() + ")  na linha " + token.getLine() + " e na coluna " + token.getColumn());
        }
    }
}
