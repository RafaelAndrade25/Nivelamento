import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String pdfPath = "downloads/arquivo_original.pdf"; // Caminho do PDF
        String csvPath = "downloads/dados_rol_procedimentos.csv"; // Caminho do CSV

        try {
            // Criar diretório se não existir
            File directory = new File("downloads");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Extrair texto do PDF
            String extrairTexto = extractTextFromPDF(pdfPath);

            // Processar e converter os dados da tabela para CSV
            writeCsvFromExtractedText(extrairTexto, csvPath);

            System.out.println("Arquivo CSV gerado com sucesso: " + csvPath);
        } catch (IOException e) {
            System.err.println("Erro ao processar PDF: " + e.getMessage());
        }
    }

    // Método para extrair texto do PDF
    public static String extractTextFromPDF(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    // Método para processar o texto extraído e salvar em CSV
    public static void writeCsvFromExtractedText(String text, String csvPath) throws IOException {
        // Criar um escritor para o CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath))) {
            // Definir cabeçalho
            String[] header = {"Código", "Grupo - Subgrupo - Tipo", "Procedimento - TUSS", "AMB", "ROL ANS"};
            writer.writeNext(header);

            // Processar o texto extraído para identificar as linhas da tabela
            String[] lines = text.split("\n");
            for (String line : lines) {
                // Filtrar apenas as linhas que contêm dados da tabela
                if (line.matches("\\d{5}.*")) {  // Verifica se começa com código numérico
                    String[] columns = line.split("\\s{2,}"); // Separar colunas por espaços excessivos
                    writer.writeNext(columns);
                }
            }
        }
    }
}
