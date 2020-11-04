package subway;

import java.util.*;

public class Station {
    private String stationName;     //站点名称
    private Set<Line>lineName;      //所属地铁线
    private Set<Station>linkStations; //相连的地铁站

    //获得站点名称
    public String getStationName() {

        return stationName;
    }

    //获得站点相连的站点
    public Set<Station> getlinkStations() {

        return linkStations;
    }

    //为站点添加所在地铁线
    public void addLineName(Line lineName) {

        this.lineName.add(lineName);
    }

    //为当前站点添加相邻站点
    public void addlinkStations(Station linkStations) {

        this.linkStations.add(linkStations);
    }

    //添加一个站点包括它的名称，附近相连站点，所在线路站点
    public Station(String stationName) {
        this.stationName=stationName;
        linkStations= new HashSet<>();
        lineName= new HashSet<>();
    }
}

