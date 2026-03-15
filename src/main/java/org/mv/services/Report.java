package org.mv.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.mv.DTO.DepartamentoDTO;
import org.mv.departamento.DepartamentoRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ApplicationScoped
public class Report {

    @Inject
    DepartamentoRepository dr;

    public byte[] generateReport() {
        try {
            // Cargar el reporte compilado (.jasper)
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream("Blank_A4.jasper");
            if (reportStream == null) {
                throw new RuntimeException("Reporte no encontrado.");
            }

            // Datos para el reporte (pueden venir de una base de datos, servicio, etc.)
            List<DepartamentoDTO> data = dr.list("order by id").stream()
                    .map(DepartamentoDTO::new)
                    .toList();


            // Crear el datasource
            //JRDataSource ds = new JRBeanCollectionDataSource(data);

            JRBeanArrayDataSource ds=new JRBeanArrayDataSource(data.toArray());


            // Parámetros del reporte (si es necesario)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ds", ds);

            // Llenar el reporte
            //JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, ds);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, ds);

            // Exportar a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException("Error al generar el reporte", e);
        }
    }
}