package cn.barrywangmeng.designModel.factory.factoryMethod;

public class ExportFinancianPdfFile implements ExportFile {
    @Override
    public boolean export(String data) {
        System.out.println("导出财务版PDF文件 "  + data);
        return false;
    }
}
