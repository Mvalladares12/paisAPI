package org.mv.departamento;

import io.agroal.api.AgroalDataSource;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mv.services.Report;
import java.util.List;
import java.util.stream.Collectors;

@Path("/departamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartamentoResource {


    DepartamentoRepository departamentoRepository;

    DepartamentoMapper departamentoMapper;

    Report report;

    AgroalDataSource datasource;

    @Inject
    public DepartamentoResource(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper, Report report, AgroalDataSource datasource) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
        this.report = report;
        this.datasource = datasource;
    }

    @GET
    public List<DepartamentoDTO> getAllDepartamentos() {
        return departamentoRepository.list("order by id").stream()
                .map(DepartamentoDTO::new)
                .collect(Collectors.toList());
    }

//    @GET
//    @Path("/pdf")
//    @Produces("application/pdf")
//    public Response getDepartamentosPdf() {
//        byte[] repo= report.generateReportPDF();
//        return Response.ok(repo).header("Content-Disposition", "attachment; filename=reporte.pdf").build();
//    }

    /*@GET
    @Path("/report/{tipo}")
    @Produces("application/pdf")
    public Response getDepartamentosPdf(/*@PathParam("tipo") String tipo*) {
        byte[] repo= report.generateReportPDF();
        return Response.ok(repo).header("Content-Disposition", "attachment; filename=reporte.pdf").build();
    }*/


    @GET
    @Path("{id}")
    public Departamento getDep(@PathParam("id") Long id) {
        return departamentoRepository.findById(id);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateDepartamentoDTO departamento) {
        var entity=departamentoMapper.createDepartamento(departamento);
        departamentoRepository.persist(entity);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
        departamentoRepository.deleteById(id);
    }


    @PUT
    @Path("{id}")
    @Transactional
    public CreateDepartamentoDTO update(@PathParam("id")Long id, CreateDepartamentoDTO depa) {
        return departamentoRepository.update(id,depa);
    }


    /*@GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporteDepartamentos() {
        try {
            // 1. Cargar el diseño del reporte (.jasper)
            InputStream jasperStream = this.getClass()
                    .getResourceAsStream("muni.jasper");

            InputStream jasperStream = getClass().getClassLoader().getResourceAsStream("muni.jasper");
            if (jasperStream == null) {
                throw new RuntimeException("Reporte no encontrado.");
            }

            // 2. Parámetros del reporte (si es necesario)
            Map<String, Object> parametros = new HashMap<>();

            // 3. Obtener conexión a la base de datos
            Connection conexion = datasource.getConnection();

            // 4. Generar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperStream,
                    parametros,
                    conexion);

            // 5. Exportar a PDF
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);

            // 6. Cerrar conexión
            conexion.close();

            // 7. Devolver el archivo
            return Response.ok(reporte)
                    .header("Content-Disposition",
                            "attachment; filename=reporte_departamentos.pdf")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar el reporte: " + e.getMessage())
                    .build();
        }
    }*/


    @GET
    @Path("/report/{format}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {

            return departamentoMapper.generarReportes(format, download);
//        try {
//            // 1. Cargar el diseño del reporte
//            /*InputStream jasperStream = Thread.currentThread()
//                    .getContextClassLoader()
//                    .getResourceAsStream("reportes/departamentos.jasper");*/
//
//            InputStream jasperStream = getClass().getClassLoader().getResourceAsStream("Blank_A4.jasper");
//            if (jasperStream == null) {
//                throw new RuntimeException("Reporte no encontrado.");
//            }
//
//            if (jasperStream == null) {
//                return Response.status(Response.Status.NOT_FOUND)
//                        .entity("Archivo de reporte no encontrado")
//                        .build();
//            }
//
//            // 2. Generar el JasperPrint
//            Connection conn = datasource.getConnection();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(
//                    jasperStream,
//                    new HashMap<>(),
//                    conn);
//            conn.close();
//
//            // 3. Exportar al formato solicitado
//            byte[] reporte;
//            String contentType;
//            String fileExtension;
//
//            switch (format.toLowerCase()) {
//                case "docx":
//                    reporte = exportToDocx(jasperPrint);
//                    contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//                    fileExtension = "docx";
//                    break;
//
//                case "xlsx":
//                    reporte = exportToXlsx(jasperPrint);
//                    contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//                    fileExtension = "xlsx";
//                    break;
//
//                case "pdf":
//                default:
//                    reporte = JasperExportManager.exportReportToPdf(jasperPrint);
//                    contentType = "application/pdf";
//                    fileExtension = "pdf";
//            }
//
//            // 4. Preparar la respuesta
//            Response.ResponseBuilder response = Response.ok(reporte)
//                    .type(contentType);
//
//            if (download) {
//                response.header("Content-Disposition",
//                        "attachment; filename=reporte_departamentos." + fileExtension);
//            }
//
//            return response.build();
//
//        } catch (Exception e) {
//            return Response.serverError()
//                    .entity("Error al generar el reporte: " + e.getMessage())
//                    .build();
//        }
//    }
//
//    private byte[] exportToDocx(JasperPrint jasperPrint) throws JRException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JRDocxExporter exporter = new JRDocxExporter();
//
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
//
//        SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
//        exporter.setConfiguration(configuration);
//
//        exporter.exportReport();
//        return outputStream.toByteArray();
//    }
//
//    private byte[] exportToXlsx(JasperPrint jasperPrint) throws JRException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JRXlsxExporter exporter = new JRXlsxExporter();
//
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
//
//        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//        configuration.setOnePagePerSheet(false);
//        configuration.setRemoveEmptySpaceBetweenRows(true);
//        configuration.setDetectCellType(true);
//        configuration.setWhitePageBackground(false);
//
//        exporter.setConfiguration(configuration);
//        exporter.exportReport();
//        return outputStream.toByteArray();
//    }
    }
}
