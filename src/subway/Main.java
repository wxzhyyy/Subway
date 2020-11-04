package subway;


public class Main {
    //主函数
    public static void main(String[] args) throws Exception {
        //-map 地图读入
        if(args[0].equals("-map")){
            readfile(args[1]);
            System.out.println("地图读取成功！");
        }
        // -a 地铁线站点输出
        else if (args[0].equals("-a")){
            readfile(args[3]);
            Subway.check(args[1]);
            //读取
            Subway.outstation(args[1],args[5]);
            System.out.println("车站信息输出成功！");
        }
        // -b 最短路径查询
        else if(args[0].equals("-b")){
            readfile(args[4]);
            Subway.makeTb();
            //读取输出文件，起点和终点
            Subway.outroutine(args[6],args[1],args[2]);
            System.out.println("路线信息输出成功！");
        }
        else{
            System.out.println("请输入指定命令进行相应操作！");
        }
    }
    //地图文件读取
    public static void readfile(String filename) throws Exception {
        Subway.readin(filename);
        Subway.statisticsStation();
        Subway.getHashMap();
        Subway.setMap();
        Subway.readMetroline(filename);
    }

}
