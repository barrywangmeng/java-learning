package cn.barrywangmeng.designpattern.factory.factory.method;

public class ExportFinancianPdfFile implements ExportFile {
    @Override
    public boolean export(String data) {
        System.out.println("导出财务版PDF文件 "  + data);
        return false;
    }
}
