/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dni;

/**
 * Esta clase representa un DNI/NIf Español. Valida que el NIF tenga el formato
 * correcto mediante expresiones regulares (8 digitos y una letra) y que la
 * letra sea la correcta segun el algoritmo del módulo de 23.
 *
 * @author Enrique Sainz-Pardo Lopez
 */
public class DNI {

    private int numDNI;
    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    /**
     * Devolver el NIF completo (numero y letra).
     *
     * @return NIF en formato 12345678Z
     */
    public String obtenerNIF() {
        char letra = calcularLetraNIF(numDNI);
        //Se formatea el numero a 8 digitos (rellando con 0 si es necesario)
        return String.format("%08d%c", numDNI, letra);
    }

    /**
     * Devolver el número de DNI (sin letra).
     *
     * @return numero de DNI
     */
    public int obtenerDNI() {
        return numDNI;
    }

    /**
     * Establece el DNI a partir de un NIF completo. Sirve para validar que el
     * formato son 8 digitos + 1 letra mayuscula.
     *
     * @param nif String con el NIF (por ejemplo "12345678Z"
     * @throws java.lang.Exception Si el formato es incorrecto o la letra no
     * coincide lanza una excepcion.
     */
    public void establecer(String nif) throws Exception {
        if (nif == null || !nif.matches("^[0-9]{8}[A-Z]$")) {
            throw new Exception("Formato de NIF incorrecto: " + nif);
        }
        int numero = Integer.parseInt(nif.substring(0, 8));
        char letra = nif.charAt(8);
        if (letra != calcularLetraNIF(numero)) {
            throw new Exception("El NIF es incorrecto: la letra no coincide.");
        }
        this.numDNI = numero;
    }

    /**
     * Establece el DNI a partir de un entero.
     *
     * @param dni Numero de DNI.
     * @throws java.lang.Exception Lanza una excepcion si el numero esta fuera
     * de rango.
     */
    public void establecer(int dni) throws Exception {
        if (dni < 00000000 || dni > 99999999) {
            throw new Exception("Numero de DNI fuera de rango: " + dni);
        }
        this.numDNI = dni;
    }

    /**
     * Metodo para calcular la letra del NIF a partir del numero mediante el
     * modulo 23.
     */
    private static char calcularLetraNIF(int dni) {
        return LETRAS_DNI.charAt(dni % 23);
    }
}
