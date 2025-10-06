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
        System.out.println("Olá, eu sou o JARVIS. Como posso ajudar?");

        try {
            while (true) {
                var input = scanner.nextLine().toLowerCase();
                if (input.contains("clima")) {
                    // Integração com a API OpenWeatherMap para obter informações sobre o clima
                    var response = "O clima está ensolarado hoje.";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                } else if (input.contains("toca uma música")) {
                    // Integração com a API do Spotify para tocar música
                    var response = "Tocando a sua música favorita.";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                } else if (input.contains("anote")) {
                    // Criação de anotações
                    var response = "O que você gostaria de anotar?";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                    var note = scanner.nextLine();
                    response = "Anotado: " + note;
                    System.out.println(response);
                    speak(response); // Fala a resposta
                } else if (input.contains("tchau")) {
                    var response = "Até mais!";
                    System.out.println(response);
                    speak(response); // Fala a resposta
                    break;
                } else {
                    var response = "Desculpe, não entendi o que você disse.";
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
            var audio = mary.generateAudio(text); // Chama o método generateAudio() da instância de MaryInterface
            
            // Reproduz o áudio gerado
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
                // Não é necessário chamar o método shutdown() a partir da versão 5.2 da biblioteca MaryTTS
            }
        }
    }
}