package cn.barrywangmeng.designpattern.factory.factory.method;

public class ExportPdfFactory implements ExportFactory {
    @Override
    public ExportFile factory(String type) {
        if ("standard".equalsIgnoreCase(type)) {
            return new ExportStandarPdfFile();
        } else if ("financial".equalsIgnoreCase(type)) {
            return new ExportFinancianPdfFile();
        }

        return null;
    }
}
