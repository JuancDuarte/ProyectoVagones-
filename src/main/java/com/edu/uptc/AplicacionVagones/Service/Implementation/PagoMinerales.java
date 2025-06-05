package com.edu.uptc.AplicacionVagones.Service.Implementation;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.edu.uptc.AplicacionVagones.Entities.Material;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service("PagoMinerales")
public class PagoMinerales {
   @Async
    public void generarPagoPDF(Long id, double cargaActual, Date hora_entrada, Date hora_salida, List<Material> materiales, double valor) {
        try {
            TimeUnit.SECONDS.sleep(5); // Simula el tiempo de generación
            String fileName = "Vagon-" + id + "pago de minerales.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

           /* 
            Image logo = Image.getInstance("src/main/resources/static/logoUptc.png");
            logo.scaleToFit(100, 100);
            document.add(logo);
            */

            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Pago del vagon "+ id + "por sus materiales" , fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            document.add(new Paragraph("Hora de entrada: " + dateFormat.format(hora_entrada)));
            document.add(new Paragraph("Hora de salida: " + dateFormat.format(hora_salida)));
            document.add(new Paragraph("Numero de identificacion del vagon: " + id));
            document.add(new Paragraph("Carga acutal del vagon: " + cargaActual+ "km"));
            document.add(new Paragraph("Materiales que contiene el vagon: "));

            materiales.forEach(material -> {
            try {
                document.add(new Paragraph("- " + material.getnombre_material() + ": " + material.getPeso() + " kg"+ ", tipo: "+ material.getTipo()));
                document.add(new Paragraph("Precio:" + material.getPeso()*1000));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            });
            document.add(new Paragraph("Precio total recolectado de la vagoneta " + id + ": "+ valor  ));
            document.close();

            System.out.println("PDF del pago de minerales del vagon generado: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
