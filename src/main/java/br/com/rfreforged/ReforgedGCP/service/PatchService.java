package br.com.rfreforged.ReforgedGCP.service;

import br.com.rfreforged.ReforgedGCP.model.servidor.PatchConfig;
import br.com.rfreforged.ReforgedGCP.model.servidor.PatchFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class PatchService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public PatchConfig getHashes() {
        File patchConf = Paths.get(System.getProperty("user.dir") + "\\patch\\patch_config.json").toFile();
        PatchConfig config = objectMapper.readValue(patchConf, PatchConfig.class);
        boolean needHash = config.isNeedHash();
        if (needHash) {
            config.setList(new ArrayList<>());
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            Path start = Paths.get(patchConf.getAbsolutePath().split("patch_config.json")[0] + "files");
            try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
                stream.forEach(p -> {
                    if (!p.getFileName().toString().equals("files")) {
                        if (p.getFileName().toString().contains(".")) {
                            try {
                                String relative = p.toAbsolutePath().toString().split("files")[1];
                                byte[] inputBytes = Files.readAllBytes(p);
                                messageDigest.update(inputBytes);
                                byte[] hashedBytes = messageDigest.digest();
                                config.getList().add(new PatchFile(relative, convertByteArrayToHexString(hashedBytes)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            config.setNeedHash(false);
            Files.write(patchConf.toPath(), objectMapper.writeValueAsBytes(config));
        }
        return config;
    }

    public ByteArrayOutputStream getFiles(List<String> necessarios) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {

            for (String relative : necessarios) {
                File fileToZip = new File(System.getProperty("user.dir") + "\\patch\\files" + relative);

                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(relative);
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[4096];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
            return baos;
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (byte arrayByte : arrayBytes) {
            stringBuffer.append(Integer.toString((arrayByte & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
