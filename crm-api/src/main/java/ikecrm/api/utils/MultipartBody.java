package ikecrm.api.utils;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class MultipartBody {
    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    InputStream file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    String fileName;

    public InputStream getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }
}
