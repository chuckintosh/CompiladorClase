package mx.uach.test.compiladores;

import java.util.ArrayList;
import mx.uach.compiladores.AnalizadorSintacticoAvanzado;
import mx.uach.compiladores.Token;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jesus
 */
public class AnalizadorSintacticoAvanzadoTest {
    
    public AnalizadorSintacticoAvanzadoTest() {
    }
    
    @Test
    public void numeroDeMasDosDigitosTest(){
        String exp = "123456689;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1,600,"123456689"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test(expected = Error.class)
    public void numeroDeMasDosDigitosTestFail(){
        String exp = "12345 6689;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1,600,"123456689"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test
    public void sumarDosNumerosTokesTest(){
        String exp = "1+2;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1"));
        esperado.add(new Token(1, '+', "+")); 
        esperado.add(new Token(1, 600, "2"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
        
    }
    
    @Test(expected = Error.class)
    public void sumarDosNumerosTokesTestFail(){
        String exp = "1+ 2;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1"));
        esperado.add(new Token(1, '+', "+"));
        esperado.add(new Token(1, ' ', " "));
        esperado.add(new Token(1, 600, "2"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
        
    }
    
    @Test
    public void dosLineasCodigoTest(){
        String exp = "1+2;44*5;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1" ));
        esperado.add(new Token(1, '+', "+" ));
        esperado.add(new Token(1, 600, "2" ));
        esperado.add(new Token(1, ';', ";" ));
        esperado.add(new Token(2, 600, "44"));
        esperado.add(new Token(2, '*', "*" ));
        esperado.add(new Token(2, 600, "5" ));
        esperado.add(new Token(2, ';', ";" ));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test(expected = Error.class)
    public void dosLineasCodigoTestFail(){
        String exp = "1+2; 44*5;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1" ));
        esperado.add(new Token(1, '+', "+" ));
        esperado.add(new Token(1, 600, "2" ));
        esperado.add(new Token(1, ';', ";" ));
        esperado.add(new Token(1, 600, "44"));
        esperado.add(new Token(1, '*', "*" ));
        esperado.add(new Token(1, 600, "5" ));
        esperado.add(new Token(1, ';', ";" ));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test
    public void parentecisCodigoTest(){
        String exp = "1+(2/44)*5;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1" ));
        esperado.add(new Token(1, '+', "+" ));
        esperado.add(new Token(1, '(', "(" ));
        esperado.add(new Token(1, 600, "2" ));
        esperado.add(new Token(1, '/', "/" ));
        esperado.add(new Token(1, 600, "44"));
        esperado.add(new Token(1, ')', ")" ));
        esperado.add(new Token(1, '*', "*" ));
        esperado.add(new Token(1, 600, "5" ));
        esperado.add(new Token(1, ';', ";" ));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test(expected = Error.class)
    public void parentecisCodigoTestFail(){
        String exp = "1+(2/ 44)*5;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1" ));
        esperado.add(new Token(1, '+', "+" ));
        esperado.add(new Token(1, '(', "(" ));
        esperado.add(new Token(1, 600, "2" ));
        esperado.add(new Token(1, '/', "/" ));
        esperado.add(new Token(1, 600, "44"));
        esperado.add(new Token(1, ')', ")" ));
        esperado.add(new Token(1, '*', "*" ));
        esperado.add(new Token(1, 600, "5" ));
        esperado.add(new Token(1, ';', ";" ));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test
    public void multiplicacionDosNumerosTest(){
        String exp = "1124*2123;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1124"));
        esperado.add(new Token(1, '*', "*"   ));
        esperado.add(new Token(1, 600, "2123"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test(expected = Error.class)
    public void multiplicacionDosNumerosTestFail(){
        String exp = "1124*2123 ;";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1124"));
        esperado.add(new Token(1, '*', "*"   ));
        esperado.add(new Token(1, 600, "2123"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        ArrayList <Token> generado = analizador.getTokens(exp);
        
        assertTrue(esperado.equals(generado));
    }
    
    @Test
    public void tokensRandomTest(){
        String exp = "1234567890+-*/();";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1234567890"));
        esperado.add(new Token(1, '+', "+"));
        esperado.add(new Token(1, '-', "-"));
        esperado.add(new Token(1, '*', "*"));
        esperado.add(new Token(1, '/', "/"));
        esperado.add(new Token(1, '(', "("));
        esperado.add(new Token(1, ')', ")"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        
        assertTrue(esperado.equals(analizador.getTokens(exp)));
    }
    
    @Test(expected = Error.class)
    public void tokensRandomTestFail(){
        String exp = "1234567890+- */();";
        ArrayList <Token> esperado = new ArrayList();
        esperado.add(new Token(1, 600, "1234567890"));
        esperado.add(new Token(1, '+', "+"));
        esperado.add(new Token(1, '-', "-"));
        esperado.add(new Token(1, '*', "*"));
        esperado.add(new Token(1, '/', "/"));
        esperado.add(new Token(1, '(', "("));
        esperado.add(new Token(1, ')', ")"));
        esperado.add(new Token(1, ';', ";"));
        
        AnalizadorSintacticoAvanzado analizador = new AnalizadorSintacticoAvanzado();
        
        assertTrue(esperado.equals(analizador.getTokens(exp)));
    }
}
