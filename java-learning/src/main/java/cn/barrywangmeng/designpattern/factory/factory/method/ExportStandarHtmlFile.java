package cn.barrywangmeng.designpattern.factory.factory.method;

public class ExportStandarHtmlFile implements ExportFile {
    @Override
    public boolean export(String data) {
        System.out.println("导出标准的html文件 " + data);
        return false;
    }
}
