package com.br.tattoo.api.upload;

import org.springframework.http.MediaType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/upload")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileStorageProperties fileStorageProperties;
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
  
    
   @RequestMapping("/{file:.+}")
    public ResponseEntity<Resource> doGet(@PathVariable String file ) {
         
        /*Obtem o caminho relatorio da pasta img*/
       //String path = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize().toString();
 
      // File files = new File(path);
        //response.setContentType("image/jpeg");
         
        /*Mostra o arquivo que está na pasta img onde foi realizado o upload*/
        //for (String file : files.list()) {
        	Resource resource = fileStorageService.loadFileAsResource(file);
        	

    		 return ResponseEntity.ok()
    	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    	            .body(resource);
//            File f = new File(path+"/" + file);
//            BufferedImage bi = ImageIO.read(f);
//            OutputStream out = response.getOutputStream();
//            ImageIO.write(bi, "jpg", out);
//            out.close();
            
       // }
		//return null;
    }
    
    @GetMapping("/getallfiles")
	public List<String> getListFiles() {
    	List<String> lstFiles = new ArrayList<String>();
    	String path = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize().toString();
    	File filess = new File(path);
    	String[] files = filess.list();
		try{
			lstFiles = Arrays.asList(files)
	                .stream()
	                .map(fileName -> MvcUriComponentsBuilder
							.fromMethodName(FileController.class, "doGet", fileName).build().toString())
	                .collect(Collectors.toList());
				
		}catch(Exception e){
			throw e;
		}
		
		return lstFiles;
	}
}
