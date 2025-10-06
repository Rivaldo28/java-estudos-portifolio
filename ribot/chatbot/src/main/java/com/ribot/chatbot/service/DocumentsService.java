package com.ribot.chatbot.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

@Service
public class DocumentsService {

    public static List<Map<String, Object>> getDocuments(String question, int codeUser) {
        try {
            question = question.trim();

            String nome = getName(question);
            String idade = getYears(question);
            String email = "";
            String celular = "";
            String telefone = "";
            String cep = "";
            String endereco = getEndereco(question);
            String bairro = getBairro(question);
            String numero = "";
            String cpf = "";
            String cnpj = "";

            String[] questionTokens = question.split(" ");
            for (String word : questionTokens) {
                word = word.trim();
                if (word.length() >= 1) {
                    if (email.isEmpty()) email = email(word);
                    if (celular.isEmpty()) celular = validateCelular(word);
                    if (telefone.isEmpty()) telefone = telefone(word);
                    if (cep.isEmpty()) cep = cep(word);
                    if (numero.isEmpty()) numero = obterNumero(word, question);
                    if (cpf.isEmpty()) cpf = validarCPF(word);
                    if (cnpj.isEmpty()) cnpj = validarCNPJ(word);
                }
            }

            List<Map<String, Object>> documents = new ArrayList<>();
            Map<String, Object> objJSON = new HashMap<>();
            objJSON.put("code_user", codeUser > 0 ? codeUser : -1);
            objJSON.put("nome", nome.isEmpty() ? "" : nome);
            objJSON.put("idade", idade.isEmpty() ? "" : Integer.parseInt(idade));
            objJSON.put("email", email.isEmpty() ? "" : email);
            objJSON.put("celular", celular.isEmpty() ? "" : Long.parseLong(celular));
            objJSON.put("telefone", telefone.isEmpty() ? "" : Long.parseLong(telefone));
            objJSON.put("cep", cep.isEmpty() ? "" : Long.parseLong(cep));
            objJSON.put("endereco", endereco.isEmpty() ? "" : endereco);
            objJSON.put("bairro", bairro.isEmpty() ? "" : bairro);
            objJSON.put("numero", numero.isEmpty() ? "" : Integer.parseInt(numero));
            objJSON.put("cpf", cpf.isEmpty() ? "" : Long.parseLong(cpf));
            objJSON.put("cnpj", cnpj.isEmpty() ? "" : Long.parseLong(cnpj));
            objJSON.put("activate", 1);

            if (!nome.isEmpty() || !idade.isEmpty() || !email.isEmpty() || !celular.isEmpty() || !telefone.isEmpty()
                    || !cep.isEmpty() || !endereco.isEmpty() || !bairro.isEmpty() || !numero.isEmpty() || !cpf.isEmpty()
                    || !cnpj.isEmpty()) {
                documents.add(objJSON);
            }

            return documents;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void mainDocumentos(String[] args) {
        String question = ""; // Provide the question as input
        int codeUser = -1; // Provide the code_user as input
        List<Map<String, Object>> result = getDocuments(question, codeUser);
        System.out.println(result);
    }

    private static String getDefaultName(String question)  {
        try {
            String nome = "";
            BufferedReader reader = new BufferedReader(new FileReader("./names.csv"));
            String data = reader.readLine();
            String[] names = data.trim().split(",");
            String tempName = "";
            int tempIndex = Integer.MAX_VALUE;
            for (String name : names) {
                name = name.trim();
                int indexStart = question.indexOf(name);
                if (indexStart >= 0) {
                    if (!name.equals(tempName) && indexStart < tempIndex) {
                        tempName = name;
                        tempIndex = indexStart;

                        int index1 = question.indexOf(" e ");
                        if (index1 < 0 || index1 < indexStart) index1 = Integer.MAX_VALUE;
                        int index2 = question.indexOf(" é ");
                        if (index2 < 0 || index2 < indexStart) index2 = Integer.MAX_VALUE;
                        int index3 = question.indexOf(",");
                        if (index3 < 0 || index3 < indexStart) index3 = Integer.MAX_VALUE;
                        int index4 = question.indexOf(";");
                        if (index4 < 0 || index4 < indexStart) index4 = Integer.MAX_VALUE;
                        int index5 = question.indexOf(".");
                        if (index5 < 0 || index5 < indexStart) index5 = Integer.MAX_VALUE;
                        int[] indexes = {index1, index2, index3, index4, index5};
                        int indexEnd = Math.min(Math.abs(index1 - indexStart), Math.min(Math.abs(index2 - indexStart),
                                Math.min(Math.abs(index3 - indexStart), Math.min(Math.abs(index4 - indexStart),
                                        Math.abs(index5 - indexStart))))) + indexStart;
                        if (indexEnd < indexStart) indexEnd = question.length();
                        if (indexEnd < 0) indexEnd = question.length();
                        nome = question.substring(indexStart, indexEnd);
                        nome = nome.replace(" é ", "");
                        nome = nome.replace(":", "");
                        nome = nome.replaceAll("[0-9]", "").trim();

                        if (nome.indexOf(" e ") > 0) {
                            nome = nome.split(" e ")[0].trim();
                        }
                        if (nome.indexOf(" é ") > 0) {
                            nome = nome.split(" é ")[0].trim();
                        }
                        if (nome.indexOf(",") > 0) {
                            nome = nome.split(",")[0].trim();
                        }
                        if (nome.indexOf(";") > 0) {
                            nome = nome.split(";")[0].trim();
                        }
                        if (nome.indexOf(".") > 0) {
                            nome = nome.split("\\.")[0].trim();
                        }
                    }
                }
            }
            reader.close();
            return nome.trim();
        } catch (IOException e) {
            System.out.println("{error: 'erro de requisição 37'}");
            return "";
        }
    }


    public static void main(String[] args) {
        String question = ""; // Preencha com a pergunta desejada
        String defaultName = getDefaultName(question);
        String years = getYears(question);
        System.out.println(defaultName);
    }


    public static String getName(String question) {
        try {
            question = question.trim();
            String nome = "";
            String Default = getDefaultName(question);
            if (Default.length() <= 0) {
                String start = "";
                if (question.indexOf("Nome") >= 0) start = "Nome";
                if (question.indexOf("nome") >= 0) start = "nome";
                if (question.indexOf("chamo") >= 0) start = "chamo";

                if ((start.length() > 0) && (question.indexOf("seu") < 0)) {
                    int indexStart = question.indexOf(start) + start.length() + 1;

                    int index1 = question.indexOf(" e ");
                    if ((index1 < 0) || (index1 < indexStart)) index1 = Integer.MAX_VALUE;
                    int index2 = question.indexOf(",");
                    if ((index2 < 0) || (index2 < indexStart)) index2 = Integer.MAX_VALUE;
                    int index3 = question.indexOf(";");
                    if ((index3 < 0) || (index3 < indexStart)) index3 = Integer.MAX_VALUE;
                    int index4 = question.indexOf(".");
                    if ((index4 < 0) || (index4 < indexStart)) index4 = Integer.MAX_VALUE;
                    int[] indices = {index1, index2, index3, index4};
                    int indexEnd = findSmallestPositive(indices) + indexStart;
                    if (indexEnd < indexStart) indexEnd = question.length();
                    nome = question.substring(indexStart, indexEnd);
                    nome = nome.replace(" é ", "");
                    nome = nome.replace(":", "");
                    nome = nome.replaceAll("[0-9]", "").trim();

                    if (nome.indexOf(" e ") > 0) {
                        nome = nome.split(" e ")[0].trim();
                    }
                    if (nome.indexOf(",") > 0) {
                        nome = nome.split(",")[0].trim();
                    }
                    if (nome.indexOf(";") > 0) {
                        nome = nome.split(";")[0].trim();
                    }
                    if (nome.indexOf(".") > 0) {
                        nome = nome.split("\\.")[0].trim();
                    }
                }
            } else {
                nome = Default;
            }
            return nome;
        } catch (Exception e) {
            System.out.println("{error: 'erro de requisição 38'}");
            return "";
        }
    }

    private static int findSmallestPositive(int[] array) {
        int smallest = Integer.MAX_VALUE;
        for (int num : array) {
            if (num > 0 && num < smallest) {
                smallest = num;
            }
        }
        return smallest;
    }

    public static String getYears(String question) {
        try {
            question = question.toString().trim();
            question = question.replaceAll("[^0-9a-zA-Z\\s]", "");
            String idade = "";
            if (question.contains("anos")) {
                String[] arr = question.split(" ");
                int index = -1;
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("anos")) {
                        index = i;
                        break;
                    }
                }
                if (index > 0 && index < arr.length - 1) {
                    String anos = arr[index - 1];
                    if ((Integer.parseInt(anos) > 0) && (Integer.parseInt(anos) < 125)) {
                        idade = anos;
                    }
                }
            }
            return idade;
        } catch (Exception e) {
            System.out.println("{error: 'erro de requisição 39'}");
            return "";
        }
    }


    public static String email(String email) {
        try {
            email = email.toString().trim();
            email = email.replaceAll("[^0-9a-zA-Z@.-_]", "");
            if ((email.indexOf('@') > 0) && (email.indexOf('.') > 0) && (email.length() >= 5)) {
                char c = email.charAt(email.length() - 1);
                if (c == '.') {
                    email = email.substring(0, email.length() - 1);
                }
                return email;
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("{error: 'erro de requisição 40'}");
            return "";
        }
    }


    public static String validateCelular(String celular) {
        try {
            celular = celular.toString().trim();
            celular = celular.replaceAll("[^0-9]", "");
            if (celular.indexOf("55") == 0) {
                celular = celular.replace("55", "");
            }
            String cpf = validarCPF(celular);
            if ((celular.length() == 11) && (cpf.length() <= 0) && (celular.indexOf("9") > 0)) {
                return celular; }
            else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("{error: 'erro de requisição 41'}");
            return "";
        }
//        return celular;
    }

    public static String telefone(String telefone) {
        try {
            telefone = telefone.trim();
            telefone = telefone.replaceAll("[^0-9]", "");

            // Verificar e remover código do país
            if (telefone.startsWith("+")) {
                int index = telefone.indexOf(' ');
                if (index != -1) {
                    telefone = telefone.substring(index + 1);
                } else {
                    telefone = telefone.substring(1);
                }
            }

            // Verificar e remover DDD do estado
            if (telefone.startsWith("(")) {
                int index = telefone.indexOf(')');
                if (index != -1) {
                    telefone = telefone.substring(index + 1);
                }
            }

            // Verificar o comprimento final do número de telefone
            if (telefone.length() == 9) {
                return telefone;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static void mainTelefone(String[] args) {
        String telefone = "+55 (11) 1234-5678";
        String telefoneFormatado = telefone(telefone);
        System.out.println(telefoneFormatado);
    }

    public static String cep(String cep) {
        try {
            cep = cep.trim();
            cep = cep.replaceAll("[^0-9]", "");
            if (cep.length() != 8) {
                return "";
            } else {
                return cep;
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static void mainCep(String[] args) {
        String cep = "12345-678";
        String cepFormatado = cep(cep);
        System.out.println(cepFormatado);
    }

    public static String getEndereco(String question) {
        try {
            question = question.trim();
            String endereco = "";
            String start = "";

            if (question.contains("Endereço")) {
                start = "Endereço";
            } else if (question.contains("Rua")) {
                start = "Rua";
            } else if (question.contains("R.")) {
                start = "R.";
            } else if (question.contains("Avenida")) {
                start = "Avenida";
            } else if (question.contains("Av.")) {
                start = "Av.";
            } else if (question.contains("Travessa")) {
                start = "Travessa";
            }

            if (!start.isEmpty()) {
                int indexStart = question.indexOf(start);

                int index1 = question.indexOf(" e ");
                if (index1 < 0 || index1 < indexStart) {
                    index1 = Integer.MAX_VALUE;
                }
                int index2 = question.indexOf(",");
                if (index2 < 0 || index2 < indexStart) {
                    index2 = Integer.MAX_VALUE;
                }
                int index3 = question.indexOf(";");
                if (index3 < 0 || index3 < indexStart) {
                    index3 = Integer.MAX_VALUE;
                }
                int index4 = question.indexOf(".");
                if (index4 < 0 || index4 < indexStart) {
                    index4 = Integer.MAX_VALUE;
                }
                int index5 = question.indexOf("-");
                if (index5 < 0 || index5 < indexStart) {
                    index5 = Integer.MAX_VALUE;
                }
                int indexEnd = Math.min(
                        Math.min(Math.min(Math.min(index1, index2), index3), index4),
                        index5
                ) + indexStart;
                if (indexEnd < indexStart) {
                    indexEnd = question.length();
                }
                endereco = question.substring(indexStart, indexEnd);
                endereco = endereco.replace(" é ", "").trim();

                String carac = "";
                index1 = endereco.indexOf(" e ");
                if (index1 >= 0) {
                    carac = " e ";
                }
                index2 = endereco.indexOf(",");
                if (index2 >= 0) {
                    carac = ",";
                }
                index3 = endereco.indexOf(";");
                if (index3 >= 0) {
                    carac = ";";
                }
                index4 = endereco.indexOf(".");
                if (index4 >= 0) {
                    carac = ".";
                }
                index5 = endereco.indexOf("-");
                if (index5 >= 0) {
                    carac = "-";
                }
                String[] arrEndereco = endereco.split(carac);
                endereco = arrEndereco[0].trim();
            }
            return endereco;
        } catch (Exception e) {
            return "";
        }
    }

    public static void mainEndereco(String[] args) {
        String question = "Qual é o endereço da Rua ABC?";
        String endereco = getEndereco(question);
        System.out.println(endereco);
    }

    public static String getBairro(String question) {
        try {
            question = question.trim();
            String bairro = "";
            String start = "";

            if (question.contains("Bairro")) {
                start = "Bairro";
            }

            if (!start.isEmpty()) {
                int indexStart = question.indexOf(start) + start.length() + 1;

                int index1 = question.indexOf(" e ");
                if (index1 < 0 || index1 < indexStart) {
                    index1 = Integer.MAX_VALUE;
                }
                int index2 = question.indexOf(",");
                if (index2 < 0 || index2 < indexStart) {
                    index2 = Integer.MAX_VALUE;
                }
                int index3 = question.indexOf(";");
                if (index3 < 0 || index3 < indexStart) {
                    index3 = Integer.MAX_VALUE;
                }
                int index4 = question.indexOf(".");
                if (index4 < 0 || index4 < indexStart) {
                    index4 = Integer.MAX_VALUE;
                }
                int index5 = question.indexOf("-");
                if (index5 < 0 || index5 < indexStart) {
                    index5 = Integer.MAX_VALUE;
                }
                int indexEnd = Math.min(
                        Math.min(Math.min(Math.min(index1, index2), index3), index4),
                        index5
                ) + indexStart;
                if (indexEnd < indexStart) {
                    indexEnd = question.length();
                }
                bairro = question.substring(indexStart, indexEnd);
                bairro = bairro.replace(":", "");
                bairro = bairro.replace(" é ", "").trim();

                String carac = "";
                index1 = bairro.indexOf(" e ");
                if (index1 >= 0) {
                    carac = " e ";
                }
                index2 = bairro.indexOf(",");
                if (index2 >= 0) {
                    carac = ",";
                }
                index3 = bairro.indexOf(";");
                if (index3 >= 0) {
                    carac = ";";
                }
                index4 = bairro.indexOf(".");
                if (index4 >= 0) {
                    carac = ".";
                }
                index5 = bairro.indexOf("-");
                if (index5 >= 0) {
                    carac = "-";
                }
                String[] arrBairro = bairro.split(carac);
                bairro = arrBairro[0].trim();
            }
            return bairro;
        } catch (Exception e) {
            return "";
        }
    }

    public static void mainBairro(String[] args) {
        String question = "Qual é o bairro da cidade?";
        String bairro = getBairro(question);
        System.out.println(bairro);
    }

    public static String obterNumero(String numero, String question) {
        try {
            String Numero = "";
            String idade = getYears(question);
            Numero = numero.trim();
            Numero = Numero.replaceAll("[^0-9]", "");
            if (Numero.length() >= 1 && Numero.length() <= 4 && !Numero.equals(idade)) {
                return Numero;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static void mainNumero(String[] args) {
        String numero = "123";
        String question = "Qual é o número?";
        String resultado = obterNumero(numero, question);
        System.out.println(resultado);
    }

    public static String validarCPF(String cpf) {
        try {
            cpf = cpf.trim().replaceAll("\\D", "");
            if (cpf.length() != 11) {
                return "";
            }
            String result = cpf;
            int[] digitsToVerify = {9, 10};
            for (int j : digitsToVerify) {
                int soma = 0;
                int r;
                for (int i = 0; i < j; i++) {
                    int digit = Character.getNumericValue(cpf.charAt(i));
                    soma += digit * ((j + 2) - (i + 1));
                }
                r = soma % 11;
                r = (r < 2) ? 0 : 11 - r;
                if (r != Character.getNumericValue(cpf.charAt(j))) {
                    result = "";
                }
            }
            return result;
        } catch (Exception e) {
            return "";
            // System.out.println({error: 'erro de requisição 47'});
        }
    }

    public static void mainCpf(String[] args) {
        String cpf = "46395485083";
        String resultado = validarCPF(cpf);
        System.out.println(resultado);
    }

    public static String validarCNPJ(String cnpj) {
        try {
            cnpj = cnpj.trim().replaceAll("[^\\d]+", "");
            if (cnpj.isEmpty() || cnpj.length() != 14) {
                return "";
            }

            String[] invalidCNPJs = {"00000000000000", "11111111111111", "22222222222222", "33333333333333",
                    "44444444444444", "55555555555555", "66666666666666", "77777777777777",
                    "88888888888888", "99999999999999"};
            for (String invalidCNPJ : invalidCNPJs) {
                if (cnpj.equals(invalidCNPJ)) {
                    return "";
                }
            }

            int tamanho = cnpj.length() - 2;
            String numeros = cnpj.substring(0, tamanho);
            String digitos = cnpj.substring(tamanho);
            int soma = 0;
            int pos = tamanho - 7;
            for (int i = tamanho; i >= 1; i--) {
                soma += Character.getNumericValue(numeros.charAt(tamanho - i)) * pos--;
                if (pos < 2) {
                    pos = 9;
                }
            }
            int resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
            if (resultado != Character.getNumericValue(digitos.charAt(0))) {
                return "";
            }

            tamanho = tamanho + 1;
            numeros = cnpj.substring(0, tamanho);
            soma = 0;
            pos = tamanho - 7;
            for (int i = tamanho; i >= 1; i--) {
                soma += Character.getNumericValue(numeros.charAt(tamanho - i)) * pos--;
                if (pos < 2) {
                    pos = 9;
                }
            }
            resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
            if (resultado != Character.getNumericValue(digitos.charAt(1))) {
                return "";
            }

            return cnpj;
        } catch (Exception e) {
            return "";
            // System.out.println({error: 'erro de requisição 48'});
        }
    }

    public static void mainCpnj(String[] args) {
        String cnpj = "31835728000167";
        String resultado = validarCNPJ(cnpj);
        System.out.println(resultado);
    }

}
