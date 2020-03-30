package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.model.servidor.PatchConfig;
import br.com.rfreforged.ReforgedGCP.model.servidor.PatchFile;
import br.com.rfreforged.ReforgedGCP.service.PatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/patch")
public class PatchController {

    @Autowired
    private PatchService patchService;

    @GetMapping("/list")
    public List<PatchFile> getPatchFilesHashes() {
        return patchService.getHashes().getList();
    }

    @PostMapping(value = "/files",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFiles(@RequestBody List<String> necessarios) throws IOException {

        ByteArrayOutputStream files = patchService.getFiles(necessarios);
        ByteArrayResource resource = new ByteArrayResource(files.toByteArray());
        files.close();
        return ResponseEntity.ok()
                .contentLength(files.size())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
