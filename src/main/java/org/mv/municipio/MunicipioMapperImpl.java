package org.mv.municipio;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.mv.departamento.Departamento;
import org.mv.departamento.DepartamentoRepository;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

@RequestScoped
public class MunicipioMapperImpl implements MunicipioMapper {


    DepartamentoRepository depa;

    AgroalDataSource datasource;

    @Inject
    public  MunicipioMapperImpl(DepartamentoRepository depa, AgroalDataSource datasource) {
        this.depa = depa;
        this.datasource = datasource;
    }

    @Override
    public Municipio createMunicipio(CreateMunicipioDTO dto) {
        Municipio municipio=new Municipio();
        Departamento departamento=depa.findById(dto.idDepartamento());
        municipio.setIdDepartam(departamento);
        municipio.setNombre(dto.nombre());
        municipio.setCodigo(dto.codigo());
        return municipio;
    }

    @Override
    public Response generarReportes(String format, boolean download) {
        try {
            // hacer el datasource y cargarlo con la info del reporte
            InputStream jasperStream = getClass().getClassLoader().getResourceAsStream("muni.jasper");
            if (jasperStream == null) {
                throw new RuntimeException("Reporte no encontrado.");
            }

            // se genera el archivo que se va imprimir
            Connection conn = datasource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperStream,
                    new HashMap<>(),
                    conn);
            conn.close();

            // convirtiendo reporte a word, xlsx o pdf
            byte[] reporte;
            String contentType;
            String fileExtension;

            switch (format.toLowerCase()) {
                case "docx":
                    reporte = exportToDocx(jasperPrint);
                    contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                    fileExtension = "docx";
                    break;

                case "xlsx":
                    reporte = exportToXlsx(jasperPrint);
                    contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    fileExtension = "xlsx";
                    break;

                case "pdf":
                default:
                    reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                    contentType = "application/pdf";
                    fileExtension = "pdf";
            }

            //devovler la respuesta
            Response.ResponseBuilder response = Response.ok(reporte)
                    .type(contentType);

            if (download) {
                response.header("Content-Disposition",
                        "attachment; filename=reporte_municipios." + fileExtension);
            }

            return response.build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar el reporte: " + e.getMessage())
                    .build();
        }
    }

    private byte[] exportToDocx(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRDocxExporter exporter = new JRDocxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
        exporter.setConfiguration(configuration);

        exporter.exportReport();
        return outputStream.toByteArray();
    }

    private byte[] exportToXlsx(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return outputStream.toByteArray();
    }
}
