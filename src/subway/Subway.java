package subway;

import java.io.*;
import java.util.*;


import java.util.Map;


public class Subway {
	//定义存储地铁线路的hashmap
    public static Map<Integer, Station> subway_information;
    public static Map<String, Integer> Station_num;
    public static Set<String> set;
    public static Set<List<String>> listSet;
    
    //
    public static void readin(String filename) throws Exception {
    	subway_information = new HashMap<Integer, Station>();
    	Station_num = new HashMap<String, Integer>();
        set = new HashSet<String>();
        lines = new HashMap<String,Line>();
        listSet=readMetroline(filename);

    }
    
    public static void getHashMap() {
        int i = 0;
        for (String s : set) {
            Station station = new Station(s);
            subway_information.put(i, station);
            Station_num.put(s, i);
            i++;
        }
    }
    
    public static void setMap() {
        for (List<String> lineList : listSet) {
            Line line = new Line();
            for (int i = 1; i < lineList.size(); i++) {
                Station station = subway_information.get(Station_num.get(lineList.get(i)));
                station.addLineName(line);
                if (i != 1) {
                    Station laStation = subway_information.get(Station_num.get(lineList.get(i - 1)));
                    station.addlinkStations(laStation);
                    laStation.addlinkStations(station);
                }
                line.addStation(station);
            }
            lines.put(lineList.get(0), line);
        }
    }
    
    //################################LINE#####################################//
    public static java.util.Map<String, Line> lines;

    public static List<String> check(String linename){
        Line line=lines.get(linename);
        List<Station>stations=line.getStations();
        List<String>strings=new ArrayList<String>();
        for(Station station:stations){
            strings.add(station.getStationName());
        }
        return strings;
    }
    
    //读入文件
    public static Set<List<String>> readMetroline(String filename) throws Exception {
        FileInputStream inputStream = new FileInputStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Set<List<String>>set=new HashSet<List<String>>();
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {

            String[] lineInformations = str.split(" ");
            List<String>stations=new ArrayList<String>();
            for (String s : lineInformations) {
                stations.add(s);

            }
            set.add(stations);
        }
       
        inputStream.close();
        bufferedReader.close();
        return set;
    }
    public static void statisticsStation() {
        for (List<String> lineList : listSet)
            for (int i = 1; i < lineList.size(); i++)
                set.add(lineList.get(i));
    }
    
    //######################ROUTE######################################################//
    public static int len;
    public static int[][] path;
    public static int[][] dist;
    public static int[][] table;
    public static int INF = Integer.MAX_VALUE;   //设置INF无限大
    public static List<Integer> result = new ArrayList<Integer>();
    public static void findFloydRoad(int[][] table) {
        int size = len;
        path = new int[size][size];
        dist = new int[size][size];
        //initialize dist and path
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = -1;
                dist[i][j] = table[i][j];
            }
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF &&
                            dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }

    }
    public static void makeTb(){
        len = subway_information.size();
        table =new int[len][len];
        for(int i=0;i<len;i++){
            for(int  j=0;j<len;j++){
                table[i][j]=INF;
            }
        }
        for(int i=0;i<len;i++){
            Station station=subway_information.get(i);
            for(Station s:station.getlinkStations()){
                int j=Station_num.get(s.getStationName());
                table[i][j]=1;
                table[j][i]=1;
            }
        }
    }
    public static void findCheapestPath(int begin, int end, int[][] table) {
        findFloydRoad(table);
        result.add(begin);
        findPath(begin, end);
        result.add(end);
    }

    public  static void findPath(int i, int j) {
        int k = path[i][j];
        if (k == -1)        
            return ;
        else {
            findPath(i, k);
            result.add(k);
            findPath(k, j);
        }
    }
    //###########################OUTFILE#####################################//
    public static void outstation(String linename,String fileName) throws Exception {
        if(fileName.equals("")||fileName.equals(" ")){
            System.out.println("输出文件名不能为空！");
        }
        else if(linename.equals("")||linename.equals(" ")){
            System.out.println("查询线路名不能为空");
        }
        else {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            out.write(linename+"所经过的站点有");
            out.newLine(); //换行
            for (Station s : lines.get(linename).getStations()) {
                out.write(s.getStationName());
                out.newLine();
            }
            out.flush();
            out.close();
        }


    }

    public static void outroutine(String fileName,String a,String b) throws Exception {
        if(fileName.equals("")||fileName.equals(" ")){
            System.out.println("输出文件名不能为空！");
        }
        else {
            int x =Station_num.get(a);
            int y =Station_num.get(b);
            findCheapestPath(x, y, table);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            out.write(" 从 " + subway_information.get(x).getStationName() + " 到 " + subway_information.get(y).getStationName() + " 一共历经" + dist[x][y] + "站" + " 的最佳路径是 ");
            out.newLine();  //换行
            for (int r : result) {
                if (!subway_information.get(r).getStationName().equals(subway_information.get(x).getStationName())) {
                    out.write("  |");
                    out.newLine();
                }
                out.write(subway_information.get(r).getStationName());
                out.newLine();
            }
            out.flush();
            out.close();
        }
    }
}
