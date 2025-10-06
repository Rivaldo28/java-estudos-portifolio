package robot;

import java.util.Scanner;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.LocalMaryInterface;

public class Jarvis {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("Ol�, eu sou o JARVIS. Como posso ajudar?");

        try {
            while (true) {
                var input = scanner.nextLine().toLowerCase();
                if (input.contains("clima")) {
                    // Integra��o com a API OpenWeatherMap para obter informa��es sobre o clima
                    var response = "O clima est� ensolarado hoje.";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                } else if (input.contains("toca uma m�sica")) {
                    // Integra��o com a API do Spotify para tocar m�sica
                    var response = "Tocando a sua m�sica favorita.";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                } else if (input.contains("anote")) {
                    // Cria��o de anota��es
                    var response = "O que voc� gostaria de anotar?";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                    var note = scanner.nextLine();
                    response = "Anotado: " + note;
                    System.out.println(response);
                    speak(response); // Fala a resposta
                } else if (input.contains("tchau")) {
                    var response = "At� mais!";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                    break;
                } else {
                    var response = "Desculpe, n�o entendi o que voc� disse.";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Fala a resposta
    public static void speak(String text) {
        MaryInterface mary = null;
        try {
            mary = new LocalMaryInterface();
            mary.setVoice("cmu-slt-hsmm");
            var audio = mary.generateAudio(text); // Chama o m�todo generateAudio() da inst�ncia de MaryInterface
            
            // Reproduz o �udio gerado
            var format = new AudioFormat(16000, 16, 1, true, true);
            var info = new DataLine.Info(SourceDataLine.class, format);
            var line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            
            var buffer = new byte[4096];
            int n;
            while ((n = audio.read(buffer, 0, buffer.length)) > 0) {
                line.write(buffer, 0, n);
            }
            
            line.drain();
            line.stop();
        } catch (MaryConfigurationException | SynthesisException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mary != null) {
                // N�o � necess�rio chamar o m�todo shutdown() a partir da vers�o 5.2 da biblioteca MaryTTS
            }
        }
    }
}