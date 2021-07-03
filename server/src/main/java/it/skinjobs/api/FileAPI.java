package it.skinjobs.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.skinjobs.dto.UploadFileResponse;
import it.skinjobs.utils.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class FileAPI {

   private static final Logger logger = LoggerFactory.getLogger(FileAPI.class);

   @Autowired
   private FileStorageService fileStorageService;

   @Autowired
   protected CredentialAPI credentialAPI;

   @CrossOrigin(origins = "*")
   @PostMapping("/uploadFile")
   public ResponseEntity<UploadFileResponse> uploadFile(@RequestHeader Map<String, String> headers,
         @RequestParam("file") MultipartFile file) {
      String token = headers.get("token");
      if (credentialAPI.sessionIsValid(token)) {
         String fileName = fileStorageService.storeFile(file);
         String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
               .path(fileName).toUriString();
         return new ResponseEntity<>(
               new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize()), HttpStatus.OK);
      } else {
         return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }
   }

   @CrossOrigin(origins = "*")
   @GetMapping("/downloadFile/{fileName:.+}")
   public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
      Resource resource = fileStorageService.loadFileAsResource(fileName);
      String contentType = null;
      try {
         contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
      } catch (IOException ex) {
         logger.info("Could not determine file type.");
      }
      if (contentType == null) {
         contentType = "application/octet-stream";
      }
      return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
   }
}
