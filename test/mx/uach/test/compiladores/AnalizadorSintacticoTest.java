package mx.uach.test.compiladores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import mx.uach.compiladores.AnalizadorSintactico;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author jesus
 */
public class AnalizadorSintacticoTest {
    
    private ByteArrayOutputStream salida;
    private InputStream entrada;
    
    /**
     * antes de ejecutar una prueva se cambian las entrasdas y salidas por
     * variables de una prueba.
     */
    
    @Before
    public void setUp(){
        this.entrada = System.in;
        this.salida  = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.salida));
    }
    
    /**
     * despues des ejecutar una prueba vuelve a colocar las entradas y salidas
     * correctamente.
     */
    @After
    public void tearDown(){
        System.setIn(this.entrada);
        System.setOut(null);
    }
    
    /**
     * Prueba que verifica que la expresion de la suma de dos numeros sea
     * correcta al ser transformada a notacion posfija.
     * 
     * @throws IOException cuando las entradas o salidas son incorrectas.
     */
    
    @Test 
    public void sumarDosNumerosTest() throws IOException{
        String exp = "5+4;";
        this.setData(exp);
        AnalizadorSintactico analizador  =  new AnalizadorSintactico();
        analizador.iniciarAnalisis();
        String resultado = salida.toString().trim();
        assertTrue(resultado.equals("54+"));
    }
    
    /**
     * Prueba que verifica que cuando el compilador intenta evaluar una expresion
     * de suma de dos numeros y no encuentra el final de sentencia(;) genere un Error.
     * 
     * @see Error
     * 
     * @throws IOException  Cuando las entrasdas y salidas son correctas
     */
    
    @Test(expected = Error.class)
    public void sumarDosNumerosTestFail() throws IOException{
        String exp = "5+4";
        this.setData(exp);
        AnalizadorSintactico analizador  =  new AnalizadorSintactico();
        analizador.iniciarAnalisis();
        String resultado = salida.toString().trim();
        assertTrue(resultado.equals("54+"));
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void sumarTresNumerosTest() throws IOException{
        String exp = "5+4+7;";
        this.setData(exp);
        AnalizadorSintactico analizador  =  new AnalizadorSintactico();
        analizador.iniciarAnalisis();
        String resultado = salida.toString().trim();
        assertTrue(resultado.equals("54+7+"));
    }
    
    /**
     *
     * @throws IOException
     */
    @Test (expected = Error.class)
    public void sumarTresNumerosTestFail() throws IOException{
        String exp = "5+47;";
        this.setData(exp);
        AnalizadorSintactico analizador  =  new AnalizadorSintactico();
        analizador.iniciarAnalisis();
        String resultado = salida.toString().trim();
        assertTrue(resultado.equals("54+7+"));
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void restarDosNumerosTest() throws IOException{
        String exp = "5-2;";
        this.setData(exp);
        AnalizadorSintactico analizador  =  new AnalizadorSintactico();
        analizador.iniciarAnalisis();
        String resultado = salida.toString().trim();
        assertTrue(resultado.equals("52-"));
    }
    
    /**
     *
     * @throws IOException
     */
    @Test (expected = Error.class)
    public void restarDosNumerosTestFail() throws IOException{
        String exp = "5-;";
        AnalizadorSintactico analizador  =  new AnalizadorSintactico();
        analizador.iniciarAnalisis();
        String resultado = salida.toString().trim();
        assertTrue(resultado.equals("54+7+"));
    }
    
    /**
     * Asigna un valor o valores como entradaa compilador simulando entrada por 
     * consola.
     * 
     * @param data cadena a evaluar por compilador.
     */
    private void setData(String data){
        System.setIn(new ByteArrayInputStream(String.format("%s", data).getBytes()));
    }
}
