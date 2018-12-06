package com.kaveesha.webApplication.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestApiController {


    @RequestMapping("/greeting")
    public String greeting(){
        return "greeting";
    }


    @RequestMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable String id) throws IOException {

//        String[] command = {"/home/kaveeshab/Dev/Kavee Dev/Scripts/script.sh", id, id};
//        ProcessBuilder p = new ProcessBuilder(command);

        String resp = "";
        ProcessBuilder builder = new ProcessBuilder("/home/kaveeshab/Dev/Kavee Dev/Scripts/script.sh", id, id);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            resp = resp + System.lineSeparator() + line ;
            System.out.println(line);

            //resp = "Responce is " + line;

        }


        Map<String, String> stringStringMap = new HashMap<>();

            stringStringMap.put("status", "success");
            stringStringMap.put("message", resp);
            stringStringMap.put("code", "200");
            return ResponseEntity.status(HttpStatus.OK).
                    body(stringStringMap);


//            stringStringMap.put("status", "failed");
//            stringStringMap.put("message", "not  deleted");
//            stringStringMap.put("code", "200");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).
//                    body(stringStringMap);



    }


    @PostMapping("/getCSV") // //new annotation since 4.3
    @ResponseBody
    public ResponseEntity<Object> singleFileUpload(@RequestParam("file") MultipartFile file) {
         String resp = "";
         String command = "123";
         String UPLOADED_FOLDER = "/home/kaveeshab/Dev/Kavee Dev/Scripts";
        if (file.isEmpty()) {
            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("status", "failed");
            stringStringMap.put("message", "Please select a file and upload");
            stringStringMap.put("code", "200");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(stringStringMap);

        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            System.out.println("File name is :- "+ file.getOriginalFilename() );
            Files.write(path, bytes);

           // ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"D:\\temp\"  && tesseract "+file.getOriginalFilename()+" stdout -l eng");
            ProcessBuilder builder = new ProcessBuilder("/home/kaveeshab/Dev/Kavee Dev/Scripts/script.sh", command, command);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                resp = resp + System.lineSeparator() + line ;
                System.out.println(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("status", "failed");
            stringStringMap.put("message", "not  deleted");
            stringStringMap.put("code", "200");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(stringStringMap);
        }

            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("status", "success");
            stringStringMap.put("message", resp);
            stringStringMap.put("code", "200");
            return ResponseEntity.status(HttpStatus.OK).
                    body(stringStringMap);
    }
}

