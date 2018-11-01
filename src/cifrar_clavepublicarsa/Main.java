/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrar_clavepublicarsa;

/**
 *
 * @author IMCG
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.*;
import java.util.Scanner;
import javax.crypto.*;

//Encriptar y desencriptar un texto mediante clave pública RSA
public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Crear clave pública y privada");
        //Crea e inicializa el generador de claves RSA
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);//tamaño de la clave
        KeyPair clavesRSA = keyGen.generateKeyPair();
        PrivateKey clavePrivada = clavesRSA.getPrivate();//obtiene clave privada
        PublicKey clavePublica = clavesRSA.getPublic();//obtiene clave pública
        Scanner sc = new Scanner(System.in);

        //muestra las claves generadas
        System.out.println("clavePublica: " + clavePublica);
        System.out.println("clavePrivada: " + clavePrivada);
        //texto a encriptar o cifrar
        System.out.println("Introduce el texoto a encritar");
        String textoentrada = sc.nextLine();

        //combierte el codigo a byte
        byte[] bufferClaro = textoentrada.getBytes();

        //Crea cifrador RSA
        Cipher cifrador = Cipher.getInstance("RSA");
        //Pone cifrador en modo ENCRIPTACIÓN utilizando la clave pública
        cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
        System.out.println("Cifrar con clave pública el Texto:");
        mostrarBytes(bufferClaro);

        //obtiene todo el texto cifrado
        File file = new File("Prueba");
        byte[] bufferCifrado = cifrador.doFinal(bufferClaro);
        System.out.println("Texto CIFRADO");
        mostrarBytes(bufferCifrado); //muestra texto cifrado
        System.out.println("\n_______________________________");
        System.out.println("Guardo el texto en el fiechoro Prueba");
        FileOutputStream fileOuputStream = new FileOutputStream(file);
        fileOuputStream.write(bufferCifrado);
        fileOuputStream.close();

        //Pone cifrador en modo DESENCRIPTACIÓN utilizando la clave privada
        cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
        System.out.println("Leo el texto del fichero Prueba y descifro con clave privada");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileArray = new byte[(int) file.length()];
        fileInputStream.read(fileArray);

        //obtiene el texto descifrado
        bufferClaro = cifrador.doFinal(fileArray);
        System.out.println("Texto DESCIFRADO");
        mostrarBytes(bufferClaro);//muestra texto descifrado
        System.out.println("\n_______________________________");
    }

    public static void mostrarBytes(byte[] buffer) throws IOException {
        System.out.write(buffer);

    }
}
