/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER-HP
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AhorcadoVisual extends JFrame implements ActionListener {

    private static final String[] PALABRAS = {"JAVA", "PYTHON", "HTML", "CSS", "JAVASCRIPT"};
    private String palabraSecreta;
    private char[] palabraDescubierta;
    private int intentosRestantes;
    private JButton[] botonesLetras;
    private JLabel etiquetaPalabra;
    private JLabel etiquetaIntentos;
    private JLabel etiquetaAhorcado;
    private JTextArea areaResultado;

    private static final String[] AHORCADO_DIBUJO = {
            "    -----\n" +
            "    |   |\n" +
            "        |\n" +
            "        |\n" +
            "        |\n" +
            "        |\n" +
            "---------",
            
            "    -----\n" +
            "    |   |\n" +
            "    O   |\n" +
            "        |\n" +
            "        |\n" +
            "        |\n" +
            "---------",

            "    -----\n" +
            "    |   |\n" +
            "    O   |\n" +
            "    |   |\n" +
            "        |\n" +
            "        |\n" +
            "---------",

            "    -----\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|   |\n" +
            "        |\n" +
            "        |\n" +
            "---------",

            "    -----\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|\\  |\n" +
            "        |\n" +
            "        |\n" +
            "---------",

            "    -----\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|\\  |\n" +
            "   /    |\n" +
            "        |\n" +
            "---------",

            "    -----\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|\\  |\n" +
            "   / \\  |\n" +
            "        |\n" +
            "---------"
    };

    public AhorcadoVisual() {
        setTitle("Juego de Ahorcado");
        setSize(600, 400);  // Ajusta el tamaño según tus necesidades
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarJuego();

        // Crear componentes
        etiquetaPalabra = new JLabel();
        etiquetaIntentos = new JLabel();
        etiquetaAhorcado = new JLabel();
        areaResultado = new JTextArea(10, 30);
        areaResultado.setEditable(false);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 13));

        botonesLetras = new JButton[26];
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton boton = new JButton(String.valueOf(c));
            boton.addActionListener(this);
            botonesLetras[c - 'A'] = boton;
            panelBotones.add(boton);
        }

        // Diseño de la interfaz
        setLayout(new BorderLayout());
        add(etiquetaPalabra, BorderLayout.CENTER);
        add(etiquetaIntentos, BorderLayout.SOUTH);
        add(etiquetaAhorcado, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.NORTH);
        add(areaResultado, BorderLayout.EAST);

        actualizarInterfaz();
    }

    private void iniciarJuego() {
        palabraSecreta = seleccionarPalabraAleatoria().toUpperCase();
        palabraDescubierta = new char[palabraSecreta.length()];
        for (int i = 0; i < palabraDescubierta.length; i++) {
            palabraDescubierta[i] = '_';
        }
        intentosRestantes = 6; // Número de intentos permitidos
    }

    private String seleccionarPalabraAleatoria() {
        int indice = (int) (Math.random() * PALABRAS.length);
        return PALABRAS[indice];
    }

    private void actualizarInterfaz() {
        etiquetaPalabra.setText("Palabra: " + new String(palabraDescubierta));
        etiquetaIntentos.setText("Intentos restantes: " + intentosRestantes);
        etiquetaAhorcado.setText(AHORCADO_DIBUJO[6 - intentosRestantes]);

        // Deshabilitar botones de letras ya utilizadas
        for (int i = 0; i < botonesLetras.length; i++) {
            botonesLetras[i].setEnabled(true);
            char letra = (char) ('A' + i);
            for (int j = 0; j < palabraSecreta.length(); j++) {
                if (letra == palabraSecreta.charAt(j) || letra == Character.toLowerCase(palabraSecreta.charAt(j))) {
                    if (palabraDescubierta[j] != '_') {
                        botonesLetras[i].setEnabled(false);
                    }
                }
            }
        }

        if (intentosRestantes <= 0 || esAdivinada()) {
            mostrarResultado();
            reiniciarJuego();
        }
    }

    private boolean esAdivinada() {
        return new String(palabraDescubierta).equals(palabraSecreta);
    }

    private void mostrarResultado() {
        String mensaje;
        if (esAdivinada()) {
            mensaje = "¡Felicidades! Has adivinado la palabra: " + palabraSecreta;
        } else {
            mensaje = "¡Agotaste tus intentos! La palabra era: " + palabraSecreta;
        }

        areaResultado.setText(""); // Limpiar el área de resultados

        // Agregar imagen actual del ahorcado al área de resultados
        for (int i = 6 - intentosRestantes; i < AHORCADO_DIBUJO.length; i++) {
            areaResultado.append(AHORCADO_DIBUJO[i] + "\n");
        }

        // Mostrar el resultado y la imagen en el JTextArea
        areaResultado.append("\n" + mensaje);

        int option = JOptionPane.showOptionDialog(this, areaResultado, "Resultado del Juego",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);

        // Cerrar la aplicación si el usuario hace clic en "Ok"
        if (option == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    private void reiniciarJuego() {
        iniciarJuego();
        actualizarInterfaz();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) e.getSource();
            char letra = boton.getText().charAt(0);

            if (!intento(letra)) {
                intentosRestantes--;
            }

            boton.setEnabled(false);
            actualizarInterfaz();
        }
    }

    private boolean intento(char letra) {
        boolean acierto = false;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra || palabraSecreta.charAt(i) == Character.toLowerCase(letra)) {
                palabraDescubierta[i] = letra;
                acierto = true;
            }
        }

        return acierto;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AhorcadoVisual().setVisible(true));
    }
}
