package cn.barrywangmeng.designpattern.factory.factory.method;

public class ExportFinancialHtmlFile implements ExportFile {
    @Override
    public boolean export(String data) {
        System.out.println("导出财务版HTML文件 " + data);
        return false;
    }
}
