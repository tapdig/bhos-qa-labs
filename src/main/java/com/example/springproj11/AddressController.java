package com.example.springproj11;

import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/updatebyid")
    public ResponseEntity<Address> updateAddressByID(@RequestParam UUID id, @RequestParam String address_line) {
        try {
            Address address = addressRepository.findById(id);
            address.setAddressLine(address_line);
            Address savedAddress = addressRepository.save(address);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/updatebyname")
    public ResponseEntity<Address> updateAddressByName(@RequestParam String name, @RequestParam String address_line) {
        try {
            Address address = addressRepository.findByName(name);
            address.setAddressLine(address_line);
            Address savedAddress = addressRepository.save(address);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/mock100k")
    public String mockData() throws IOException {
        MockNeat m = MockNeat.threadLocal();

        List<String> generator = m.fmt("#{name},#{addressline},#{lat},#{lon}")
                .param("name", m.names())
                .param("addressline", m.addresses())
                .param("lat", m.doubles())
                .param("lon", m.doubles())
                .list(100000)
                .val();

        BufferedWriter writer = new BufferedWriter(new FileWriter("mock_data_100k.csv"));
        writer.write("name,address_line,lat,lon" + "\n");

        for (String row : generator) {
            writer.write(row + "\n");
        }
        writer.close();

        return "Generated Succesfully";
    }

    @PostMapping("/add_address")
    public ResponseEntity<Address> createAddress(@RequestParam String name, @RequestParam String address_line, @RequestParam Double lat, @RequestParam Double lon) {
        try {
            Address address = new Address(name, address_line, lat, lon);
            Address savedAddress = addressRepository.save(address);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}