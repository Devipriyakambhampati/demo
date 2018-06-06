package com.demoapplication.demo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/Station")
public class StationController extends AbstractRestHandler {
    @Autowired
    private StationMapper stationMapper;


    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a Station resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createStation(@RequestBody Station station,
                              HttpServletRequest request, HttpServletResponse response) {
         this.stationMapper.insert(station);
        response.setHeader("Success",String.valueOf(HttpStatus.CREATED));
    }


    @RequestMapping(value = "/{stationId}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a Station resource.", notes = "You have to provide a valid Station ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateStation(@ApiParam(value = "The ID of the existing Station resource.", required = true)
                              @PathVariable("stationId") String stationId, @RequestBody Station station,
                              HttpServletRequest request, HttpServletResponse response) throws DataFormatException {
        final Station station1 = checkResourceFound(this.stationMapper.findByStationId(stationId));
        if (!stationId.equalsIgnoreCase(station.getStationId())) throw new DataFormatException("ID doesn't match!");
       this.stationMapper.update(station);
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single Station.", notes = "You have to provide a valid Station ID.")
    public
    @ResponseBody
    Station getStation(@ApiParam(value = "The ID of the Station.", required = true)
                       @PathVariable("id") String id,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        Station station = this.stationMapper.findByStationId(id);
        checkResourceFound(station);

        return station;
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a Station resource.", notes = "You have to provide a valid Station ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteStation(@ApiParam(value = "The ID of the existing Station resource.", required = true)
                              @PathVariable("id") String id, HttpServletRequest request,
                              HttpServletResponse response) {
        checkResourceFound(this.stationMapper.findByStationId(id));
        this.stationMapper.deleteByStationId(id);
    }


    @RequestMapping(value = "/HdEnabled/{hdEnabled}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a List of Station.", notes = "You have to provide a TRUE/ False.")
    public
    @ResponseBody
    List<Station> getStationsByHdEnabled(@ApiParam(value = "The ID of the Station.", required = true)
                                         @PathVariable("hdEnabled") boolean hdEnabled,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Station> station = this.stationMapper.findByStationHdEnabled(hdEnabled);
        checkResourceFound(station);
        return station;
    }


}
