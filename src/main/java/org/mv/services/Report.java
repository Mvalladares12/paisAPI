package org.mv.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.mv.departamento.DepartamentoDTO;
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
            // aquí se carga el archivo compilado es decir el .jasper
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream("Blank_A4.jasper");
            if (reportStream == null) {
                throw new RuntimeException("Reporte no encontrado.");
            }

            // Datos para el reporte se obtienen del repository
            List<DepartamentoDTO> data = dr.list("order by id").stream()
                    .map(DepartamentoDTO::new)
                    .toList();


            // Crear el datasource y los convierte en array luego mostrar los datos
            JRBeanArrayDataSource ds=new JRBeanArrayDataSource(data.toArray());


            // enviar el parametro ds ya que en el reporte se espera un parametro llamado ds
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ds", ds);

            // Llenar el reporte con los datos del datasource
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, ds);

            // Exportar a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException("Error al generar el reporte", e);
        }
    }
}
