package cn.barrywangmeng.designModel.factory.factoryMethod;

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
