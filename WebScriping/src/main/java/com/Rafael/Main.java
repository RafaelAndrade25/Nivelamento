import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            // URL da página da ANS
            String urlPagina = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

            // Baixar a página HTML
            Document document = Jsoup.connect(urlPagina).get();

            // Pegar o link do PDF diretamente (já temos o link fixo)
            String pdfUrl = "https://www.gov.br/ans/pt-br/arquivos/assuntos/consumidor/o-que-seu-plano-deve-cobrir/nota13_geas_ggras_dipro_17012013.pdf";

            // Buscar o link do Excel na página
            Elements links = document.select("a[href$=.xls], a[href$=.xlsx]"); // Filtra links que terminam em .xls ou .xlsx
            String excelUrl = links.isEmpty() ? null : links.get(0).absUrl("href"); // Pega o primeiro link

            if (excelUrl == null) {
                System.out.println("Nenhum link de Excel encontrado.");
                return;
            }

            // Define caminho
            Path pdfCaminho = Paths.get("downloads/arquivo_original.pdf");
            Path excelCaminho = Paths.get("downloads/arquivo.xlsx");

            // Cria pasta download
            Files.createDirectories(pdfCaminho.getParent());

            // Baixa os arquivos
            baixarArquivo(pdfUrl, pdfCaminho.toString());
            baixarArquivo(excelUrl, excelCaminho.toString());


            String zipFilePath = "downloads/arquivos.zip";
            criarArquivoZip(zipFilePath, pdfCaminho, excelCaminho);

            System.out.println("Download e compactação concluídos! Arquivo ZIP salvo em: " + zipFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para baixar um arquivo
    public static void baixarArquivo(String fileURL, String savePath) throws IOException {
        System.out.println("Baixando: " + fileURL);

        try (InputStream in = new URL(fileURL).openStream();
             FileOutputStream out = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("Arquivo salvo em: " + savePath);
    }

    // Método para criar um arquivo ZIP com os arquivos baixados
    public static void criarArquivoZip(String zipFilePath, Path... arquivos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (Path arquivo : arquivos) {
                if (Files.exists(arquivo)) {
                    try (FileInputStream fis = new FileInputStream(arquivo.toFile())) {
                        ZipEntry zipEntry = new ZipEntry(arquivo.getFileName().toString());
                        zos.putNextEntry(zipEntry);

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            zos.write(buffer, 0, bytesRead);
                        }
                    }
                    System.out.println("Adicionado ao ZIP: " + arquivo.getFileName());
                } else {
                    System.out.println("Arquivo não encontrado: " + arquivo);
                }
            }
        }
        System.out.println("Arquivo ZIP criado com sucesso!");
    }
}
