package com.demoapplication.demo;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface StationMapper{

    @Select("SELECT * FROM Station WHERE stationId = #{stationId}")
    Station findByStationId(String stationId);

    @Select("SELECT * FROM Station WHERE hdEnabled = #{hdEnabled}")
    List<Station> findByStationHdEnabled(boolean hdEnabled);

    @Delete("DELETE FROM Station WHERE stationId = #{stationId}")
    public int deleteByStationId(String StationID);
    @Insert("INSERT INTO Station(stationId, name, hdEnabled, callSign) VALUES (#{stationId}, #{name}, #{hdEnabled}, #{callSign})")
    public int insert(Station station);
    @Update("UPDATE Station set name = #{name}, hdEnabled = #{hdEnabled}, callSign = #{callSign} where stationId = #{stationId}")
    public int update(Station station);




}

