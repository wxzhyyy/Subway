package subway;

import java.util.*;

public class Line {
    private List<Station>stations;   //所经过站点
    //返回地铁站当前的站点
    public List<Station> getStations() {
        return stations;
    }

    //为地铁线路添加站点
    public void addStation(Station station) {
        this.stations.add(station);
    }

    //添加地铁线和其经过的站点
    public Line() {
        stations= new ArrayList<>();
    }
}

