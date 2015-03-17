package mx.uach.compiladores;

import java.util.*;

/**
 * Clase que implementa el compilador de un solo paso
 * 
 * <ul>
 * <li> sent → expr;</li>
 * <li> expr → expr + term| expr - term | term </li>
 * <li> term → term * factor| term / factor | factor </li>
 * <li> factor → (expr) | num | id </li>
 * <li> num → [0 - 9]</li>
 * <li> id → [a - z] </li>
 * </ul>
 * 
 * @author Jesus Jose Garcia Pardo
 * @version  1.0
 * @since 6/03/2015
 */
public class AnalizadorSintacticoAvanzado {
    
    private static final int FIN_SENT = ';';
    private static final int SUMA = '+';
    private static final int RESTA = '-';
    private static final int MULTIPLICACION = '*';
    private static final int DIVISION = '/';
    private static final int PAR_DER = '(';
    private static final int PAR_IZQ = ')';
    private static final int NUMERO = 600;
    private static final int ID = 700;
    private static final int EOF = '.';
    
    private Integer linea = 1;
    private StringTokenizer tokenizer = null;
    private Token currenToken;
    private String salida = "";
    
    public void parser(){
        this.currenToken = this.lex();
        this.sent();
        if(!(this.currenToken.getToken() == EOF)){
            System.out.println(String.format("\n Resultado: %s \n Se ejecuto como es debido", salida) );
        }
    }
    
    public void sent(){
        expr();
        if (!(this.currenToken.getToken() == FIN_SENT)){
            throw new Error(String.format(
                    "\nError de sintaxis: se esperaba '%s'", (char)FIN_SENT));
        }
    }
    
    public void expr(){
        term();
        while (this.currenToken.getToken() == SUMA 
                || this.currenToken.getToken() == RESTA) {
            if (this.currenToken.getToken() == SUMA) {
                this.currenToken = lex ();
                term();
                if(!esNumeroId(currenToken)){
                    throw new Error ("\nError de Sintaxis: se esperaba un numero o un id");
                }
                this.salida = String.format("%s%s", this.salida, (char)SUMA );
            } else if (this.currenToken.getToken() == RESTA) {
                this.currenToken = lex ();
                term();
                if(!esNumeroId(currenToken)){
                    throw new Error ("\nError de Sintaxis: se esperaba un numero o un id");
                }
                this.salida = String.format("%s%s", this.salida, (char)RESTA);
            }
            
        }
    }
    
    public void term(){
        factor();
        this.currenToken = lex();
        while (this.currenToken.getToken() == DIVISION || this.currenToken.getToken() == MULTIPLICACION) {
            if(this.currenToken.getToken() == DIVISION){
                this.currenToken = lex();
                factor();
                if(!esNumeroId(currenToken)){
                    throw new Error ("\nError de Sintaxis: se esperaba un numero o un id");
                }
                this.salida = String.format("%s%s", this.salida, (char)DIVISION);
                this.currenToken = lex();
            }else if (this.currenToken.getToken() == MULTIPLICACION) {
                this.currenToken = lex();
                factor();
                if(!esNumeroId(currenToken)){
                    throw new Error ("\nError de Sintaxis: se esperaba un numero o un id");
                }
                this.currenToken = lex();
                this.salida = String.format("%s%s", this.salida, (char)MULTIPLICACION);
            }
        }
    }
    
    public void factor(){
        if(this.currenToken.getToken() == ID){
            this.salida = String.format("%s%s", this.salida, this.currenToken.getLexema());
        }else if (this.currenToken.getToken() == NUMERO) {
            this.salida = String.format("%s%s", this.salida, this.currenToken.getLexema());
            
        }else if (this.currenToken.getToken() == PAR_DER){
            this.currenToken = lex();
            expr();
            if (!(this.currenToken.getToken() == PAR_IZQ)){
                throw new Error(String.format("\nError de sintaxis: se esperaba '%s'",
                        (char)PAR_IZQ));
            }
        }
    }
    
    private Boolean esNumeroId(Token token){
        return token.getToken() == ID || token.getToken() == NUMERO;
    }
    
    private StringTokenizer getTokenizer (String codigoFuente){
        if (tokenizer == null){
            //;+-*/()
            String alfabeto = String.format("%s%s%s%s%s%s%s",
                    (char)FIN_SENT,
                    (char)SUMA, 
                    (char)RESTA, 
                    (char)MULTIPLICACION,
                    (char)DIVISION,
                    (char)PAR_DER,
                    (char)PAR_IZQ
            );
            this.tokenizer = new StringTokenizer(codigoFuente.trim(), alfabeto, true);
        }
        return tokenizer;
    }
    
    private Token lex(){
        Token token = null;
        if(this.getTokenizer("").hasMoreTokens()){
        String currentToken = this.getTokenizer("").nextToken();
        if(esNumero(currentToken)){
            token = new Token(this.linea, NUMERO, currentToken);
        }else if (esIdentificardor(currentToken)){
            token = new Token(this.linea, ID, currentToken);
        }else{
            int tokenSimple = currentToken.charAt(0);
            switch (tokenSimple) {
                case FIN_SENT:
                    token = new Token(this.linea, FIN_SENT, String.format("%s", (char)tokenSimple));
                    this.linea++;
                    break;
                case SUMA:
                    token = new Token(this.linea, SUMA, String.format("%s", (char)tokenSimple));
                    break;
                case RESTA: 
                    token = new Token(this.linea, RESTA, String.format("%s", (char)tokenSimple));
                    break;
                case MULTIPLICACION:
                    token = new Token(this.linea, MULTIPLICACION, String.format("%s", (char)tokenSimple));
                    break;
                case DIVISION:
                    token = new Token(this.linea, DIVISION, String.format("%s", (char)tokenSimple));
                    break;
                case PAR_DER:
                    token = new Token(this.linea, PAR_DER, String.format("%s", (char)tokenSimple));
                    break;
                case PAR_IZQ:
                    token = new Token(this.linea, PAR_IZQ, String.format("%s", (char)tokenSimple));
                    break;

                default:
                    throw new Error(
                   "Error de lexico: El caracter no esta dentro del alfabeto.");
            }
        }
        }else{
            token = new Token(this.linea, EOF, ".");
        }
        return token;
    }
    
    public ArrayList <Token> getTokens(String codigoFuente) {
        ArrayList <Token> lista = new <Token> ArrayList();
        
        while(this.getTokenizer(codigoFuente).hasMoreTokens()){
            Token t = this.lex();
            lista.add(t);
        }
        
        return lista;
    }
    
    public static Boolean esNumero(String textoRevisar){
        Boolean esNumero = true;
        for (int i = 0; i < textoRevisar.length(); i++) {
            esNumero = esNumero && Character.isDigit(textoRevisar.charAt(i));
        }
        return esNumero;
    }
    
    public static Boolean esIdentificardor(String textoRevisar){
        Boolean esId = true;
        for (int i = 0; i < textoRevisar.length() ; i++) {
            esId = esId &&  (Character.isAlphabetic(textoRevisar.charAt(i)) || Character.isDigit(textoRevisar.charAt(i)));
        }
        return esId;
    }
    
    public static void main(String... args) {
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        analizador.getTokenizer(";");
        analizador.parser();
    }
}
