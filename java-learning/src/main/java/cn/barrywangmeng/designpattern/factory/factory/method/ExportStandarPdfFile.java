package cn.barrywangmeng.designpattern.factory.factory.method;

public class ExportStandarPdfFile implements ExportFile {
    @Override
    public boolean export(String data) {
        System.out.println("导出标准的PDF文件 " + data);
        return false;
    }
}
