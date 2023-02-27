package realtalk.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploadUtil {

    @Value("${upload.path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file){
        Path destinationFile = Path.of(uploadPath).resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename;
        resultFilename = uuidFile + "." +file.getOriginalFilename();
        //На случай если будет проблема с кириллицей
//        if(isCyrillic(file.getOriginalFilename())){
//            Transliterator transliterator = Transliterator.getInstance(CYRILLIC_TO_LATIN);
//            resultFilename = uuidFile + "." + transliterator.transliterate(file.getOriginalFilename());
//        }

        File newFile = new File(String.valueOf(destinationFile));
        newFile.renameTo(new File(uploadPath + "/" + resultFilename));

        return resultFilename;
    }
}
