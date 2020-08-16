package okon.ECIP;

import java.util.List;

public class ReportManager {
    public List<Report> getReports() {
        HttpGateway server = new HttpGateway();
        ContentAnalyzer analyzer= new ContentAnalyzer();
        RecognitionService service = new RecognitionService(server, analyzer);
        return service.recognizeQueueSizes();
    }

    /*public static List<Report> getMessages(Job job) {
        List<Report> result = new ArrayList<>();
        try (GatewaySybase db = GatewayFactory.make(job)) {
            PerformanceService service = PerformanceServiceFactory.make(job, db);
            result = service.reportProcessorPerformance(job.getTime(), job.getServer());
        } catch (ConnectionException e) {
            Report report = new ExceptionReport();
            report.setAlias(job.getServer().getAlias());
            report.setServerIP(job.getServer().getIp());
            result.add(report);
        } catch (Exception e) {
            throw new AppException(e);
        }
        return result;
    }*/
}
