package cn.barrywangmeng.designmodel.factory.factory.method;

public class ExportHtmlFactory implements ExportFactory {
    @Override
    public ExportFile factory(String type) {
        if ("standard".equalsIgnoreCase(type)) {
            return new ExportStandarHtmlFile();
        } else if ("financial".equalsIgnoreCase(type)) {
            return new ExportFinancialHtmlFile();
        }

        return null;
    }
}
