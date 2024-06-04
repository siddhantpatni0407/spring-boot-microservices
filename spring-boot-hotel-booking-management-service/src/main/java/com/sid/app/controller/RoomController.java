package com.sid.app.controller;


import com.sid.app.constants.AppConstants;
import com.sid.app.dto.Response;
import com.sid.app.service.interfac.IBookingService;
import com.sid.app.service.interfac.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IBookingService bookingService;

    @PostMapping(value = AppConstants.ADD_NEW_ROOM_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewRoom(@RequestParam(value = "photoUrl", required = false) String photoUrl,
                                               @RequestParam(value = "roomType", required = false) String roomType,
                                               @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription", required = false) String roomDescription) {

        if (photoUrl == null || photoUrl.isEmpty() || roomType == null || roomType.isBlank() || roomPrice == null) {
            Response response = new Response();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Please Provide values for all fields(photoUrl, roomType, roomPrice)");
        }
        Response response = roomService.addNewRoom(photoUrl, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(value = AppConstants.ALL_ROOMS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAllRooms() {
        Response response = roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping(value = AppConstants.GET_ROOM_TYPE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    @GetMapping(value = AppConstants.ROOM_BY_ID_ENDPOINT + "/" + "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getRoomByID(@PathVariable("roomId") Long roomId) {
        Response response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(value = AppConstants.ALL_AVAILABLE_ROOMS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAvailableRooms() {
        Response response = roomService.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(value = AppConstants.AVAILABLE_ROOMS_BY_DATE_TYPE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAvailableRoomsByDateAndType(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                                   @RequestParam(required = false) String roomType) {
        if (checkInDate == null || checkOutDate == null || roomType.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required(checkInDate,checkOutDate,roomType )");
        }

        Response response = roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping(value = AppConstants.UPDATE_ROOM_ENDPOINT + "/" + "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId,
                                               @RequestParam(value = "photoUrl", required = false) String photo,
                                               @RequestParam(value = "roomType", required = false) String roomType,
                                               @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription", required = false) String roomDescription) {
        Response response = roomService.updateRoom(roomId, roomDescription, roomType, roomPrice, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @DeleteMapping(value = AppConstants.DELETE_ROOM_ENDPOINT + "/" + "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId) {
        Response response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}