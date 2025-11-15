package com.tiendamascota.util;

public class RutValidator {
    
    /**
     * Valida un RUT chileno en formato 
     * Retorna true si es válido, false si no
     */
    public static boolean esValido(String rut) {
        if (rut == null || rut.isEmpty()) {
            return true; // RUT opcional, null es válido
        }
        
        try {
            // Limpiar: remover puntos y espacios
            String rutLimpio = rut.replaceAll("\\.", "").replaceAll(" ", "").toUpperCase();
            
            // Validar formato: XXXXXXXX-X o XXXXXXX-X
            if (!rutLimpio.matches("\\d{7,8}-[0-9K]")) {
                return false;
            }
            
            // Separar número y dígito verificador
            String[] partes = rutLimpio.split("-");
            long numero = Long.parseLong(partes[0]);
            char digitoVerificador = partes[1].charAt(0);
            
            // Calcular dígito verificador correcto
            char digitoCalculado = calcularDV(numero);
            
            return digitoVerificador == digitoCalculado;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    /**
     * Calcula el dígito verificador según algoritmo chileno
     */
    private static char calcularDV(long numero) {
        int suma = 0;
        int multiplicador = 2;
        
        while (numero > 0) {
            suma += (numero % 10) * multiplicador;
            numero /= 10;
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }
        
        int residuo = 11 - (suma % 11);
        
        if (residuo == 11) return '0';
        if (residuo == 10) return 'K';
        return String.valueOf(residuo).charAt(0);
    }
    
    /**
     * Formatea un RUT a formato 
     */
    public static String formatear(String rut) {
        if (rut == null || rut.isEmpty()) {
            return rut;
        }
        
        String rutLimpio = rut.replaceAll("[^0-9K]", "").toUpperCase();
        
        if (rutLimpio.length() < 8) {
            return rutLimpio;
        }
        
        String numero = rutLimpio.substring(0, rutLimpio.length() - 1);
        String dv = rutLimpio.substring(rutLimpio.length() - 1);
        
        // Formatear con puntos
        StringBuilder formatted = new StringBuilder();
        int contador = 0;
        for (int i = numero.length() - 1; i >= 0; i--) {
            if (contador == 3) {
                formatted.insert(0, ".");
                contador = 0;
            }
            formatted.insert(0, numero.charAt(i));
            contador++;
        }
        
        return formatted.toString() + "-" + dv;
    }
}
